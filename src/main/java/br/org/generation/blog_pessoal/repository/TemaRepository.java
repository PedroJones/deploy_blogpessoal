package br.org.generation.blog_pessoal.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.org.generation.blog_pessoal.model.Tema;



@Repository
public interface TemaRepository extends JpaRepository<Tema, Long> {
	
	  public List <Tema> findAllByDescricaoContainingIgnoreCase (String descricao);

}
