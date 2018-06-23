package cxx.tomcat.server;

import cxx.catalina.Request;
import cxx.catalina.Response;
import cxx.tomcat.server.util.SystemConf;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.OutputStream;

/**
 * @Author: cxx
 * @Date: 2018/6/23 12:51
 */
public class CommonService extends Servlet {
    private static String action;
    private static int code;
    private static String WebRoot=SystemConf.WEB_ROOT+"\\";

    @Override
    public void doGet(Request req, Response resp) throws Exception {
        System.out.println("请求静态资源"+req.getAction());
        action=req.getAction();
            byte[] b = new byte[1024];
            FileInputStream fis = null;
            try {
                StringBuilder contextText = new StringBuilder();
                File file = new File(WebRoot,action);
                if (!file.exists()){
                    this.code=404;
                    file = new File(WebRoot,"404.html");
                }
                fis = new FileInputStream(file);
                BufferedInputStream bis = new BufferedInputStream(fis);
                //图片要特殊处理（自己找bug得出的结论）
                if (action.endsWith("jpg")||action.endsWith("png")||action.endsWith("ico")){
                    OutputStream os = resp.getOutStream();
                    StringBuilder sb = new StringBuilder();
                    sb.append("http/1.1 200 ok").append("\r\n");
                    sb.append("content-type: image/jpeg\r\n");
                    sb.append("\n");
                    os.write(sb.toString().getBytes());
                    int len=0;
                    byte[] buffer = new byte[1024 * 10];
                    while ((len = fis.read(buffer)) != -1){
                        os.write(buffer,0,len);
                    }
                    os.flush();
                    os.close();
                    return;
                }else {
                    int ch = 0;
                    while ((ch = bis.read(b)) != -1) {
                        contextText.append(new String(b, 0, ch));
                    }
                    resp.htmlContent(contextText.toString());
                }
            } catch (Exception e) {
                this.code = 500;
                resp.htmlContent("<h1>服务器错误500!</h1>");
                resp.htmlContent("<p style='color:red;'>"+e.getMessage()+"</p>");
            }
        resp.pushToClient(code);
    }

    @Override
    public void doPost(Request req, Response resp) throws Exception {
        this.doGet(req,resp);
    }
}
