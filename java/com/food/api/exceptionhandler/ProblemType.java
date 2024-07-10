package com.food.api.exceptionhandler;

import lombok.Getter;

@Getter
public enum ProblemType {

	RECURSO_NAO_ENCONTRADO("/recurso-nao-econtrado", "Recurso nao encontrado"),

	ENTIDADE_EM_USO("/entidade-em-uso", "Entidade em uso"),

	ERRO_NEGOCIO("/erro-negocio", "Violacao de regra de negocio"),

	MENSAGEM_INCOMPREENSIVEL("/mensagem-incompreensivel", "Mensagem incompreensivel"),

	PARAMETRO_INVALIDO("/parametro-invalido", "Parametro invalido"),

	ERRO_DE_SISTEMA("/erro-de-sistema", "Erro de sistema"),

	DADOS_INVALIDOS("/dados-invalidos", "Dados inválidos");

	private String title;
	private String uri;

	ProblemType(String path, String title) {
		this.uri = "https://apiFood.com.br" + path;
		this.title = title;

	}
}
