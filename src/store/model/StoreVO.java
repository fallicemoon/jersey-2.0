package store.model;

import tools.AbstractVo;
import tools.JerseyEnum.StoreType;

public class StoreVO extends AbstractVo {
	/**
	 * 
	 */
	private static final long serialVersionUID = 4628934097511568808L;
	private Integer storeId;
	private StoreType type;
	private String name;

	public Integer getStoreId() {
		return this.storeId;
	}

	public void setStoreId(Integer storeId) {
		this.storeId = storeId;
	}

	public StoreType getType() {
		return this.type;
	}

	public void setType(StoreType type) {
		this.type = type;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((storeId == null) ? 0 : storeId.hashCode());
		return result;
	}

	//StoreId一樣就是一樣
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		StoreVO other = (StoreVO) obj;
		if (storeId == null) {
			if (other.storeId != null)
				return false;
		} else if (!storeId.equals(other.storeId))
			return false;
		return true;
	}

	
	
	
}
