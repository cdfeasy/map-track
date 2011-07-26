/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.maptrack.client.entrys;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;

/**
 *
 * @author d.asadullin
 */
@XmlType(name = "rtept")
@XmlAccessorType(XmlAccessType.FIELD)

public class rtept {
	@XmlAttribute
	String lat;
	
	@XmlAttribute
	String lon;
	
	@XmlElement
	name name;

	public String getLat() {
		return lat;
	}

	public void setLat(String lat) {
		this.lat = lat;
	}

	public String getLon() {
		return lon;
	}

	public void setLon(String lon) {
		this.lon = lon;
	}

	public name getName() {
		return name;
	}

	public void setName(name name) {
		this.name = name;
	}
}
