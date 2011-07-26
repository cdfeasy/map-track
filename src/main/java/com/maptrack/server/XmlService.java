/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.maptrack.server;

import com.google.appengine.repackaged.com.google.common.util.Base64;
import com.google.appengine.repackaged.com.google.common.util.Base64DecoderException;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import com.maptrack.client.entrys.consts;
import com.maptrack.client.gpx.GpxType;
import com.maptrack.client.gpx.LinkType;
import com.maptrack.client.gpx.MetadataType;
import com.maptrack.client.gpx.RteType;
import com.maptrack.client.gpx.WptType;
import com.maptrack.client.services.IXmlService;
//import com.sun.xml.internal.bind.marshaller.NamespacePrefixMapper;
import java.io.File;
import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;

/**
 *
 * @author Dmitry
 */
public class XmlService extends RemoteServiceServlet implements IXmlService {


    @Override
    public String GetPathForXml(String source) {
       javax.xml.bind.JAXBContext context = null;
       javax.xml.bind.Unmarshaller unmarshaller=null;;
       JAXBElement<GpxType>  result = null;
        try {
            context = javax.xml.bind.JAXBContext.newInstance(new Class[]{GpxType.class});
            unmarshaller = context.createUnmarshaller();

        } catch (JAXBException ex) {
            Logger.getLogger(XmlService.class.getName()).log(Level.SEVERE, null, ex);
        }

        if (source instanceof String) {
            try {
                    result = unmarshaller.unmarshal(XMLInputFactory.newFactory().createXMLStreamReader(new StringReader(source)), GpxType.class);

            } catch (Exception ex) {
                 throw new UnsupportedOperationException("Source must String or InputStream but not " + source.getClass().getName());
            }
        } else {
            throw new UnsupportedOperationException("Source must String or InputStream but not " + source.getClass().getName());
        }
        GpxType p =  result.getValue();
        String Sresult = "";
        int count=p.getRte().get(0).getRtept().size();
        if(count>25) count=25;
        for (int i = 0; i < count; i++) {
            if (i > 1) {
                Sresult += "+to:";
            } else if (i == 0) {
                Sresult += "?saddr=";
            } else if (i == 1) {
                Sresult += "&daddr=";
             } else {
            }
            String lat =p.getRte().get(0).getRtept().get(i).getLat();
            String lon = p.getRte().get(0).getRtept().get(i).getLon();
            Sresult += lat + "," + lon;

        }
        return Sresult;
    }

    private static String addNamespaces(String xml)
    {
         return xml.replace("xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\"", "xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xmlns:gpxx=\"http://www.garmin.com/xmlschemas/WaypointExtension/v1\" xmlns:gpxtrx=\"http://www.garmin.com/xmlschemas/GpxExtensions/v3\" xmlns:gpxtpx=\"http://www.garmin.com/xmlschemas/TrackPointExtension/v1\" ");
    }

    private static String getXml(String path, String name) throws JAXBException, IOException
    {
        String[] _pathParts=path.split("#");
        ArrayList<String> points=new ArrayList<String>();
        for(String pathPart:_pathParts)
        {
            String _path=pathPart.substring(path.indexOf("?")+1);

            String[] _points=_path.split("((saddr=)|(&daddr=)|(\\+to:))");
            for(String s:_points)
             {

                String val=s.replace("+", "");
                if(val.length()>0)
                 if(val.indexOf("&")!=-1)
                     points.add(val.substring(0,val.indexOf("&")));
                 else
                     points.add(val);
             }
        }

        GpxType p=new GpxType();
        MetadataType metadata=new MetadataType();
        LinkType link=new LinkType();
        link.setHref("http://www.garmin.com");
                link.setText("Garmin International");

        metadata.getLink().add(link);
        p.setMetadata(metadata);

		RteType rte=new RteType();
        rte.setName(name);
        for(int i=0;i<points.size();i++)
        {
            String[] latlon=points.get(i).split(",");
            WptType d=new WptType();
            d.setLat(latlon[0]);
            d.setLon(latlon[1]);
            String pointname="";
            if(i>25)
                pointname=new String(new char[]{(char)(65+i%25)})+Integer.toString(i/25);
            else
                pointname=new String(new char[]{(char)(65+i)});
            d.setName(pointname);
            rte.getRtept().add(d);
        }
		p.getRte().add(rte);
		javax.xml.bind.JAXBContext context = javax.xml.bind.JAXBContext.newInstance(new Class[]{GpxType.class}, new HashMap());
        javax.xml.bind.Marshaller marshaller = context.createMarshaller();
        marshaller.setProperty(Marshaller.JAXB_SCHEMA_LOCATION, consts.schemaLocation);
        StringWriter stringWriter = new StringWriter();
        marshaller.marshal(p, stringWriter);
        String res=stringWriter.toString();
        try {
            context=null;
            marshaller=null;
            stringWriter.close();
        } catch (IOException ex) {

        }
        return res;
    }

    @Override
    public String GetXmlForPath(String path, String name) {
        return localGetXmlForPath(path,name);
    }

     public static String localGetXmlForPath(String path, String name) {
        try {
            return addNamespaces(getXml(path,name));
        } catch (JAXBException ex) {

        } catch (IOException ex) {

        }
        return null;
    }

    @Override
    public String Base64Decode(String xml) {
        return localBase64Decode(xml);
    }

    public static String localBase64Decode(String xml) {
        try {
            return new String(Base64.decode(xml));
        } catch (Base64DecoderException ex) {
            Logger.getLogger(XmlService.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    @Override
    public String Base64Encode(String xml) {
        return localBase64Encode(xml);
    }

     public static String localBase64Encode(String xml) {
          return Base64.encode(xml.getBytes());
       
    }
}
