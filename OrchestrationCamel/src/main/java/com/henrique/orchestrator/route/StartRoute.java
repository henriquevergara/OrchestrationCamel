package com.henrique.orchestrator.route;

import org.apache.camel.Exchange;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.model.dataformat.JsonLibrary;
import org.apache.camel.model.rest.RestBindingMode;
import org.springframework.stereotype.Component;

@Component
public class StartRoute extends RouteBuilder {

    @Override
    public void configure(){

        restConfiguration()
                .component("servlet")
                .bindingMode(RestBindingMode.auto);

        rest("/start")

                .get()
                .route()
                .setHeader(Exchange.HTTP_RESPONSE_CODE, constant(200))
                .setHeader(Exchange.CONTENT_TYPE,constant("text"))
                .endRest()

                .post()
                .route()
                .to("direct:http-onboard")
                .endRest();

        from("direct:http-onboard")
            .doTry()
                .marshal().json(JsonLibrary.Jackson)
                .setHeader(Exchange.HTTP_METHOD, constant("POST"))
                .setHeader(Exchange.CONTENT_TYPE, constant("application/json"))
                .setHeader("Accept", constant("application/json"))
                .to("http://localhost:8001/onboard?bridgeEndpoint=true")
                .setHeader(Exchange.HTTP_RESPONSE_CODE, constant(200))
                .transform().simple("Usuario enviado com sucesso!").stop()
            .doCatch(Exception.class)
                .setHeader(Exchange.HTTP_RESPONSE_CODE, constant(503))
                .setHeader(Exchange.CONTENT_TYPE,constant("text"))
                .setBody(simple(""))
            .end();

    }
}
