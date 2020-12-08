package com.ynov.microservices.tlm.author;


import java.util.Collection;
import java.util.Optional;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;

@RestController
@RequestMapping("/")
public class AuthorController {

	/******************************************** VARIABLES ********************************************/
	private AuthorRepository authors;
	
	/******************************************** CONTRUCTOR *******************************************/
	public AuthorController(AuthorRepository authors) {
		this.authors = authors;
		
		/*
		// Create anonymous Author
		Author author = new Author();
		author.setId(1);
		author.setPseudo("Anonymous");
		authors.save(author);
		*/
	}
	
	/*
	 *  Annotation @HystrixCommand on App-gateway's QuoteRepository's methods
	 */
	
	/****************************************************************************************************/
	/******************************************** GET MAPPING *******************************************/
	/****************************************************************************************************/
	@GetMapping("/authors")
	@HystrixCommand
	public Iterable<Author> getAuthor(){
			return authors.findAll();
	}
	
	@GetMapping("/authors/{id}")
	@HystrixCommand
	public Optional<Author> getAuthorById(@PathVariable("id") Integer id){
			return authors.findById(id);
	}
	
	@GetMapping("/authors/pseudo/{pseudo}")
	@HystrixCommand
	public Collection<Author> findByPseudo(@PathVariable("pseudo") String pseudo) throws InterruptedException {
		return authors.findByPseudo(pseudo);
	}
	
	@GetMapping("/authors/exact-pseudo/{pseudo}")
	@HystrixCommand
	public Optional<Author> findPseudo(@PathVariable("pseudo") String pseudo) throws InterruptedException {
		return authors.findPseudo(pseudo);
	}
	
	/****************************************************************************************************/
	/******************************************** POST MAPPING ******************************************/
	/****************************************************************************************************/
	@PostMapping("/authors/new")
	public Author addAuthor(@RequestParam("pseudo") String pseudo){
			if(!StringUtils.hasLength(pseudo)) return null;
			Author author = new Author();
			Collection<Author> authorList = (Collection<Author>) authors.findAll();
			if(authorList.isEmpty()) {
				author.setId(1);
			}
			else {
				author.setId(authorList.size()+1);
			}
			author.setPseudo(pseudo);
			return authors.save(author);
	}
	
	@PostMapping("/authors/")
	@HystrixCommand
	public Author save(@RequestBody Author author){
			return authors.save(author);
	}
	
	@PostMapping("/authors/{authorId}/add/quote")
	@HystrixCommand
	public Author addQuoteToAuthor(@PathVariable("authorId") Integer authorId, @RequestParam("quoteId") Integer quoteId) {
		if(quoteId == null) return null;
		Optional<Author> authorOpt = authors.findById(authorId);
		if (authorOpt.isPresent()) {
			Author author = authorOpt.get();
			author.addQuote(quoteId);
			authors.save(author);
			return author;
		}		
		return null;
	}
	
	@PostMapping("/authors/{authorId}/add/comment")
	@HystrixCommand
	public Author addCommentToAuthor(@PathVariable("authorId") Integer authorId, @RequestParam("commentId") Integer commentId) {
		Optional<Author> authorOpt = authors.findById(authorId);
		if (authorOpt.isPresent()) {
			Author author = authorOpt.get();
			author.addComment(commentId);
			authors.save(author);
			return author;
		}		
		return null;
	}
	
	/****************************************************************************************************/
	/******************************************** DELETE MAPPING ****************************************/
	/****************************************************************************************************/
	@DeleteMapping("/authors/{id}")
	@HystrixCommand
	public void deleteAuthor(@PathVariable("id") Integer id){
		Optional<Author> authorOpt = authors.findById(id);
		if(!authorOpt.isEmpty()) {
			authors.deleteById(authorOpt.get().getId());
		}
	}
	
}
