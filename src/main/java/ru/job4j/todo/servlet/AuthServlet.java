package ru.job4j.todo.servlet;

import org.json.simple.JSONObject;
import org.json.simple.JSONValue;
import ru.job4j.todo.model.User;
import ru.job4j.todo.store.HibernateUserStore;
import ru.job4j.todo.store.UserStore;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.nio.Buffer;
import java.util.stream.Collectors;

/**
 * Сервлет для страницы авторизации
 * пользователей.
 *
 * Поддерживает валидацию, если
 * введены неверные данные, пользователю
 * будет выведено всплывающее окно
 * с соответствующей ошибкой.
 *
 * @author Egor Geraskin(yegeraskin13@gmail.com)
 * @version 1.0
 * @since 25.01.2021
 */
public class AuthServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        RequestDispatcher disp = req.getRequestDispatcher("/auth.jsp");
        disp.forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");
        String login = req.getParameter("login");
        String password = req.getParameter("password");
        UserStore store = HibernateUserStore.instOf();
        User user = store.get(login);

        if (user == null) {
            resp.sendRedirect("auth.jsp?errMsg=\"No such login\"");
        } else if (!user.getPassword().equals(password)) {
            resp.sendRedirect("auth.jsp?errMsg=\"Incorrect password\"");
        } else {
            HttpSession session = req.getSession();
            session.setAttribute("user", user);
            resp.sendRedirect("index.do");
        }
    }
}
