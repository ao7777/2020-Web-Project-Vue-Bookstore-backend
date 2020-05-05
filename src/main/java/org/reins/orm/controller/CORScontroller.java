package org.reins.orm.controller;

import com.opensymphony.xwork2.ActionSupport;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.interceptor.ServletResponseAware;
import org.springframework.stereotype.Controller;

import javax.servlet.http.HttpServletResponse;
@Controller
@Namespace("/")
public class CORScontroller extends ActionSupport implements ServletResponseAware {

    @Override
    @Action(value ="/")
    public void setServletResponse(HttpServletResponse response) {
        // 设置发送文件头:允许跨域的地址
        response.setHeader("Access-Control-Allow-Origin", "http://localhost:8080");
        // 允许Credentials
        response.setHeader("Access-Control-Allow-Credentials", "true");
        // 允许的请求方法
        response.setHeader("Access-Control-Allow-Methods", "GET,HEAD,OPTIONS,POST,PUT");
        // 允许的请求头
        response.setHeader("Access-Control-Allow-Headers", "Access-Control-Allow-Headers, Origin,Accept, X-Requested-With, Content-Type, Access-Control-Request-Method, Access-Control-Request-Headers");
    }
}
