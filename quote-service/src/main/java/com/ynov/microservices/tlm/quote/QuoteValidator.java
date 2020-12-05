package com.ynov.microservices.tlm.quote;

import org.springframework.util.StringUtils;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

public class QuoteValidator implements Validator {

	private static final String REQUIRED = "required";

	@Override
	public void validate(Object obj, Errors errors) {
		Quote quote = (Quote) obj;
		String content = quote.getContent();
		// name validation
		if (!StringUtils.hasLength(content)) {
			errors.rejectValue("quote", REQUIRED, REQUIRED);
		}
	}
	
	@Override
	public boolean supports(Class<?> T) {
		return Quote.class.isAssignableFrom(T);
	}
}
