package ru.easy.imagebase.maptrack;

import com.maptrack.client.gpx.LinkType;
import com.maptrack.client.gpx.MetadataType;
import com.maptrack.client.gpx.WptType;
import com.maptrack.client.gpx.RteType;
import com.maptrack.client.gpx.GpxType;
import javax.xml.bind.Marshaller;
//import com.sun.xml.internal.bind.marshaller.NamespacePrefixMapper;
import java.io.StringReader;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.io.StringWriter;
import com.maptrack.client.entrys.*;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import org.junit.Test;

/**
 * Unit test for simple App.
 */
public class AppTest {

    @Test 
    public void Apptest4()throws JAXBException, IOException, XMLStreamException
    {
//        String source=org.apache.commons.io.FileUtils.readFileToString(new File("G:/Garmin/GPX/Маршрут_09-ИЮЛ-11 122243 AM.gpx"));
//       javax.xml.bind.JAXBContext context = javax.xml.bind.JAXBContext.newInstance(new Class[]{GpxType.class});
//       javax.xml.bind.Unmarshaller unmarshaller = context.createUnmarshaller();
//       JAXBElement<GpxType>  result = null;
//        if (source instanceof String) {
//            try {
//                result = unmarshaller.unmarshal(XMLInputFactory.newFactory().createXMLStreamReader(new StringReader(source)),GpxType.class);
//            } catch (JAXBException ex) {
//                 throw new UnsupportedOperationException("Source must String or InputStream but not " + source.getClass().getName());
//            }
//        } else {
//            throw new UnsupportedOperationException("Source must String or InputStream but not " + source.getClass().getName());
//        }
//        GpxType p =  result.getValue();
//        String Sresult = "";
//        int count=p.getRte().get(0).getRtept().size();
//        if(count>25) count=25;
//        for (int i = 0; i < count; i++) {
//            if (i > 1) {
//                Sresult += "+to:";
//            } else if (i == 0) {
//                Sresult += "?saddr=";
//            } else if (i == 1) {
//                Sresult += "&daddr=";
//             } else {
//            }
//            String lat =p.getRte().get(0).getRtept().get(i).getLat();
//            String lon = p.getRte().get(0).getRtept().get(i).getLon();
//            Sresult += lat + "," + lon;
//
//        }
//        System.out.println(Sresult);
    }

	@Test
    public void AppTest1( ) throws JAXBException, IOException
    {
	
		PathInfo p=new PathInfo();
		rte t=new rte();
		rtept d=new rtept();
		d.setLat("123");
		d.setLon("234");
		name n=new name();
		n.setText("23");
		d.setName(n);
		t.getRtept().add(d);
		p.setRte(t);
		 javax.xml.bind.JAXBContext context = javax.xml.bind.JAXBContext.newInstance(new Class[]{PathInfo.class}, new HashMap());
        javax.xml.bind.Marshaller marshaller = context.createMarshaller();
     //   marshaller.setProperty(Marshaller., "http://model model.xsd");
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
		System.out.println(res);
		
    }

    @Test
    public void AppTest3( ) throws JAXBException, IOException
    {

		GpxType p=new GpxType();
        MetadataType metadata=new MetadataType();
        LinkType link=new LinkType();
        link.setHref("http://www.garmin.com");
        link.setText("Garmin International");
        
        metadata.getLink().add(link);
        p.setMetadata(metadata);

		RteType t=new RteType();
		WptType d=new WptType();
		d.setLat("123");
		d.setLon("234");
        d.setName("23");
		t.getRtept().add(d);
        t.setName("edgfdfg");
		p.getRte().add(t);
		 javax.xml.bind.JAXBContext context = javax.xml.bind.JAXBContext.newInstance(new Class[]{GpxType.class}, new HashMap());
        javax.xml.bind.Marshaller marshaller = context.createMarshaller();
          marshaller.setProperty(Marshaller.JAXB_SCHEMA_LOCATION, consts.schemaLocation);
     //   marshaller.setProperty(Marshaller., "http://model model.xsd");
       // marshaller.setProperty(Marshaller.JAXB_SCHEMA_LOCATION, consts.schemaLocation);
        StringWriter stringWriter = new StringWriter();
        marshaller.marshal(p, stringWriter);
        String res=stringWriter.toString();
        try {
            context=null;
            marshaller=null;
            stringWriter.close();
        } catch (IOException ex) {

        }
        res=res.replace("xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\"", "xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xmlns:gpxx=\"http://www.garmin.com/xmlschemas/WaypointExtension/v1\" xmlns:gpxtrx=\"http://www.garmin.com/xmlschemas/GpxExtensions/v3\" xmlns:gpxtpx=\"http://www.garmin.com/xmlschemas/TrackPointExtension/v1\" ");
		System.out.println(res);

    }
	
	//@Test
    public void AppTest2( ) throws JAXBException, IOException
    {
	
		String source=org.apache.commons.io.FileUtils.readFileToString(new File("G:/Garmin/GPX/Маршрут_09-ИЮЛ-11 122243 AM.gpx"));
		javax.xml.bind.JAXBContext context = javax.xml.bind.JAXBContext.newInstance(new Class[]{PathInfo.class}, new HashMap());
        javax.xml.bind.Unmarshaller unmarshaller = context.createUnmarshaller();

        Object result = null;
        if (source instanceof String) {
            result = unmarshaller.unmarshal(new StringReader((String) source));
        }
		else {
            throw new UnsupportedOperationException("Source must String or InputStream but not " + source.getClass().getName());
        }
		PathInfo p=(PathInfo)result;
		System.out.println(p.getRte().getRtept().size());
		
    }
	
	

    

}
