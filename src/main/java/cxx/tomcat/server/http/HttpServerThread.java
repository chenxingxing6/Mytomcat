package cxx.tomcat.server.http;

import cxx.catalina.Request;
import cxx.catalina.Response;

import java.net.Socket;

/**
 * 多线程实现 多的客户端发请求
 * @Author: cxx
 * @Date: 2018/6/22 23:02
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
        Servlet servlet = new Servlet();
        servlet.service(request,response);
        response.pushToClient(code);
        try {
            client.close();
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
    }
}
