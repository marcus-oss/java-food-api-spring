package com.food.domain.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import com.food.domain.exception.CidadeNaoEncontradaException;
import com.food.domain.exception.EntidadeEmUsoException;
import com.food.domain.model.Cidade;
import com.food.domain.model.Estado;
import com.food.domain.repository.CidadeRepository;

@Service
public class CadastroCidadeService {

	private static final String MSG_NAO_PODE_SER_REMOVIDA = "Cidade de código %d não pode ser removida, pois está em uso";

	@Autowired
	private CidadeRepository cidadeRepository;

	@Autowired
	private CadastroEstadoSrevice cadastroEstadoSrevice;

	public Cidade salvar(Cidade cidade) {
		Long estadoId = cidade.getEstado().getCodigo();

		Estado estado = cadastroEstadoSrevice.buscarOuFalhar(estadoId);

		cidade.setEstado(estado);

		return cidadeRepository.save(cidade);
	}

	public Cidade buscarOuFalhar(Long cidadeId) {
		return cidadeRepository.findById(cidadeId).orElseThrow(() -> new CidadeNaoEncontradaException(cidadeId));
	}

	public void excluir(Long cidadeId) {
		try {
			cidadeRepository.deleteById(cidadeId);

		} catch (EmptyResultDataAccessException e) {
			throw new CidadeNaoEncontradaException(cidadeId);

		} catch (DataIntegrityViolationException e) {
			throw new EntidadeEmUsoException(String.format(MSG_NAO_PODE_SER_REMOVIDA, cidadeId));
		}
	}

}
