package br.alg.gifoodapi.domain.model.repository;

import java.util.List;

import br.alg.gifoodapi.domain.model.Restaurante;

public interface RestauranteRepositoryQueries {

	public List<Restaurante> findComFreteGratis(String nome);
	
}
