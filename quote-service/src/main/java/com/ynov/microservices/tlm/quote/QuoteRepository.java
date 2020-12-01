package com.ynov.microservices.tlm.quote;

import org.springframework.data.repository.CrudRepository;

public interface QuoteRepository extends CrudRepository<Quote, Integer> {

}
