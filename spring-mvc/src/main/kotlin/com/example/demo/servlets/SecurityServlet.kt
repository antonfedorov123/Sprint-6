package com.example.demo.servlets

import java.io.IOException
import java.time.Instant
import javax.servlet.ServletException
import javax.servlet.annotation.WebServlet
import javax.servlet.http.Cookie
import javax.servlet.http.HttpServlet
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

@WebServlet(urlPatterns = ["/login"])
class SecurityServlet : HttpServlet() {

    private val username = "admin"
    private val password = "pass"

    override fun doGet(req: HttpServletRequest?, resp: HttpServletResponse?) {
        req!!.getRequestDispatcher("/login.html").forward(req, resp)
    }

    override fun doPost(req: HttpServletRequest?, resp: HttpServletResponse?) {
        if (username == req?.getParameter("login") && password == req.getParameter("password")) {
            resp!!.addCookie(Cookie("auth", Instant.now().toString()))
            resp.sendRedirect("/login.html")
        }
        else {
            resp!!.sendRedirect("/loginError.html")
        }
    }


    /*

            val usernamePost = request?.getParameter("username")
        val passwordPost = request?.getParameter("password")

        if (usernamePost.equals(username) && passwordPost.equals(password)) {
            val cookie = Cookie("auth", System.currentTimeMillis().toString())
            response!!.addCookie(cookie)
            response.sendRedirect("/app/add")
        } else {
            val requestDispatcher = servletContext.getRequestDispatcher("/login.html")
            val out = response!!.writer
            out.println("<font color=red>Either user name or password is wrong.</font>")
            requestDispatcher.include(request, response)
        }



     */

}