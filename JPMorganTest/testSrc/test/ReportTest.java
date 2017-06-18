package test;

import static org.junit.Assert.*;

import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;

import entity.Instruction;
import main.Report;

public class ReportTest {
	
	Report report;
	DateFormat formatter;

	@Before
	public void setUp() throws Exception {
		report = new Report();
		formatter = new SimpleDateFormat("dd MMM yyyy");
	}
	@Test
	public void getSettledIncomingAmoutForDateTestFor02JanuarySaturday() throws ParseException {
		Date date = formatter.parse("02 Jan 2016");
		int result = report.getSettledIncomingAmoutForDate(date).intValue();
		assertEquals(0, result);
	}
	@Test
	public void getSettledIncomingAmoutForDateTestFor07JanuaryThursday() throws ParseException {
		Date date = formatter.parse("07 Jan 2016");
		int result = report.getSettledIncomingAmoutForDate(date).intValue();
		assertEquals(14899, result);
	}
	@Test
	public void getSettledOutgoingAmoutForDateTestFor02JanuarySaturday() throws ParseException {
		Date date = formatter.parse("02 Jan 2016");
		int result = report.getSettledIncomingAmoutForDate(date).intValue();
		assertEquals(0, result);
	}
	@Test
	public void getSettledOutgoingAmoutForDateTestFor07JanuaryThursday() throws ParseException {
		Date date = formatter.parse("07 Jan 2016");
		int result = report.getSettledOutgoingAmoutForDate(date).intValue();
		assertEquals(0, result);
	}
	@Test
	public void getSettledOutgoingAmoutForDateTestFor04JanuaryMonday() throws ParseException {
		Date date = formatter.parse("04 Jan 2016");
		int result = report.getSettledOutgoingAmoutForDate(date).intValue();
		assertEquals(10025, result);
	}
	@Test
	public void getSettledOutgoingAmoutForDateTestFor23JulySunday() throws ParseException {
		Date date = formatter.parse("23 Jul 2017");
		int result = report.getSettledOutgoingAmoutForDate(date).intValue();
		assertEquals(12830, result);
	}
	@Test
	public void getSettledIncomingAmoutForDateTestFor25JuneTuesday() throws ParseException {
		Date date = formatter.parse("25 Jul 2017");
		int result = report.getSettledIncomingAmoutForDate(date).intValue();
		assertEquals(881930, result);
	}
	@Test
	public void generateOutgoingReportTest() throws ParseException {
		for (Map.Entry<Date, BigDecimal> entry : report.generateOutgoingReport().entrySet()) {
		    System.out.println(entry.getKey() + "   " + entry.getValue());
		}
	}
	@Test
	public void generateIncomingReportTest() throws ParseException {
		for (Map.Entry<Date, BigDecimal> entry : report.generateIncomingReport().entrySet()) {
		    System.out.println(entry.getKey() + "   " + entry.getValue());
		}
	}
	@Test
	public void getOutgoingsInDescendingOrderTest() throws ParseException {
		List<Instruction> outgoingInstructions = report.getOutgoingsInDescendingOrder();
		for (int i=0; i<outgoingInstructions.size(); i++) {
			System.out.println((i+1)+": "+outgoingInstructions.get(i).getEntity()+ " " + outgoingInstructions.get(i).getUnits() + " units at " + outgoingInstructions.get(i).getPricePerUnit() + "" + outgoingInstructions.get(i).getCurrency());
		}
	}
	@Test
	public void getincomingsInDescendingOrderTest() throws ParseException {
		List<Instruction> incomingInstructions = report.getIncomingsInDescendingOrder();
		for (int i=0; i<incomingInstructions.size(); i++) {
			System.out.println((i+1)+": "+incomingInstructions.get(i).getEntity()+ " " + incomingInstructions.get(i).getUnits() + " units at " + incomingInstructions.get(i).getPricePerUnit() + "" + incomingInstructions.get(i).getCurrency());
		}
	}

}
