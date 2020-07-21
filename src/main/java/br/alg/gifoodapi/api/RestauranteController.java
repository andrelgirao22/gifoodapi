package br.alg.gifoodapi.api;

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
import org.springframework.web.bind.annotation.DeleteMapping;
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

import br.alg.gifoodapi.api.assembler.RestauranteModelAssembler;
import br.alg.gifoodapi.api.assembler.RestauranteModelDisassembler;
import br.alg.gifoodapi.api.model.RestauranteModel;
import br.alg.gifoodapi.api.model.input.RestauranteInput;
import br.alg.gifoodapi.core.validation.ValidacaoException;
import br.alg.gifoodapi.domain.exception.HandleEntidadeNaoEncontradaException;
import br.alg.gifoodapi.domain.exception.NegocioException;
import br.alg.gifoodapi.domain.model.Restaurante;
import br.alg.gifoodapi.domain.service.RestauranteService;

@RequestMapping("/restaurantes")
@RestController
public class RestauranteController {

	
	@Autowired
	private RestauranteService service;
	
	@Autowired
	private SmartValidator validator;
	
	@Autowired
	private RestauranteModelAssembler restauranteModelAssembler;
	
	@Autowired
	private RestauranteModelDisassembler restauranteModelDisassembler;
	
	@GetMapping
	public List<RestauranteModel> listar() {
		return restauranteModelAssembler.toCollectionModel(service.listar());
	}
	
	@GetMapping("/{id}")
	public RestauranteModel buscar(@PathVariable Long id) {
		
		Restaurante restaurante = this.service.buscarOuFalhar(id);
		
		return restauranteModelAssembler.toModel(restaurante);
	}

	@PostMapping
	@ResponseStatus(code = HttpStatus.CREATED)
	public RestauranteModel adicionar(
			@RequestBody @Valid RestauranteInput restauranteInput) {
		try {		
			
			Restaurante restaurante = restauranteModelDisassembler.toDomainObject(restauranteInput);
			
			return restauranteModelAssembler.toModel(this.service.salvar(restaurante));
		} catch(HandleEntidadeNaoEncontradaException e) {
			throw new NegocioException(e.getMessage());
		}
	}
	
	@PatchMapping("/{id}")
	public RestauranteModel atualizarParcial(@PathVariable Long id, @RequestBody Map<String, Object> campos, HttpServletRequest request) {
		Restaurante restauranteAtual = this.service.buscarOuFalhar(id);
		merge(campos, restauranteAtual, request);
		validate(restauranteAtual, "restaurante");
		return alterar(id, new RestauranteInput(restauranteAtual));
	}

	private void validate(Restaurante restauranteAtual, String objectName) {
		
		BeanPropertyBindingResult bindingResult = new BeanPropertyBindingResult(restauranteAtual, objectName);
		
		validator.validate(restauranteAtual, bindingResult);
		
		if(bindingResult.hasErrors()) {
			throw new ValidacaoException(bindingResult);
		}
	}

	private void merge(Map<String, Object> campos, Restaurante restauranteAtual, HttpServletRequest request) {
		
		ServletServerHttpRequest serverHttpRequest = new ServletServerHttpRequest(request);
		
		try {
			ObjectMapper objectMapper = new ObjectMapper();
			objectMapper.configure(DeserializationFeature.FAIL_ON_IGNORED_PROPERTIES, true);
			objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, true);
			
			Restaurante restauranteOrigem = objectMapper.convertValue(campos, Restaurante.class);
			
			campos.forEach((propriedade, valor) -> {
				
				Field field = ReflectionUtils.findField(Restaurante.class, propriedade);
				field.setAccessible(true);
				
				Object novoValor = ReflectionUtils.getField(field, restauranteOrigem);
				
				ReflectionUtils.setField(field, restauranteAtual, novoValor);
			});
		} catch(IllegalArgumentException e) {
			Throwable rootCause = ExceptionUtils.getRootCause(e);
			throw new HttpMessageNotReadableException(e.getMessage(), rootCause, serverHttpRequest); 
		}
	}
	
	@PutMapping("/{id}")
	public RestauranteModel alterar(@PathVariable Long id, @Valid @RequestBody RestauranteInput restauranteInput) {
		
		Restaurante entidade = restauranteModelDisassembler.toDomainObject(restauranteInput);
		
		Restaurante entidadeAtual =  service.buscarOuFalhar(id);
		
		BeanUtils.copyProperties(entidade, entidadeAtual,"id", "formasPagamento", "endereco", "dataCadastro", "produtos");
		
		try {	
			entidade.setId(id);
			return restauranteModelAssembler.toModel(this.service.alterar(entidade));
		}
		catch(HandleEntidadeNaoEncontradaException e) {
			throw new NegocioException(e.getMessage());
		}
	}
	
	@DeleteMapping("/{id}")
	public void remover(@PathVariable Long id) {
		this.service.remover(id);
	}
}
