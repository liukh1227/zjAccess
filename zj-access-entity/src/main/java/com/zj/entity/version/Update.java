package com.zj.entity.version;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * @author liukh
 * @date 2016-12-7 上午10:41:36
 */
@XmlRootElement(name = "update")
public class Update {
	
	private String channel;
	private String version;
	private String name;
	private String url;
	private String updatetip;
	private String force;

	@XmlAttribute(name = "channel")
	public String getChannel() {
		return channel;
	}

	public void setChannel(String channel) {
		this.channel = channel;
	}
	
	@XmlElement(name = "version")
	public String getVersion() {
		return version;
	}

	@XmlElement(name = "name")
	public String getName() {
		return name;
	}

	@XmlElement(name = "url")
	public String getUrl() {
		return url;
	}

	@XmlElement(name = "updatetip")
	public String getUpdatetip() {
		return updatetip;
	}

	@XmlElement(name = "force")
	public String getForce() {
		return force;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public void setUpdatetip(String updatetip) {
		this.updatetip = updatetip;
	}

	public void setForce(String force) {
		this.force = force;
	}

	@Override
	public String toString() {
		return "Update [channel=" + channel + ", version=" + version
				+ ", name=" + name + ", url=" + url + ", updatetip="
				+ updatetip + ", force=" + force + "]";
	}


}
