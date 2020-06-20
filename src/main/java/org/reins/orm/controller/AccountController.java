package org.reins.orm.controller;

import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Action;
import org.reins.orm.entity.AccountInfoEntity;
import org.reins.orm.entity.UserEntity;
import org.reins.orm.entity.UserInfo;
import org.reins.orm.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.configurationprocessor.json.JSONObject;
import org.springframework.stereotype.Controller;

import javax.json.Json;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObject;
import javax.json.JsonWriter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Controller
public class AccountController extends ActionSupport implements ModelDriven<UserEntity> {
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
                UserInfo accountInfoEntity=userService.getUserInfo(userName);
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
    @Action("/BanUser")
    public String BanUser() throws IOException{
        try{
            HttpServletRequest request= ServletActionContext.getRequest();
            HttpServletResponse response=ServletActionContext.getResponse();
            setCORSHeader(response);
            String userName=request.getParameter("user_name");
            StringWriter sw=new StringWriter();
            try(JsonWriter jsonWriter= Json.createWriter(sw)){
                user=userService.getUserByName(userName);
                if(user!=null) {
                    user.setType("banned");
                    userService.updateUser(user);
                    JsonObject object = Json.createObjectBuilder().add("success", true).build();
                    jsonWriter.writeObject(object);
                }
                else{
                    JsonObject object = Json.createObjectBuilder().add("success", false).build();
                    jsonWriter.writeObject(object);
                }
                response.getWriter().print(sw.toString());
                response.getWriter().flush();
                return null;
            }
        }catch (Exception e){
            e.printStackTrace();
            return ERROR;
        }
    }
    @Action("/RecoverUser")
    public String RecoverUser() throws IOException{
        try{
            HttpServletRequest request= ServletActionContext.getRequest();
            HttpServletResponse response=ServletActionContext.getResponse();
            setCORSHeader(response);
            String userName=request.getParameter("user_name");

            Logger logger= LogManager.getLogger("RecoverWatcher");
            logger.info(userName);
            StringWriter sw=new StringWriter();
            try(JsonWriter jsonWriter= Json.createWriter(sw)){
                user=userService.getUserByName(userName);
                if(user!=null) {
                    user.setType("user");
                    userService.updateUser(user);
                    JsonObject object = Json.createObjectBuilder().add("success", true).build();
                    jsonWriter.writeObject(object);
                }
                else{
                    JsonObject object = Json.createObjectBuilder().add("success", false).build();
                    jsonWriter.writeObject(object);
                }
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
            BufferedReader bufferedReader=request.getReader();
            String line;
            StringBuffer buffer=new StringBuffer();
            while((line=bufferedReader.readLine())!=null)
                buffer.append(line);
            bufferedReader.close();
            Logger logger= LogManager.getLogger("SignInWatcher");
            logger.info(buffer.toString());
            JSONObject object = new JSONObject(buffer.toString());
            String userName=object.getString("user_name");
            user=userService.getUserByName(userName);
            StringWriter sw=new StringWriter();
            if(user!=null){
                try(JsonWriter jsonWriter= Json.createWriter(sw)){
                    JsonObject object2=Json.createObjectBuilder().add("success",false).build();
                    jsonWriter.writeObject(object2);
                    response.getWriter().print(sw.toString());
                    response.getWriter().flush();
                    return null;
                }
            }
            String password=object.getString("password");
            String email=object.getString("email_address");
            user=new UserEntity();
            user.setName(userName);
            user.setPwd(password);
            user.setEmail(email);
            user.setType("user");
            user.setAvatar("/avatar.jpg");
            userService.AddUser(user);
            try(JsonWriter jsonWriter= Json.createWriter(sw)){
                JsonObject object2=Json.createObjectBuilder().add("success",true).build();
                jsonWriter.writeObject(object2);
                response.getWriter().print(sw.toString());
                response.getWriter().flush();
            }
            return null;
        }catch (Exception e){
            e.printStackTrace();
            return ERROR;
        }
    }
    @Action("/CheckUserName")
    public String checkUserName()throws IOException{
        try{
            HttpServletRequest request= ServletActionContext.getRequest();
            HttpServletResponse response=ServletActionContext.getResponse();
            setCORSHeader(response);
            String userName=request.getParameter("user_name");
            user=userService.getUserByName(userName);
            StringWriter sw=new StringWriter();
            try(JsonWriter jsonWriter= Json.createWriter(sw)){
                if(user==null){
                    JsonObject object=Json.createObjectBuilder().add("exist",false).build();
                    jsonWriter.writeObject(object);
                }
                else {
                    JsonObject object=Json.createObjectBuilder().add("exist",true).build();
                    jsonWriter.writeObject(object);
                }
                response.getWriter().print(sw.toString());
                response.getWriter().flush();
                return null;
            }
        }catch (Exception e){
            e.printStackTrace();
            return ERROR;
        }
    }
    @Action("/GetUsers")
    public String getUsers() throws IOException{
        try{
            HttpServletRequest request= ServletActionContext.getRequest();
            HttpServletResponse response=ServletActionContext.getResponse();
            setCORSHeader(response);
            List<UserEntity> userEntities=userService.getUsers();
            if(!userEntities.isEmpty()) {
                JsonArrayBuilder builder=Json.createArrayBuilder();
                userEntities.forEach(
                        userEntity -> {
                            builder.add(
                                    Json.createObjectBuilder().add("id",userEntity.getId())
                                            .add("name",userEntity.getName())
                                            .add("join_date",userEntity.getJoinDate().toString())
                                            .add("type",userEntity.getType())
                                            .add("avatar",userEntity.getAvatar())
                                            .add("email",userEntity.getEmail())
                                            .build()
                            );
                        });
                StringWriter sw=new StringWriter();
                try(JsonWriter jsonWriter=Json.createWriter(sw)) {
                    jsonWriter.writeArray(builder.build());
                    response.getWriter().print(sw.toString());
                    response.getWriter().flush();
                }
            }
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
