
package com.mytechnopal.banner;

import java.io.File;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

@MultipartConfig
public class UploadFileController extends HttpServlet {
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String action = request.getParameter("txtAction");
		String fileId = request.getParameter("txtFileId");
		String label = request.getParameter("txtLabel");
		String fileType = request.getParameter("txtFileType");

		Part filePart = request.getPart("file");
		String fileName = filePart.getSubmittedFileName();
		String uploadDir = this.getServletContext().getRealPath("") + File.separator + "uploads";

		File uploadDirFile = new File(uploadDir);
		if (!uploadDirFile.exists()) {
			uploadDirFile.mkdirs();
		}

		filePart.write(uploadDir + File.separator + fileName);

		response.setContentType("application/json");
		response.getWriter().write("{\"MESSAGE_TYPE\": \"SUCCESS\", \"uploadedFileContent\": \"" + fileName
				+ "\", \"uploadedFileRemarks\": \"File uploaded successfully.\"}");
	}
}
