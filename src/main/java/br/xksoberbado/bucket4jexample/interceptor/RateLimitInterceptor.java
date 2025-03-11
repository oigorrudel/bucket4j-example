package br.xksoberbado.bucket4jexample.interceptor;

import io.github.bucket4j.Bandwidth;
import io.github.bucket4j.Bucket;
import io.github.bucket4j.BucketConfiguration;
import io.github.bucket4j.Refill;
import io.github.bucket4j.distributed.proxy.ProxyManager;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.time.Duration;
import java.util.function.Supplier;

@Slf4j
@Component
@RequiredArgsConstructor
public class RateLimitInterceptor implements HandlerInterceptor {

    private static final String HEADER = "X-Request-ID";

    private final ProxyManager proxyManager;

    @Override
    public boolean preHandle(final HttpServletRequest request, final HttpServletResponse response, final Object handler) throws Exception {
        final var header = request.getHeader(HEADER);
        final var bucket = resolveBucket(header);

        if (bucket.tryConsume(1)) {
            log.info("Bucket. [header: {}, availableTokens: {}]", header, bucket.getAvailableTokens());

            return true;
        }

        response.sendError(HttpStatus.TOO_MANY_REQUESTS.value());

        return false;
    }

    public Bucket resolveBucket(final String key) {
        return proxyManager.builder().build(key, getConfigSupplier());
    }

    private Supplier<BucketConfiguration> getConfigSupplier() {
        return () -> BucketConfiguration.builder()
            .addLimit(Bandwidth.classic(3, Refill.intervally(3, Duration.ofSeconds(20))))
            .build();
    }
}
