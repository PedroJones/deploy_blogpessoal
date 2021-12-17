package br.org.generation.blog_pessoal.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import br.org.generation.blog_pessoal.model.Tema;
import br.org.generation.blog_pessoal.repository.TemaRepository;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class TemaControllerTest {
	@Autowired
	private TestRestTemplate testRestTemplate;

	@Autowired
	private TemaRepository temaRepository;

	@Test
	@Order(1)
	@DisplayName("Postar um Tema")
	public void deveCriarUmTema() {

		HttpEntity<Tema> requisicao = new HttpEntity<Tema>(new Tema(0L, 
			"Repositório Sistema de Banco"));

		ResponseEntity<Tema> resposta = testRestTemplate
			.withBasicAuth("root", "root")
			.exchange("/temas", HttpMethod.POST, requisicao, Tema.class);

		assertEquals(HttpStatus.CREATED, resposta.getStatusCode());
		assertEquals(requisicao.getBody().getDescricao(), resposta.getBody().getDescricao());

	}

	@Test
	@Order(2)
	@DisplayName("Listar os Temas por Id")
	public void deveMostrarOsTemasPorId() {

		HttpEntity<Tema> requisicao = new HttpEntity<Tema>(new Tema(0L, 
				"Repositório Sistema de Banco"));
		
		HttpEntity<Tema> req = new HttpEntity<Tema>(new Tema(0L, 
				"Repositório Git Hub"));

		ResponseEntity<String> resposta = testRestTemplate
			.withBasicAuth("root", "root")
			.exchange("/temas/1", HttpMethod.GET, null, String.class);

		assertEquals(HttpStatus.OK, resposta.getStatusCode());
	}


	@Test
	@Order(3)
	@DisplayName("Alterar um Tema")
	public void deveAtualizarUmTema() {

		Tema temaCreate = temaRepository.save(new Tema(0L,
				"Linguagem PHP"));

		Tema temaUpdate = new Tema(temaCreate.getId(), 
			"Repositório de Netflix");
		
		HttpEntity<Tema> requisicao = new HttpEntity<Tema>(temaUpdate);

		ResponseEntity<Tema> resposta = testRestTemplate
			.withBasicAuth("root", "root")
			.exchange("/temas", HttpMethod.PUT, requisicao, Tema.class);

		assertEquals(HttpStatus.OK, resposta.getStatusCode());
		assertEquals(temaUpdate.getDescricao(), resposta.getBody().getDescricao());
		
	}

	@Test
	@Order(4)
	@DisplayName("Listar todos os Temas")
	public void deveMostrarTodosTemas() {

		HttpEntity<Tema> requisicao = new HttpEntity<Tema>(new Tema(0L, 
				"Repositório Sistema de Banco"));
		
		HttpEntity<Tema> req = new HttpEntity<Tema>(new Tema(0L, 
				"Repositório Git Hub"));

		ResponseEntity<String> resposta = testRestTemplate
			.withBasicAuth("root", "root")
			.exchange("/temas", HttpMethod.GET, null, String.class);

		assertEquals(HttpStatus.OK, resposta.getStatusCode());
	}
	
	@Test
	@Order(5)
	@DisplayName("Apagar um Tema")
	public void deveApagarUmTema() {

		Tema temaDelete = temaRepository.save(new Tema(0L,
				"Linguagem JavaScript"));

		ResponseEntity<String> resposta = testRestTemplate
				.withBasicAuth("root", "root")
				.exchange("/temas/" + temaDelete.getId(), HttpMethod.DELETE, null, String.class);

		assertEquals(HttpStatus.NO_CONTENT, resposta.getStatusCode());
	}
}
