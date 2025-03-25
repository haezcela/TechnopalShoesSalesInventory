package com.laponhcet.news;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.mytechnopal.SessionInfo;
import com.mytechnopal.link.LinkDTO;

/**
 * Servlet implementation class News
 */
@WebServlet("/News")
public class News extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public News() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		SessionInfo sessionInfo = new SessionInfo();
		sessionInfo.setCurrentLink(new LinkDTO());

		String newsCode = request.getParameter("code");
		NewsDTO news = new NewsDAO().getNewsByCode(newsCode);
		if(news == null) {
			
		}
		else {
			
		}
		
		request.getSession().setAttribute(SessionInfo.SESSION_INFO, sessionInfo);
		request.getSession().setAttribute(NewsDTO.SESSION_NEWS, news);
		RequestDispatcher rd = request.getRequestDispatcher("news.jsp");
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
