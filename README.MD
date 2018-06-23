### 1.前言
>版本：v1.0.0  MyTomcat
 功能：访问静态资源文件（html,css,js）  
 工具：Maven+Idea
 此篇博客Mytomcat实现的功能只能访问静态资源(图片处理点问题)，我们把项目部署到Mytomcat的webapps目录下，开启MyTomcat服务后，就可以进行访问了！
 
 csdn博客：https://blog.csdn.net/m0_37499059/article/details/80779823
 ---
### 2.需要准备的知识
> 1. Socket编程
2. HTTP协议 
3. 反射
4. xml解析 
5. 多线程

---
### 3.设计步骤
1.封装response      
  > A:构建报文头  
  B:构建响应的HTML正文内容
  C：将报文头和HTML正文内容发送给客户端（浏览器）   
      
2.封装request    
  >A:接受浏览器发送的请求  
  B:解析浏览器发送来的请求
  
3.建Servlet类来转码处理请求和响应的业务
>A.实现service方法,传入request,response

4.多线程实现多的客户端发请求
>A.把与客户端浏览器的通信封装到一个线程当中

5.启动MyTomcat服务
>A.Bootstrap类中的start()方法

---

---
### 测试截图
![这里写图片描述](https://img-blog.csdn.net/20180623001336377?watermark/2/text/aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L20wXzM3NDk5MDU5/font/5a6L5L2T/fontsize/400/fill/I0JBQkFCMA==/dissolve/70)

###### 访问的资源
![这里写图片描述](https://img-blog.csdn.net/20180623001500771?watermark/2/text/aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L20wXzM3NDk5MDU5/font/5a6L5L2T/fontsize/400/fill/I0JBQkFCMA==/dissolve/70)

![这里写图片描述](https://img-blog.csdn.net/20180623001509292?watermark/2/text/aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L20wXzM3NDk5MDU5/font/5a6L5L2T/fontsize/400/fill/I0JBQkFCMA==/dissolve/70)

![这里写图片描述](https://img-blog.csdn.net/20180623001518104?watermark/2/text/aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L20wXzM3NDk5MDU5/font/5a6L5L2T/fontsize/400/fill/I0JBQkFCMA==/dissolve/70)

![这里写图片描述](https://img-blog.csdn.net/20180623001636266?watermark/2/text/aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L20wXzM3NDk5MDU5/font/5a6L5L2T/fontsize/400/fill/I0JBQkFCMA==/dissolve/70)