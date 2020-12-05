package com.ynov.microservices.tlm.quote;

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
	public String processCreation(Author author, @Valid Quote quote, BindingResult result, ModelMap model) {
		if (StringUtils.hasLength(quote.getContent()) && quote.isNew()) {
			result.rejectValue("name", "duplicate", "already exists");
		}
		author.addQuote(quote.getId());
		if (result.hasErrors()) {
			model.put("quote", quote);
			return "500 : Quote id not valid";
		}
		else {
			this.quotes.save(quote);
			return "redirect:/quotes/{quoteId}";
		}
	}
}