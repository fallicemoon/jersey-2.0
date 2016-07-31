package commodity.model;

import purchaseCase.model.PurchaseCaseVO;
import tools.AbstractVo;

public class CommodityVO extends AbstractVo{
	/**
	 * 
	 */
	private static final long serialVersionUID = 5376904358224813395L;
	private Integer commodityId;
	private PurchaseCaseVO purchaseCaseVO;
	private String itemName;
	private Integer qty;
	private String link;
	private String player;
	private String number;
	private String season;
	private String team;
	private String style;
	private String color;
	private String brand;
	private String size;
	private String level;
	private String condition;
	private String tag;
	private String patchAndCertificate;
	private String serial;
	private String owner;
	private Integer cost;
	private Integer sellPrice;
	private String sellPlatform;
	private Boolean isStored;

	public Integer getQty() {
		return this.qty;
	}

	public void setQty(Integer qty) {
		this.qty = qty;
	}

	public String getStyle() {
		return this.style;
	}

	public void setStyle(String style) {
		this.style = style;
	}

	public Integer getCost() {
		return this.cost;
	}

	public void setCost(Integer cost) {
		this.cost = cost;
	}

	public Integer getSellPrice() {
		return this.sellPrice;
	}

	public void setSellPrice(Integer sellPrice) {
		this.sellPrice = sellPrice;
	}

	public PurchaseCaseVO getPurchaseCaseVO() {
		return this.purchaseCaseVO;
	}

	public void setPurchaseCaseVO(PurchaseCaseVO purchaseCaseVO) {
		this.purchaseCaseVO = purchaseCaseVO;
	}

	public String getLink() {
		return this.link;
	}

	public void setLink(String link) {
		this.link = link;
	}

	public Boolean getIsStored() {
		return this.isStored;
	}

	public void setIsStored(Boolean isStored) {
		this.isStored = isStored;
	}

	public String getSellPlatform() {
		return this.sellPlatform;
	}

	public void setSellPlatform(String sellPlatform) {
		this.sellPlatform = sellPlatform;
	}

	public Integer getCommodityId() {
		return this.commodityId;
	}

	public void setCommodityId(Integer commodityId) {
		this.commodityId = commodityId;
	}

	public String getItemName() {
		return this.itemName;
	}

	public void setItemName(String itemName) {
		this.itemName = itemName;
	}

	public String getPlayer() {
		return this.player;
	}

	public void setPlayer(String player) {
		this.player = player;
	}

	public String getNumber() {
		return this.number;
	}

	public void setNumber(String number) {
		this.number = number;
	}

	public String getSeason() {
		return this.season;
	}

	public void setSeason(String season) {
		this.season = season;
	}

	public String getTeam() {
		return this.team;
	}

	public void setTeam(String team) {
		this.team = team;
	}

	public String getColor() {
		return this.color;
	}

	public void setColor(String color) {
		this.color = color;
	}

	public String getBrand() {
		return this.brand;
	}

	public void setBrand(String brand) {
		this.brand = brand;
	}

	public String getSize() {
		return this.size;
	}

	public void setSize(String size) {
		this.size = size;
	}

	public String getLevel() {
		return this.level;
	}

	public void setLevel(String level) {
		this.level = level;
	}

	public String getCondition() {
		return this.condition;
	}

	public void setCondition(String condition) {
		this.condition = condition;
	}

	public String getTag() {
		return this.tag;
	}

	public void setTag(String tag) {
		this.tag = tag;
	}

	public String getPatchAndCertificate() {
		return this.patchAndCertificate;
	}

	public void setPatchAndCertificate(String patchAndCertificate) {
		this.patchAndCertificate = patchAndCertificate;
	}

	public String getSerial() {
		return this.serial;
	}

	public void setSerial(String serial) {
		this.serial = serial;
	}

	public String getOwner() {
		return this.owner;
	}

	public void setOwner(String owner) {
		this.owner = owner;
	}

	@Override
	public String toString() {
		return "CommodityVO [commodityId=" + commodityId + ", purchaseCaseVO=" + purchaseCaseVO + ", itemName="
				+ itemName + ", qty=" + qty + ", link=" + link + ", player=" + player + ", number=" + number
				+ ", season=" + season + ", team=" + team + ", style=" + style + ", color=" + color + ", brand=" + brand
				+ ", size=" + size + ", level=" + level + ", condition=" + condition + ", tag=" + tag
				+ ", patchAndCertificate=" + patchAndCertificate + ", serial=" + serial + ", owner=" + owner + ", cost="
				+ cost + ", sellPrice=" + sellPrice + ", sellPlatform=" + sellPlatform + ", isStored=" + isStored + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((commodityId == null) ? 0 : commodityId.hashCode());
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
		CommodityVO other = (CommodityVO) obj;
		if (commodityId == null) {
			if (other.commodityId != null)
				return false;
		} else if (!commodityId.equals(other.commodityId))
			return false;
		return true;
	}
	
	
}
