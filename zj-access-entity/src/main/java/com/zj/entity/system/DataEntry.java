package com.zj.entity.system;

import java.io.Serializable;

public class DataEntry implements Serializable, EntityEnableIF {
	

	private static final long serialVersionUID = -8199088509167630055L;
	private String catalogString;
	private String keyString;
	private String valueString;
	private boolean enabled = true;
	private Integer orderSeq;

	
	public String getCatalogString() {
		return catalogString;
	}
	public void setCatalogString(String catalogString) {
		this.catalogString = catalogString;
	}
	public String getKeyString() {
		return keyString;
	}
	public void setKeyString(String keyString) {
		this.keyString = keyString;
	}
	public String getValueString() {
		return valueString;
	}
	public void setValueString(String valueString) {
		this.valueString = valueString;
	}
	public boolean isEnabled() {
		return enabled;
	}
	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}
	public Integer getOrderSeq() {
		return orderSeq;
	}
	public void setOrderSeq(Integer orderSeq) {
		this.orderSeq = orderSeq;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((catalogString == null) ? 0 : catalogString.hashCode());
		result = prime * result + ((keyString == null) ? 0 : keyString.hashCode());
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
		DataEntry other = (DataEntry) obj;
		if (catalogString == null) {
			if (other.catalogString != null)
				return false;
		} else if (!catalogString.equals(other.catalogString))
			return false;
		if (keyString == null) {
			if (other.keyString != null)
				return false;
		} else if (!keyString.equals(other.keyString))
			return false;
		return true;
	}

}
