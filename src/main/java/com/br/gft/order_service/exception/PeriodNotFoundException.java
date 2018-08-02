package com.br.gft.order_service.exception;

public class PeriodNotFoundException extends RuntimeException {

	private static final long serialVersionUID = 8735640592989772241L;

	public PeriodNotFoundException(String message) {
    	super(message);
    }
}
