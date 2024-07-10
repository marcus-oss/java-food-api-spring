package com.food.infrastucture.repository;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import com.food.domain.model.Restaurante;
import com.food.domain.repository.RestauranteRepository;
import com.food.domain.repository.RestauranteRepositoryQueries;
import com.food.infrastucture.repository.spec.RestauranteSpec;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.Predicate;

@Repository
public class RestauranteRepositoryImpl implements RestauranteRepositoryQueries {

	@PersistenceContext
	private EntityManager manager;

	@Autowired
	@Lazy
	private RestauranteRepository restauranteRepository;

	@Override
	public List<Restaurante> find(String nome, BigDecimal taxaFreteInicial, BigDecimal taxaFreteFinal) {
		CriteriaBuilder criteriaBuilder = manager.getCriteriaBuilder();

		var criteria = criteriaBuilder.createQuery(Restaurante.class);
		var root = criteria.from(Restaurante.class);

		var predicatesArray = new ArrayList<Predicate>();

		if (StringUtils.hasText(nome)) {
			predicatesArray.add(criteriaBuilder.like(root.get("nome"), "%" + nome + "%"));

		}
		if (taxaFreteInicial != null) {
			predicatesArray.add(criteriaBuilder.greaterThanOrEqualTo(root.get("taxaFrete"), taxaFreteInicial));
		}

		if (taxaFreteFinal != null) {
			predicatesArray.add(criteriaBuilder.lessThanOrEqualTo(root.get("taxaFrete"), taxaFreteFinal));
		}

		criteria.where(predicatesArray.toArray(new Predicate[0]));

		var queryTyped = manager.createQuery(criteria);
		return queryTyped.getResultList();
	}

	@Override
	public List<Restaurante> findComFretesGratis(String nome) {
		return restauranteRepository
				.findAll(RestauranteSpec.comFreteGratis().and(RestauranteSpec.comNomeSemelhante(nome)));

	}
}
