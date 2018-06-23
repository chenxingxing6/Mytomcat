package cxx.tomcat.server.http;

import cxx.catalina.Request;
import cxx.catalina.Response;
import cxx.tomcat.server.CommonService;
import cxx.tomcat.server.Servlet;
import cxx.tomcat.server.WebApp;

import java.net.Socket;

/**
 * @Author: cxx
 * @Date: 2018/6/22 23:02
 * @Description: 多线程实现 多的客户端发请求
 */
public class HttpServerThread implements Runnable {
    private Socket client;
    private Request request;
    private Response response;
    private int code = 200;
    public HttpServerThread(Socket client){
        this.client=client;
        try{
            request=new Request(client.getInputStream());
            response=new Response(client.getOutputStream(),request);
        }catch (Exception e){
            code=500;
            return;
        }
    }

    @Override
    public void run() {
        try {
            String action = request.getAction();
            System.out.println("****"+action);
            Servlet servlet = WebApp.getServlet(action);
            if (servlet==null){
                //请求静态资源，和错误页面
                new CommonService().doGet(request,response);
                return;
            }
            servlet.service(request,response);
        }catch (Exception e){
            this.code=500;
        }
        try{
            response.pushToClient(code);
            if (client!=null) {
                client.close();
            }
        }catch (Exception e){
            //e.printStackTrace();
        }
    }
}
