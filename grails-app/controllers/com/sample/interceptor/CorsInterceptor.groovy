package com.sample.interceptor


class CorsInterceptor {

    CorsInterceptor() {
        match(uri: "/sample/api/**")
    }

    boolean before() {
        def origin = request.getHeader("Origin")
        header "Access-Control-Allow-Origin", (origin == null ?: "*")
        header "Access-Control-Allow-Credentials", "true"
        true
    }

    boolean after() {
        true
    }

    void afterView() {
        // no-op
    }
}
