package br.alg.gifoodapi.domain.infrastructure.repository;

import java.util.Optional;

import javax.persistence.EntityManager;

import org.springframework.data.jpa.repository.support.JpaEntityInformation;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;

import br.alg.gifoodapi.domain.model.repository.CustomRepository;

public class CustomRepositoryImpl<T, ID> extends SimpleJpaRepository<T, ID> implements CustomRepository<T, ID> {

	private EntityManager manager;

	public CustomRepositoryImpl(JpaEntityInformation<T, ?> entityInformation, 
			EntityManager entityManager) {
		super(entityInformation, entityManager);
		
		this.manager = entityManager;
	}
	

	@Override
	public Optional<T> buscarPrimeiro() {
		var jpql = "from " + getDomainClass().getName();
		
		T entity = this.manager.createQuery(jpql, getDomainClass())
			.setMaxResults(1).getSingleResult();
		
		return Optional.ofNullable(entity);
	}

}
