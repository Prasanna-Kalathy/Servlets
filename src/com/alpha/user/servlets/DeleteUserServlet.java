package com.alpha.user.servlets;

import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletException;
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

@WebServlet("/DltUser")
public class DeleteUserServlet extends HttpServlet {
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
		String email = request.getParameter("email");
		try {
			Statement stmt = con.createStatement();
			int result = stmt.executeUpdate("delete from user where email= '" + email + "' ");
			PrintWriter out = response.getWriter();
			if (result > 0) {
				out.println("<h1> User Deleted Sucessfully </h1>");
			} else {
				out.println("<h1> User Does't Exist </h1>");

			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	public void destroy() {
		try {
			con.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
