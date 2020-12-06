package com.ynov.microservices.tlm.quote;

import java.util.Collection;
import java.util.Iterator;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.ynov.microservices.tlm.author.Author;
import com.ynov.microservices.tlm.author.AuthorRepository;

@Controller
public class QuoteController {
	
	/******************************************** VARIABLES **************************************************/
	private final QuoteRepository quotes;
	private final AuthorRepository authors;
	
	/******************************************** CONSTRUCTOR ************************************************/
	public QuoteController (QuoteRepository quotes, AuthorRepository authors) {
		this.quotes = quotes;
		this.authors = authors;
	}

	/******************************************** BINDER ****************************************************/
	@InitBinder
	public void setAllowedFields(WebDataBinder dataBinder) {
		dataBinder.setDisallowedFields("id");
	}
	
	/****************************************************************************************************/
	/******************************************** GET MAPPING *******************************************/
	/****************************************************************************************************/
	@GetMapping("/quotes")
	public Iterable<Quote> findQuotes() {
		return quotes.findAll();			
	}
	
	@GetMapping("/quotes/{id}")
	public Optional<Quote> findQuoteById(@PathVariable("id") Integer id) {
		return quotes.findById(id);			
	}
	
	@GetMapping("/quotes/pseudo/{pseudo}")
	public Iterable<Quote> findQuoteByPseudo(@PathVariable("id") Integer author) {
		return quotes.findByAuthor(author);			
	}	
	
	/****************************************************************************************************/
	/******************************************** POST MAPPING ******************************************/
	/****************************************************************************************************/
	@PostMapping("/quotes/new")
	public String addQuote(@RequestParam("pseudo") String pseudo, @RequestParam("content") String content) {
		// REPO
		if(!StringUtils.hasLength(content)) return null;
		if(!StringUtils.hasLength(pseudo)) return null;
		
		Collection<Quote> quoteList = (Collection<Quote>) quotes.findAll();
		Collection<Author> authorFullList = (Collection<Author>) authors.findAll();
		Collection<Author> authorList = authors.findByPseudo(pseudo);
		
		// AUTHOR
		Author author = new Author();
		Quote quote = new Quote();
		Integer authorId = 0;
		/*
		if(!authorList.isEmpty()) {			
			Iterator<Author> iterator = authorList.iterator();
			while(iterator.hasNext()) {
				if(iterator.next().getPseudo().equals(pseudo)) {
					author = iterator.next();
					authorId = author.getId();
					break;
				}
			}
		}
		*/
		Optional<Author> authorOpt = authors.findPseudo(pseudo);
		if (authorOpt.isPresent()) {
			author = authorOpt.get();
			authorId = author.getId();
		}
		
		if(authorId == 0){
			author.setPseudo(pseudo);
			author.setId(authorFullList.size()+1);
			authorId = author.getId();
		}		
		// QUOTE
		quote.setContent(content);
		if(quoteList.isEmpty()) {
			quote.setId(1);
		}
		else {
			quote.setId(quoteList.size()+1);
		}
		
		quote.setAuthor(authorId);
		quote.setUpVote(0);
		quote.setDownVote(0);
		author.addQuote(quote.getId());
		this.authors.save(author);
		this.quotes.save(quote);
	return "redirect:/quotes";
	}
	
	/****************************************************************************************************/
	/******************************************** PUT MAPPING *******************************************/
	/****************************************************************************************************/
	@PutMapping("/quotes/{id}/edit/content")
	public Quote editQuoteContent(@PathVariable("id") Integer id, @RequestParam("content") String content) {
		Optional<Quote> quoteOpt = quotes.findById(id);
		if (quoteOpt.isPresent()) {
			Quote quote = quoteOpt.get();
			quote.setContent(content);
			quotes.save(quote);
			return quote;
		}
		return null;
	}
	
	@PutMapping("/quotes/{id}/edit/upvote")
	public Quote editQuoteUpVote(@PathVariable("id") Integer id) {
		Optional<Quote> quoteOpt = quotes.findById(id);
		if (quoteOpt.isPresent()) {
			Quote quote = quoteOpt.get();
			quote.setUpVote(quote.getUpVote()+1);
			quotes.save(quote);
		}
		return quoteOpt.get();
	}
	
	@PutMapping("/quotes/{id}/edit/downvote")
	public Quote editQuoteDownVote(@PathVariable("id") Integer id) {
		Optional<Quote> quoteOpt = quotes.findById(id);
		if (quoteOpt.isPresent()) {
			Quote quote = quoteOpt.get();
			quote.setDownVote(quote.getDownVote()+1);
			quotes.save(quote);
		}
		return quoteOpt.get();
	}	
}