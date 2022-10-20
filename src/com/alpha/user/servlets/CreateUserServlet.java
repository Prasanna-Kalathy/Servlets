package com.alpha.user.servlets;

import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebInitParam;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Servlet  implementation class CreateUserServlet
 */
@WebServlet(urlPatterns="/MyUser")
public class CreateUserServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private Connection con;

	public void init(ServletConfig config ) {
		try {
			ServletContext context = config.getServletContext();
			System.out.println("init()");
			Class.forName("com.mysql.jdbc.Driver");
			con = DriverManager.getConnection(context.getInitParameter("dbUrl"),
											  context.getInitParameter("dbUser"),
											  context.getInitParameter("dbPass"));
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		System.out.println("doPost()");
		String firstname = request.getParameter("firstname");
		String lastname = request.getParameter("lastname");
		String email = request.getParameter("email");
		String password = request.getParameter("password");

		try {
			Statement stmt = con.createStatement();
			int result = stmt.executeUpdate("insert into user values('" + firstname + "','" + lastname + "','" + email
					+ "','" + password + "')");

			PrintWriter out = response.getWriter();

			if (result > 0) {
				out.println("<h1>USER CREATED SUCESSFULLY</h1>");
			} else {
				out.println("<h1>USER CREATION FAILED</h1>");

			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void destroy() {
		try {
			System.out.println("destroy()");
			con.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

}
