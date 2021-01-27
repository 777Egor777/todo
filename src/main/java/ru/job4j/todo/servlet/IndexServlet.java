package ru.job4j.todo.servlet;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import ru.job4j.todo.model.Task;
import ru.job4j.todo.model.User;
import ru.job4j.todo.store.HibernateTaskStore;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Timestamp;
import java.util.List;

/**
 * @author Egor Geraskin(yegeraskin13@gmail.com)
 * @version 1.0
 * @since 25.01.2021
 */
public class IndexServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String showAllStr = req.getParameter("show_all");
        if (showAllStr == null) {
            req.getRequestDispatcher("index.jsp").forward(req, resp);
        } else {
            User user = (User) req.getSession().getAttribute("user");
            JSONObject obj = new JSONObject();
            JSONArray tasks = new JSONArray();
            List<Task> ts;
            if (showAllStr.equals("true")) {
                ts = HibernateTaskStore.instOf().getAll(user.getLogin());
            } else {
                ts = HibernateTaskStore.instOf().getAllOpen(user.getLogin());
            }
            for (Task task: ts) {
                JSONObject taskObj = new JSONObject();
                taskObj.put("desc", task.getDescription());
                taskObj.put("done", task.isDone());
                taskObj.put("id", task.getId());
                tasks.add(taskObj);
            }
            obj.put("tasks", tasks);
            resp.setCharacterEncoding("UTF-8");
            resp.setContentType("application/json");
            PrintWriter writer = new PrintWriter(resp.getWriter());
                writer.write(obj.toJSONString());
                writer.flush();
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("utf-8");
        String taskIdStr = req.getParameter("task_id");
        if (taskIdStr != null) {
            Task task = HibernateTaskStore.instOf().get(Integer.parseInt(taskIdStr));
            task.setDone(true);
            HibernateTaskStore.instOf().update(task);
        } else {
            String desc = req.getParameter("desc");
            User user = (User) req.getSession().getAttribute("user");
            Task task = new Task(user.getLogin(), desc, new Timestamp(System.currentTimeMillis()), false);
            HibernateTaskStore.instOf().add(task);
            resp.sendRedirect("index.do");
        }

    }
}
