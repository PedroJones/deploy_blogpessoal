package br.org.generation.blog_pessoal.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;

import br.org.generation.blog_pessoal.model.Tema;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class TemaRepositoryTest {
	@Autowired
	private TemaRepository temaRepository;
	
	@BeforeAll
	void start(){

		temaRepository.save(new Tema(0L, "Repositório Sistema de Banco"));
		
		temaRepository.save(new Tema(0L, "Repositório Ecommerce"));
		
		temaRepository.save(new Tema(0L, "Repositório Boas Práticas de Programação"));

		temaRepository.save(new Tema(0L, "Repositório GitHub"));

	}


	@Test
	@DisplayName("Retorna 4 descrições")
	public void deveRetornarQuatroDescricoes() {

		List<Tema> listaDeTemas = temaRepository.findAllByDescricaoContainingIgnoreCase("Repositório");
		assertEquals(4, listaDeTemas.size());
		assertTrue(listaDeTemas.get(0).getDescricao().equals("Repositório Sistema de Banco"));
		assertTrue(listaDeTemas.get(1).getDescricao().equals("Repositório Ecommerce"));
		assertTrue(listaDeTemas.get(2).getDescricao().equals("Repositório Boas Práticas de Programação"));
		assertTrue(listaDeTemas.get(3).getDescricao().equals("Repositório GitHub"));
	}
}
