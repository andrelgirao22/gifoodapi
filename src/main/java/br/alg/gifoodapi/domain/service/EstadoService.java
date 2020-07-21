package br.alg.gifoodapi.domain.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import br.alg.gifoodapi.domain.exception.EntidadeEmUsoException;
import br.alg.gifoodapi.domain.exception.EstadoNaoEncontradaException;
import br.alg.gifoodapi.domain.model.Estado;
import br.alg.gifoodapi.domain.model.repository.EstadoRepository;

@Service
public class EstadoService {

	
	
	private static final String MSG_ESTADO_EM_USO = "Estado de código %d não pode ser removido, pois está em uso.";
	@Autowired
	private EstadoRepository repository;
	
	public Estado salvar(Estado entidade) {
		return this.repository.save(entidade);
	}
	
	public Estado buscar(Long id) {
		return this.repository.findById(id)
				.orElseThrow(() -> new EstadoNaoEncontradaException(id));
	}
	
	public void remover(Long id) {
		try {
			this.buscar(id);
			this.repository.deleteById(id);
		} catch (DataIntegrityViolationException e) {
			throw new EntidadeEmUsoException(
					String.format(MSG_ESTADO_EM_USO, id));
		}
	}
	
	public List<Estado> listar() {
		return repository.findAll();
	}

	public Estado alterar(Estado entidade) {
		return this.repository.save(entidade);
	}
}
