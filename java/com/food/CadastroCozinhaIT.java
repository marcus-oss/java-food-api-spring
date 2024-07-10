package com.food;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.equalTo;

import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.TestPropertySource;

import com.food.domain.model.Cozinha;
import com.food.domain.repository.CozinhaRepository;
import com.food.util.DatabaseCleaner;
import com.food.util.ResourceUtils;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource("/application-test.properties")
class CadastroCozinhaIT {

	private static final int COZINHA_CODIGO_INEXISTENTE = 12;

	@LocalServerPort
	private int port;

	@Autowired
	private DatabaseCleaner databaseCleaner;

	@Autowired
	private CozinhaRepository cozinhaRepository;

	private int quantidadeCozinhasCadastradas;
	private String jsonCorretoCozinhaChinesa;

	@BeforeEach
	public void Setup() {
		RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();
		RestAssured.port = port;
		RestAssured.basePath = "/cozinhas";

		databaseCleaner.clearTables();
		preparaDados();

		jsonCorretoCozinhaChinesa = ResourceUtils.getContentFromResource("/json/correto/cozinha-chinesa.json");

	}

	@Test
	public void deve_RetornarStatus200_QuandoConsultarCozinhas() {

		given().accept(ContentType.JSON).when().get().then().statusCode(HttpStatus.OK.value());
	}

	@Test
	public void deve_Retornar_Quantidades_CorretasDeCozinha_QuandoConsultarCozinhas() {

		given().accept(ContentType.JSON).when().get().then().body("", Matchers.hasSize(quantidadeCozinhasCadastradas));

	}

	@Test
	public void deveRetornarStatus201_QuandoCadastrarCozionha() {
		given().body(jsonCorretoCozinhaChinesa).contentType(ContentType.JSON).accept(ContentType.JSON).when().post()
				.then().statusCode(HttpStatus.CREATED.value());

	}

	@Test
	public void deve_Retornar_Respostas_Status_Correto_QuandoConsultarCozinhaExistente() {
		given().pathParam("cozinhaCodigo", 2).accept(ContentType.JSON).when().get("/{cozinhaCodigo}").then()
				.statusCode(HttpStatus.OK.value()).body("nome", equalTo("Americana"));

	}

	@Test
	public void deve_Retornar__Status_404_QuandoConsultarCozinhaInexistente() {
		given().pathParam("cozinhaCodigo", COZINHA_CODIGO_INEXISTENTE).accept(ContentType.JSON).when()
				.get("/{cozinhaCodigo}").then().statusCode(HttpStatus.NOT_FOUND.value());

	}

	private void preparaDados() {
		Cozinha cozinhaTailadensa = new Cozinha();
		cozinhaTailadensa.setNome("Tailandesa");
		cozinhaRepository.save(cozinhaTailadensa);

		Cozinha cozinhaAmericana = new Cozinha();
		cozinhaAmericana.setNome("Americana");
		cozinhaRepository.save(cozinhaAmericana);

		quantidadeCozinhasCadastradas = (int) cozinhaRepository.count();

	}
}
