package com.javaparttwo.servlet;

import java.io.IOException;
import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;
import com.javaparttwo.service.AuthService;
import com.javaparttwo.service.DepartmentService;

/**
 * Handles student requests and responses.
 */
@WebServlet({"/StudentServlet", "/student"})
public class StudentServlet extends HttpServlet {

    /**
     * Java related serial version UID.
     */
    private static final long serialVersionUID = 1L;

    /**
     * An instance of the database connection.
     */
    @Resource(name = "jdbc/javapart3")
    private DataSource ds;

    /**
     * Handles all GET requests.
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        AuthService authService = new AuthService(request.getSession());

        if (!authService.isLoggedIn()) {
            response.sendRedirect("login");
            return;
        }

        if (!authService.hasRole("student")) {
            response.sendError(HttpServletResponse.SC_FORBIDDEN);
            return;
        }

        DepartmentService deptService = new DepartmentService(ds);

        request.setAttribute("department",
                deptService.getDepartment(authService.getUser().getDepartmentId()));
        request.getRequestDispatcher("WEB-INF/views/student/index.jsp").forward(request, response);
    }
}
