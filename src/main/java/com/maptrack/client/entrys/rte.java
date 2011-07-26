/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.maptrack.client.entrys;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

/**
 *
 * @author d.asadullin
 */
@XmlType(name = "rte")
@XmlAccessorType(XmlAccessType.FIELD)
public class rte {
	@XmlElement
	name name;
	
	@XmlElement
	List<rtept> rtept=new ArrayList<rtept>();

	public name getName() {
		return name;
	}

	public void setName(name name) {
		this.name = name;
	}

	public List<com.maptrack.client.entrys.rtept> getRtept() {
		return rtept;
	}

	public void setRtepts(List<com.maptrack.client.entrys.rtept> rtepts) {
		this.rtept = rtepts;
	}
	
}
