package org.reins.orm.controller;

import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Action;
import org.reins.orm.entity.CartsEntity;
import org.reins.orm.entity.OrderEntity;
import org.reins.orm.entity.OrderItemEntity;
import org.reins.orm.entity.UserEntity;
import org.reins.orm.service.TransactionService;
import org.reins.orm.service.UserService;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.configurationprocessor.json.JSONArray;
import org.springframework.boot.configurationprocessor.json.JSONObject;
import org.springframework.stereotype.Controller;

import javax.json.Json;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObjectBuilder;
import javax.json.JsonWriter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringWriter;
import java.text.SimpleDateFormat;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Controller
public class TransactionController extends ActionSupport implements ModelDriven<OrderItemEntity> {
   @Autowired
   TransactionService transactionService;
    @Autowired
    UserService userService;
    @Action(value="/Cart")
    public String getCart() throws IOException{
        try {
            HttpServletRequest request= ServletActionContext.getRequest();
            HttpServletResponse response=ServletActionContext.getResponse();
            String isbnJSON=request.getParameter("isbn");
            response.setHeader("Access-Control-Allow-Origin", "http://localhost:8080");
            response.setHeader("Access-Control-Allow-Methods", "GET");
            response.setHeader("Cache-Control","no-cache");
            response.setStatus(HttpServletResponse.SC_OK);
            JSONObject object = new JSONObject();
            object.put("ID",isbnJSON);
            int ID=object.getInt("ID");
            Set<CartsEntity> cartsEntities=transactionService.getCartByID(ID);
            response.setContentType("text/html;charset=UTF-8");
            if(!cartsEntities.isEmpty()) {
                JsonArrayBuilder builder = Json.createArrayBuilder();
                cartsEntities.forEach(
                        cartsEntity -> {
                            builder.add(
                                    Json.createObjectBuilder().add("isbn", cartsEntity.getIsbn())
                                            .add("quantity", cartsEntity.getQuantity())
                                            .build()
                            );
                        }
                );
                StringWriter sw = new StringWriter();
                try (JsonWriter jsonWriter = Json.createWriter(sw)) {
                    jsonWriter.writeArray(builder.build());
                    response.getWriter().print(sw.toString());
                    response.getWriter().flush();
                }
            }
            return  null;
        } catch (Exception e){
            e.printStackTrace();
            return ERROR;
        }
    }
    @Action("/getOrder")
    public String getOrder()throws IOException {
        try {
            HttpServletRequest request= ServletActionContext.getRequest();
            HttpServletResponse response=ServletActionContext.getResponse();
            String IDjson=request.getParameter("ID");
            response.setHeader("Access-Control-Allow-Origin", "http://localhost:8080");
            response.setHeader("Access-Control-Allow-Methods", "GET");
            response.setHeader("Cache-Control","no-cache");
            response.setContentType("text/html;charset=UTF-8");
            response.setStatus(HttpServletResponse.SC_OK);
            JSONObject object = new JSONObject();
            object.put("ID",IDjson);
            int ID=object.getInt("ID");
//            Logger logger= LogManager.getLogger("getOrderWatcher");
//            logger.info(ID);
            List<OrderEntity> orderList=transactionService.getOrderByUserID(ID);
            JsonArrayBuilder orderArray=Json.createArrayBuilder();
            orderList.forEach(
                    order-> {
                        List<OrderItemEntity> orderEntities = order.getOrderItem();
                        JsonArrayBuilder builder = Json.createArrayBuilder();
                        orderEntities.forEach(
                                orderEntity -> {
                                    builder.add(
                                            Json.createObjectBuilder().add("isbn", orderEntity.getIsbn())
                                                    .add("quantity", orderEntity.getQuantity())
                                                    .add("amount", orderEntity.getAmount())
                                                    .build()
                                    );
                                }
                        );
                        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                        JsonObjectBuilder jsonObjectBuilder=Json.createObjectBuilder().add("items",builder.build())
                                .add("order_id",order.getOrder_id())
                                .add("transaction_date",sdf.format(order.getTransaction_date()));
                        orderArray.add(jsonObjectBuilder.build());
                    }
            );
                StringWriter sw = new StringWriter();
                try (JsonWriter jsonWriter = Json.createWriter(sw)) {
                    jsonWriter.writeArray(orderArray.build());
                    response.getWriter().print(sw.toString());
                    response.getWriter().flush();
                }
            return  null;
        } catch (Exception e){
            e.printStackTrace();
            return ERROR;
        }
    }
    @Action("/getOrders")
    public String getOrders()throws IOException {
        try {
            HttpServletRequest request= ServletActionContext.getRequest();
            HttpServletResponse response=ServletActionContext.getResponse();
            setCORSHeader(response);
//            Logger logger= LogManager.getLogger("getOrderWatcher");
//            logger.info(ID);
            List<OrderEntity> orderList=transactionService.getOrders();
            JsonArrayBuilder orderArray=Json.createArrayBuilder();
            orderList.forEach(
                    order-> {
                        List<OrderItemEntity> orderEntities = order.getOrderItem();
                        JsonArrayBuilder builder = Json.createArrayBuilder();
                        orderEntities.forEach(
                                orderEntity -> {
                                    builder.add(
                                            Json.createObjectBuilder().add("isbn", orderEntity.getIsbn())
                                                    .add("quantity", orderEntity.getQuantity())
                                                    .add("amount", orderEntity.getAmount())
                                                    .build()
                                    );
                                }
                        );
                        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                        JsonObjectBuilder jsonObjectBuilder=Json.createObjectBuilder().add("items",builder.build())
                                .add("order_id",order.getOrder_id())
                                .add("user_id",order.getUser_id())
                                .add("transaction_date",sdf.format(order.getTransaction_date()));
                        orderArray.add(jsonObjectBuilder.build());
                    }
            );
            StringWriter sw = new StringWriter();
            try (JsonWriter jsonWriter = Json.createWriter(sw)) {
                jsonWriter.writeArray(orderArray.build());
                response.getWriter().print(sw.toString());
                response.getWriter().flush();
            }
            return  null;
        } catch (Exception e){
            e.printStackTrace();
            return ERROR;
        }
    }
    @Action("/OrderSubmit")
    public String submitOrder()throws IOException{
        try {
            HttpServletRequest request= ServletActionContext.getRequest();
            HttpServletResponse response=ServletActionContext.getResponse();
            setCORSHeader(response);
            BufferedReader bufferedReader=request.getReader();
            String line;
            StringBuffer buffer=new StringBuffer();
            while((line=bufferedReader.readLine())!=null)
                buffer.append(line);
            bufferedReader.close();
            JSONObject object = new JSONObject(buffer.toString());
            int ID=object.getInt("ID");
            UserEntity userEntity = userService.getUserByID(ID);
            transactionService.submitOrder(userEntity);
            return  null;
        } catch (Exception e){
            e.printStackTrace();
            return ERROR;
        }
    }
    @Action(value = "/CartSubmit")
    public  String submitCart() throws IOException {
        try {
            HttpServletRequest request= ServletActionContext.getRequest();
            HttpServletResponse response=ServletActionContext.getResponse();
            setCORSHeader(response);
            BufferedReader bufferedReader=request.getReader();
            String line;
            StringBuffer buffer=new StringBuffer();
            while((line=bufferedReader.readLine())!=null)
                buffer.append(line);
            bufferedReader.close();
            Logger logger= LogManager.getLogger("OrderSubmitWatcher");
            logger.info(buffer.toString());
            JSONObject object = new JSONObject(buffer.toString());
            JSONArray jsonArray=object.getJSONArray("order");
            int ID=object.getInt("ID");
            String name=object.getString("name");
            Set<CartsEntity> cartsEntities= new HashSet<>();
            for(int i=0;i<jsonArray.length();i++){
                CartsEntity cartsEntity=new CartsEntity();
                cartsEntity.setQuantity(jsonArray.getJSONObject(i).getInt("quantity"));
                cartsEntity.setIsbn(jsonArray.getJSONObject(i).getString("isbn"));
                cartsEntities.add(cartsEntity);
            }
            transactionService.updateCartByID(cartsEntities,ID);
            return  null;
        } catch (Exception e){
            e.printStackTrace();
            return ERROR;
        }
    }
    private void setCORSHeader(HttpServletResponse response){
        response.setHeader("Access-Control-Allow-Origin", "http://localhost:8080");
        response.setHeader("Access-Control-Allow-Methods", "GET,POST");
        response.setHeader("Cache-Control","no-cache");
        response.setStatus(HttpServletResponse.SC_OK);
        response.setContentType("text/html;charset=UTF-8");
    }
    @Override
    public OrderItemEntity getModel() {
        return null;
    }
}
