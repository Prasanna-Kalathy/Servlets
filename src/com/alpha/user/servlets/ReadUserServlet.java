package com.alpha.user.servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

//@WebServlet("/ReadServlet")
public class ReadUserServlet extends HttpServlet {
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

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		System.out.println("doGet()");

		try {
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery("Select * from user ");
			PrintWriter out = response.getWriter();
			out.print("<table>");
			out.print("<tr>");
			
			out.print("<th>");
			out.print("First Name");
			out.print("</th>");
			
			out.print("<th>");
			out.print("last Name");
			out.print("</th>");
			
			out.print("<th>");
			out.print("Email");
			out.print("</th>");
			
			out.print("</tr>");
			
			while(rs.next()) {
				out.println("<tr>");
				
				out.println("<td>");
				out.println(rs.getString(1));
				out.println("</td>");
				
				out.println("<td>");
				out.println(rs.getString(2));
				out.println("</td>");
				
				out.println("<td>");
				out.println(rs.getString(3));
				out.println("</td>");
				
				out.println("</tr>");
			}
			
			out.print("</table>");
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
