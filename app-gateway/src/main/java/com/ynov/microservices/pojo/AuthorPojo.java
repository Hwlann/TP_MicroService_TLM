package com.ynov.microservices.pojo;

import java.util.HashSet;
import java.util.LinkedHashSet;

public class AuthorPojo {	

	private Integer id;	
	private String pseudo;
	private HashSet<Integer> quotes;
	private HashSet<Integer> comments;	
	
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
	
	/******************************************** COMMENTS ************************************************/
	public HashSet<Integer> getComments() {
		if (this.comments == null) {
			this.comments = new LinkedHashSet<>();
		}
		return this.comments;
	}

	public void addComment(Integer commentId) {
		getComments().add(commentId);
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
	
	public void setQuotes(HashSet<Integer> quotes) {
		this.quotes = quotes;
	}

	public void setComments(HashSet<Integer> comments) {
		this.comments = comments;
	}
}
