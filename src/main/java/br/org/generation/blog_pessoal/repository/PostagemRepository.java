package br.org.generation.blog_pessoal.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.org.generation.blog_pessoal.model.Postagem;

@Repository
public interface PostagemRepository extends JpaRepository<Postagem, Long> {

	
	public List <Postagem> findAllByTituloContainingIgnoreCase(String titulo);
}
