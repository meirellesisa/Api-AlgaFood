package com.algaworks.algafood;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.TestPropertySource;

import com.algaworks.algafood.util.DatabaseCleaner;

import io.restassured.RestAssured;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@TestPropertySource("/application-test.properties")
public class CadastroRestauranteIT {
	
	@LocalServerPort
	private int port;
	
	@Autowired
	private DatabaseCleaner dataBaseCleaner;
	
	public void setup() {
		RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();
		RestAssured.basePath = ("/restaurantes");
		RestAssured.port = port;
		
		dataBaseCleaner.clearTables();
	
	}
	
	
	@Test
	public void deveRetornar200_QuandoCadastrarCozinha() {
		
		RestAssured.given()
		     .basePath("/restaurantes")
		     .port(port)
		.when()
		     .get()
		     
		.then()
		    .statusCode(HttpStatus.OK.value());
	}
	
	
	

}
