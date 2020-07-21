package br.alg.gifoodapi;

import static io.restassured.RestAssured.enableLoggingOfRequestAndResponseIfValidationFails;
import static io.restassured.RestAssured.given;

import java.util.ArrayList;

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
import br.alg.gifoodapi.domain.model.repository.CozinhaRepository;
import br.alg.gifoodapi.util.DatabaseCleaner;
import br.alg.gifoodapi.util.ResourceUtils;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource("/application-test.properties")
public class CadastroCozinhaIT {

	@LocalServerPort
	private int port;
	
	private int quantidadeRegistros = 0;
	
	private static final int COZINHA_ID_INEXISTENTE = 100;
	
	private String jsonCorretoCozinhaChinesa;
	
	@Autowired
	private DatabaseCleaner databaseCleaner;
	
	@Autowired
	private CozinhaRepository repository;

	private ArrayList<Cozinha> cozinhas;

	private Cozinha cozinhaAmericana;
	
	@Before
	public void setup() {
		
		enableLoggingOfRequestAndResponseIfValidationFails();
		RestAssured.port = this.port;
		RestAssured.basePath = "/cozinhas";
		
		
		jsonCorretoCozinhaChinesa = ResourceUtils.getContentFromResource(
				"/json/correto/cozinha-teste.json");
		
		databaseCleaner.clearTables();
		prepararDados();
	}
	
	@Test
	public void deveRetornarStatus200_QuandoConsultarCozinhas() {
		given()
			.accept(ContentType.JSON)
		.when()
			.get()
		.then()
			.statusCode(HttpStatus.OK.value());
	}
	
	@Test
	public void deveConterQuantidadeCorretaDeRegistrosCozinhas_QuandoConsultarCozinhas() {
		given()
			.accept(ContentType.JSON)
		.when() 
			.get()
		.then()
			.body("", Matchers.hasSize(quantidadeRegistros));
	}
	
	@Test
	public void deveRetornarStatus201_QuandoCadastrarCozinhas() {
		given()
			.body(jsonCorretoCozinhaChinesa)
			.contentType(ContentType.JSON)
			.accept(ContentType.JSON)
		.when() 
			.post()
		.then()
			.statusCode(HttpStatus.CREATED.value());
	}
	
	@Test
	public void deveRetornarRespostaEStatusCorretos_QuandoConsultarCozinhaExistente() {
		
		given()
			.pathParam("cozinhaId", cozinhaAmericana.getId())
			.accept(ContentType.JSON)
		.when()
			.get("/{cozinhaId}")
		.then()
			.statusCode(HttpStatus.OK.value())
			.body("nome", Matchers.equalTo(cozinhaAmericana.getNome()));
	}
	
	/*
	@Test
	public void deveRetornarStatus404_QuandoConsultarCozinhaInexistente() {
		given()
			.pathParam("cozinhaId", COZINHA_ID_INEXISTENTE)
			.accept(ContentType.JSON)
		.when()
			.get("/{cozinhaId}")
		.then()
			.statusCode(HttpStatus.NOT_FOUND.value());
	}*/
	
	private void prepararDados() {
		Cozinha cozinha1 = new Cozinha();
		cozinha1.setNome("Tailandesa");
		
		cozinhaAmericana = new Cozinha();
		cozinhaAmericana.setNome("Americana");
			
		cozinhas = new ArrayList<Cozinha>();
		cozinhas.add(cozinha1);
		cozinhas.add(cozinhaAmericana);
		
		cozinhas.forEach(repository::save);
		
		quantidadeRegistros = cozinhas.size();
	}
	
	/*
	 * @Autowired private CozinhaService cozinhaService;
	 * 
	 * @Test public void deveAtribuirId_QuandoCadastrarCozinhaComDadosCorretos() {
	 * 
	 * //cenário
	 * 
	 * Cozinha novaCozinha = new Cozinha(); novaCozinha.setNome("Chinesa");
	 * 
	 * //acao
	 * 
	 * novaCozinha = cozinhaService.salvar(novaCozinha);
	 * 
	 * //validacao
	 * 
	 * assertThat(novaCozinha).isNotNull();
	 * assertThat(novaCozinha.getId()).isNotNull();
	 * 
	 * }
	 * 
	 * 
	 * 
	 * @Test(expected = ConstraintViolationException.class) public void
	 * deveFalhar_QuandoCadastrarCozinhaSemNome() {
	 * 
	 * Cozinha novaCozinha = new Cozinha(); novaCozinha.setNome(null);
	 * 
	 * novaCozinha = cozinhaService.salvar(novaCozinha); }
	 * 
	 * 
	 * @Test(expected = EntidadeEmUsoException.class) public void
	 * deveFalhar_QuandoExcluirCozinhaEmUso() { this.cozinhaService.remover(1l); }
	 * 
	 * @Test(expected = CozinhaNaoEncontradaException.class) public void
	 * deveFalhar_QuandoExcluirCozinhaInexistente() {
	 * this.cozinhaService.remover(10l); }
	 */
	
}
