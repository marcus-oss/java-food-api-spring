package com.food.api.controller;

import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.food.domain.model.Estado;
import com.food.domain.repository.EstadoRepository;
import com.food.domain.services.CadastroEstadoSrevice;

import jakarta.validation.Valid;

@RestController
@RequestMapping(value = "/estados")
public class EstadoController {
	@Autowired
	private CadastroEstadoSrevice cadastroEstado;
	@Autowired
	private EstadoRepository estadoRepository;

	@GetMapping
	public List<Estado> listar() {
		return estadoRepository.findAll();
	}

	@GetMapping("/{estadoId}")
	public Estado buscar(@PathVariable Long estadoId) {

		return cadastroEstado.buscarOuFalhar(estadoId);

	}

	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public Estado adicionar(@RequestBody @Valid Estado estado) {
		return estadoRepository.save(estado);
	}

	@PutMapping("/{estadoId}")
	public Estado atualizar(@PathVariable Long estadoId, @RequestBody @Valid Estado estado) {
		Estado estadoAtual = cadastroEstado.buscarOuFalhar(estadoId);

		BeanUtils.copyProperties(estado, estadoAtual, "id");

		return cadastroEstado.salvar(estadoAtual);

	}

	@DeleteMapping("/{estadoId}")
	public void remover(@PathVariable Long estadoId) {
		cadastroEstado.Excluir(estadoId);
	}

}
