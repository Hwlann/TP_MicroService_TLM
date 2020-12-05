package com.ynov.microservices.tlm.quote;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;

import com.ynov.microservices.tlm.author.AuthorRepository;


@Controller
public class QuoteController {
	
	/******************************************** VARIABLES ********************************************/
	private final QuoteRepository quotes;
	private final AuthorRepository authors;
	
	/******************************************** CONSTRUCTOR ******************************************/
	public QuoteController(QuoteRepository quotes, AuthorRepository authors) {
		this.quotes = quotes;
		this.authors = authors;
	}
	
	/****************************************** BINDER **************************************************/
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
	
	/****************************************************************************************************/
	/******************************************** POST MAPPING ******************************************/
	/****************************************************************************************************/
	
	/****************************************************************************************************/
	/******************************************** DELETE MAPPING ****************************************/
	/****************************************************************************************************/	
}