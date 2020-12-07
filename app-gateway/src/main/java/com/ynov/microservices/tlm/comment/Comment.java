package com.ynov.microservices.tlm.comment;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import org.springframework.format.annotation.DateTimeFormat;

public class Comment {
	/****************************************************************************************************/
	/******************************************** VARIABLES *********************************************/
	/****************************************************************************************************/
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;		
	@Column(name = "commment_date")
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private LocalDate date;	
	@Column
	private String content;
	@Column
	private Integer author;
	@Column
	private Integer quote;	
	
	/****************************************************************************************************/
	/******************************************** METHODS ***********************************************/
	/****************************************************************************************************/
	
	/******************************************** CONSTRUCTOR *******************************************/
	public Comment() {
		this.date = LocalDate.now();
	}
	
	/******************************************** ID *****************************************************/
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}
	
	/******************************************** DATE ***************************************************/
	public LocalDate getDate() {
		return this.date;
	}

	public void setDate(LocalDate date) {
		this.date = date;
	}
	
	/******************************************** QUOTE ***************************************************/	
	public Integer getQuote() {
		return quote;
	}
	public void setQuote(Integer quote) {
		this.quote = quote;
	}
	
	/******************************************** AUTHOR **************************************************/	
	public Integer getAuthor() {
		return author;
	}
	public void setAuthor(Integer author) {
		this.author = author;
	}
	
	/******************************************** TEXT ****************************************************/	
	public String getContent() {
		return content;
	}
	public void setContent(String text) {
		this.content = text;
	}	
}
