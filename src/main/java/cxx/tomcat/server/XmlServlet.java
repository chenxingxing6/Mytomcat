package cxx.tomcat.server;

/**
 * @Author: cxx
 * @Date: 2018/6/23 10:04
 * @Describtion: 解析xml文件 解析好的值放入servlet上下文中
 */
public class XmlServlet {
    private String servlet_name;
    private String servlet_class;

    public String getServlet_name() {
        return servlet_name;
    }

    public void setServlet_name(String servlet_name) {
        this.servlet_name = servlet_name;
    }

    public String getServlet_class() {
        return servlet_class;
    }

    public void setServlet_class(String servlet_class) {
        this.servlet_class = servlet_class;
    }
}
