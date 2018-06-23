### 自己弄个简单的Tomcat玩玩 
 
>版本：v1.0.0  
 功能：访问静态资源文件（html,css,js）  
 工具：Maven+Idea
 
### 需要准备的知识
1. Socket编程
2. HTTP协议 
3. 反射
4. xml解析 


### 设计步骤
#### 1. 封装response    
  A:构建报文头  
  B:构建响应的HTML正文内容
  C：将报文头和HTML正文内容发送给客户端（浏览器）
#### 2. 封装request    
  A:接受浏览器发送的请求  
  B:解析浏览器发送来的请求
  
#### 3. Tomcat启动



