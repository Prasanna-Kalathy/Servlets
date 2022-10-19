package com.alpha.user.servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class CreateUserServlet
 */
@WebServlet("/Udserv")
public class UpdateUserServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private Connection con;

	public void init() {
		try {
			System.out.println("init()");
			Class.forName("com.mysql.jdbc.Driver");
			con = DriverManager.getConnection("jdbc:mysql://localhost/mydb", "student", "student");
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		System.out.println("doPost()");
		String email = request.getParameter("email");
		String password = request.getParameter("password");

		try {
			Statement stmt = con.createStatement();
			int result = stmt.executeUpdate("update user set password='" + password + "' where email='" + email + "'");

			PrintWriter out = response.getWriter();

			if (result > 0) {
				out.println("<h1>YOUR PASSWORD UPDATED SUCESSFULLY</h1>");
			} else {
				out.println("<h1>PASSWOD UPDATION FAILED</h1>");

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
