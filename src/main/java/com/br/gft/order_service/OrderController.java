package com.br.gft.order_service;

import static com.br.gft.order_service.util.OrderUtils.DELIMETER;
import static com.br.gft.order_service.util.OrderUtils.MENU;
import static com.br.gft.order_service.util.OrderUtils.MENU_MESSAGE;
import static com.br.gft.order_service.util.OrderUtils.MULTIPLE_DISHES_TYPE_MORNING_MESSAGE_ALLOWED;
import static com.br.gft.order_service.util.OrderUtils.MULTIPLE_DISHES_TYPE_MORNING_MESSAGE_EXCEPTION;
import static com.br.gft.order_service.util.OrderUtils.MULTIPLE_DISHES_TYPE_NIGHT_MESSAGE_ALLOWED;
import static com.br.gft.order_service.util.OrderUtils.MULTIPLE_DISHES_TYPE_NIGHT_MESSAGE_EXCEPTION;
import static com.br.gft.order_service.util.OrderUtils.NO_ITEM_SELECTED_MESSAGE_EXCEPTION;
import static com.br.gft.order_service.util.OrderUtils.PERIOD_MESSAGE_EXCEPTION;
import static com.br.gft.order_service.util.OrderUtils.PERIOD_NOT_FOUND_MESSAGE_EXCEPTION;
import static com.br.gft.order_service.util.OrderUtils.REGEX;
import static com.br.gft.order_service.util.OrderUtils.VALID_DISHES_MORNING;
import static com.br.gft.order_service.util.OrderUtils.VALID_DISHES_NIGHT;
import static com.br.gft.order_service.util.OrderUtils.VALID_MULTIPLE_DISHES_TYPE_MORNING;
import static com.br.gft.order_service.util.OrderUtils.VALID_MULTIPLE_DISHES_TYPE_NIGHT;
import static com.br.gft.order_service.util.OrderUtils.VALID_PERIOD;
import static com.br.gft.order_service.util.OrderUtils.WELCOME_MESSAGE;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.logging.Logger;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import org.apache.commons.lang3.ArrayUtils;

import com.br.gft.order_service.enumeration.DayPeriodEnum;
import com.br.gft.order_service.enumeration.DishesMorningEnum;
import com.br.gft.order_service.enumeration.DishesNightEnum;
import com.br.gft.order_service.exception.MultipleDisheTypeException;
import com.br.gft.order_service.exception.NoTimeOfDayInformedException;
import com.br.gft.order_service.exception.PeriodException;
import com.br.gft.order_service.exception.PeriodNotFoundException;
import com.br.gft.order_service.util.OrderUtils;


/**
 * 
 * @author Silvio
 * The class is responsilble to controll of Dishes for each time of day.
 */
public class OrderController {

	private static Logger LOGGER = Logger.getLogger(OrderController.class.toString());

	/***
	 * Main method.
	 * 
	 * @param args
	 *            - Program arguments.
	 */
	public static void main(String[] args) {
		Scanner scan = new Scanner(System.in);
		getDishTypeMenu(scan);
	}

	/***
	 * readInputInformation method.
	 * 
	 * Read input information.
	 * 
	 */
	public static String readInputInformation(Scanner scan) {
		String input = scan.nextLine();

		return input;
	}

	/***
	 * getDishTypeMenu method.
	 * 
	 * Get Dish type menu.
	 * 
	 */
	public static void getDishTypeMenu(Scanner scan) {

		boolean validInput = false;

		System.out.println(WELCOME_MESSAGE);
		System.out.println(MENU_MESSAGE);

		while (!validInput) {

			String inputOrder = readInputInformation(scan);
			String[] inputItems = inputOrder.split(DELIMETER);
			try {

				if (MENU.equalsIgnoreCase(inputOrder)) {
					OrderUtils.showMenu();
				} else {

					List<String> periods = findTimeOfDay(inputItems);

					if (isValidPeriod(periods)) {
						//System.out.println("**********" + periods.get(0));
						
						String periodOfDay = periods.get(0);

						Map<String, Integer> items = findDishType(inputItems, periodOfDay);

						hasItemSelected(items);

						items = sortItems(items);
						
						verifyDishesForEachTimeOfDay(items,periodOfDay);

						validInput = true;
					}
				}

			} catch (NoTimeOfDayInformedException ex) {
				System.out.println(ex.getMessage());
			} catch (PeriodException ex) {
				System.out.println(ex.getMessage());
			} catch (PeriodNotFoundException ex) {
				System.out.println(ex.getMessage());
			}catch (MultipleDisheTypeException ex) {
				System.out.println(ex.getMessage());
			}catch (Exception ex) {
				LOGGER.severe(ex.getMessage());
			}

		}
	}

	/***
	 * findTimeOfDay method.
	 * 
	 * Find time of day.
	 * 
	 */
	public static List<String> findTimeOfDay(String[] argument) {
		List<String> periods = Arrays.asList(argument).stream()
				.filter(c -> ArrayUtils.contains(VALID_PERIOD, c.trim().toLowerCase())).collect(Collectors.toList());
		return periods;
	}

	/***
	 * isValidPeriod method.
	 * 
	 * Verify if the period is valid.
	 * 
	 * @exception PeriodNotFoundException, PeriodException
	 */
	private static boolean isValidPeriod(List<String> period) {
		if (period.isEmpty()) {
			throw new PeriodNotFoundException(PERIOD_NOT_FOUND_MESSAGE_EXCEPTION);
		} else if (period.size() > 1) {
			throw new PeriodException(PERIOD_MESSAGE_EXCEPTION);
		}
		return true;
	}

	/***
	 * findDishType method.
	 * 
	 * Find Dish type.
	 * 
	 */
	private static Map<String, Integer> findDishType(String[] input, String timeOfDay) {

		Map<String, Integer> maps = new HashMap<>();

		List<String> list = new ArrayList<String>();
		Collections.addAll(list, input);
		list.remove(timeOfDay);

		for (String valor : list) {

			if (!maps.containsKey(valor.trim())) {
				maps.put(valor.trim(), 0);
			}
			maps.put(valor.trim(), maps.get(valor.trim()) + 1);
		}

		return maps;
	}

	/***
	 * hasItemSelected method.
	 * 
	 * Verify if have item selected for the dish type.
	 * 
	 * @exception PeriodNotFoundException
	 */
	private static void hasItemSelected(Map<String, Integer> itens) {
		if (itens.isEmpty()) {
			throw new PeriodNotFoundException(NO_ITEM_SELECTED_MESSAGE_EXCEPTION);
		}

	}

	/***
	 * sortItems method.
	 * 
	 * Sort items.
	 * 
	 */
	private static Map<String, Integer>  sortItems(Map<String, Integer> itens) {
		Map<String, Integer> sortItems = itens.entrySet().stream().sorted(Map.Entry.comparingByKey())
				.collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));

		/*for (Map.Entry<String, Integer> item : sortItems.entrySet()) {
			System.out.println("item: " + item);
		}*/
		
		return sortItems;
	}

	/***
	 * verifyDishesForEachTimeOfDay method.
	 * 
	 * Verify dishes for time of day.
	 * 
	 */
	private static void verifyDishesForEachTimeOfDay(Map<String, Integer> itens, String periodOfDay) {
		
		Map<String, Integer> invalidItens = new HashMap<String, Integer>();
		Map<String, Integer> validItens = new HashMap<String, Integer>();
		
		for (Map.Entry<String, Integer> item : itens.entrySet()) {
			
			Pattern p = Pattern.compile(REGEX);
			if(p.matcher(item.getKey()).find() && ArrayUtils.contains(getValidDisheType(periodOfDay), Integer.valueOf(item.getKey()))){
				verifyQuantityAllowedForDishesType(periodOfDay, item);
				validItens.put(item.getKey(), item.getValue());
			}else{
				invalidItens.put(item.getKey(), item.getValue());
			}
		}
		displayOrder(validItens, invalidItens, periodOfDay);
	}

	/***
	 * getValidDisheType method.
	 * 
	 * Get valid dishe type.
	 * 
	 */
	private static Integer[] getValidDisheType(String periodOfDay) {
		if(DayPeriodEnum.MORNING.getDescription().equalsIgnoreCase(periodOfDay.trim())){
			return VALID_DISHES_MORNING;
		}
		
		return VALID_DISHES_NIGHT;
	}

	/***
	 * verifyQuantityAllowedForDishesType method.
	 * 
	 * Verify quantity allowed fir dishes type.
	 * 
	 */
	private static boolean verifyQuantityAllowedForDishesType(String periodOfDay, Map.Entry<String, Integer> item ){
		
		boolean isQuantityAllowed = false;
		
		if(DayPeriodEnum.MORNING.getDescription().equalsIgnoreCase(periodOfDay.trim()) && item.getValue() > 1){
			
			if(!ArrayUtils.contains(VALID_MULTIPLE_DISHES_TYPE_MORNING, Integer.valueOf(item.getKey()))){
				throw new MultipleDisheTypeException(MULTIPLE_DISHES_TYPE_MORNING_MESSAGE_EXCEPTION + DishesMorningEnum.valueOf(Integer.valueOf(item.getKey())) + "\n" + MULTIPLE_DISHES_TYPE_MORNING_MESSAGE_ALLOWED);
			}
			
		}else if(DayPeriodEnum.NIGHT.getDescription().equalsIgnoreCase(periodOfDay.trim()) && item.getValue() > 1){
			if(!ArrayUtils.contains(VALID_MULTIPLE_DISHES_TYPE_NIGHT, Integer.valueOf(item.getKey()))){
				
				System.out.println("Tipo " + DishesNightEnum.valueOf(Integer.valueOf(item.getKey())));
				throw new MultipleDisheTypeException(MULTIPLE_DISHES_TYPE_NIGHT_MESSAGE_EXCEPTION + DishesNightEnum.valueOf(Integer.valueOf(item.getKey())) + "\n" + MULTIPLE_DISHES_TYPE_NIGHT_MESSAGE_ALLOWED);
			}
		}
		
		return isQuantityAllowed;
	}
	
	/***
	 * displayOrder method.
	 * 
	 * Display order.
	 * 
	 */
	private static void displayOrder(Map<String, Integer> validItens, Map<String, Integer> invalidItens, String periodOfDay){
		
		StringBuilder outOrder = new StringBuilder();

		for (Map.Entry<String, Integer> itemOrder : validItens.entrySet()) {
			if(outOrder.length() > 0){
				outOrder.append(", ");
			}
			outOrder.append(getValueEnumType(periodOfDay,itemOrder));
			
			if(itemOrder.getValue() > 1){
				outOrder.append("(x"+ itemOrder.getValue() +")");
			}
		}
		
		String messageInvalidItems = prepareMessageInvalidItems(invalidItens);
		
		if(messageInvalidItems.length() > 0)
		{
			outOrder.append(",").append(messageInvalidItems);
		}
		
		System.out.println("Output: " + outOrder.toString());
	}
	
	/***
	 * prepareMessageInvalidItems method.
	 * 
	 * Prepare message invalid items to display.
	 * 
	 */
	private static String prepareMessageInvalidItems(Map<String, Integer> invalidItens) {
		
		StringBuilder outInvalidItemsOrder = new StringBuilder();
		String messageInvalidItems = "";
		
		for (Map.Entry<String, Integer> invalidItem : invalidItens.entrySet()) {
			if(outInvalidItemsOrder.length() > 0){
				outInvalidItemsOrder.append(", ");
			}
			outInvalidItemsOrder.append(invalidItem.getKey());
		}
		
		if(outInvalidItemsOrder.length() > 0) {
			messageInvalidItems = " Dish Type: " + outInvalidItemsOrder.toString() + " are not applicable";
		}
		
		return messageInvalidItems;
	}

	/***
	 * getValueEnumType method.
	 * 
	 * Get value enum type.
	 * 
	 */
	private static String getValueEnumType(String periodOfDay, Map.Entry<String, Integer>  itemOrder) {
		if(DayPeriodEnum.MORNING.getDescription().equalsIgnoreCase(periodOfDay.trim())){
			return DishesMorningEnum.valueOf(Integer.valueOf(itemOrder.getKey())).getDescription();
		}
		
		return DishesNightEnum.valueOf(Integer.valueOf(itemOrder.getKey())).getDescription();
	}
}
