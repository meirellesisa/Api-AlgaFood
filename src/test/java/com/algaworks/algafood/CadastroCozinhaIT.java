package com.algaworks.algafood;

import org.springframework.boot.test.context.SpringBootTest;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;


@SpringBootTest
class CadastroCozinhaIT {

	private void deveRetornar200_QuandoConsultarCozinhas() {
		RestAssured.given()
		    .basePath("/cozinahas")
		    .port(8080)
		    .accept(ContentType.JSON)
		 .when() 
		    .get()
		 .then()
		    .statusCode(Htt );
	}
}
