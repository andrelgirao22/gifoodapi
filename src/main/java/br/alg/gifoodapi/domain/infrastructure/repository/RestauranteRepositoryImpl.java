package br.alg.gifoodapi.domain.infrastructure.repository;

import static br.alg.gifoodapi.domain.infrastructure.repository.spec.RestauranteSpecs.comFreteGratis;
import static br.alg.gifoodapi.domain.infrastructure.repository.spec.RestauranteSpecs.comNomeSemelhante;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import br.alg.gifoodapi.domain.model.Restaurante;
import br.alg.gifoodapi.domain.model.repository.RestauranteRepository;
import br.alg.gifoodapi.domain.model.repository.RestauranteRepositoryQueries;

@Repository
public class RestauranteRepositoryImpl implements RestauranteRepositoryQueries {

	@PersistenceContext
	private EntityManager manager;
	
	@Autowired @Lazy
	private RestauranteRepository restauranteRepository;
	
	public List<Restaurante> find(String nome, BigDecimal taxaFreteInicial, BigDecimal taxaFreteFinal) {
		/*
		var jpql = new StringBuilder(); //"from Restaurante where nome like :nome and taxaFrete between :taxaInicial and :taxaFinal";
		jpql.append("from Restaurante where 1=1 ");
		
		var parameters = new HashMap<String, Object>();
		
		if(StringUtils.hasLength(nome)) {
			jpql.append(" and nome like :nome ");
			parameters.put("nome", "%" + nome +"%");
		}
		
		if(taxaFrenteInicial != null) {
			jpql.append(" and taxaFrete >= :taxaInicial ");
			parameters.put("taxaInicial", taxaFrenteInicial);
		}
		
		if(taxaFreteFinal != null) {
			jpql.append(" and taxaFrete <= :taxafinal ");
			parameters.put("taxafinal", taxaFreteFinal);
		}
		
		TypedQuery<Restaurante> typedQuey = manager.createQuery(jpql.toString(), Restaurante.class);
		parameters.forEach(typedQuey::setParameter);
			*/
		
		CriteriaBuilder builder = manager.getCriteriaBuilder();
		
		CriteriaQuery<Restaurante> criteria = builder.createQuery(Restaurante.class);
		Root<Restaurante> root = criteria.from(Restaurante.class);

		var predicates = new ArrayList<Predicate>();
		
		if(StringUtils.hasLength(nome)) {
			predicates.add(builder.like(root.get("nome"), "%" + nome + "%"));
		}
		
		if(taxaFreteInicial != null) {
			predicates.add( builder.greaterThanOrEqualTo(root.get("taxaFrete"), taxaFreteInicial));
		}
		
		if(taxaFreteFinal != null) {
			predicates.add(builder.lessThanOrEqualTo(root.get("taxaFrete"), taxaFreteFinal));
		}
		
		criteria.where(predicates.toArray(new Predicate[0]));
		
		return manager.createQuery(criteria).getResultList();
	}

	@Override
	public List<Restaurante> findComFreteGratis(String nome) {
		return restauranteRepository.findAll(comFreteGratis().and(comNomeSemelhante(nome)));
	}
	
}
