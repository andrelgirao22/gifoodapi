package br.alg.gifoodapi.domain.service;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import br.alg.gifoodapi.domain.exception.CozinhaNaoEncontradaException;
import br.alg.gifoodapi.domain.exception.EntidadeEmUsoException;
import br.alg.gifoodapi.domain.model.Cozinha;
import br.alg.gifoodapi.domain.model.repository.CozinhaRepository;

@Service
public class CozinhaService {

	private static final String MSG_COZINHA_EM_USO = "Cozinha de código %d não pode ser removida, pois está em uso.";
	
	@Autowired
	private CozinhaRepository cozinhaRepository;
	
	@Transactional
	public Cozinha salvar(Cozinha cozinha) {
		return this.cozinhaRepository.save(cozinha);
	}
	
	public Optional<Cozinha> buscar(Long id) {
		
		 Optional<Cozinha> optionalCozinha = this.cozinhaRepository.findById(id);
		
		return optionalCozinha;
	}
	
	@Transactional
	public void remover(Long cozinhaId) {
		
		try {
			buscarOuFalhar(cozinhaId);
			this.cozinhaRepository.deleteById(cozinhaId);
		} catch (DataIntegrityViolationException e) {
			throw new EntidadeEmUsoException(
					String.format(MSG_COZINHA_EM_USO, cozinhaId));
		}
	}
	
	public List<Cozinha> listar() {
		return cozinhaRepository.findAll();
	}

	@Transactional
	public Cozinha alterar(Long cozinhaId, Cozinha cozinha) {
		Cozinha cozinhaAtual =  buscarOuFalhar(cozinhaId);
		BeanUtils.copyProperties(cozinha, cozinhaAtual,"id");
		return this.cozinhaRepository.save(cozinhaAtual);
	}
	
	public Cozinha buscarOuFalhar(Long cozinhaId) {
		return this.cozinhaRepository.findById(cozinhaId)
				.orElseThrow(() -> new CozinhaNaoEncontradaException(cozinhaId));
	}
}
