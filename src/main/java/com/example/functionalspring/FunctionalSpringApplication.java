package com.example.functionalspring;

import org.springframework.http.server.reactive.HttpHandler;
import org.springframework.http.server.reactive.ReactorHttpHandlerAdapter;
import org.springframework.web.reactive.function.server.HandlerFunction;
import org.springframework.web.reactive.function.server.RequestPredicates;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;

import reactor.core.publisher.Mono;
import reactor.netty.http.server.HttpServer;

public class FunctionalSpringApplication {
	public static void main(String[] args) throws Exception {

		HandlerFunction helloHandler = (ServerRequest req) -> {
			String name = req.pathVariable("name");
			return ServerResponse.ok().bodyValue("Hello" + name);
		};

		RouterFunction helloRouter = (ServerRequest req) -> {
			boolean isValidPath = RequestPredicates.path("/hello/{name}").test(req);
			return isValidPath ? Mono.just(helloHandler) : Mono.empty();
		};

		HttpHandler httpHandler = RouterFunctions.toHttpHandler(helloRouter);

		ReactorHttpHandlerAdapter adapter = new ReactorHttpHandlerAdapter(httpHandler);

		HttpServer.create().host("localhost").port(8080).handle(adapter).bind().block();

		System.in.read();
	}

}

