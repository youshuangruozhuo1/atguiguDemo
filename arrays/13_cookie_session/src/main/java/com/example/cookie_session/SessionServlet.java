package com.example.cookie_session;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet(name = "sessionServlet",value = "/sessionServlet")
public class SessionServlet extends BaseServlet{


    public void createOrGetSession(HttpServletRequest req , HttpServletResponse resp) throws Exception {
        HttpSession session = req.getSession();
        boolean aNew = session.isNew();
        String id = session.getId();
        resp.getWriter().write("得到Session,它的id是:"+id+"<br />");
        resp.getWriter().write("这个Session是否是新创建的:"+aNew+"<br />");
    }


    public void setAttribute(HttpServletRequest req , HttpServletResponse resp) throws Exception {
        HttpSession session = req.getSession();
        session.setAttribute("key1","value1");
        resp.getWriter().write("已经从Session中存入数据");
    }


    public void getAttribute(HttpServletRequest req , HttpServletResponse resp) throws Exception {
        HttpSession session = req.getSession();
        Object key1 = session.getAttribute("key1");
        resp.getWriter().write("从session中获取到key1的值:"+key1);
    }


    public void defaultLife(HttpServletRequest req , HttpServletResponse resp) throws Exception {
        HttpSession session = req.getSession();
        resp.getWriter().write("Session默认超时市场:"+session.getMaxInactiveInterval());
    }


    public void life3(HttpServletRequest req , HttpServletResponse resp) throws Exception {
        HttpSession session = req.getSession();
        session.setMaxInactiveInterval(3);
        resp.getWriter().write("Session已经设置成3秒后超时");
    }


    public void deleteNow(HttpServletRequest req , HttpServletResponse resp) throws Exception {
        HttpSession session = req.getSession();
        session.invalidate();
        resp.getWriter().write("Session已经设置立即销毁");
    }

}
