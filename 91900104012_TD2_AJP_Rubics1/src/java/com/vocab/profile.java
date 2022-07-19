/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.vocab;

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

/**
 *
 * @author Administrator
 */
public class profile extends HttpServlet {
    public void doGet(HttpServletRequest req, HttpServletResponse res) throws IOException {
        ConnectDb connect = new ConnectDb();
        res.setContentType("text/html");
        PrintWriter out = res.getWriter();
        HttpSession session = req.getSession();

        home hm = new home();
        out.println(hm.getMaterialize());
        out.println(hm.getNavBar());

        String name = (String) session.getAttribute("username");
        String email = (String) session.getAttribute("email");


        String password = null;

        try {
            Connection con = connect.getConnection();
            PreparedStatement stmt = con.prepareStatement("select password from user where username = ?");
            stmt.setString(1, name);

            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                password = rs.getString(1);

            }
        } catch (Exception e) {
            out.println(e);
        }

        out.println("<ul class='container'>");
        out.println("<li class='black-text'>your email id is: " + email + "</li><br>");
        out.println("<li class='black-text'>Your username is: " + name + "</li><br>");
        out.println("<li class='black-text'>Your Password is: " + password + "</li><br>");

        out.println("<a href='changePass.html'>Change my password</a>");
    }



}