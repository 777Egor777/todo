package ru.job4j.todo.servlet;

import ru.job4j.todo.model.User;
import ru.job4j.todo.store.HibernateUserStore;
import ru.job4j.todo.store.UserStore;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

/**
 * Сервлет для страницы регистрации
 * пользователей.
 *
 * Поддерживает валидацию, если
 * введённый e-mail уже занят,
 * пользователю будет выведено
 * всплывающее окно с соответствующим
 * сообщением.
 *
 * @author Egor Geraskin(yegeraskin13@gmail.com)
 * @version 1.0
 * @since 25.01.2021
 */
public class RegServlet extends HttpServlet {

    /**
     * Переопределяет метод doGet
     * класса HttpServlet.
     *
     * Перенаправляет запрос на JSP
     * @param req - объект запроса(HttpServletRequest)
     * @param resp - объект ответа(HttpServletResponse)
     */
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        RequestDispatcher disp = req.getRequestDispatcher("/reg.jsp");
        disp.forward(req, resp);
    }


    /**
     * Переопределяет метод doPost
     * класса HttpServlet
     *
     * Получает данные пользователя
     * с формы JSP.
     * Валидирует их.
     *
     * Добавляет нового пользователя
     * в хранилище.
     *
     * Добавляет объект пользователя
     * в сессию.
     *
     * @param req - объект запроса(HttpServletRequest)
     * @param resp - объект ответа(HttpServletResponse)
     */
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");
        req.setCharacterEncoding("UTF-8");
        String name = req.getParameter("name");
        String login = req.getParameter("login");
        String password = req.getParameter("password");
        UserStore store = HibernateUserStore.instOf();
        User user = store.get(login);

        if (user != null) {
            resp.sendRedirect("reg.jsp?errMsg=\"Login already exists\"");
        } else {
            user = store.add(new User(login, password, name));
            HttpSession session = req.getSession();
            session.setAttribute("user", user);
            resp.sendRedirect("index.do");
        }
    }
}
