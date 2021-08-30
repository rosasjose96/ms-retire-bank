package com.example.msretire.config;

import com.example.msretire.handler.RetireHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.web.reactive.function.server.RequestPredicates.*;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;

/**
 * The type Router config.
 */
@Configuration
public class RouterConfig {
    /**
     * Rutas router function.
     *
     * @param handler the handler
     * @return the router function
     */
    @Bean
    public RouterFunction<ServerResponse> rutas(RetireHandler handler){
        return route(GET("/api/retire"), handler::findAll)
                .andRoute(GET("/api/retire/{id}"), handler::findDebit)
                .andRoute(DELETE("/api/retire/{id}"), handler::deleteDebit)
                .andRoute(PUT("/api/retire/{id}"), handler::updateDebit)
                .andRoute(POST("/api/retire"), handler::createRetire);
    }
}