package br.xksoberbado.bucket4jexample.config;

import br.xksoberbado.bucket4jexample.interceptor.RateLimitInterceptor;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@RequiredArgsConstructor
public class CustomMvcConfigurer implements WebMvcConfigurer {

    private final RateLimitInterceptor interceptor;

    @Override
    public void addInterceptors(final InterceptorRegistry registry) {
        registry.addInterceptor(interceptor);
    }
}
