package com.henrique.orchestrator.route;

import com.henrique.orchestrator.model.Usuario;
import org.apache.camel.Exchange;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.model.dataformat.JsonLibrary;
import org.apache.camel.model.rest.RestParamType;
import org.springframework.stereotype.Component;

@Component
public class StartRoute extends RouteBuilder {

    @Override
    public void configure(){

        rest("/start").description("Start orchestration")
                .consumes("application/json")
                .produces("application/json")

                .get()
                    .to("direct:teste-2")


                .post().description("Start o processo de cadastro de um novo cliente.").type(Usuario.class)
                    .param().name("body").type(RestParamType.body).description("Cliente").endParam()
                    .responseMessage()
                    .code(200).message("Usuario enviado com sucesso!.")
                    .endResponseMessage()
                    .responseMessage()
                    .code(503).message("Problema ao enviar a requisição.")
                    .endResponseMessage()
                .route()
                .to("direct:http-onboard")
                .end();

        from("direct:http-onboard")
            .doTry()
                .marshal().json(JsonLibrary.Jackson)
                .setHeader(Exchange.HTTP_METHOD, constant("POST"))
                .setHeader(Exchange.CONTENT_TYPE, constant("application/json"))
                .setHeader("Accept", constant("application/json"))
                .to("http://localhost:8001/onboard?bridgeEndpoint=true")
                .setHeader(Exchange.HTTP_RESPONSE_CODE, constant(200))
                .to("direct:email-service")
                .transform().simple("${body}").stop()
            .doCatch(Exception.class)
                .setHeader(Exchange.HTTP_RESPONSE_CODE, constant(503))
                .setHeader(Exchange.CONTENT_TYPE,constant("text"))
                .setBody(simple("Falha no UserOnboarding"));

        from("direct:email-service")
            .doTry()
                .setHeader(Exchange.HTTP_METHOD, constant("POST"))
                .setHeader(Exchange.CONTENT_TYPE, constant("application/json"))
                .setHeader("Accept", constant("application/json"))
                .to("http://localhost:8002/email/send?bridgeEndpoint=true")
                .setHeader(Exchange.HTTP_RESPONSE_CODE, constant(200))
                .setHeader(Exchange.CONTENT_TYPE,constant("text"))
                .setBody(simple("Usuário cadastrado e email enviado."))
            .doCatch(Exception.class)
                .setHeader(Exchange.HTTP_RESPONSE_CODE, constant(503))
                .setHeader(Exchange.CONTENT_TYPE,constant("text"))
                .setBody(simple("Falha no email Service"));
    }
}
