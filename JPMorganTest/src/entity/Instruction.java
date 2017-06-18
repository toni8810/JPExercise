package entity;

import java.math.BigDecimal;
import java.util.Date;

public class Instruction {
	private String entity;
	private char buySell;
	private BigDecimal agreedFx;
	private String currency;
	private Date instructionDate;
	private Date settlementDate;
	private long units;
	private BigDecimal pricePerUnit;
	
	public String getEntity() {
		return entity;
	}
	public void setEntity(String entity) {
		this.entity = entity;
	}
	public BigDecimal getAgreedFx() {
		return agreedFx;
	}
	public void setAgreedFx(BigDecimal agreedFx) {
		this.agreedFx = agreedFx;
	}
	public String getCurrency() {
		return currency;
	}
	public void setCurrency(String currency) {
		this.currency = currency;
	}
	public Date getInstructionDate() {
		return instructionDate;
	}
	public void setInstructionDate(Date instructionDate) {
		this.instructionDate = instructionDate;
	}
	public Date getSettlementDate() {
		return settlementDate;
	}
	public void setSettlementDate(Date settlementDate) {
		this.settlementDate = settlementDate;
	}
	public long getUnits() {
		return units;
	}
	public void setUnits(long units) {
		this.units = units;
	}
	public BigDecimal getPricePerUnit() {
		return pricePerUnit;
	}
	public void setPricePerUnit(BigDecimal pricePerUnit) {
		this.pricePerUnit = pricePerUnit;
	}
	public char getBuySell() {
		return buySell;
	}
	public void setBuySell(char buySell) {
		this.buySell = buySell;
	}
	@Override
	public String toString() {
		return "Instruction [entity=" + entity + ", buySell=" + buySell + ", agreedFx=" + agreedFx + ", currency="
				+ currency + ", instructionDate=" + instructionDate + ", settlementDate=" + settlementDate + ", units="
				+ units + ", pricePerUnit=" + pricePerUnit + "]";
	}
	
	
}
