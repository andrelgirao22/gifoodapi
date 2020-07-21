package br.alg.gifoodapi;

import static io.restassured.RestAssured.enableLoggingOfRequestAndResponseIfValidationFails;
import static io.restassured.RestAssured.given;

import org.hamcrest.Matcher;
import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import br.alg.gifoodapi.domain.model.Cozinha;
import br.alg.gifoodapi.domain.model.Restaurante;
import br.alg.gifoodapi.domain.model.repository.CozinhaRepository;
import br.alg.gifoodapi.domain.model.repository.RestauranteRepository;
import br.alg.gifoodapi.util.DatabaseCleaner;
import br.alg.gifoodapi.util.ResourceUtils;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource("/application-test.properties")
public class CadastroRestauranteIT {

	@LocalServerPort
	private int port;
	
	private String jsonCorretoRestaurante;
	
	private String jsonIncorretoRestaurante;
	
	@Autowired
	private DatabaseCleaner databaseCleaner;
	
	@Autowired
	private RestauranteRepository repository;
	
	@Autowired
	private CozinhaRepository cozinhaRepository;

	private int quantidadeRegistros;
	
	@Before
	public void setup() {
		enableLoggingOfRequestAndResponseIfValidationFails();
		RestAssured.port = this.port;
		RestAssured.basePath = "/restaurantes";
		
		
		jsonCorretoRestaurante = ResourceUtils.getContentFromResource(
				"/json/correto/restaurante-teste.json");
		
		jsonIncorretoRestaurante = ResourceUtils.getContentFromResource(
				"/json/correto/restaurante-sem-frete.json");
		
		databaseCleaner.clearTables();
		prepararDados();
	}
	
	
	@Test
	public void deveRetornarStatus200_QuandoConsultarRestaurantes() {
		given()
			.accept(ContentType.JSON)
		.when()
			.get()
		.then()
			.statusCode(HttpStatus.OK.value());
	}
	
	@Test
	public void deveConterQuantidadeCorretaDeRegistrosRestaurantes_QuandoConsultarRestaurantes() {
		given()
			.accept(ContentType.JSON)
		.when() 
			.get()
		.then()
			.body("", Matchers.hasSize(quantidadeRegistros));
	}
	
	@Test
	public void deveRetornarStatus201_QuandoCadastrarRestaurante() {
		given()
			.accept(ContentType.JSON)
			.contentType(ContentType.JSON)
			.body(jsonCorretoRestaurante)
		.when()
			.post()
		.then()
			.statusCode(HttpStatus.CREATED.value());
	}

	private void prepararDados() {
		
		quantidadeRegistros = this.repository.findAll().size();
		
		Cozinha cozAmericana = new Cozinha();
		cozAmericana.setNome("Americana");
		
		this.cozinhaRepository.save(cozAmericana);
		
		Cozinha cozChinesa = new Cozinha();
		cozChinesa.setNome("Chinesa");
		
		this.cozinhaRepository.save(cozChinesa);
		
		
	}
	
}
