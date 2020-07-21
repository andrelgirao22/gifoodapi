package br.alg.gifoodapi.api;

import java.util.List;

import javax.validation.Valid;

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

import br.alg.gifoodapi.domain.model.Cozinha;
import br.alg.gifoodapi.domain.service.CozinhaService;

@RequestMapping("/cozinhas")
@RestController
public class CozinhaController {

	@Autowired
	private CozinhaService service;
	
	@GetMapping
	public List<Cozinha> listar() {
		return service.listar();
	}
	
	@GetMapping("/{cozinhaId}")
	public Cozinha buscar(@PathVariable Long cozinhaId) {
		return this.service.buscarOuFalhar(cozinhaId);
	}
	
	@PostMapping
	@ResponseStatus(code = HttpStatus.CREATED)
	public Cozinha adicionar(@RequestBody @Valid Cozinha cozinha) {
		cozinha = this.service.salvar(cozinha);
		return cozinha;
	}
	
	@PutMapping("/{cozinhaId}")
	public Cozinha alterar(@PathVariable Long cozinhaId, @RequestBody @Valid Cozinha cozinha) {
		return this.service.alterar(cozinhaId, cozinha);
	}
	
	@DeleteMapping("/{cozinhaId}")
	@ResponseStatus(value = HttpStatus.NO_CONTENT)
	public void remover(@PathVariable Long cozinhaId) {
		this.service.remover(cozinhaId);
	}
	
}
