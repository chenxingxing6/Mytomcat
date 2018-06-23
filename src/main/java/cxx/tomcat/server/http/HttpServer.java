package cxx.tomcat.server.http;

import cxx.catalina.Request;
import cxx.catalina.Response;

import java.io.File;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * 程序的入口
 * 只能处理静态的资源
 * @Author: cxx
 * @Date: 2018/6/22 9:55
 */
public class HttpServer {
    //项目部署目录
    public static final String WEB_ROOT=System.getProperty("user.dir")+ File.separator+"webapps";
    //关闭命令
    private static final String SHUTDOWN_CMD="/shutdown";
    //收到关闭命令
    private boolean shutdown = false;

    public void start(){
        ServerSocket serverSocket=null;
        int port=8080;
        try{
            serverSocket= new ServerSocket(port);
            //等待请求
            System.out.println("MyTomcat服务开启，等待请求...:http://127.0.0.1:"+port+"/login.html");
            while (!shutdown){
                recevie(serverSocket);
            }
            System.out.println("服务关闭!");
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
    }

    private void recevie(ServerSocket serverSocket){
        try {
            Socket client = serverSocket.accept();
            Servlet servlet = new Servlet();
            Request request = new Request(client.getInputStream());
            Response response = new Response(client.getOutputStream(),request);
            servlet.service(request, response);
            response.pushToClient(200);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        HttpServer server = new HttpServer();
        //1、创建一个服务器端并开启
        server.start();
    }
}
