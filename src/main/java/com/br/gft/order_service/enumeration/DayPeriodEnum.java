package com.br.gft.order_service.enumeration;

public enum DayPeriodEnum {

	MORNING(1, "morning"),
	NIGHT(2, "night");
	
	private Integer id;

	private String description;

	DayPeriodEnum(Integer id, String description) {
		this.id = id;
		this.description = description;
	}

	public Integer getId() {
		return id;
	}

	public String getDescription() {
		return description;
	}
}
