package com.ynov.microservices.tlm.author;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.ynov.microservices.pojo.AuthorDetailsPojo;
import com.ynov.microservices.pojo.AuthorInfoPojo;
import com.ynov.microservices.pojo.AuthorPojo;
import com.ynov.microservices.tlm.quote.Quote;
import com.ynov.microservices.tlm.quote.QuoteRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
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
	
	/****************************************** BINDER **************************************************/
	@InitBinder
	public void setAllowedFields(WebDataBinder dataBinder) {
		dataBinder.setDisallowedFields("id");
	}
	
	/****************************************************************************************************/
	/******************************************** GET MAPPING *******************************************/
	/****************************************************************************************************/
	
	/******************************************** FIND AUTHORS *******************************************/
	@GetMapping("/authors")
	@HystrixCommand
	public String findAuthors() {
		Iterator<Author> authorIter = authors.findAll().iterator();
		ArrayList<AuthorPojo> authorsInfos = new ArrayList<AuthorPojo>();
		AuthorPojo authorPojo = new AuthorPojo();
		while(authorIter.hasNext()) {
			authorPojo.setPseudo(authorIter.next().getPseudo());
			authorPojo.setComments(authorIter.next().getComments());
			authorPojo.setQuotes(authorIter.next().getQuotes());
			authorsInfos.add(authorPojo);
		}		
		return authorsInfos.toString();		
	}
	
	/******************************************** AUTHORS DETAILS  *******************************************/
	@GetMapping("/authors/{id}/details")
	@HystrixCommand
	public String authorsDetails(@PathVariable("id") Integer id) {
		Author author = authors.findById(id);
		if(author != null) {
			AuthorDetailsPojo authorDetailsPojo = new AuthorDetailsPojo();
			authorDetailsPojo.setPseudo(author.getPseudo());
			Iterator<Quote> quoteIter = quotes.findByAuthor(id).iterator();	
			while(quoteIter.hasNext()) {
				authorDetailsPojo.addQuote(quoteIter.next().getContent());
			}
			return authorDetailsPojo.toString();
		}			
		return null;		
	}
	
	/******************************************** FIND BY AUTHOR ID *******************************************/
	@GetMapping("/authors/{id}")
	@HystrixCommand
	public String findAuthorsById(@PathVariable("id") Integer id) {
		AuthorPojo authorPojo = new AuthorPojo();
		Author author = authors.findById(id);
		if(author != null) {
			authorPojo.setId(author.getId());
			authorPojo.setPseudo(author.getPseudo());
			authorPojo.setQuotes(author.getQuotes());
			authorPojo.setComments(author.getComments());
			return authorPojo.toString();			
		}
		return null;
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
				upVote += iter.next().getUpVote();
				downVote += iter.next().getDownVote();
			}
			authorInfo.setAvgUpvote(upVote/quoteList.size());
			authorInfo.setAvgUpvote(downVote/quoteList.size());
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
	public String addAuthor(@RequestParam("pseudo") String pseudo) {
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
	return "redirect:/authors/" + author.getId();
	}
	
	/******************************************** UPDATE AUTHOR *******************************************/
	@PostMapping("/authors/{authorId}/edit")
	@HystrixCommand
	public String updateAuthor(@PathVariable("authorId") Integer authorId, @RequestParam("pseudo") String pseudo) {
		Author author = authors.findById(authorId);
		author.setPseudo(pseudo);
		authors.save(author);
		return "redirect:/authors/" + authorId;			
	}
	
	/****************************************************************************************************/
	/******************************************** DELETE MAPPING ****************************************/
	/****************************************************************************************************/
	@DeleteMapping("authors/{id}")
	@HystrixCommand
	public String deleteAuthor(@PathVariable("id") Integer id) {
		authors.deleteById(id);
		return null;		
	}
	
}
