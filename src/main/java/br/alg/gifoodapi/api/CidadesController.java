package br.alg.gifoodapi.api;

import java.util.List;

import javax.validation.Valid;

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

import br.alg.gifoodapi.domain.exception.EstadoNaoEncontradaException;
import br.alg.gifoodapi.domain.exception.HandleEntidadeNaoEncontradaException;
import br.alg.gifoodapi.domain.exception.NegocioException;
import br.alg.gifoodapi.domain.model.Cidade;
import br.alg.gifoodapi.domain.service.CidadeService;

@RequestMapping("/cidades")
@RestController
public class CidadesController {

	@Autowired
	private CidadeService service;

	@GetMapping
	public List<Cidade> listar() {
		return service.listar();
	}
	
	@GetMapping("/{id}")
	public Cidade buscar(@PathVariable Long id) {
		return this.service.buscar(id);
	}
	
	@PostMapping
	@ResponseStatus(code = HttpStatus.CREATED)
	public Cidade adicionar(@RequestBody @Valid Cidade entidade) {
		try {
			return this.service.salvar(entidade);
			
		} catch(HandleEntidadeNaoEncontradaException e) {
			throw new NegocioException(e.getMessage(), e);
		}
	}
	
	@PutMapping("/{id}")
	public Cidade alterar(@PathVariable Long id, 
			@RequestBody @Valid Cidade entidade) {
		
		try {			
			Cidade cidadeAtual =  buscar(id);
			BeanUtils.copyProperties(entidade, cidadeAtual,"id");
			return this.service.alterar(cidadeAtual);
		} catch(EstadoNaoEncontradaException e) {
			throw new NegocioException(e.getMessage());
		}
	}
	
	@DeleteMapping("/{id}")
	@ResponseStatus(value = HttpStatus.NO_CONTENT)
	public void remover(@PathVariable Long id) {
		this.service.remover(id);
	}	
	
}
