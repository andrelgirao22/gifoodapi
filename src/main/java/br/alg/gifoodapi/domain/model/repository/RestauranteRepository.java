package br.alg.gifoodapi.domain.model.repository;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import br.alg.gifoodapi.domain.model.Restaurante;

@Repository
public interface RestauranteRepository extends CustomRepository<Restaurante, Long>, 
	JpaSpecificationExecutor<Restaurante>, RestauranteRepositoryQueries {
	
	@Query(value = "from Restaurante r join r.cozinha") //left join fetch r.formasPagamento
	public List<Restaurante> findAll();
	
	List<Restaurante> findByTaxaFreteBetween(BigDecimal taxaInicial, BigDecimal taxaFinal);
	
	
	public List<Restaurante> find(String nome, BigDecimal taxaFrenteInicial, BigDecimal taxaFreteFinal);
	
}
