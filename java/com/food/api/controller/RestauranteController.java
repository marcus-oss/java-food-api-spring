package com.food.api.controller;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.util.ReflectionUtils;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.SmartValidator;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.food.core.validation.ValidacaoException;
import com.food.domain.exception.CozinhaNaoEncontradaException;
import com.food.domain.exception.NegocioException;
import com.food.domain.model.Restaurante;
import com.food.domain.repository.RestauranteRepository;
import com.food.domain.services.CadastroRestauranteService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;

@RestController
@RequestMapping(value = "/restaurantes")
public class RestauranteController {

	@Autowired
	private RestauranteRepository restauranteRepository;

	@Autowired
	private CadastroRestauranteService cadasRestauranteServices;

	@Autowired
	private SmartValidator validator;

	@GetMapping
	public List<Restaurante> listar() {
		return restauranteRepository.findAll();

	}

	@GetMapping("/{restauranteId}")
	public Restaurante buscarRestaurante(@PathVariable Long restauranteId) {

		return cadasRestauranteServices.buscarOuFalhar(restauranteId);

	}

	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public Restaurante adicionar(@RequestBody @Valid Restaurante restaurante) {

		try {
			return cadasRestauranteServices.salvar(restaurante);
		} catch (CozinhaNaoEncontradaException e) {
			throw new NegocioException(e.getMessage());
		}
	}

	@PutMapping("/{restauranteId}")
	public Restaurante atualizar(@PathVariable Long restauranteId, @RequestBody @Valid Restaurante restaurante) {
		Restaurante restauranteAtual = cadasRestauranteServices.buscarOuFalhar(restauranteId);

		BeanUtils.copyProperties(restaurante, restauranteAtual, "id", "formasPagamento", "endereco", "dataCadastro",
				"produtos");

		try {

			return cadasRestauranteServices.salvar(restauranteAtual);

		} catch (CozinhaNaoEncontradaException e) {
			throw new NegocioException(e.getMessage());
		}

	}

	@PatchMapping("/{restauranteId}")
	public Restaurante atualizarParcial(@PathVariable Long restauranteId, @RequestBody Map<String, Object> campos,
			HttpServletRequest request) {
		Restaurante restauranteAtual = cadasRestauranteServices.buscarOuFalhar(restauranteId);

		merge(campos, restauranteAtual, request);

		validate(restauranteAtual, "restaurante");

		return atualizar(restauranteId, restauranteAtual);
	}

	private void validate(Restaurante restaurante, String objectName) {

		BeanPropertyBindingResult bindingResult = new BeanPropertyBindingResult(restaurante, objectName);

		validator.validate(restaurante, bindingResult);

		if (bindingResult.hasErrors()) {
			throw new ValidacaoException(bindingResult);
		}
	}

	private void merge(Map<String, Object> camposOrigem, Restaurante restauranteDestinos, HttpServletRequest request) {
		ServletServerHttpRequest servletHttpRequest = new ServletServerHttpRequest(request);

		try {
			camposOrigem.forEach((nomePropiedade, valorPropriedade) -> {

				ObjectMapper objectMapper = new ObjectMapper();

				objectMapper.configure(DeserializationFeature.FAIL_ON_IGNORED_PROPERTIES, true);
				objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, true);

				Restaurante restauranteOrigem = objectMapper.convertValue(camposOrigem, Restaurante.class);

				Field field = ReflectionUtils.findField(Restaurante.class, nomePropiedade);
				field.setAccessible(true);

				Object novoValor = ReflectionUtils.getField(field, restauranteOrigem);

				System.out.println(nomePropiedade + " = " + valorPropriedade + " = " + novoValor);

				ReflectionUtils.setField(field, restauranteDestinos, valorPropriedade);
			});
		} catch (IllegalArgumentException e) {
			Throwable rootCuase = ExceptionUtils.getRootCause(e);
			throw new HttpMessageNotReadableException(e.getMessage(), rootCuase, servletHttpRequest);
		}
	}
}
