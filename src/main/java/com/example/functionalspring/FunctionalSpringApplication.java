package com.example.functionalspring;

import org.springframework.http.server.reactive.ReactorHttpHandlerAdapter;
import org.springframework.web.reactive.function.server.RequestPredicates;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;

import reactor.netty.http.server.HttpServer;

public class FunctionalSpringApplication {
	public static void main(String[] args) throws Exception {
		HttpServer
			.create()
			.host("localhost")
			.port(8080)
			.handle(
				new ReactorHttpHandlerAdapter(
					RouterFunctions.toHttpHandler(
						RouterFunctions.route(
							RequestPredicates.path("/hello/{name}"),
							(ServerRequest req) ->
								ServerResponse.ok().bodyValue("Hello" + req.pathVariable("name"))
						)
					)
				)
			)
			.bind()
			.block();

		System.in.read();
	}

}

