package org.reins.orm.controller;

import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Action;
import org.reins.orm.entity.CartsEntity;
import org.reins.orm.entity.OrderEntity;
import org.reins.orm.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.configurationprocessor.json.JSONArray;
import org.springframework.boot.configurationprocessor.json.JSONObject;
import org.springframework.stereotype.Controller;

import javax.json.Json;
import javax.json.JsonArrayBuilder;
import javax.json.JsonWriter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringWriter;
import java.util.List;

@Controller
public class TransactionController extends ActionSupport implements ModelDriven<OrderEntity> {
   @Autowired
   TransactionService transactionService;

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
            List<CartsEntity> cartsEntities=transactionService.getCartByID(ID);
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
            response.setStatus(HttpServletResponse.SC_OK);
            JSONObject object = new JSONObject();
            object.put("ID",IDjson);
            int ID=object.getInt("ID");
            List<OrderEntity> orderEntities=transactionService.getOrderByID(ID);
            response.setContentType("text/html;charset=UTF-8");
            if(!orderEntities.isEmpty()) {
                JsonArrayBuilder builder = Json.createArrayBuilder();
                orderEntities.forEach(
                       orderEntity -> {
                            builder.add(
                                    Json.createObjectBuilder().add("isbn", orderEntity.getIsbn())
                                            .add("quantity", orderEntity.getQuantity())
                                            .add("orderID",orderEntity.getOrderId())
                                            .add("order_setid",orderEntity.getOrderSetId())
                                            .add("transaction_date",orderEntity.getTransactionDate().toString())
                                            .add("amount",orderEntity.getAmount())
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
    @Action("/OrderSubmit")
    public String submitOrder()throws IOException{
        try {
            HttpServletRequest request= ServletActionContext.getRequest();
            HttpServletResponse response=ServletActionContext.getResponse();
            response.setHeader("Access-Control-Allow-Origin", "http://localhost:8080");
            response.setHeader("Access-Control-Allow-Methods", "POST,OPTIONS");
            response.setHeader("Cache-Control","no-cache");
            response.setContentType("text/html;charset=UTF-8");
            response.setStatus(HttpServletResponse.SC_OK);
            BufferedReader bufferedReader=request.getReader();
            String line;
            StringBuffer buffer=new StringBuffer();
            while((line=bufferedReader.readLine())!=null)
                buffer.append(line);
            bufferedReader.close();
            Logger logger= LogManager.getLogger("OrderSubmitWatcher");
            logger.info(buffer.toString());
            JSONObject object = new JSONObject(buffer.toString());
            int ID=object.getInt("ID");
            List<CartsEntity> cartsEntities = transactionService.getCartByID(ID);
            transactionService.submitOrder(cartsEntities);
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
            response.setHeader("Access-Control-Allow-Origin", "http://localhost:8080");
            response.setHeader("Access-Control-Allow-Methods", "POST,OPTIONS");
            response.setHeader("Cache-Control","no-cache");
            response.setContentType("text/html;charset=UTF-8");
            response.setStatus(HttpServletResponse.SC_OK);
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
            List<CartsEntity> cartsEntities=transactionService.getCartByID(ID);
            cartsEntities.clear();
            for(int i=0;i<jsonArray.length();i++){
                CartsEntity cartsEntity=new CartsEntity();
                cartsEntity.setId(ID);
                cartsEntity.setQuantity(jsonArray.getJSONObject(i).getInt("quantity"));
                cartsEntity.setName(name);
                cartsEntity.setIsbn(jsonArray.getJSONObject(i).getString("isbn"));
                cartsEntities.add(cartsEntity);
            }
            transactionService.updateCart(cartsEntities);
            return  null;
        } catch (Exception e){
            e.printStackTrace();
            return ERROR;
        }
    }
    @Override
    public OrderEntity getModel() {
        return null;
    }
}
