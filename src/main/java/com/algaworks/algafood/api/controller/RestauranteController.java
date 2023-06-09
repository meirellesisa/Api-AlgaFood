package com.algaworks.algafood.api.controller;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

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

import com.algaworks.algafood.domain.exception.CozinhaNaoEncontradaException;
import com.algaworks.algafood.domain.exception.NegocioException;
import com.algaworks.algafood.domain.exception.ValidacaoException;
import com.algaworks.algafood.domain.model.Restaurante;
import com.algaworks.algafood.domain.repository.RestauranteRepository;
import com.algaworks.algafood.domain.service.CadastroRestauranteService;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

@RestController
@RequestMapping("/restaurantes")
public class RestauranteController {
	
	@Autowired
	private RestauranteRepository restauranteRepository;
	
	@Autowired
	private CadastroRestauranteService cadastroRestaurante;
	
	@Autowired
	private SmartValidator validator;

	
	
	@GetMapping
	public List<Restaurante> listar(){
 	return restauranteRepository.findAll();
	}
	
	
//	@GetMapping
//	public List<Restaurante> listar(){
//		List<Restaurante> restaurantes = restauranteRepository.findAll();
//		
//		System.out.println("o nome da cozinha é: ");
//		System.out.println(restaurantes.get(0).getCozinha().getNome());
//		return restaurantes;
//	}
	
	
	
	
	@GetMapping("/{restauranteId}")
	public Restaurante buscar(@PathVariable Long restauranteId) {
		
		return cadastroRestaurante.buscarOuFalhar(restauranteId);
	
	}
	
	@PostMapping
	@ResponseStatus(value = HttpStatus.CREATED)
	public Restaurante adicionar(@RequestBody @Valid Restaurante restaurante){
		
		try {
			return cadastroRestaurante.salvar(restaurante); 
			
		} catch (CozinhaNaoEncontradaException e) {
			throw new NegocioException(e.getMessage());
		}
			
		
	}
	
	
	@PutMapping("/{restauranteId}")
	public Restaurante atualizar(@PathVariable Long restauranteId,  @RequestBody @Valid Restaurante restaurante){
		
		try {
			Restaurante restauranteAtual = cadastroRestaurante.buscarOuFalhar(restauranteId);
			BeanUtils.copyProperties(restaurante, restauranteAtual, "id", "formasPagamento", "endereco","dataCadastro");
			
			return  cadastroRestaurante.salvar(restauranteAtual);
			
		} catch (CozinhaNaoEncontradaException e) {
			throw new NegocioException(e.getMessage(),e);
			
		}
			
		
	}
	
	@PatchMapping("/{restauranteId}")
	public Restaurante atualizarParcial(@PathVariable Long restauranteId, 
			@RequestBody Map<String, Object> campos, HttpServletRequest request){
		
	     Restaurante restauranteAtual = cadastroRestaurante.buscarOuFalhar(restauranteId);
	
		merge(campos, restauranteAtual, request);
		validate(restauranteAtual, "restaurante");
		return atualizar(restauranteId, restauranteAtual);
		
		
	}

	private void validate(Restaurante restauranteAtual, String objectName) {
		BeanPropertyBindingResult bindingResult = new BeanPropertyBindingResult(restauranteAtual, objectName);
		
		
		validator.validate(restauranteAtual, bindingResult);
		
		if(bindingResult.hasErrors()) {
			throw new ValidacaoException(bindingResult);
		}
		
	}


	private void merge(Map<String, Object> camposOrigem, Restaurante restauranteDetino, HttpServletRequest request) {
		
		ServletServerHttpRequest serverHttpRequest = new ServletServerHttpRequest(request);
		
		
		try {
			
			ObjectMapper objectMapper =  new ObjectMapper();
			objectMapper.configure(DeserializationFeature.FAIL_ON_IGNORED_PROPERTIES, true);
			objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES,true);
			
			
			Restaurante restauranteOrigem = objectMapper.convertValue(camposOrigem, Restaurante.class);
			
			
			
			camposOrigem.forEach((nomePropriedade, valorPropriedade)->{
				
				Field field = ReflectionUtils.findField(Restaurante.class, nomePropriedade);
				field.setAccessible(true);
				
				
				Object novoValor = ReflectionUtils.getField(field, restauranteOrigem);
				
				//System.out.println(nomePropriedade + " = " + valorPropriedade + " = " + novoValor);
				
				ReflectionUtils.setField(field, restauranteDetino, novoValor);
			});
		} catch (IllegalArgumentException ex) {
			 Throwable rootCause = ExceptionUtils.getRootCause(ex);
			 throw new HttpMessageNotReadableException(ex.getMessage(), rootCause, serverHttpRequest);
		}
		
	}

}
