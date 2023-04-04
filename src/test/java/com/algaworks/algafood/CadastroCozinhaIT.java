package com.algaworks.algafood;

import static org.hamcrest.CoreMatchers.equalTo;

import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.TestPropertySource;

import com.algaworks.algafood.domain.model.Cozinha;
import com.algaworks.algafood.domain.repository.CozinhaRepository;
import com.algaworks.algafood.util.DatabaseCleaner;
import com.algaworks.algafood.util.MetodoReadJson;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;


@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@TestPropertySource("/application-test.properties")
class CadastroCozinhaIT {
	
	private static final int COZINHA_ID_INEXISTENTE = 100;

	@LocalServerPort
	private int port;

	@Autowired
	private DatabaseCleaner dataBaseCleaner;
	
	@Autowired
	private CozinhaRepository cozinhaRepository;
	
	
	private int quantidadeCozinhasCadastradas;
	private Cozinha cozinhaAmericana;
	
	String jsonCozinhaChinesa = MetodoReadJson.readJson("/json/cozinha-chinesa.json");
	
	
	@BeforeEach
	public void setup() {
		RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();
		RestAssured.port = port;
		RestAssured.basePath = ("/cozinhas");
		
		dataBaseCleaner.clearTables();
		
		prepararDados();
		
	}

	@Test
	public void deveRetornar200_QuandoConsultarCozinhas() {
		
		
		RestAssured.given()
		    .accept(ContentType.JSON)
		 .when() 
		    .get()
		 .then()
		    .statusCode(HttpStatus.OK.value());
	}
	
	@Test
	public void deveRetornatarQuantidadeExataDeCozinha_QuandoConsultarCozinhas() {
	
		RestAssured.given()
		.when()
		       .get()
		.then()
		       .body("", Matchers.hasSize(quantidadeCozinhasCadastradas));
		
		
		
	}
	
	@Test
	public void deveRetornarStatus201_QuandoCadastarCozinha() {
		
		RestAssured.given()
		      .body(jsonCozinhaChinesa)
		      .contentType(ContentType.JSON)
		      .accept(ContentType.JSON)
		.when()
		      .post()
		.then()
		      .statusCode(HttpStatus.CREATED.value());
			
	}
	
	
	public void deveRetornarRespostaEStatusCorretos_QuandoConsultarCozinhaExistente() {
		
		RestAssured.given()
		           .pathParam("cozinhaId", cozinhaAmericana.getId())
		           .accept(ContentType.JSON)
		   .when()
		           .get("/{cozinhaId}")
		   .then()
		           .statusCode(HttpStatus.OK.value())
		           .body("nome", equalTo(cozinhaAmericana.getNome()));
		         
		
	}
	
	@Test
	public void deveRetornarStatus400_QuandoConsultarCozinhaInexistente(){
		
		RestAssured.given()
		       .pathParam("cozinhaId",COZINHA_ID_INEXISTENTE)
		       .accept(ContentType.JSON)
		.when()
		       .get("/{cozinhaId}")
		.then()
		      .statusCode(HttpStatus.NOT_FOUND.value());
		      
	}
	
	
	
	
	private void prepararDados() {
		
		cozinhaAmericana = new Cozinha();
		cozinhaAmericana.setNome("Americama");
		cozinhaAmericana = cozinhaRepository.save(cozinhaAmericana);
		
		Cozinha cozinha2 = new Cozinha();
		cozinha2.setNome("Tailandesa");
		cozinha2 = cozinhaRepository.save(cozinha2);
		
		quantidadeCozinhasCadastradas = (int) cozinhaRepository.count();
	}
	
	
}
