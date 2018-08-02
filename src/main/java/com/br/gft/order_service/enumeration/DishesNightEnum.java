package com.br.gft.order_service.enumeration;

import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;

public enum DishesNightEnum {

	STEAK(1, "steak"),
	POTATO(2, "potato"),
	WINE(3, "wine"),
	CAKE(4, "cake");
	
	private static final Map<Integer, DishesNightEnum> LOOKUP = new HashMap<>();

	static {
		for (DishesNightEnum e : EnumSet.allOf(DishesNightEnum.class)) {
			LOOKUP.put(e.getId(), e);
		}
	}
	
	private Integer id;

	private String description;

	DishesNightEnum(Integer id, String description) {
		this.id = id;
		this.description = description;
	}

	public Integer getId() {
		return id;
	}

	public String getDescription() {
		return description;
	}
	
	public static DishesNightEnum valueOf(Integer id) {
		return LOOKUP.get(id);
	}
}
