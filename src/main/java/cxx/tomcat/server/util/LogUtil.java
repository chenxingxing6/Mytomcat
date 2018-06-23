package cxx.tomcat.server.util;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;
import java.util.logging.*;

public class LogUtil {
    private static Logger logger;
    private static String pattern;
    private static String LOG_HOME=SystemConf.MT_HOME+"\\logs\\";

    /**
     * 获取日志对象
     * @param clazz
     * @return
     */
    public static Logger getLogger(Class<?> clazz){
            InputStream is = null;
            try {
                //读取配置文件,创建日志文件目录
                is = new FileInputStream(LOG_HOME + "log.properties");
                Properties properties = new Properties();
                properties.load(is);
                pattern = properties.getProperty("java.util.logging.FileHandler.pattern");
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                pattern = LOG_HOME + pattern.replace(".", sdf.format(new Date()) + ".");
                pattern = LOG_HOME + "cxx.log";
                File file = new File(pattern);
                if (!file.exists()) {
                    file.createNewFile();
                }
                logger = Logger.getLogger("Mytomcat");
                //日志处理器
                FileHandler fileHandler = new FileHandler(pattern, true);
                //设置日志格式
                MySimpleFormatter sf = new MySimpleFormatter();
                fileHandler.setFormatter(sf);
                //注册处理器
                logger.addHandler(fileHandler);
                return logger;
            } catch (Exception e) {

            }
            return logger;
    }

    public static void main(String[] args) {
        getLogger(Object.class).info("你好.......");
    }
}

class MySimpleFormatter extends SimpleFormatter{
    private static final DateFormat df = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");


    @Override
    public String format(LogRecord record) {
        StringBuilder builder = new StringBuilder(1000);
        builder.append("MyTomcat:"+df.format(new Date(record.getMillis()))).append("-");
        builder.append("[").append(record.getSourceClassName()).append(".");
        builder.append(record.getSourceMethodName()).append("]-");
        builder.append("[").append(record.getLevel()).append("]-");
        builder.append(formatMessage(record));
        builder.append("\r\n");
        return builder.toString();
    }
}