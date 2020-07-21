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

import br.alg.gifoodapi.domain.model.Estado;
import br.alg.gifoodapi.domain.service.EstadoService;

@RequestMapping("/estados")
@RestController
public class EstadoController {

	@Autowired
	private EstadoService service;

	@GetMapping
	public List<Estado> listar() {
		return service.listar();
	}
	
	@GetMapping("/{id}")
	public Estado buscar(@PathVariable Long id) {
		return this.service.buscar(id);
	}
	
	@PostMapping
	@ResponseStatus(code = HttpStatus.CREATED)
	public Estado adicionar(@RequestBody @Valid Estado entidade) {
		entidade = this.service.salvar(entidade);
		return entidade;
	}
	
	@PutMapping("/{id}")
	public Estado alterar(@PathVariable Long id, @RequestBody @Valid Estado entidade) {
		
		Estado estadoAtual =  this.buscar(id);
		BeanUtils.copyProperties(entidade, estadoAtual,"id");
		return this.service.alterar(estadoAtual);
	}
	
	@DeleteMapping("/{id}")
	@ResponseStatus(value = HttpStatus.NO_CONTENT)
	public void remover(@PathVariable Long id) {
		this.service.remover(id);
	}	
	
}
