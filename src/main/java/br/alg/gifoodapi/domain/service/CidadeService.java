package br.alg.gifoodapi.domain.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import br.alg.gifoodapi.domain.exception.CidadeNaoEncontradaException;
import br.alg.gifoodapi.domain.exception.EntidadeEmUsoException;
import br.alg.gifoodapi.domain.model.Cidade;
import br.alg.gifoodapi.domain.model.Estado;
import br.alg.gifoodapi.domain.model.repository.CidadeRepository;

@Service
public class CidadeService {

	private static final String MSG_CIDADE_EM_USO = "Cidade de código %d não pode ser removida, pois está em uso.";
	
	@Autowired
	private CidadeRepository repository;
	
	@Autowired
	private EstadoService estadoService;
	
	public Cidade salvar(Cidade entidade) {
		
		this.buscaEstado(entidade);
		
		return this.repository.save(entidade);
	}
	
	public Cidade buscar(Long id) {
		return this.repository.findById(id)
				.orElseThrow(() -> new CidadeNaoEncontradaException(id));
	}
	
	public void remover(Long id) {
		try {
			this.buscar(id);
			this.repository.deleteById(id);
		} catch (DataIntegrityViolationException e) {
			throw new EntidadeEmUsoException(
					String.format(MSG_CIDADE_EM_USO, id));
		}
	}
	
	public List<Cidade> listar() {
		return repository.findAll();
	}

	public Cidade alterar(Cidade entidade) {
		
		this.buscaEstado(entidade);
		
		return this.repository.save(entidade);
	}

	private void buscaEstado(Cidade entidade) {
		Estado estado = this.estadoService.buscar(entidade.getEstado().getId());
		entidade.setEstado(estado);
	}
}
