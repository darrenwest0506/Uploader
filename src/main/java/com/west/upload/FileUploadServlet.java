package com.west.upload;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

import org.apache.commons.fileupload.FileItemIterator;
import org.apache.commons.fileupload.FileItemStream;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.fileupload.util.Streams;

public class FileUploadServlet extends HttpServlet{


	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse resp) throws ServletException ,IOException {
		boolean isMultipart = ServletFileUpload.isMultipartContent(request);
		
		// Create a new file upload handler
		ServletFileUpload upload = new ServletFileUpload();

		// Parse the request
		FileItemIterator iter;
		try {
			iter = upload.getItemIterator(request);
	
			while (iter.hasNext()) {
			    FileItemStream item = iter.next();
			    String name = item.getFieldName();
			    InputStream stream = item.openStream();
			    if (item.isFormField()) {
			        System.out.println("Form field " + name + " with value "
			            + Streams.asString(stream) + " detected.");
			    } else {
			        System.out.println("File field " + name + " with file name "
			            + item.getName() + " detected.");
			        // Process the input stream
				    int read = 0;
			        final byte[] bytes = new byte[1024];
			        FileOutputStream fileOut = new FileOutputStream(new File(item.getName()));
			        while ((read = stream.read(bytes)) != -1) {
			            System.out.println("read " + read + " bytes");
			            fileOut.write(bytes, 0, read);
			        }
			        stream.close();
			        fileOut.close();
			    }
			}
		} catch (FileUploadException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		
		
//		final Part filePart = req.getPart("file");
//	    int read = 0;
//        final byte[] bytes = new byte[1024];
//        InputStream filecontent = filePart.getInputStream();
//	    final String fileName = getFileName(filePart);
//        FileOutputStream fileOut = new FileOutputStream(new File(fileName));
//        while ((read = filecontent.read(bytes)) != -1) {
//            System.out.println("read " + read + " bytes");
//            fileOut.write(bytes, 0, read);
//        }
//        filecontent.close();
//        fileOut.close();
        
	}
	
	private String getFileName(final Part part) {
	    final String partHeader = part.getHeader("content-disposition");
	    for (String content : part.getHeader("content-disposition").split(";")) {
	        if (content.trim().startsWith("filename")) {
	            return content.substring(
	                    content.indexOf('=') + 1).trim().replace("\"", "");
	        }
	    }
	    return null;
	}
}
