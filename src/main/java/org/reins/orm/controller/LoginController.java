package org.reins.orm.controller;

import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Action;
import org.reins.orm.entity.AccountInfoEntity;
import org.reins.orm.entity.UserEntity;
import org.reins.orm.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.configurationprocessor.json.JSONObject;
import org.springframework.stereotype.Controller;

import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonWriter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringWriter;
import java.text.SimpleDateFormat;
import java.util.Date;

@Controller
public class LoginController extends ActionSupport implements ModelDriven<UserEntity> {
    @Autowired
    private UserService userService;
    private UserEntity user;
    @Action(value = "/Login")
    public String Login() throws IOException{
        try{
            HttpServletRequest request= ServletActionContext.getRequest();
            HttpServletResponse response=ServletActionContext.getResponse();
            setCORSHeader(response);
            String userName=request.getParameter("user_name");
            String password=request.getParameter("password");
            user=userService.checkLogin(userName,password);
            StringWriter sw=new StringWriter();
            try(JsonWriter jsonWriter= Json.createWriter(sw)){
                if(user==null){
                   JsonObject object=Json.createObjectBuilder().add("LoginStatus",false).build();
                    jsonWriter.writeObject(object);
                    response.getWriter().print(sw.toString());
                    response.getWriter().flush();
                    return null;
                }
                else {
                    JsonObject object=Json.createObjectBuilder().add("LoginStatus",true)
                            .add("ID",user.getId())
                            .add("user_type",user.getType())
                            .add("avatar",user.getAvatar())
                            .add("join_date", user.getJoinDate().toString()).build();
                    jsonWriter.writeObject(object);
                    response.getWriter().print(sw.toString());
                    response.getWriter().flush();
                    return null;
                }
            }

        }catch (Exception e){
            e.printStackTrace();
            return ERROR;
        }
    }
    @Action("/UserInfo")
    public String getUserInfo() throws IOException{
        try{
            HttpServletRequest request= ServletActionContext.getRequest();
            HttpServletResponse response=ServletActionContext.getResponse();
            setCORSHeader(response);
            String userName=request.getParameter("user_name");
            StringWriter sw=new StringWriter();
            try(JsonWriter jsonWriter= Json.createWriter(sw)){
                AccountInfoEntity accountInfoEntity=userService.getAccountInfo(userName);
                    JsonObject object=Json.createObjectBuilder().add("balance",accountInfoEntity.getBalance())
                            .add("profile",accountInfoEntity.getProfile()).build();
                jsonWriter.writeObject(object);
                response.getWriter().print(sw.toString());
                response.getWriter().flush();
                return null;
            }
        }catch (Exception e){
            e.printStackTrace();
            return ERROR;
        }
    }
    @Action("/SignIn")
    public String signIn()throws IOException{
        try{
            HttpServletRequest request= ServletActionContext.getRequest();
            HttpServletResponse response=ServletActionContext.getResponse();
            setCORSHeader(response);
            String userName=request.getParameter("user_name");
            String password=request.getParameter("password");
            BufferedReader bufferedReader=request.getReader();
            String line;
            StringBuilder buffer=new StringBuilder();
            while((line=bufferedReader.readLine())!=null)
                buffer.append(line);
            JSONObject object = new JSONObject(buffer.toString());
            Date today=new Date();
            SimpleDateFormat dateFormat=new SimpleDateFormat("yyyy-MM-dd");
            dateFormat.format(today);
            user.setJoinDate((java.sql.Date) today);
            user.setName(userName);
            user.setPwd(password);
            userService.AddUser(user);
            return null;
        }catch (Exception e){
            e.printStackTrace();
            return ERROR;
        }
    }
    @Override
    public UserEntity getModel() {
        return null;
    }
    private void setCORSHeader(HttpServletResponse response){
        response.setHeader("Access-Control-Allow-Origin", "http://localhost:8080");
        response.setHeader("Access-Control-Allow-Methods", "GET,POST");
        response.setHeader("Cache-Control","no-cache");
        response.setStatus(HttpServletResponse.SC_OK);
        response.setContentType("text/html;charset=UTF-8");

    }
}
