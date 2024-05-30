package com.food.domain.repository;

import java.util.List;

import com.food.domain.model.Cozinha;

public interface CozinhaRepository {

	List<Cozinha> listar();

	Cozinha buscar(Long id);

	Cozinha salvar(Cozinha cozinha);

	void Remover(Long Id);

}
