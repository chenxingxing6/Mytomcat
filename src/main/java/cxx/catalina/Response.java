package cxx.catalina;

import cxx.tomcat.server.util.LogUtil;

import java.io.*;
import java.util.Date;
import java.util.logging.Logger;


/**
 * Http响应
 * @Author: cxx
 * @Date: 2018/6/22 9:45
 */
public class Response {
    private static final Logger log = LogUtil.getLogger(Response.class);
    private static final int BUFFER_SIZE=1024;
    private static final String SPACE=" ";
    private static final String ENTER = "\r\n";
    //头信息
    private StringBuilder headerInfo;
    //正文信息
    private StringBuilder textContent;
    //正文信息长度
    private int contentLength;
    //构建输出流
    private BufferedWriter bw;
    //获取request请求
    private Request request;
    private OutputStream os;


    public Response(){
        headerInfo=new StringBuilder();
        textContent=new StringBuilder();
        contentLength=0;
    }

    public Response(OutputStream os,Request request) throws Exception {
        this();
        this.os=os;
        this.request=request;
        this.bw=new BufferedWriter(new OutputStreamWriter(os));
    }

    public OutputStream getOutStream(){
        return os;
    }

    /**
     * 创建头部信息 html报文
     * @param code
     */
    private void createHeader(int code){
        log.info("创建响应头....");
        String type = request.getAction();
        headerInfo.append("HTTP/1.1").append(SPACE).append(code).append(SPACE);
        switch (code) {
            case 200:
                headerInfo.append("OK").append(ENTER);
                break;
            case 404:
                headerInfo.append("NOT FOUND").append(ENTER);
                break;
            case 500:
                headerInfo.append("SERVER ERROR").append(ENTER);
                break;
            default:
                break;
        }
        headerInfo.append("Server:myServer").append(SPACE).append("1.0.1v").append(ENTER);
        headerInfo.append("Date:Sat,"+SPACE).append(new Date()).append(ENTER);
        headerInfo.append("Content-Length:").append(contentLength).append(ENTER);
        if(type.endsWith("html")){
            headerInfo.append("Content-Type: text/html;charset=UTF-8").append(ENTER);
        }
        if (type.endsWith("ico")){
            headerInfo.append("Content-Type: application/octet-stream;charset=UTF-8").append(ENTER);
        }
        if (type.endsWith("jpg")){
            headerInfo.append("Content-Type: image/jpeg").append(ENTER);
        }
        if (type.endsWith("png")){
            headerInfo.append("Content-Type: image/png").append(ENTER);
        }
        if (type.endsWith("css")){
            headerInfo.append("Content-Type: text/css;charset=UTF-8").append(ENTER);
        }
        if (type.endsWith("txt")){
            headerInfo.append("Content-Type: text/plain;charset=UTF-8").append(ENTER);
        }
        if (type.endsWith("js")){
            headerInfo.append("Content-Type:application/javascript;charset=UTF-8").append(ENTER);

        }
        if (!type.contains(".")){
            headerInfo.append("Content-Type:text/html;charset=UTF-8").append(ENTER);
            headerInfo.append("Connection:keep-alive").append(ENTER);
        }
        headerInfo.append(ENTER);
    }

    /**
     * 响应给浏览器解析的内容（html正文）
     * @param content
     * @return
     */
    public Response htmlContent(String content){
        textContent.append(content).append(ENTER);
        contentLength+=(content+ENTER).toString().getBytes().length;
        return this;
    }

    /**
     * 发送给浏览器端
     * @param code
     */
    public void pushToClient(int code){
        log.info("响应客户端...头部信息...");
        try {
            createHeader(code);
            bw.append(headerInfo.toString());
            log.info(headerInfo.toString());
            bw.append(textContent.toString());
            bw.flush();
        }catch (Exception e){
            System.out.println(e.getMessage());
        }finally {
            try {
                if (bw!=null) {
                    bw.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    //客户端重定向
    public void sendRedirect(String location){

    }
}
