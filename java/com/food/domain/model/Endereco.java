package com.food.domain.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Data;

@Data
@Embeddable
public class Endereco {

	@Column(name = "endereco_cep")
	private String cep;

	@Column(name = "endereco_logradouro")
	private String logadouro;

	@Column(name = "endereco_numero")
	private String numero;

	@Column(name = "endereco_complemento")
	private String complemento;

	@Column(name = "endereco_bairro")
	private String bairro;

	@JsonIgnore
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "endereco_cidade_codigo")
	private Cidade cidade;

}
