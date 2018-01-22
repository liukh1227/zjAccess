package com.zj.entity.version;
import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElements;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * @author liukh
 * @date 2016-12-7 上午10:43:39
 */
@XmlRootElement(name = "aPPVersion")
public class LeasingSideVersion {
	private List<Update> leasingSidelist;

	public List<Update> getLeasingSidelist() {
		if(leasingSidelist == null){
			leasingSidelist = new ArrayList<Update>();
		}
		return leasingSidelist;
	}
	 @XmlElements({@XmlElement(name="update", type=Update.class)})
	public void setLeasingSidelist(List<Update> leasingSidelist) {
		this.leasingSidelist = leasingSidelist;
	}

	@Override
	public String toString() {
		return "LeasingSideVersion [leasingSidelist=" + leasingSidelist + "]";
	}
	
	
	
}
