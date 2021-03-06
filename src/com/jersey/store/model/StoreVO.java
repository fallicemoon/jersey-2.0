package com.jersey.store.model;

import com.jersey.tools.AbstractVo;
import com.jersey.tools.JerseyEnum.StoreType;

public class StoreVO extends AbstractVo {
	/**
	 * 
	 */
	private static final long serialVersionUID = 4628934097511568808L;
	private StoreType type;
	private String name;

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
		result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
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
		if (getId() == null) {
			if (other.getId() != null)
				return false;
		} else if (!getId().equals(other.getId()))
			return false;
		return true;
	}

	
	
	
}
