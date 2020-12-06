package com.ynov.microservices.tlm.author;

import java.util.HashSet;
import java.util.LinkedHashSet;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

public class Author {
	
	/****************************************************************************************************/
	/******************************************** VARIABLES *********************************************/
	/****************************************************************************************************/
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;	
	@Column(name = "pseudo")
	private String pseudo;
	@Column
	private HashSet<Integer> quotes;	
	
	/****************************************************************************************************/
	/******************************************** METHODS ***********************************************/
	/****************************************************************************************************/
	
	/******************************************** QUOTES ************************************************/
	public HashSet<Integer> getQuotes() {
		if (this.quotes == null) {
			this.quotes = new LinkedHashSet<>();
		}
		return this.quotes;
	}

	public void addQuote(Integer quoteId) {
			getQuotes().add(quoteId);
	}
	
	/******************************************** ID *****************************************************/
	public Integer getId() {
		return id;
	}
	
	public void setId(Integer id) {
		this.id = id;
	}
	
	/******************************************** PSEUDO *************************************************/
	public String getPseudo() {
		return pseudo;
	}
	
	public void setPseudo(String pseudo) {
		this.pseudo = pseudo;
	}
}
