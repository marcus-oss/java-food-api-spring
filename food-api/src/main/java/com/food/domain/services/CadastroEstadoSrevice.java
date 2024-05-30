package com.food.domain.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import com.food.domain.exception.EntidadeEmUsoException;
import com.food.domain.exception.EntidadeNãoEncontradaException;
import com.food.domain.model.Estado;
import com.food.domain.repository.EstadoRepository;

@Service
public class CadastroEstadoSrevice {

	@Autowired
	private EstadoRepository estadoRepository;

	public Estado salvar(Estado estado) {
		return estadoRepository.salvar(estado);
	}

	public void Excluir(Long estadoId) {
		try {
			estadoRepository.remover(estadoId);

		} catch (EmptyResultDataAccessException e) {
			throw new EntidadeNãoEncontradaException(
					String.format("Não existe um cadastro de estado com esse codigo %d", estadoId));
		} catch (DataIntegrityViolationException e) {
			throw new EntidadeEmUsoException(
					String.format("Estado de codigo %d não pode ser removido por causa que está em uso", estadoId));
		}
	}
}
