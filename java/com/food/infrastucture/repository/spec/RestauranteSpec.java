package com.food.infrastucture.repository.spec;

import java.math.BigDecimal;

import org.springframework.data.jpa.domain.Specification;

import com.food.domain.model.Restaurante;

public class RestauranteSpec {

	public static Specification<Restaurante> comFreteGratis() {
		return (root, query, criteriabuilder) -> criteriabuilder.equal(root.get("taxaFrete"), BigDecimal.ZERO);

	}

	public static Specification<Restaurante> comNomeSemelhante(String nome) {
		return (root, query, criteriaBuilder) -> criteriaBuilder.like(root.get("nome"), "%" + nome + "%");

	}
}
