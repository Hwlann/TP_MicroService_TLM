package com.ynov.microservices.pojo;

import java.util.HashSet;

public class AuthorDetailsPojo {
	private String pseudo;
	private HashSet<String> quoteList;
	
	public AuthorDetailsPojo() {
		quoteList = new HashSet<>();
	}

	public String getPseudo() {
		return pseudo;
	}

	public void setPseudo(String pseudo) {
		this.pseudo = pseudo;
	}

	public HashSet<String> getQuoteList() {
		return quoteList;
	}

	public void addQuote(String quote) {
		quoteList.add(quote);
	}	
}
