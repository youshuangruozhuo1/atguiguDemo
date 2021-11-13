package com.example.cookie_session;

import com.example.utils.CookieUtils;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "cookieServlet",value = "/cookieServlet")
public class CookieServlet extends BaseServlet{

    public void createCookie(HttpServletRequest req , HttpServletResponse resp) throws Exception {
        //1.创建cookie对象
        Cookie cookie = new Cookie("key","value");
        //2.通知客户端保存
        resp.addCookie(cookie);



        //1.创建cookie对象
        Cookie cookie1 = new Cookie("key1","value1");
        //2.通知客户端保存
        resp.addCookie(cookie1);

        //1.创建cookie对象
        Cookie cookie2 = new Cookie("key2","value2");
        //2.通知客户端保存
        resp.addCookie(cookie2);

        //1.创建cookie对象
        Cookie cookie3 = new Cookie("key3","value3");
        //2.通知客户端保存
        resp.addCookie(cookie3);

        resp.getWriter().write("Cookie创建成功!!!");

    }

    public void getCookie(HttpServletRequest req , HttpServletResponse resp) throws Exception {
        Cookie[] cookies = req.getCookies();
        for (Cookie cookie : cookies) {
            resp.getWriter().write("Cookie["+cookie.getName()+" => "+cookie.getValue()+"] <br/> ");
        }
        Cookie key = CookieUtils.getCookieByName("key",cookies);
        if (key != null){
            resp.getWriter().write("找到了想要的cookie!!!");
        }

    }



    public void updateCookie(HttpServletRequest req , HttpServletResponse resp) throws Exception {

        //方式一:
        Cookie cookie = new Cookie("key","newValue");
        resp.addCookie(cookie);

        //方式二:
       /* Cookie key = CookieUtils.getCookieByName("key",req.getCookies());
        key.setValue("newValue...");
        resp.addCookie(key);*/


    }


    public void life3600(HttpServletRequest req , HttpServletResponse resp) throws Exception {
        //方式一:
        Cookie cookie = new Cookie("life3600","life3600");
        cookie.setMaxAge(60 * 60);
        resp.addCookie(cookie);
    }

    public void deleteNow(HttpServletRequest req , HttpServletResponse resp) throws Exception {
        //方式一:
        Cookie cookie = CookieUtils.getCookieByName("key3",req.getCookies());
        cookie.setMaxAge(0);
        resp.addCookie(cookie);

        resp.getWriter().write("key3的Cookie已删除!!!");
    }


    public void defaultLife(HttpServletRequest req , HttpServletResponse resp) throws Exception {
        //方式一:
        Cookie cookie = new Cookie("defaultLife","defaultLife");
        cookie.setMaxAge(0);
        resp.addCookie(cookie);

        resp.getWriter().write("key3的Cookie已删除!!!");
    }


    public void testPath(HttpServletRequest req , HttpServletResponse resp) throws Exception {
        Cookie cookie = new Cookie("path1","path1");
        cookie.setPath(req.getContextPath()+"/abc");
        resp.addCookie(cookie);
        resp.getWriter().write("创建了一个带有Path路径的Cookie");
    }


}
