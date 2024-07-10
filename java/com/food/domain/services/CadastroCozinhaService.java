package com.food.domain.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import com.food.domain.exception.CozinhaNaoEncontradaException;
import com.food.domain.exception.EntidadeEmUsoException;
import com.food.domain.model.Cozinha;
import com.food.domain.repository.CozinhaRepository;

@Service
public class CadastroCozinhaService {

	private static final String MSG_COZINHA_NAO_PODE_SER_REMOVIDA = "Cozinha de codigo %d não pode ser removida pois está em uso";

	@Autowired
	private CozinhaRepository cozinhaRepository;

	public Cozinha Salvar(Cozinha cozinha) {

		return cozinhaRepository.save(cozinha);

	}

	public void Excluir(Long cozinhaId) {
		try {
			cozinhaRepository.deleteById(cozinhaId);

		} catch (EmptyResultDataAccessException e) {
			throw new CozinhaNaoEncontradaException(cozinhaId);

		} catch (DataIntegrityViolationException e) {
			throw new EntidadeEmUsoException(String.format(MSG_COZINHA_NAO_PODE_SER_REMOVIDA, cozinhaId));

		}

	}

	public Cozinha buscarouFalha(Long cozinhaId) {
		return cozinhaRepository.findById(cozinhaId).orElseThrow(() -> new CozinhaNaoEncontradaException(cozinhaId));
	}

}
