package cxx.tomcat.server.util;

/**
 * MyTomcat 配置信息
 * @Author: cxx
 * @Date: 2018/6/23 20:02
 */
public class SystemConf {
    public static final String MT_HOME=System.getenv("MT_HOME");
    public static final String WEB_ROOT=MT_HOME+"\\webapps";

    public static void main(String[] args) {
        System.out.println(WEB_ROOT);
    }
}
