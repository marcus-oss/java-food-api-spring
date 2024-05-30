package com.food.domain.exception;

public class EntidadeNãoEncontradaException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public EntidadeNãoEncontradaException(String mensagem) {
		super(mensagem);
	}
}
