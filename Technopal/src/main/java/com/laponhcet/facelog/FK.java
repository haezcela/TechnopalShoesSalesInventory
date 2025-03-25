package com.laponhcet.facelog;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.mytechnopal.SessionInfo;
import com.mytechnopal.dao.SettingsDAO;
import com.mytechnopal.dto.SettingsDTO;

/**
 * Servlet implementation class FK
 */
public class FK extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public FK() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		SessionInfo sessionInfo = new SessionInfo();
		request.getSession().setAttribute(SessionInfo.SESSION_INFO, sessionInfo);
		RequestDispatcher rd = request.getRequestDispatcher("/faceLog/faceLog.jsp");
		rd.forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
