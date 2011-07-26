/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.maptrack.client.services;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

/**
 *
 * @author d.asadullin
 */
@RemoteServiceRelativePath("XmlService")
public interface IXmlService extends RemoteService{
	public String GetPathForXml(String xm);
	public String GetXmlForPath(String xm, String name);
    public String Base64Decode(String xml);
    public String Base64Encode(String xml);
}
