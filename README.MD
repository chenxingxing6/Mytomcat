### 1.前言
> 花了快2天的时间了，终于完善了我这个MyTomcat,到目前来说，我感觉还可以吧。当然我希望感兴趣的小伙伴们，你们可以在我这个基础上继续完善，你们可以把解析jsp功能加进去，还要一个较严重的问题就是，中文字符会乱码。总之，希望对路过的小伙伴们有帮助。

博客：https://blog.csdn.net/m0_37499059
### 2.MyTomcat介绍
##### 2.1 项目目录结构
![这里写图片描述](https://img-blog.csdn.net/20180624003512668?watermark/2/text/aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L20wXzM3NDk5MDU5/font/5a6L5L2T/fontsize/400/fill/I0JBQkFCMA==/dissolve/70)

##### 2.2 配置环境变量Mytomcat_home
![这里写图片描述](https://img-blog.csdn.net/20180624003638988?watermark/2/text/aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L20wXzM3NDk5MDU5/font/5a6L5L2T/fontsize/400/fill/I0JBQkFCMA==/dissolve/70)
配完之后重启电脑，使配置生效。

##### 2.3 部署项目
![这里写图片描述](https://img-blog.csdn.net/20180624003825629?watermark/2/text/aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L20wXzM3NDk5MDU5/font/5a6L5L2T/fontsize/400/fill/I0JBQkFCMA==/dissolve/70)
![这里写图片描述](https://img-blog.csdn.net/20180624003947604?watermark/2/text/aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L20wXzM3NDk5MDU5/font/5a6L5L2T/fontsize/400/fill/I0JBQkFCMA==/dissolve/70)

##### 2.3 启动项目
运行bin目录下的startup.bat文件
![这里写图片描述](https://img-blog.csdn.net/2018062400425276?watermark/2/text/aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L20wXzM3NDk5MDU5/font/5a6L5L2T/fontsize/400/fill/I0JBQkFCMA==/dissolve/70)

#### 2.4 访问项目
![这里写图片描述](https://img-blog.csdn.net/20180624004423559?watermark/2/text/aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L20wXzM3NDk5MDU5/font/5a6L5L2T/fontsize/400/fill/I0JBQkFCMA==/dissolve/70)

![这里写图片描述](https://img-blog.csdn.net/20180624004430502?watermark/2/text/aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L20wXzM3NDk5MDU5/font/5a6L5L2T/fontsize/400/fill/I0JBQkFCMA==/dissolve/70)

![这里写图片描述](https://img-blog.csdn.net/20180624004437312?watermark/2/text/aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L20wXzM3NDk5MDU5/font/5a6L5L2T/fontsize/400/fill/I0JBQkFCMA==/dissolve/70)

##### 2.5 日志记录
![这里写图片描述](https://img-blog.csdn.net/20180624004634629?watermark/2/text/aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L20wXzM3NDk5MDU5/font/5a6L5L2T/fontsize/400/fill/I0JBQkFCMA==/dissolve/70)

![这里写图片描述](https://img-blog.csdn.net/20180624004642731?watermark/2/text/aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L20wXzM3NDk5MDU5/font/5a6L5L2T/fontsize/400/fill/I0JBQkFCMA==/dissolve/70)

---
### 3.Mytomcat源码目录结构
> 工具：Idea+Maven
> ![这里写图片描述](https://img-blog.csdn.net/20180624004819662?watermark/2/text/aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L20wXzM3NDk5MDU5/font/5a6L5L2T/fontsize/400/fill/I0JBQkFCMA==/dissolve/70)  

#### 唉，封装真是不简单啊....

---
###### 我的想法
1.解析web.xml文件，分别将servlet,servlet-mapping放到map集合中去，然后我们根据请求的url,去找到对应的servlet-class的名字，然后我们根据反射，调用service方法就好。
2.service方法，有2个参数（request，response），所以我们要对request和response进行封装。
3.servlet的上下文,封装servlet与请求
4.响应客户端，响应行，响应头，响应体，通过流的操作发送个客户端
其实想想也还是很简单的。

----

### 4.项目源码下载
csdn：https://download.csdn.net/download/m0_37499059/10495680
github:https://github.com/chenxingxing6/Mytomcat/tree/develop