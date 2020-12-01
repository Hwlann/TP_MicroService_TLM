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

@Entity
@Table(name = "authors")
public class Author {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Integer m_id;
	
	@Column(name = "pseudo")
	private String m_pseudo;
	
	private HashSet<Integer> quotes;	
	
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
	
	/*
	 * METHODS
	 */
	public Integer getId() {
		return m_id;
	}
	
	public void setId(Integer id) {
		m_id = id;
	}
	public String getPseudo() {
		return m_pseudo;
	}
	
	public void setPseudo(String pseudo) {
		m_pseudo = pseudo;
	}
}
