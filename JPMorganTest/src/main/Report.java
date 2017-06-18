package main;

import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import entity.Instruction;

public class Report {
	
	Instruction instruction1;
	Instruction instruction2;
	Instruction instruction3;
	Instruction instruction4;
	Instruction instruction5;
	List<Instruction> instructions;
	DateFormat formatter;
	
	public Report() throws ParseException {
		instructions = new ArrayList<>();
		formatter = new SimpleDateFormat("dd MMM yyyy");
		
		instruction1 = new Instruction();
		instruction1.setAgreedFx(new BigDecimal(0.5));
		instruction1.setBuySell('B');
		instruction1.setCurrency("SGP");
		instruction1.setEntity("foo");
		instruction1.setInstructionDate(formatter.parse("01 Jan 2016"));
		instruction1.setPricePerUnit(new BigDecimal(100.25));
		instruction1.setSettlementDate(formatter.parse("02 Jan 2016"));
		instruction1.setUnits(200);
		
		instruction2 = new Instruction();
		instruction2.setAgreedFx(new BigDecimal(0.22));
		instruction2.setBuySell('S');
		instruction2.setCurrency("AED");
		instruction2.setEntity("bar");
		instruction2.setInstructionDate(formatter.parse("05 Jan 2016"));
		instruction2.setPricePerUnit(new BigDecimal(150.5));
		instruction2.setSettlementDate(formatter.parse("07 Jan 2016"));
		instruction2.setUnits(450);
		
		instruction3 = new Instruction();
		instruction3.setAgreedFx(new BigDecimal(1.0));
		instruction3.setBuySell('S');
		instruction3.setCurrency("USD");
		instruction3.setEntity("GOOG");
		instruction3.setInstructionDate(formatter.parse("20 Jun 2017"));
		instruction3.setPricePerUnit(new BigDecimal(150.5));
		instruction3.setSettlementDate(formatter.parse("25 Jul 2017"));
		instruction3.setUnits(2450);
		
		instruction4 = new Instruction();
		instruction4.setAgreedFx(new BigDecimal(0.25));
		instruction4.setBuySell('B');
		instruction4.setCurrency("SAR");
		instruction4.setEntity("samp");
		instruction4.setInstructionDate(formatter.parse("21 Jul 2017"));
		instruction4.setPricePerUnit(new BigDecimal(150.5));
		instruction4.setSettlementDate(formatter.parse("23 Jul 2017"));
		instruction4.setUnits(341);
		
		instruction5 = new Instruction();
		instruction5.setAgreedFx(new BigDecimal(1.00));
		instruction5.setBuySell('S');
		instruction5.setCurrency("USD");
		instruction5.setEntity("AMZ");
		instruction5.setInstructionDate(formatter.parse("21 Jul 2017"));
		instruction5.setPricePerUnit(new BigDecimal(150.5));
		instruction5.setSettlementDate(formatter.parse("25 Jul 2017"));
		instruction5.setUnits(3410);
		
		instructions.add(instruction1);
		instructions.add(instruction2);
		instructions.add(instruction3);
		instructions.add(instruction4);
		instructions.add(instruction5);
	}
	
	public BigDecimal getSettledIncomingAmoutForDate(Date date) {
		BigDecimal aggregateAmount = new BigDecimal(0);
		for (Instruction instruction : instructions) {
			//Checking if settlement is incoming
			if (instruction.getBuySell() == 'B') continue;
			//If settlement day is out of trading days set date to the next trading day
			instruction.setSettlementDate(checkDate(instruction.getSettlementDate(), instruction.getCurrency()));
			//Checking if settlement date is the date we are after  
			if (instruction.getSettlementDate().equals(date)) {
				BigDecimal oneUnit = instruction.getPricePerUnit().multiply(instruction.getAgreedFx());
				BigDecimal allUnits = oneUnit.multiply(new BigDecimal(instruction.getUnits()));
				aggregateAmount = aggregateAmount.add(allUnits);
			}
		}
		return aggregateAmount;
	}
	public BigDecimal getSettledOutgoingAmoutForDate(Date date) {
		BigDecimal aggregateAmount = new BigDecimal(0);
		for (Instruction instruction : instructions) {
			//Checking if settlement is incoming
			if (instruction.getBuySell() == 'S') continue;
			//If settlement day is out of trading days set date to the next trading day
			instruction.setSettlementDate(checkDate(instruction.getSettlementDate(), instruction.getCurrency()));
			//Checking if settlement date is the date we are after  
			if (instruction.getSettlementDate().equals(date)) {
				BigDecimal oneUnit = instruction.getPricePerUnit().multiply(instruction.getAgreedFx());
				BigDecimal allUnits = oneUnit.multiply(new BigDecimal(instruction.getUnits()));
				aggregateAmount = aggregateAmount.add(allUnits);
			}
		}
		return aggregateAmount;
	}
	public Map<Date, BigDecimal> generateOutgoingReport() throws ParseException {
		Map<Date, BigDecimal> returnMap = new HashMap<>();
		Calendar start = Calendar.getInstance();
		start.setTime(formatter.parse("01 Jan 2016"));
		Calendar end = Calendar.getInstance();
		end.setTime(formatter.parse("26 Jul 2017"));

		for (Date date = start.getTime(); start.before(end); start.add(Calendar.DATE, 1), date = start.getTime()) {
			BigDecimal result = getSettledOutgoingAmoutForDate(date);
			if (result.intValue() != 0) {
				returnMap.put(date, result);
			}
		}
		return returnMap;
	}
	public Map<Date, BigDecimal> generateIncomingReport() throws ParseException {
		Map<Date, BigDecimal> returnMap = new HashMap<>();
		Calendar start = Calendar.getInstance();
		start.setTime(formatter.parse("01 Jan 2016"));
		Calendar end = Calendar.getInstance();
		end.setTime(formatter.parse("26 Jul 2017"));

		for (Date date = start.getTime(); start.before(end); start.add(Calendar.DATE, 1), date = start.getTime()) {
			BigDecimal result = getSettledIncomingAmoutForDate(date);
			if (result.intValue() != 0) {
				returnMap.put(date, result);
			}
		}
		return returnMap;
	}
	public List<Instruction> getOutgoingsInDescendingOrder() {
		List<Instruction> outgoings = new ArrayList<>();
		instructions.forEach(instruction -> {
			if (instruction.getBuySell() == 'S') outgoings.add(instruction);
		});
		
		Collections.sort(outgoings, (i1, i2) -> {
			
			BigDecimal i1aggregateAmount = new BigDecimal(0);
			BigDecimal i1OneUnit = i1.getPricePerUnit().multiply(i1.getAgreedFx());
			BigDecimal i1AllUnits = i1OneUnit.multiply(new BigDecimal(i1.getUnits()));
			i1aggregateAmount = i1aggregateAmount.add(i1AllUnits);
			
			BigDecimal i2aggregateAmount = new BigDecimal(0);
			BigDecimal i2OneUnit = i2.getPricePerUnit().multiply(i2.getAgreedFx());
			BigDecimal i2AllUnits = i2OneUnit.multiply(new BigDecimal(i2.getUnits()));
			i2aggregateAmount = i1aggregateAmount.add(i2AllUnits);
			
			return i1aggregateAmount.compareTo(i2aggregateAmount);
		});
		return outgoings;
	}
	public List<Instruction> getIncomingsInDescendingOrder() {
		List<Instruction> incomings = new ArrayList<>();
		instructions.forEach(instruction -> {
			if (instruction.getBuySell() == 'B') incomings.add(instruction);
		});
		
		Collections.sort(incomings, (i1, i2) -> {
			
			BigDecimal i1aggregateAmount = new BigDecimal(0);
			BigDecimal i1OneUnit = i1.getPricePerUnit().multiply(i1.getAgreedFx());
			BigDecimal i1AllUnits = i1OneUnit.multiply(new BigDecimal(i1.getUnits()));
			i1aggregateAmount = i1aggregateAmount.add(i1AllUnits);
			
			BigDecimal i2aggregateAmount = new BigDecimal(0);
			BigDecimal i2OneUnit = i2.getPricePerUnit().multiply(i2.getAgreedFx());
			BigDecimal i2AllUnits = i2OneUnit.multiply(new BigDecimal(i2.getUnits()));
			i2aggregateAmount = i1aggregateAmount.add(i2AllUnits);
			
			return i1aggregateAmount.compareTo(i2aggregateAmount);
		});
		return incomings;
	}
	private boolean isDateSaturday(Date date) {
		Calendar day = Calendar.getInstance();
		day.setTime(date);
		if (day.get(Calendar.DAY_OF_WEEK) == 7) {
			return true;
		}
		else {
			return false;
		}
	}
	private boolean isDateFriday(Date date) {
		Calendar day = Calendar.getInstance();
		day.setTime(date);
		if (day.get(Calendar.DAY_OF_WEEK) == 6) {
			return true;
		}
		else {
			return false;
		}
	}
	private boolean isDateSunday(Date date) {
		Calendar day = Calendar.getInstance();
		day.setTime(date);
		if (day.get(Calendar.DAY_OF_WEEK) == 1) {
			return true;
		}
		else {
			return false;
		}
	}
	private Date rollDateToNextWorkingDay(Date date) {
		Calendar day = Calendar.getInstance();
		day.setTime(date);
		//Day is Sunday
		if (day.get(Calendar.DAY_OF_WEEK) == 1) {
			day.add(Calendar.DATE, 1);
			return day.getTime();
		}
		//Day is Saturday
		else {
			day.add(Calendar.DATE, 2);
			return day.getTime();
		}
	}
	private Date checkDate(Date date, String currency) {
		if ((currency.contentEquals("SAR")) || (currency.contentEquals("AED"))) {
			Calendar day = Calendar.getInstance();
			day.setTime(date);
			if (isDateFriday(date)) {
				day.add(Calendar.DATE, 2);
				return day.getTime();
			}
			else if (isDateSaturday(date)) {
				day.add(Calendar.DATE, 1);
				return day.getTime();
			}
			else {
				return date;
			}
		}
		else {
			if (isDateSaturday(date)) {
				return rollDateToNextWorkingDay(date);
			}
			else if (isDateSunday(date)) {
				return rollDateToNextWorkingDay(date);
			}
			else {
				return date;
			}
		}
	}
}
