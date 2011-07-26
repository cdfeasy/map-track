/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.maptrack.client.entrys;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.XmlValue;

/**
 *
 * @author d.asadullin
 */
@XmlType(name = "name")
@XmlAccessorType(XmlAccessType.FIELD)
public class name {
	@XmlValue
	String text;

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}
	
}
