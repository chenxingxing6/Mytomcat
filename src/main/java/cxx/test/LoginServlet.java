package cxx.test;

import cxx.catalina.Request;
import cxx.catalina.Response;
import cxx.tomcat.server.Servlet;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.OutputStream;

/**
 * @Author: cxx
 * @Date: 2018/6/23 10:14
 * @Description: 我的测试Servlet
 */
public class LoginServlet extends Servlet {
    @Override
    public void doGet(Request req, Response resp) throws Exception {
        String username = req.getParamter("username");
        String password = req.getParamter("password");
        System.out.println("处理登录逻辑");
        System.out.println(username+":"+password);
        String cc= "<!DOCTYPE html><html><head><title>Login Page</title>\n" +
                "\t<meta charset=\"UTF-8\">\n" +
                "\t<link rel=\"shortcut icon\" href=\"favicon.ico\" >\n" +
                "\t<link rel=\"stylesheet\" type=\"text/css\" href=\"css/style.css\">\n" +
                "</head><body><div>username:"+username+" password:"+password+"<div>" +
                "<a href='/index.html'>click here</a></body></html>";
        resp.htmlContent(cc);
        resp.pushToClient(200);
    }

    @Override
    public void doPost(Request req, Response resp) throws Exception {
        System.out.println("doPost方法");
    }
}
