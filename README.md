# bucket4j-example
POC utilizando o **Bucket4j** que é uma biblioteca Java que pode ser usada para controlar rate limit.

Objetivo: controlar/gerenciar consumo de recursos/endpoints por uma determinada chave. 

OBS: esta POC adiciona o uso de Redis (Redisson) 
para que os buckets sejam cacheados/compartilhados entre as instâncias da aplicação.

## Execução/teste
Executar duas instâncias da aplicação em portas diferentes (Ex: 8080 e 8081), 
efetuar requisições nos endpoints com o Header ```X-Request-ID``` e observar os log's.