# licensing-service


## Build project
`mvn clean install`

## Run locally
`mvn spring-boot:run`

## Test manually
`curl localhost:8080/hello/maksym/stepanenko`

## Build docker image
`mvn clean package docker:build`


## Copied from
`https://github.com/carnellj/spmia-chapter1`

## Bugs discovered

1. Feign client fails on first call (https://github.com/Netflix/ribbon/issues/339)
2. Feign client with fallback failed if annotated with @RequestMapping (https://github.com/spring-cloud/spring-cloud-netflix/issues/2677)
