package ru.job4j.todo.servlet;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import ru.job4j.todo.model.User;
import ru.job4j.todo.store.HibernateUserStore;
import ru.job4j.todo.store.UserStore;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import java.io.IOException;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.verify;

@RunWith(PowerMockRunner.class)
@PrepareForTest(HibernateUserStore.class)
public class RegServletTest {

    @Test
    public void doGet() throws ServletException, IOException {
        HttpServletRequest req = mock(HttpServletRequest.class);
        HttpServletResponse resp = mock(HttpServletResponse.class);
        RequestDispatcher disp = mock(RequestDispatcher.class);
        RegServlet servlet = new RegServlet();
        when(req.getRequestDispatcher(anyString())).thenReturn(disp);
        servlet.doGet(req, resp);
        ArgumentCaptor<String> arg = ArgumentCaptor.forClass(String.class);
        verify(req).getRequestDispatcher(arg.capture());
        verify(disp).forward(eq(req), eq(resp));
        assertThat(arg.getValue(), is("/reg.jsp"));
    }

    @Test
    public void doPostWhenCorrectReg() throws ServletException, IOException {
        HttpServletRequest req = mock(HttpServletRequest.class);
        HttpServletResponse resp = mock(HttpServletResponse.class);
        when(req.getParameter(eq("login"))).thenReturn("egor");
        when(req.getParameter(eq("password"))).thenReturn("123");
        when(req.getParameter(eq("name"))).thenReturn("Egor");
        PowerMockito.mockStatic(HibernateUserStore.class);
        UserStore store = mock(UserStore.class);
        User user = new User("egor", "123", "Egor");
        when(store.get(eq("egor"))).thenReturn(null);
        when(HibernateUserStore.instOf()).thenReturn(store);
        HttpSession session = mock(HttpSession.class);
        when(req.getSession()).thenReturn(session);
        when(store.add(eq(user))).thenReturn(user);

        RegServlet servlet = new RegServlet();
        servlet.doPost(req, resp);

        verify(store).add(eq(user));
        verify(session).setAttribute(eq("user"), eq(user));
        verify(resp).sendRedirect(eq("index.do"));
    }

    @Test
    public void doPostWhenLoginAlreadyExists() throws ServletException, IOException {
        HttpServletRequest req = mock(HttpServletRequest.class);
        HttpServletResponse resp = mock(HttpServletResponse.class);
        when(req.getParameter(eq("login"))).thenReturn("egor");
        when(req.getParameter(eq("password"))).thenReturn("123");
        PowerMockito.mockStatic(HibernateUserStore.class);
        UserStore store = mock(UserStore.class);
        when(HibernateUserStore.instOf()).thenReturn(store);
        User user = new User("egor", "123", "Egor");
        when(store.get(eq("egor"))).thenReturn(user);

        RegServlet servlet = new RegServlet();
        servlet.doPost(req, resp);

        verify(resp).sendRedirect(eq("reg.jsp?errMsg=\"Login already exists\""));
    }

}