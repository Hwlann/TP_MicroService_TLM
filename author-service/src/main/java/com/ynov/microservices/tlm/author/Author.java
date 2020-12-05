package com.ynov.microservices.tlm.author;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;

@Entity
@Table(name = "authors")
public class Author {
	
	/****************************************************************************************************/
	/******************************************** VARIABLES *********************************************/
	/****************************************************************************************************/
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Integer id;	

	@Column(name = "pseudo")
	@NotEmpty
	private String pseudo;
	
	private HashSet<Integer> quotes;	
	
	/****************************************************************************************************/
	/******************************************** METHODS ***********************************************/
	/****************************************************************************************************/
	
	/******************************************** QUOTES ************************************************/
	protected Set<Integer> getQuotesInternal() {
		if (this.quotes == null) {
			this.quotes = new HashSet<>();
		}
		return this.quotes;
	}

	protected void setQuotesInternal(Set<Integer> quotes) {
		this.quotes = new HashSet<>(quotes);
	}

	public List<Integer> getQuotes() {
		return new ArrayList<>(getQuotesInternal());

	}

	public void addQuote(Integer quoteId) {
			getQuotesInternal().add(quoteId);
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
