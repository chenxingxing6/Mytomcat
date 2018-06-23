package cxx.tomcat.server;

import cxx.tomcat.server.http.HttpServerThread;

import java.net.ServerSocket;
import java.net.Socket;

/**
 * 启动MyTomcat
 * @Author: cxx
 * @Date: 2018/6/22 23:11
 */
public class Bootstrap {
    private boolean isShutDown = false;
    /**
     * 启动服务器
     */
    public void start(){
        int port=8888;
        System.out.println("Mytomcat服务开启.....http://127.0.0.1:"+port+"/login.html");
        start(port);
    }

    /**
     * 关闭服务器
     */
    private void stop() {
        isShutDown = true;
        System.out.println("Mytomcat服务关闭.....");
    }

    /**
     * 指定服务器端口
     * @param port
     */
    public void start(int port){
        try {
            ServerSocket serverSocket = new ServerSocket(port);
            //2、接收来自浏览器的请求
            this.recevie(serverSocket);
        } catch (Exception e) {
            stop();
        }
    }

    /**
     * 接受客户端信息
     * @param serverSocket
     */
    private void recevie(ServerSocket serverSocket){
        try {
            while(!isShutDown){
                Socket client = serverSocket.accept();
                new Thread(new HttpServerThread(client)).start();
            }
        } catch (Exception e) {
            //如果这里面有问题直接关闭服务器
            isShutDown = true;
        }
    }

    public static void main(String[] args) {
       Bootstrap boot = new Bootstrap();
       boot.start();
    }
}
