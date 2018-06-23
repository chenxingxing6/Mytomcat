package cxx.tomcat.server;

import cxx.catalina.Request;
import cxx.catalina.Response;

/**
 * @Author: cxx
 * @Date: 2018/6/23 9:42
 * @Describtion: 专门处理请求和响应
 */
public abstract class Servlet {
    private static final String POST="post";
    private static final String GET="get";
    public void service(Request req,Response resp) throws Exception{
        String method = req.getMethod();
        if (method.equalsIgnoreCase(POST)){
            this.doPost(req,resp);
        }else if (method.equalsIgnoreCase(GET)){
            this.doGet(req,resp);
        }
    }

    public void doGet(Request req,Response resp) throws Exception{

    }

    public void doPost(Request req,Response resp) throws Exception{

    }
}
