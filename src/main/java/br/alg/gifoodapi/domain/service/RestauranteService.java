package br.alg.gifoodapi.domain.service;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import br.alg.gifoodapi.domain.exception.RestauranteNaoEncontradaException;
import br.alg.gifoodapi.domain.model.Restaurante;
import br.alg.gifoodapi.domain.model.repository.RestauranteRepository;

@Service
public class RestauranteService {

	@Autowired
	private RestauranteRepository repository;
	
	@Autowired
	private CozinhaService cozinhaService;
	
	@Transactional
	public Restaurante salvar(Restaurante restaurante) {
		restaurante.setCozinha(this.cozinhaService.buscarOuFalhar(restaurante.getCozinha().getId()));
		return this.repository.save(restaurante);
	}
	
	public Restaurante buscarOuFalhar(Long id) {
		return this.repository.findById(id)
				.orElseThrow(() -> new RestauranteNaoEncontradaException(id));
	}
	
	@Transactional
	public void remover(Long id) {
		try {
			buscarOuFalhar(id);
			this.repository.deleteById(id);
		} catch (DataIntegrityViolationException e) {
				throw new RestauranteNaoEncontradaException(id);
			}
	}
	
	public List<Restaurante> listar() {
		return repository.findAll();
	}

	@Transactional
	public Restaurante alterar(Restaurante entidade) {
		this.cozinhaService.buscarOuFalhar(entidade.getCozinha().getId());
		return this.repository.save(entidade);
	}
}
