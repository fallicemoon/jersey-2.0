package sellCase.model;

import java.util.Date;
import java.util.Set;

import purchaseCase.model.PurchaseCaseVO;
import tools.AbstractVo;

public class SellCaseVO extends AbstractVo {
	/**
	 * 
	 */
	private static final long serialVersionUID = -2114817843335424889L;
	private Integer sellCaseId;
	private String addressee;
	private String phone;
	private String address;
	private String description;
	private String trackingNumber;
	private String transportMethod;
	private Boolean isShipping;
	private Integer income;
	private Integer transportCost;
	private Integer collected;
	private Integer uncollected;
	private Date shippingTime;
	private Date closeTime;
	private Boolean isChecked;
	private Set<PurchaseCaseVO> purchaseCases;

	public Set<PurchaseCaseVO> getPurchaseCases() {
		return this.purchaseCases;
	}

	public void setPurchaseCases(Set<PurchaseCaseVO> purchaseCases) {
		this.purchaseCases = purchaseCases;
	}

	public Boolean getIsChecked() {
		return this.isChecked;
	}

	public void setIsChecked(Boolean isChecked) {
		this.isChecked = isChecked;
	}

	public Date getShippingTime() {
		return this.shippingTime;
	}

	public void setShippingTime(Date shippingTime) {
		this.shippingTime = shippingTime;
	}

	public Date getCloseTime() {
		return this.closeTime;
	}

	public void setCloseTime(Date closeTime) {
		this.closeTime = closeTime;
	}

	public String getAddressee() {
		return this.addressee;
	}

	public void setAddressee(String addressee) {
		this.addressee = addressee;
	}

	public String getPhone() {
		return this.phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public Integer getCollected() {
		return this.collected;
	}

	public void setCollected(Integer collected) {
		this.collected = collected;
	}

	public Integer getUncollected() {
		return this.uncollected;
	}

	public void setUncollected(Integer uncollected) {
		this.uncollected = uncollected;
	}

	public Integer getSellCaseId() {
		return this.sellCaseId;
	}

	public void setSellCaseId(Integer sellCaseId) {
		this.sellCaseId = sellCaseId;
	}

	public String getAddress() {
		return this.address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getTrackingNumber() {
		return this.trackingNumber;
	}

	public void setTrackingNumber(String trackingNumber) {
		this.trackingNumber = trackingNumber;
	}

	public String getTransportMethod() {
		return this.transportMethod;
	}

	public void setTransportMethod(String transportMethod) {
		this.transportMethod = transportMethod;
	}

	public Boolean getIsShipping() {
		return this.isShipping;
	}

	public void setIsShipping(Boolean isShipping) {
		this.isShipping = isShipping;
	}

	public Integer getIncome() {
		return this.income;
	}

	public void setIncome(Integer income) {
		this.income = income;
	}

	public Integer getTransportCost() {
		return this.transportCost;
	}

	public void setTransportCost(Integer transportCost) {
		this.transportCost = transportCost;
	}

	@Override
	public String toString() {
		return "SellCaseVO [sellCaseId=" + sellCaseId + ", addressee=" + addressee + ", phone=" + phone + ", address="
				+ address + ", description=" + description + ", trackingNumber=" + trackingNumber + ", transportMethod="
				+ transportMethod + ", isShipping=" + isShipping + ", income=" + income + ", transportCost="
				+ transportCost + ", collected=" + collected + ", uncollected=" + uncollected + ", shippingTime="
				+ shippingTime + ", closeTime=" + closeTime + ", isChecked=" + isChecked + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((sellCaseId == null) ? 0 : sellCaseId.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		SellCaseVO other = (SellCaseVO) obj;
		if (sellCaseId == null) {
			if (other.sellCaseId != null)
				return false;
		} else if (!sellCaseId.equals(other.sellCaseId))
			return false;
		return true;
	}
	
	
}

