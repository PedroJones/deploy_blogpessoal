package br.org.generation.blog_pessoal.service;

import java.nio.charset.Charset;
import java.util.Optional;

import org.apache.commons.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import br.org.generation.blog_pessoal.model.Usuario;
import br.org.generation.blog_pessoal.model.UsuarioLogin;
import br.org.generation.blog_pessoal.repository.UsuarioRepository;

/*Classe de Serviço
Serve para indicar o que deverá ser autorizado ou não 
... fazer o cadastro do usuário. */


@Service
public class UsuarioService {

		@Autowired
		private UsuarioRepository usuarioRepository;
		
		/*if - checa se o usuário existe, se existir, não cadastra ele novamente.
			se não exisitr, criptografa a senha e cadastra o usuário.*/
		
		public Optional<Usuario> cadastrarUsuario(Usuario usuario) {

			if (usuarioRepository.findByUsuario(usuario.getUsuario()).isPresent())
				throw new ResponseStatusException(
						HttpStatus.BAD_REQUEST, "O Usuário já existe!", null);
			
			
			usuario.setSenha(criptografarSenha(usuario.getSenha()));

			return Optional.of(usuarioRepository.save(usuario));
		
		}

		public Optional<Usuario> atualizarUsuario(Usuario usuario) {
			/*If - checa se o usuario existe, se ele existe, 
			 * atualiza o usuário, se não responde uma Optional nula
			 */
			if (usuarioRepository.findById(usuario.getId()).isPresent()){
				Optional <Usuario> buscaUsuario = usuarioRepository.findByUsuario(usuario.getUsuario());
				if (buscaUsuario.isPresent()) {
					if (buscaUsuario.get().getId() != usuario.getId()) 
						 throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "o usuario já existe!", null);
				}
			
		
				usuario.setSenha(criptografarSenha(usuario.getSenha()));
				
				return Optional.of(usuarioRepository.save(usuario));
				
			}
			return Optional.empty();

			
				
	
		}	

		public Optional<UsuarioLogin> autenticarUsuario(Optional<UsuarioLogin> usuarioLogin) {
				
			/*if - checa se o usuário está presente.
			if2 - checa se a senha digitada é compatível com senha criptografada no banco de dados. */
			Optional<Usuario> usuario = usuarioRepository.findByUsuario(usuarioLogin.get().getUsuario());

			if (usuario.isPresent()) {
				if (compararSenhas(usuarioLogin.get().getSenha(), usuario.get().getSenha())) {

					usuarioLogin.get().setId(usuario.get().getId());
					usuarioLogin.get().setNome(usuario.get().getNome());
					usuarioLogin.get().setFoto(usuario.get().getFoto());
					usuarioLogin.get().setToken(gerarBasicToken(usuarioLogin.get().getUsuario(), usuarioLogin.get().getSenha()));
					usuarioLogin.get().setSenha(usuario.get().getSenha());

					return usuarioLogin;

				}
			}	
			
			return Optional.empty();
			
		}

		private String criptografarSenha(String senha) {

			BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
			
			return encoder.encode(senha);

		}
		
		private boolean compararSenhas(String senhaDigitada, String senhaBanco) {
			
			BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
			
			return encoder.matches(senhaDigitada, senhaBanco);

		}

		private String gerarBasicToken(String usuario, String senha) {

			String token = usuario + ":" + senha;
			byte[] tokenBase64 = Base64.encodeBase64(token.getBytes(Charset.forName("US-ASCII")));
			//gerarBasicToken gera um token em base 64 ("US-ASCIII")...o "Basic " precisa ter espaço.
			return "Basic " + new String(tokenBase64);

		}
		
}
