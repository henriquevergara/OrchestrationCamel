package com.henrique.orchestrator.route;

import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.model.rest.RestBindingMode;
import org.springframework.stereotype.Component;

@Component
public class MainRoute extends RouteBuilder {


    @Override
    public void configure() throws Exception {
        restConfiguration()
                .component("servlet")
                .bindingMode(RestBindingMode.auto)
                .apiContextPath("/swagger") //swagger endpoint path
                .apiContextRouteId("swagger") //id of route providing the swagger endpoint

                //Swagger properties
                .contextPath("/camel") //base.path swagger property; use the mapping path set for CamelServlet
                .apiProperty("api.title", "Orchestration API")
                .apiProperty("api.description", "API de Orquestração de microserviços.")
                .apiProperty("api.version", "1.0")
                .apiProperty("api.contact.name", "Henrique Monteiro Vergara")
                .apiProperty("api.contact.email", "henriquevergara@hotmail.com")
                .apiProperty("schemes", "http");

    }
}
