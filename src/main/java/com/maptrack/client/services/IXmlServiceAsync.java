/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.maptrack.client.services;

import com.google.gwt.user.client.rpc.AsyncCallback;

/**
 *
 * @author d.asadullin
 */
public interface IXmlServiceAsync {
	 public void GetPathForXml(String xml, AsyncCallback<String> asyncCallback);
     public void GetXmlForPath(String path, String name, AsyncCallback<String> asyncCallback);
     public void Base64Decode(String xml, AsyncCallback<String> asyncCallback);
     public void Base64Encode(String xml, AsyncCallback<String> asyncCallback);
}
