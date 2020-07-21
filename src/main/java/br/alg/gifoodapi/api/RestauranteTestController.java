package br.alg.gifoodapi.api;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.alg.gifoodapi.domain.model.Restaurante;
import br.alg.gifoodapi.domain.model.repository.RestauranteRepository;

@RestController
@RequestMapping("/restaurantes/teste")
public class RestauranteTestController {

	@Autowired
	private RestauranteRepository repository;
	
	@GetMapping
	public List<Restaurante> listarPorNomeTaxa(String nome, 
			BigDecimal taxaFreteInicial, BigDecimal taxaFreteFinal) {
		
		return this.repository.find(nome, taxaFreteInicial, taxaFreteFinal);
		
	}
	
	@GetMapping("/com-frete-gratis")
	public List<Restaurante> listarPorNomeTaxa(String nome) {
		
		return this.repository.findComFreteGratis(nome);
		
	}
	
	@GetMapping("/busca-primeiro")
	public Optional<Restaurante> buscarPrimeiro() {
		return this.repository.buscarPrimeiro();
		
	}
	
}
