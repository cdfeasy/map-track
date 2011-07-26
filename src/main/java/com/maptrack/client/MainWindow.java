/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.maptrack.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.FileUpload;
import com.google.gwt.user.client.ui.FormPanel;
import com.google.gwt.user.client.ui.FormPanel.SubmitCompleteEvent;
import com.google.gwt.user.client.ui.FormPanel.SubmitCompleteHandler;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.ResizeLayoutPanel;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.TabLayoutPanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.xml.client.Document;
import com.google.gwt.xml.client.Element;
import com.google.gwt.xml.client.Node;
import com.google.gwt.xml.client.NodeList;
import com.google.gwt.xml.client.XMLParser;
import com.maptrack.client.services.IXmlService;
import com.maptrack.client.services.IXmlServiceAsync;



/**
 * Main entry point.
 *
 * @author Dmitry
 */
public class MainWindow implements EntryPoint {

    //   EditFraction fraction = new EditFraction();
    public MainWindow() {
    }
	private final IXmlServiceAsync xmlService = GWT.create(IXmlService.class);
    native void redirect(String url) /*-{
    $wnd.location.replace(url);
    }-*/;

   public String getUrlFromPath(String path) {
		String res = "";
		Document pathDom = XMLParser.parse(path);
		NodeList el = pathDom.getElementsByTagName("rte");
		NodeList pathL = ((Element) el.item(0)).getChildNodes();
		Window.alert(Integer.toString(pathL.getLength()));
		for (int i = 0; i < pathL.getLength(); i++) {

			
 			Node n =  pathL.item(i);
			Window.alert(""+n.getNodeName()+"/"+n.getPrefix()+"/"+n.getNodeType()+"/"+n.getNodeValue());
//			if (res.length()==0) {
//				res += "?saddr=";
//			} else {
//				res += "+to:";
//			}
//			String lat = n.getAttribute("lat");
//			String lon = n.getAttribute("lon");
//			res += lat + "," + lon;


		}
		return res;

	}

    @Override
    public void onModuleLoad() {
        RootPanel panel = RootPanel.get();
        Button button1 = new Button("Send ");
        Button button2 = new Button("Save ");
        Label lblTrackName=new Label("Track name: ");
        Label lblTrackCoord=new Label("Google maps url: ");
        Label fileUpload=new Label("Select track file fro device: ");
        final TextBox box = new TextBox();
        final TextBox namebox = new TextBox();
        namebox.setText("nameTrack");
        final TextBox res = new TextBox();
        final FormPanel form = new FormPanel();
        FileUpload fl = new FileUpload();
        fl.setName("upload track");
        form.add(fl);
        form.addSubmitCompleteHandler(new SubmitCompleteHandler() {

            @Override
            public void onSubmitComplete(SubmitCompleteEvent event) {
                //res.setText(event.getResults());
                String s = event.getResults().replace("<pre style=\"word-wrap: break-word; white-space: pre-wrap;\">", "").replace("</pre>", "");
                s = s.replace("&lt;", "<").replace("&gt;", ">").replace("&amp;", "").replace("&nbsp;","");
    			xmlService.GetPathForXml(s, new AsyncCallback<String>() {

                    @Override
					public void onFailure(Throwable caught) {
						// Show the RPC error message to the user
						//
					}

                    @Override
					public void onSuccess(String result) {
						redirect("http://maps.google.ru/maps"+result);
					}
				});
            }
        });

        ClickHandler cl = new ClickHandler() {
            @Override
            public void onClick(ClickEvent event) {
                form.submit();
            }
        };
        ClickHandler cl2 = new ClickHandler() {

            @Override
            public void onClick(ClickEvent event) {
//                xmlService.GetXmlForPath(box.getText(),namebox.getText(), new AsyncCallback<String>() {
//
//                    @Override
//					public void onFailure(Throwable caught) {
//						// Show the RPC error message to the user
//						//
//					}
//
//                    @Override
//					public void onSuccess(String result) {
//                        redirect(GWT.getModuleBaseURL()+"fileupload?file="+result+"&name="+namebox.getText());
//					}
//				});
                  xmlService.Base64Encode(box.getText(), new AsyncCallback<String>() {

                    @Override
					public void onFailure(Throwable caught) {
						// Show the RPC error message to the user
						//
					}

                    @Override
					public void onSuccess(String result) {
                         redirect(GWT.getModuleBaseURL()+"fileupload?file="+result+"&name="+namebox.getText());
					}
				});
              
            }
        };
        form.setAction(GWT.getModuleBaseURL() + "fileupload");
        form.setMethod(FormPanel.METHOD_POST);
        form.setEncoding(FormPanel.ENCODING_MULTIPART);

       
      


        button1.addClickHandler(cl);
        button2.addClickHandler(cl2);
        TabLayoutPanel tabPanel = new TabLayoutPanel(2.5, Unit.EM);
        String[] tabTitles = {"Load track from device","Save track from google maps"};
        HTML loadText = new HTML(tabTitles[0]);
    //    tabPanel.add(loadText, tabTitles[0]);
        HTML saveText = new HTML(tabTitles[1]);
     //   tabPanel.add(saveText, tabTitles[1]);



        com.google.gwt.user.client.ui.Grid loadTable = new com.google.gwt.user.client.ui.Grid(1,3);
         VerticalPanel loadvpanel=new VerticalPanel();
         loadvpanel.add(loadTable);
         HTML InstructionLoadText = new HTML("<b><big>Instruction:</big></b>");
         HTML step1Load= new HTML("<font size=\"4\" face=\"arial\" color=\"green\">1. Push 'Choose file' button and select track file from YouDevice:/Garmin/GPX/ directory </font>");
         HTML step2Load= new HTML("<font size=\"4\" face=\"arial\" color=\"green\">2. Push 'Send' button</font>");
         loadvpanel.add(InstructionLoadText);
         loadvpanel.add(step1Load);
         loadvpanel.add(step2Load);
         loadvpanel.setBorderWidth(1);
         loadvpanel.setWidth("100%");



		//loadTable.setText(0, 0, "View track");
        loadTable.setBorderWidth(2);
        loadTable.setWidget(0, 0, fileUpload);
		loadTable.setWidget(0, 1, form);
		loadTable.setWidget(0, 2, button1);
        tabPanel.add(loadvpanel, tabTitles[0]);
        com.google.gwt.user.client.ui.Grid saveTable = new com.google.gwt.user.client.ui.Grid(1,5);
        saveTable.setBorderWidth(2);
		//saveTable.setText(0, 0, "Save track");
        saveTable.setWidget(0, 0, lblTrackCoord);
		saveTable.setWidget(0, 1, box);
        saveTable.setWidget(0, 2, lblTrackName);
        saveTable.setWidget(0, 3, namebox);
		saveTable.setWidget(0, 4, button2);
        VerticalPanel savevpanel=new VerticalPanel();
        savevpanel.add(saveTable);
        HTML InstructionText = new HTML("<b><big>Instruction:</big></b>");
        HTML step1Save= new HTML("<font size=\"4\" face=\"arial\" color=\"green\">1. Go to <a href=\"http://maps.google.com\">Google Maps</a></font>");
        HTML step2Save= new HTML("<font size=\"4\" face=\"arial\" color=\"green\">2. Create a path</font>");
        Image addpoint=new Image("http://dl.dropbox.com/u/5596906/addpoint.png");
        HTML step3Save= new HTML("<font size=\"4\" face=\"arial\" color=\"green\">3. Copy path link</font>");
        Image copylink=new Image("http://dl.dropbox.com/u/5596906/copyLink.png");
        HTML step4Save= new HTML("<font size=\"4\" face=\"arial\" color=\"green\">4. Paste link to a 'Google maps url' field</font>");
        HTML step5Save= new HTML("<font size=\"4\" face=\"arial\" color=\"green\">5. Choose track name(this text you see in gps)</font>");
        HTML step6Save= new HTML("<font size=\"4\" face=\"arial\" color=\"green\">6. Push 'Save' button and select YouDevice:/Garmin/GPX/ folder to save your file)</font>");

        savevpanel.add(InstructionText);
        savevpanel.add(step1Save);
        savevpanel.add(step2Save);
        savevpanel.add(addpoint);
        savevpanel.add(step3Save);
        savevpanel.add(copylink);
        savevpanel.add(step4Save);
        savevpanel.add(step5Save);
        savevpanel.add(step6Save);
        savevpanel.setBorderWidth(1);
        savevpanel.setWidth("100%");
        savevpanel.setHeight("100%");

        tabPanel.add(savevpanel, tabTitles[1]);
        
       
        ResizeLayoutPanel resizePanel = new ResizeLayoutPanel();
        resizePanel.setSize("1000px","900px");
        resizePanel.setWidget(tabPanel);


        panel.add(resizePanel);
     //   panel.add(addpoint);
     //   panel.add(copylink);

       
    }
    // native void jsClickUpload( Element pElement ) ;
}
