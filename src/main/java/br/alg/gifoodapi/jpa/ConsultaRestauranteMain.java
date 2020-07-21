package br.alg.gifoodapi.jpa;

import org.springframework.boot.WebApplicationType;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ApplicationContext;

import br.alg.gifoodapi.GifoodApiApplication;
import br.alg.gifoodapi.domain.model.repository.RestauranteRepository;

public class ConsultaRestauranteMain {

	public static void main(String[] args) {
		
		ApplicationContext applicationContext = new SpringApplicationBuilder(GifoodApiApplication.class)
				.web(WebApplicationType.NONE)
				.run(args);
		
		RestauranteRepository restauranteRepo = applicationContext.getBean(RestauranteRepository.class);
		
		restauranteRepo.findAll().forEach(System.out::println);
		
		
	}
	
}
