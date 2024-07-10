package com.food.domain.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Repository;

import com.food.domain.model.Cozinha;

@Repository
public interface CozinhaRepository extends CustomJpaRepository<Cozinha, Long> {

	List<Cozinha> findTodasBynomeContaining(String nome);

	Optional<Cozinha> findBynome(String nome);

	boolean existsByNome(String nome);

}
