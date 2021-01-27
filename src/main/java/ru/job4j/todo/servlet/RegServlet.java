package ru.job4j.todo.servlet;

import ru.job4j.todo.model.User;
import ru.job4j.todo.store.HibernateUserStore;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

/**
 * @author Egor Geraskin(yegeraskin13@gmail.com)
 * @version 1.0
 * @since 25.01.2021
 */
public class RegServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("/reg.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");
        req.setCharacterEncoding("UTF-8");
        String name = req.getParameter("name");
        System.out.println(name);
        String login = req.getParameter("login");
        String password = req.getParameter("password");
        User user = HibernateUserStore.instOf().get(login);

        if (user != null) {
            resp.sendRedirect("reg.jsp?errMsg=\"Login already exists\"");
        } else {
            user = HibernateUserStore.instOf().add(new User(login, password, name));
            req.getSession().setAttribute("user", user);
            resp.sendRedirect("index.do");
        }
    }
}
