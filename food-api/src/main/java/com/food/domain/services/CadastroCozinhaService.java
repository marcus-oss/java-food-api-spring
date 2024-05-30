package com.food.domain.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import com.food.domain.exception.EntidadeEmUsoException;
import com.food.domain.exception.EntidadeNãoEncontradaException;
import com.food.domain.model.Cozinha;
import com.food.domain.repository.CozinhaRepository;

@Service
public class CadastroCozinhaService {

	@Autowired
	private CozinhaRepository cozinhaRepository;

	public Cozinha Salvar(Cozinha cozinha) {

		return cozinhaRepository.salvar(cozinha);

	}

	public void Excluir(Long cozinhaId) {
		try {
			cozinhaRepository.Remover(cozinhaId);

		} catch (EmptyResultDataAccessException e) {
			throw new EntidadeNãoEncontradaException(
					String.format("Não existe um  cadastro de cozinha com o codigo informado", cozinhaId));

		} catch (DataIntegrityViolationException e) {
			throw new EntidadeEmUsoException(
					String.format("Cozinha de codigo %d não pode ser emovida pois está em uso", cozinhaId));

		}

	}

}
