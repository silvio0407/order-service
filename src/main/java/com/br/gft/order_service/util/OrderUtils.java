package com.br.gft.order_service.util;

import com.br.gft.order_service.enumeration.DayPeriodEnum;
import com.br.gft.order_service.enumeration.DishesMorningEnum;
import com.br.gft.order_service.enumeration.DishesNightEnum;

public class OrderUtils {
	
	public static final String[] VALID_PERIOD = { DayPeriodEnum.MORNING.getDescription(), DayPeriodEnum.NIGHT.getDescription() };
	public static final Integer[] VALID_DISHES_MORNING = { DishesMorningEnum.EGGS.getId(), DishesMorningEnum.TOAST.getId(), DishesMorningEnum.COFFEE.getId()};
	public static final Integer[] VALID_DISHES_NIGHT = { DishesNightEnum.STEAK.getId(), DishesNightEnum.POTATO.getId(), DishesNightEnum.WINE.getId(), DishesNightEnum.CAKE.getId()};
	public static final Integer[] VALID_MULTIPLE_DISHES_TYPE_MORNING = {DishesMorningEnum.COFFEE.getId()};
	public static final Integer[] VALID_MULTIPLE_DISHES_TYPE_NIGHT = {DishesNightEnum.POTATO.getId()};
	
	public static final String MENU = "menu";
	public static final String DELIMETER = ",";
	public static final String REGEX = "^[1-9]\\d*$";
	public static final String WELCOME_MESSAGE = "Please, enter time of day that you desire (morning or night) with the selection item: 1,2,3 or 4";
	public static final String MENU_MESSAGE = "If would you like to see the options, type 'menu'! ";
	public static final String PERIOD_NOT_FOUND_MESSAGE_EXCEPTION = "Please, verify the information for argument time of day, inform only morning or night separeted by comma!";
	public static final String PERIOD_MESSAGE_EXCEPTION = "Inform only one period for each request!";
	public static final String NO_ITEM_SELECTED_MESSAGE_EXCEPTION = "Is necessary inform at least one dish type to continue!";
	public static final String MULTIPLE_DISHES_TYPE_MORNING_MESSAGE_EXCEPTION = "Multiple items are not allowed for time of day Morning for item ";
	public static final String MULTIPLE_DISHES_TYPE_MORNING_MESSAGE_ALLOWED = "Only for coffee is allowed!";
	public static final String MULTIPLE_DISHES_TYPE_NIGHT_MESSAGE_EXCEPTION = "Multiple items are not allowed for time of day Night for item ";
	public static final String MULTIPLE_DISHES_TYPE_NIGHT_MESSAGE_ALLOWED = "Only for potato is allowed!";

	public static void showMenu() {
		System.out.println("Dish Type		morning				night");
		System.out.println("1(entrée)		eggs				steak");
		System.out.println("2(side)		    	Toast				potato");
		System.out.println("3(drink)		coffee				wine");
		System.out.println("4(dessert)		Not Applicable		cake");
		System.out.println(WELCOME_MESSAGE);
	}
}
