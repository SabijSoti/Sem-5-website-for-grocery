/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.vocab;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Administrator
 */
public class authorize extends HttpServlet {
    public void doGet(HttpServletRequest req, HttpServletResponse res) throws IOException, ServletException {
        ConnectDb connect = new ConnectDb();
        res.setContentType("text/html");
        PrintWriter out = res.getWriter();

        String username = (String) req.getParameter("Username");
        String password = (String) req.getParameter("Password");
        String email = "unknown@vocab.aasu";
        try {
            Connection con = connect.getConnection();

            PreparedStatement stmt;

            stmt = con.prepareStatement("select username, password,email from user");

            ResultSet rs;

            rs = stmt.executeQuery();

            int chk = 0;

            while (rs.next()) {
                String usr = rs.getString(1);
                String pass = rs.getString(2);
                email = rs.getString(3);
                if (usr.equals(username) && pass.equals(password)) {
                    chk = 1;
                    HttpSession session = req.getSession();
                    session.setAttribute("email", email);
                    session.setAttribute("username", username);
                }
            }

            if (chk == 0) {
                out.println(username + "  " + password);
                out.println("Sorry! the provided credentials are incorrect\n");
                out.println("<a href='index.html'>Click me</a> to return to the login page");
            } else {


                RequestDispatcher rd = req.getRequestDispatcher("home");
                rd.forward(req, res);
            }

        } catch (ClassNotFoundException ex) {
            Logger.getLogger(authorize.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(authorize.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
}