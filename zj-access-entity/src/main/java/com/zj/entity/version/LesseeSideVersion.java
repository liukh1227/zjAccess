package com.zj.entity.version;
import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElements;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * @author liukh
 * @date 2016-12-7 上午10:43:54
 */
@XmlRootElement(name = "aPPVersion")
public class LesseeSideVersion {
	private List<Update> lesseeSideList;

	public List<Update> getLesseeSideList() {
		if(lesseeSideList == null){
			lesseeSideList = new ArrayList<Update>();
		}
		return lesseeSideList;
	}
	 @XmlElements({@XmlElement(name="update", type=Update.class)})
	public void setLesseeSideList(List<Update> lesseeSideList) {
		this.lesseeSideList = lesseeSideList;
	}
	

}
