package cxx.tomcat.server.http;

import cxx.catalina.Request;
import cxx.catalina.Response;
import cxx.tomcat.server.http.HttpServer;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;

/**
 * 专门处理请求和响应
 * @Author: cxx
 * @Date: 2018/6/22 21:39
 */
public class Servlet {
    private static final int BUFFER_SIZE=1024;
    public void service(Request request, Response response){
        System.out.println("service方法");
        byte[] b = new byte[BUFFER_SIZE];
        FileInputStream fis = null;
        try {
            StringBuilder contextText = new StringBuilder();
            File file = new File(HttpServer.WEB_ROOT + request.getAction());
            fis = new FileInputStream(file);
            BufferedInputStream bis = new BufferedInputStream(fis);
            int ch = 0;
            while ((ch = bis.read(b)) != -1) {
                contextText.append(new String(b, 0, ch));
            }
            String username = request.getParamter("user");
            //response.htmlContent("<html><head></head><body>This is my page<br><br>");
            //response.htmlContent("欢迎："+username+"  来到我的地盘</body></html>");
            response.htmlContent(contextText.toString());
        }catch (Exception e) {
            e.printStackTrace();
        }
    }
}
