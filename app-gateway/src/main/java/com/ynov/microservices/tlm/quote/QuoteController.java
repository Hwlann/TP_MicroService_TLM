package com.ynov.microservices.tlm.quote;

import java.util.Collection;
import java.util.Iterator;

import javax.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.util.StringUtils;

import com.ynov.microservices.tlm.author.Author;
import com.ynov.microservices.tlm.author.AuthorRepository;

@Controller
@RequestMapping("authors/{authorId}")
public class QuoteController {
	
	/******************************************** VARIABLES **************************************************/
	private final QuoteRepository quotes;
	private final AuthorRepository authors;
	
	/******************************************** CONSTRUCTOR ************************************************/
	public QuoteController (QuoteRepository quotes, AuthorRepository authors) {
		this.quotes = quotes;
		this.authors = authors;
	}
	
	/******************************************** MODEL ******************************************************/
	@ModelAttribute("author")
	public Author findOwner(@PathVariable("authorId") int authorId) {
		return this.authors.findById(authorId);
	}
	/******************************************** BINDER ****************************************************/
	@InitBinder("author")
	public void initOwnerBinder(WebDataBinder dataBinder) {
		dataBinder.setDisallowedFields("id");
	}

	@InitBinder("quote")
	public void initPetBinder(WebDataBinder dataBinder) {
		dataBinder.setValidator(new QuoteValidator());
	}
	
	/****************************************************************************************************/
	/******************************************** GET MAPPING *******************************************/
	/****************************************************************************************************/
	@GetMapping("/quotes/{quoteId}/edit")
	public ModelMap initUpdate(@PathVariable("quoteId") Integer quoteId, ModelMap model) {
		Quote quote = this.quotes.findById(quoteId);
		model.put("quote", quote);
		return model;
	}	
	
	/****************************************************************************************************/
	/******************************************** POST MAPPING ******************************************/
	/****************************************************************************************************/
	@PostMapping("/quotes/{quoteId}/edit")
	public String processUpdate(@Valid Quote quote, BindingResult result, Author author, ModelMap model) {
		if (result.hasErrors()) {
			quote.setAuthor(author.getId());
			model.put("quote", quote);
			return "500 : Quote or Author Not found";
		}
		else {
			author.addQuote(quote.getId());
			this.quotes.save(quote);
			return "redirect:/authors/{authorId}";
		}
	}
	
	@PostMapping("/quotes/new")
	public String newQuote(@RequestParam("pseudo") String pseudo, @RequestParam("content") String content) {
		Collection<Quote> quoteList = quotes.findAll();
		Collection<Author> authorList = authors.findByPseudo(pseudo);
		
		Iterator<Author> iterator = authorList.iterator();
		Integer authorId = 0;
		while(iterator.hasNext()) {
			if(iterator.next().getPseudo() == pseudo) {
				authorId = iterator.next().getId();
				break;
			}
		}
				
		Quote quote = new Quote();
		quote.setContent(content);
		if(quoteList.isEmpty()) {
			quote.setId(1);
		}
		else {
			quote.setId(quoteList.size()+1);
		}
		quote.setAuthor(authorId);
		this.quotes.save(quote);
	return "redirect:/quotes/" + quote.getId();
	}
}