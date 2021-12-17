package br.org.generation.blog_pessoal.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.org.generation.blog_pessoal.model.Usuario;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long>  {


		/**
		 * Método que busca um usuário pelo seu usuario (email).
		 * 
		 * select * from tb_usuarios where usuario = "usuario procurado"
		 */
		public Optional<Usuario> findByUsuario(String usuario);
				/**
				 * Optional<>
					Pode me retornar um objeto, mas pode não retornar.
					Por exemplo, procura um objeto da classe usuário. 
					Se encontrar, você pode retornar um objeto.
					Se não encontrar, retorna nada.
				 */
		//Método criado para a Sessão de testes
		public List<Usuario> findAllByNomeContainingIgnoreCase(String nome);
}

