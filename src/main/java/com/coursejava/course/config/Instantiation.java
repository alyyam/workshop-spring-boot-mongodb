package com.coursejava.course.config;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.TimeZone;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.core.aggregation.ArrayOperators.ArrayElemAt;

import com.coursejava.course.domain.Post;
import com.coursejava.course.domain.User;
import com.coursejava.course.dto.AuthorDTO;
import com.coursejava.course.dto.CommentDTO;
import com.coursejava.course.repository.PostRepository;
import com.coursejava.course.repository.UserRepository;

@Configuration
public class Instantiation implements CommandLineRunner {
	
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private PostRepository postRepository;
	

	@Override
	public void run(String... args) throws Exception {
		SimpleDateFormat sdf =  new SimpleDateFormat("dd/MM/yyyy");
		sdf.setTimeZone(TimeZone.getTimeZone("GMT"));
		userRepository.deleteAll();
		postRepository.deleteAll();
		
		User maria = new User(null, "Maria Brown", "maria@gmail.com");
		User alex = new User(null, "Alex Green", "alex@gmail.com");
		User bob = new User(null, "Bob Grey", "bob@gmail.com");
		
		
		userRepository.saveAll(Arrays.asList(maria, alex, bob));
		
		
		Post post1 =  new Post(null, sdf.parse("21/03/2022"), "Holidays", "I am going to Ireland, Ihaaa",new AuthorDTO(maria) );
		Post post2 =  new Post(null, sdf.parse("22/03/2022"), "Good morning", "I am so happy today", new AuthorDTO(maria ));
		
		CommentDTO c1 =  new CommentDTO("Have a nice trip", sdf.parse("21/03/2022"), new AuthorDTO(alex));
		CommentDTO c2 =  new CommentDTO("Enjoy it", sdf.parse("23/03/2022"), new AuthorDTO(bob));
		
		post1.getComments().addAll(Arrays.asList(c1));
		post2.getComments().addAll(Arrays.asList(c2));
		
		postRepository.saveAll(Arrays.asList(post1, post2));
		
		maria.getPosts().addAll(Arrays.asList(post1,post2));
		userRepository.save(maria);

	}

}
