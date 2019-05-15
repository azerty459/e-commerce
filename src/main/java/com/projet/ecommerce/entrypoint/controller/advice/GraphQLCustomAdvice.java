package com.projet.ecommerce.entrypoint.controller.advice;

import com.projet.ecommerce.entrypoint.graphql.GraphQLCustomException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class GraphQLCustomAdvice {

	@ResponseBody
	@ExceptionHandler(GraphQLCustomException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	String graphQLCustomHandler(GraphQLCustomException ex) {
		return ex.getMessage();
	}

}
