/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.maptrack.client.entrys;

//import com.sun.xml.internal.bind.v2.WellKnownNamespace;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlNs;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

/**
 *
 * @author d.asadullin
 */
//@XmlType(namespace="http://www.garmin.com/xmlschemas/WaypointExtension/v1", name = "gpx")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name="gpx")
@XmlType
//@XmlNs(prefix="gpxx" namespaceURI="http://www.garmin.com/xmlschemas/WaypointExtension/v1" )

public class PathInfo {
	@XmlElement
	metadata metadata;
	
	@XmlElement
	rte rte;
    @XmlAttribute
    String version="1.1";
    @XmlAttribute
    String creator="GPSMAP 62s";

    public String getCreator() {
        return creator;
    }

    public void setCreator(String creator) {
        this.creator = creator;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }


	public com.maptrack.client.entrys.metadata getMetadata() {
		return metadata;
	}

	public void setMetadata(com.maptrack.client.entrys.metadata metadata) {
		this.metadata = metadata;
	}

	public com.maptrack.client.entrys.rte getRte() {
		return rte;
	}

	public void setRte(com.maptrack.client.entrys.rte rte) {
		this.rte = rte;
	}
	
	
}
