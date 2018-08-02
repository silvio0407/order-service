package com.br.gft.order_service.exception;

public class NoTimeOfDayInformedException extends RuntimeException {

	private static final long serialVersionUID = 8193197758876249857L;

	public NoTimeOfDayInformedException(String message) {
    	super(message);
    }
}
