package com.br.gft.order.order_service;

import static com.br.gft.order_service.util.OrderUtils.DELIMETER;
import static com.br.gft.order_service.util.OrderUtils.MULTIPLE_DISHES_TYPE_MORNING_MESSAGE_ALLOWED;
import static com.br.gft.order_service.util.OrderUtils.MULTIPLE_DISHES_TYPE_MORNING_MESSAGE_EXCEPTION;
import static com.br.gft.order_service.util.OrderUtils.PERIOD_NOT_FOUND_MESSAGE_EXCEPTION;
import static com.br.gft.order_service.util.OrderUtils.REGEX;
import static com.br.gft.order_service.util.OrderUtils.VALID_PERIOD;
import static com.br.gft.order_service.util.OrderUtils.VALID_DISHES_MORNING;
import static com.br.gft.order_service.util.OrderUtils.VALID_MULTIPLE_DISHES_TYPE_MORNING;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import org.apache.commons.lang3.ArrayUtils;

import com.br.gft.order_service.enumeration.DishesMorningEnum;
import com.br.gft.order_service.exception.MultipleDisheTypeException;
import com.br.gft.order_service.exception.PeriodNotFoundException;

import junit.framework.TestCase;

public class OrderTest extends TestCase {

	public void testFindTimeOfDayMorning() {

		String inputOrder = "morning,1,2,3,4";

		String[] inputItems = inputOrder.split(DELIMETER);

		List<String> periods = Arrays.asList(inputItems).stream()
				.filter(c -> ArrayUtils.contains(VALID_PERIOD, c.trim().toLowerCase())).collect(Collectors.toList());

		assertEquals("morning", periods.get(0));
	}

	public void testFindTimeOfDayNight() {

		String inputOrder = "night,1,2,3,4";

		String[] inputItems = inputOrder.split(DELIMETER);

		List<String> periods = Arrays.asList(inputItems).stream()
				.filter(c -> ArrayUtils.contains(VALID_PERIOD, c.trim().toLowerCase())).collect(Collectors.toList());

		assertEquals("night", periods.get(0));
	}

	public void testInvalidTimeOfDay() {

		String inputOrder = " ,1,2,3,4";

		String[] inputItems = inputOrder.split(DELIMETER);

		List<String> periods = Arrays.asList(inputItems).stream()
				.filter(c -> ArrayUtils.contains(VALID_PERIOD, c.trim().toLowerCase())).collect(Collectors.toList());

		assertEquals(true, periods.isEmpty());
	}

	public void testDuplicatedTimeOfDay() {

		String inputOrder = "morning, night ,1,2,3,4";

		String[] inputItems = inputOrder.split(DELIMETER);

		List<String> periods = Arrays.asList(inputItems).stream()
				.filter(c -> ArrayUtils.contains(VALID_PERIOD, c.trim().toLowerCase())).collect(Collectors.toList());

		assertEquals(2, periods.size());
	}

	public void testFindDishType() {

		String inputOrder = "morning,1,2,3";

		String[] inputItems = inputOrder.split(DELIMETER);

		List<String> periods = Arrays.asList(inputItems).stream()
				.filter(c -> ArrayUtils.contains(VALID_PERIOD, c.trim().toLowerCase())).collect(Collectors.toList());

		String periodOfDay = periods.get(0);

		Map<String, Integer> maps = new HashMap<>();

		List<String> list = new ArrayList<String>();
		Collections.addAll(list, inputItems);
		list.remove(periodOfDay);

		for (String valor : list) {

			if (!maps.containsKey(valor.trim())) {
				maps.put(valor.trim(), 0);
			}
			maps.put(valor.trim(), maps.get(valor.trim()) + 1);
		}

		assertEquals(3, maps.entrySet().size());
	}

	public void testEmptyTimeOfDay() {

		String inputOrder = ",1,2,3,4";

		String[] inputItems = inputOrder.split(DELIMETER);

		List<String> periods = Arrays.asList(inputItems).stream()
				.filter(c -> ArrayUtils.contains(VALID_PERIOD, c.trim().toLowerCase())).collect(Collectors.toList());
		try {
			if (periods.isEmpty()) {
				throw new PeriodNotFoundException(PERIOD_NOT_FOUND_MESSAGE_EXCEPTION);
			}
		} catch (PeriodNotFoundException ex) {
			assertEquals(PeriodNotFoundException.class, ex.getClass());
		}
	}
	
	public void testVerifyDishesForEachTimeOfDayMorning() {
		
		String inputOrder = "morning,1,2,3,3,3";

		String[] inputItems = inputOrder.split(DELIMETER);

		List<String> periods = Arrays.asList(inputItems).stream()
				.filter(c -> ArrayUtils.contains(VALID_PERIOD, c.trim().toLowerCase())).collect(Collectors.toList());

		String periodOfDay = periods.get(0);

		Map<String, Integer> maps = new HashMap<>();

		List<String> list = new ArrayList<String>();
		Collections.addAll(list, inputItems);
		list.remove(periodOfDay);

		for (String valor : list) {

			if (!maps.containsKey(valor.trim())) {
				maps.put(valor.trim(), 0);
			}
			maps.put(valor.trim(), maps.get(valor.trim()) + 1);
		}

		Map<String, Integer> invalidItens = new HashMap<String, Integer>();
		Map<String, Integer> validItens = new HashMap<String, Integer>();
		
		for (Map.Entry<String, Integer> item : maps.entrySet()) {
			
			Pattern p = Pattern.compile(REGEX);
			if(p.matcher(item.getKey()).find() && ArrayUtils.contains(VALID_DISHES_MORNING, Integer.valueOf(item.getKey()))){
				if(item.getValue() > 1 && !ArrayUtils.contains(VALID_MULTIPLE_DISHES_TYPE_MORNING, Integer.valueOf(item.getKey()))){
					throw new MultipleDisheTypeException(MULTIPLE_DISHES_TYPE_MORNING_MESSAGE_EXCEPTION + DishesMorningEnum.valueOf(Integer.valueOf(item.getKey())) + "\n" + MULTIPLE_DISHES_TYPE_MORNING_MESSAGE_ALLOWED);
				}
				validItens.put(item.getKey(), item.getValue());
			}else{
				invalidItens.put(item.getKey(), item.getValue());
			}
		}
		
		assertEquals(3, validItens.size());
	}

}
