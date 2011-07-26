/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.maptrack.server;

import com.google.appengine.repackaged.com.google.common.util.Base64;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import org.apache.commons.fileupload.FileItemStream;
import org.apache.commons.fileupload.FileItemIterator;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import java.io.InputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;
import java.util.logging.Logger;
import javax.servlet.ServletContext;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author d.asadullin
 */
public class FileUploadServlet extends HttpServlet {

    private static final Logger log =
            Logger.getLogger(FileUploadServlet.class.getName());
//private BlobstoreService blobstoreService = BlobstoreServiceFactory.getBlobstoreService();
    byte[] bbuf = new byte[1024];
    private static final String textFileName = "files/text.txt";

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

      
        try {
            ServletOutputStream out = response.getOutputStream();
            ServletContext context = getServletConfig().getServletContext();

            String file=XmlService.localBase64Decode(request.getParameter("file"));
            String content=XmlService.localGetXmlForPath(file, request.getParameter("name"));
            String filename=request.getParameter("name")+".gpx";
            String mimetype = context.getMimeType(filename);

            response.setContentType((mimetype != null) ? mimetype : "application/octet-stream");
            response.setContentLength(content.length());
            response.setHeader("Content-Disposition", "attachement; filename=\"" + filename + "\"");

           // DataInputStream in = new DataInputStream(new FileInputStream(file));

            int length;
//            while ((in != null) && ((length = in.read(bbuf)) != -1)) {
//                out.write(bbuf, 0, length);
//            }
              out.write(content.getBytes());

           // in.close();
            out.flush();
            out.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void doPost(HttpServletRequest req, HttpServletResponse res)
            throws ServletException, IOException {
        try {
//	    
            log.info("------");
            log.info(req.toString());
            log.info(res.toString());
            ServletFileUpload upload = new ServletFileUpload();
            res.setContentType("text/plain");

            FileItemIterator iterator = upload.getItemIterator(req);
            while (iterator.hasNext()) {
                FileItemStream item = iterator.next();
                InputStream stream = item.openStream();

                if (item.isFormField()) {
                    log.info("Got a form field: " + item.getFieldName());
                } else {
                    log.info("Got an uploaded file: " + item.getFieldName()
                            + ", name = " + item.getName());

                    // You now have the filename (item.getName() and the
                    // contents (which you can read from stream).  Here we just
                    // print them back out to the servlet output stream, but you
                    // will probably want to do something more interesting (for
                    // example, wrap them in a Blob and commit them to the
                    // datastore).
                    int len;
                    byte[] buffer = new byte[8192];
                    while ((len = stream.read(buffer, 0, buffer.length)) != -1) {
                        res.getOutputStream().write(buffer, 0, len);
                        log.info("1");
                    }
                }

            }

        } catch (Exception ex) {
            throw new ServletException(ex);
        }
    }
}
