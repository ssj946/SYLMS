package com_syllabus;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.util.MyServlet;

@WebServlet("/syllabusServlet/*")
public class syllabusServlet  extends MyServlet {
  /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
 
@Override
protected void execute(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
	// TODO Auto-generated method stub
	
}

   

}
