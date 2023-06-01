package com.devsuperior.workshopmongo.config;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.TimeZone;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;

import com.devsuperior.workshopmongo.domain.Post;
import com.devsuperior.workshopmongo.domain.User;
import com.devsuperior.workshopmongo.dto.AuthorDTO;
import com.devsuperior.workshopmongo.dto.CommentDTO;
import com.devsuperior.workshopmongo.repositories.PostRepository;
import com.devsuperior.workshopmongo.repositories.UserRepository;

@Configuration
public class SeedingDatabase implements CommandLineRunner {
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private PostRepository postRepository;

	@Override
	public void run(String... args) throws Exception {
		
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		sdf.setTimeZone(TimeZone.getTimeZone("GMT"));
		
		userRepository.deleteAll();
		postRepository.deleteAll();
		
		User maria = new User(null, "Maria Brown", "maria@gmail.com");
		User alex = new User(null, "Alex Green", "alex@gmail.com");
		User bob = new User(null, "Bob Grey", "bob@gmail.com");
		User john = new User(null, "John Marsh", "john@gmail.com");
		User clark = new User(null, "Clark Stone", "clark@gmail.com");
		
		userRepository.saveAll(Arrays.asList(maria, alex, bob, john, clark));
		
		Post post1 = new Post(null, sdf.parse("21/03/2018"), "Partiu viagem", "Vou viajar para São Paulo. Abraços!", new AuthorDTO(maria));
		Post post2 = new Post(null, sdf.parse("23/03/2018"), "Bom dia", "Acordei feliz hoje!", new AuthorDTO(maria));
		Post post3 = new Post(null, sdf.parse("03/04/2018"), "Dia de praia", "Hoje vou conhecer as praias lindas de Fortaleza-CE!", new AuthorDTO(john));
		Post post4 = new Post(null, sdf.parse("10/04/2018"), "Leitura + natureza = match", "Nada melhor do que tirar uma tarde de folga com seu melhor amigo para lerem na natureza.", new AuthorDTO(clark));
		
		CommentDTO c1 = new CommentDTO("Boa viagem mano!", sdf.parse("21/03/2018"), new AuthorDTO(alex));
		CommentDTO c2 = new CommentDTO("Aproveite", sdf.parse("22/03/2018"), new AuthorDTO(bob));
		CommentDTO c3 = new CommentDTO("Tenha um ótimo dia!", sdf.parse("23/03/2018"), new AuthorDTO(alex));
		CommentDTO c4 = new CommentDTO("As praias de Fortaleza são maravilhosas!", sdf.parse("04/04/2018"), new AuthorDTO(clark));
		CommentDTO c5 = new CommentDTO("Devemos marcar mais vezes!", sdf.parse("12/04/2018"), new AuthorDTO(john));
		
		
		post1.getComments().addAll(Arrays.asList(c1, c2));
		post2.getComments().addAll(Arrays.asList(c3));
		post3.getComments().addAll(Arrays.asList(c4));
		post4.getComments().addAll(Arrays.asList(c5));
		
		postRepository.saveAll(Arrays.asList(post1, post2, post3, post4));
		
		maria.getPosts().addAll(Arrays.asList(post1, post2));
		john.getPosts().add(post3);
		clark.getPosts().add(post4);
		
		userRepository.saveAll(Arrays.asList(maria, john, clark));
	}
}
