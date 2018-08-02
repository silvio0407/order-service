package com.br.gft.order_service.enumeration;

import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;

public enum DishesMorningEnum {

	EGGS(1, "eggs"),
	TOAST(2, "toast"),
	COFFEE(3, "coffee");
	
	private static final Map<Integer, DishesMorningEnum> LOOKUP = new HashMap<>();

	static {
		for (DishesMorningEnum e : EnumSet.allOf(DishesMorningEnum.class)) {
			LOOKUP.put(e.getId(), e);
		}
	}
	
	private Integer id;

	private String description;

	DishesMorningEnum(Integer id, String description) {
		this.id = id;
		this.description = description;
	}

	public Integer getId() {
		return id;
	}

	public String getDescription() {
		return description;
	}
	
	public static DishesMorningEnum valueOf(Integer id) {
		return LOOKUP.get(id);
	}
}
