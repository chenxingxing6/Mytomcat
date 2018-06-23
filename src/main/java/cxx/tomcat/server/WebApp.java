package cxx.tomcat.server;

import cxx.tomcat.server.util.LogUtil;
import cxx.tomcat.server.util.MyClassLoader;
import cxx.tomcat.server.util.SystemConf;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

/**
 * @Author: cxx
 * @Date: 2018/6/23 9:53
 * @Describtion: 存储一些我们会用到的servlet上下文，并且提供获取他们的方法
 */
public class WebApp {
    private static ServletContext context;
    private static String WebAppPath= SystemConf.WEB_ROOT+"\\";
    private static String WebInfoPath=WebAppPath+"WEB-INF\\";
    private static final Logger log = LogUtil.getLogger(WebApp.class);
    static {
        context = new ServletContext();
        //创建servlet上下文容器
        Map<String, String> mapping = context.getMapping();
        Map<String, String> servlet = context.getServlet();
        //解析配置文件,将对应的字符存储进去SAX
        //1.获取工厂
        SAXParserFactory factory = SAXParserFactory.newInstance();
        try {
            //2.从解析工程获取解析器
            SAXParser parser = factory.newSAXParser();
            //3.加载文档并注册处理器
            XMLHandler handler = new XMLHandler();
            File file = new File(WebInfoPath+"web.xml");
            if (!file.exists()){
                //log.info("web.xml文件不存在!");
            }
            InputStream is = new FileInputStream(file);
            parser.parse(is,handler);

            //把解析好的东西放到map集合中去
            List<XmlServlet> servlet1 = handler.getServlet();
            List<XmlMapping> mapping1 = handler.getMapping();
            for (XmlServlet s : servlet1) {
                servlet.put(s.getServlet_name(),s.getServlet_class());
            }
            for (XmlMapping m : mapping1) {
                List<String> url_patterns = m.getUrl_pattern();
                for (String url : url_patterns) {
                    mapping.put(url,m.getServlet_name());
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    /**
     * @param action url-pattern
     * @return
     * @Describtion: 获取Servlet对象
     */
    public static Servlet getServlet(String action) {
        if ("".equals(action)||action==null){
            return null;
        }
        //通过action找到servlet-name
        String servlet_name = context.getMapping().get(action);
        //通过反射，找到相应类，并创建对象并返回
        String classPath = context.getServlet().get(servlet_name);
        System.out.println(classPath+"************");
        //通过类全面，反射
        Servlet servlet=null;
        if (classPath!=null){
            Class<?> clazz=null;
            try {
                MyClassLoader loader = new MyClassLoader();
                clazz = loader.loadClass(classPath);
                Method[] methods = clazz.getMethods();
                for (Method method : methods) {
                    System.out.println(method);
                }
                servlet = (Servlet) clazz.newInstance();

            }catch (Exception e){
                e.printStackTrace();
            }
        }
        return servlet;
    }
}





/**
 * 解析xml
 */
class XMLHandler extends DefaultHandler{
    private List<XmlServlet> servlet;
    private List<XmlMapping> mapping;
    private static final Logger log = LogUtil.getLogger(XMLHandler.class);

    public List<XmlServlet> getServlet() {
        return servlet;
    }

    public void setServlet(List<XmlServlet> servlet) {
        this.servlet = servlet;
    }

    public List<XmlMapping> getMapping() {
        return mapping;
    }

    public void setMapping(List<XmlMapping> mapping) {
        this.mapping = mapping;
    }

    private XmlServlet xmlServlet;
    private XmlMapping xmlMapping;
    private String beginTag;
    private boolean isMap;//判断是servlet还是servlet-mapping

    @Override
    public void startDocument() throws SAXException {
        log.info("读取xml配置文件......");
        //解析文档前创建集合
        servlet=new ArrayList<>();
        mapping=new ArrayList<>();
    }

    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
        if (null!=qName){
            beginTag=qName;
            if ("servlet".equals(qName)){
                xmlServlet = new XmlServlet();
                isMap=false;
            }else if ("servlet-mapping".equals(qName)){
                xmlMapping=new XmlMapping();
                isMap=true;
            }
        }
    }

    @Override
    public void characters(char[] ch, int start, int length) throws SAXException {
        if (beginTag!=null){
            String info = new String(ch,start,length).trim();
            //servlet-mapping
            if (isMap){
                if (beginTag.equals("servlet-name")){
                    xmlMapping.setServlet_name(info);
                }else if (beginTag.equals("url-pattern")){
                    xmlMapping.getUrl_pattern().add(info);
                }
            }else {
                if (beginTag.equals("servlet-name")){
                    xmlServlet.setServlet_name(info);
                }else if (beginTag.equals("servlet-class")){
                    xmlServlet.setServlet_class(info);
                }
            }
        }
    }

    @Override
    public void endElement(String uri, String localName, String qName) throws SAXException {
        if (qName!=null){
            if (qName.equals("servlet")){
                servlet.add(xmlServlet);
            }else if (qName.equals("servlet-mapping")){
                mapping.add(xmlMapping);
            }
        }
        beginTag=null;
    }

    @Override
    public void endDocument() throws SAXException {
        log.info("xml配置文件解析完成......");
    }
}
