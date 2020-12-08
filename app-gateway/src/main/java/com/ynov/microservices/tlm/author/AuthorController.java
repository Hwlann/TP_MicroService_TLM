package com.ynov.microservices.tlm.author;

import java.util.Collection;
import java.util.Iterator;
import java.util.Optional;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.ynov.microservices.pojo.AuthorDetailsPojo;
import com.ynov.microservices.pojo.AuthorInfoPojo;
import com.ynov.microservices.tlm.quote.Quote;
import com.ynov.microservices.tlm.quote.QuoteRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;

@RestController
public class AuthorController {

	/******************************************** VARIABLES ********************************************/
	private final AuthorRepository authors;
	private final QuoteRepository quotes;
	
	/******************************************** CONSTRUCTOR ******************************************/
	@Autowired
	public AuthorController(AuthorRepository authors, QuoteRepository quotes) {
		this.authors = authors;
		this.quotes = quotes;
	}
	
	/****************************************************************************************************/
	/******************************************** GET MAPPING *******************************************/
	/****************************************************************************************************/
	
	/******************************************** FIND AUTHORS *******************************************/
	@GetMapping("/authors")
	@HystrixCommand
	public Iterable<Author> findAuthors() {		
		return authors.findAll();	
	}
	
	/******************************************** AUTHORS DETAILS  *******************************************/
	@GetMapping("/authors/{id}/details")
	@HystrixCommand
	public AuthorDetailsPojo authorsDetails(@PathVariable("id") Integer id) {
		Author author = authors.findById(id);
		if(author != null) {
			AuthorDetailsPojo authorDetailsPojo = new AuthorDetailsPojo();
			authorDetailsPojo.setPseudo(author.getPseudo());
			Iterator<Quote> quoteIter = quotes.findByAuthor(id).iterator();	
			while(quoteIter.hasNext()) {
				authorDetailsPojo.addQuote(quoteIter.next().getContent());
			}			
			return authorDetailsPojo;
		}			
		return null;		
	}
	
	/******************************************** FIND BY AUTHOR ID *******************************************/
	@GetMapping("/authors/{id}")
	@HystrixCommand
	public Author findAuthorsById(@PathVariable("id") Integer id) {
		return authors.findById(id);
	}
	
	/******************************************** FIND BY AUTHOR PSEUDO  *******************************/
	@GetMapping("/authors/pseudo/{pseudo}")
	@HystrixCommand
	public Collection<Author> findAuthorsByPseudo(@PathVariable("pseudo") String pseudo) {
		return authors.findByPseudo(pseudo);
	}	

	/******************************************** GET RATIO ********************************************/
	@GetMapping("/authors/{id}/infos")
	@HystrixCommand
	public AuthorInfoPojo getRatio(@PathVariable("id") Integer id) {		
		Author author = authors.findById(id);
		if(author != null) {
			Integer upVote = 0;
			Integer downVote = 0;
			AuthorInfoPojo authorInfo = new AuthorInfoPojo();
			authorInfo.setPseudo(author.getPseudo());
			Collection<Quote> quoteList = quotes.findByAuthor(id);
			Iterator<Quote> iter = quoteList.iterator();
			while(iter.hasNext()) {
				Quote quote = iter.next();
				upVote += quote.getUpVote();
				downVote += quote.getDownVote();
			}
			authorInfo.setAvgUpvote(upVote/quoteList.size());
			authorInfo.setAvgDownvote(downVote/quoteList.size());
			return authorInfo;			
		}
		return null;
	}	
	
	/****************************************************************************************************/
	/******************************************** POST MAPPING ******************************************/
	/****************************************************************************************************/
	
	/******************************************** ADD AUTHOR *******************************************/
	@PostMapping("/authors/new")
	@HystrixCommand
	public Author addAuthor(@RequestParam("pseudo") String pseudo) {		
		Optional<Author> authorOpt = authors.findPseudo(pseudo);
	 	if(authorOpt.isEmpty()) {
			Collection<Author> authorList = (Collection<Author>) authors.findAll();
	 		Author author = new Author();
			author.setPseudo(pseudo);
			if(authorList.isEmpty()) {
				author.setId(1);
			}
			else {
				author.setId(authorList.size()+1);
			}			
			this.authors.save(author);
			return author;
		}
	return null;
	}
	
	/******************************************** UPDATE AUTHOR *******************************************/
	@PostMapping("/authors/{authorId}/edit")
	@HystrixCommand
	public Author updateAuthor(@PathVariable("authorId") Integer authorId, @RequestParam("pseudo") String pseudo) {
		Author author = authors.findById(authorId);
		author.setPseudo(pseudo);
		authors.save(author);
		return author;			
	}
	
	/****************************************************************************************************/
	/******************************************** DELETE MAPPING ****************************************/
	/****************************************************************************************************/
	@DeleteMapping("authors/{id}")
	@HystrixCommand
	public void deleteAuthor(@PathVariable("id") Integer id) {
		authors.deleteById(id);		
	}
	
}
