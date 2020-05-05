package org.reins.orm.controller;

import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Action;
import org.reins.orm.entity.BookDescEntity;
import org.reins.orm.entity.BookEntity;
import org.reins.orm.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.configurationprocessor.json.JSONObject;
import org.springframework.stereotype.Controller;

import javax.json.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.List;

@Controller
public class BookInfoController extends ActionSupport implements ModelDriven<BookDescEntity> {
    @Autowired
    private BookService bookService;
    private BookDescEntity bookDescEntity;
    @Action(value="/BookInfo")
    public String getBookInfo() throws IOException{
        try {
            HttpServletRequest request= ServletActionContext.getRequest();
            HttpServletResponse response=ServletActionContext.getResponse();
            String isbnJSON=request.getParameter("isbn");
            response.setHeader("Access-Control-Allow-Origin", "http://localhost:8080");
            response.setHeader("Access-Control-Allow-Methods", "GET");
            response.setHeader("Cache-Control","no-cache");
            response.setStatus(HttpServletResponse.SC_OK);
            JSONObject object = new JSONObject();
            object.put("isbn",isbnJSON);
            String isbn=object.getString("isbn");
            bookDescEntity = bookService.getBookInfoByISBN(isbn);
            response.setContentType("text/html;charset=UTF-8");
            if(bookDescEntity != null){
                JsonObject info= Json.createObjectBuilder().add("book_description", bookDescEntity.getDescription())
                        .add("code",200).build();
                StringWriter sw=new StringWriter();
                try(JsonWriter jsonWriter=Json.createWriter(sw)) {
                    jsonWriter.writeObject(info);
                    response.getWriter().print(sw.toString());
                    response.getWriter().flush();;
                }
            }
            return  null;
        } catch (Exception e){
            e.printStackTrace();
            return ERROR;
        }
    }
    @Action(value="/getBooks")
    public String getBooks()throws IOException{
        try {
            HttpServletRequest request= ServletActionContext.getRequest();
            HttpServletResponse response=ServletActionContext.getResponse();
            response.setHeader("Access-Control-Allow-Origin", "http://localhost:8080");
            response.setHeader("Access-Control-Allow-Methods", "GET");
            response.setHeader("Cache-Control","no-cache");
            response.setStatus(HttpServletResponse.SC_OK);
            List<BookEntity> bookEntityList= bookService.getBooks();
            response.setContentType("text/html;charset=UTF-8");
            if(!bookEntityList.isEmpty()){
                JsonArrayBuilder builder=Json.createArrayBuilder();
                bookEntityList.forEach(
                        bookEntity -> {
                            builder.add(
                                    Json.createObjectBuilder().add("isbn",bookEntity.getIsbn())
                                    .add("press",bookEntity.getPress())
                                    .add("author",bookEntity.getAuthor())
                                    .add("book_name",bookEntity.getName())
                                    .add("price",bookEntity.getPrice())
                                    .add("storage",bookEntity.getStorage())
                                    .add("publish_date",bookEntity.getPublishDate().toString())
                                    .add("pic","/bookpic/"+bookEntity.getIsbn().toString())
                                    .build()
                            );
                        }
                );
                StringWriter sw=new StringWriter();
                try(JsonWriter jsonWriter=Json.createWriter(sw)) {
                    jsonWriter.writeArray(builder.build());
                    response.getWriter().print(sw.toString());
                    response.getWriter().flush();;
                }
            }
            return  null;
        } catch (Exception e){
            e.printStackTrace();
            return ERROR;
        }
    }
    @Action(value = "/getBook")
    public  String getBook()throws IOException{
        try{
        HttpServletRequest request= ServletActionContext.getRequest();
        HttpServletResponse response=ServletActionContext.getResponse();
        response.setHeader("Access-Control-Allow-Origin", "http://localhost:8080");
        response.setHeader("Access-Control-Allow-Methods", "GET");
        response.setHeader("Cache-Control","no-cache");
        response.setStatus(HttpServletResponse.SC_OK);
        BookEntity bookEntity= bookService.getBookByISBN(request.getParameter("isbn"));
        response.setContentType("text/html;charset=UTF-8");
        if(bookEntity!=null){
            JsonObjectBuilder builder=Json.createObjectBuilder();
            builder.add("isbn",bookEntity.getIsbn())
                            .add("press",bookEntity.getPress())
                            .add("author",bookEntity.getAuthor())
                            .add("book_name",bookEntity.getName())
                            .add("price",bookEntity.getPrice())
                            .add("storage",bookEntity.getStorage())
                            .add("publish_date",bookEntity.getPublishDate().toString())
                            .add("pic","/bookpic/"+bookEntity.getIsbn().toString());
            StringWriter sw=new StringWriter();
            try(JsonWriter jsonWriter=Json.createWriter(sw)) {
                jsonWriter.writeObject(builder.build());
                response.getWriter().print(sw.toString());
                response.getWriter().flush();;
            }
        }
        return  null;
    }
    catch (Exception e){
        e.printStackTrace();
        return ERROR;
    }
    }
    @Action(value = "/BookUpdate")
    public String updateBook() throws IOException{
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
            Logger logger= LogManager.getLogger("BookUpdateWatcher");
            logger.info(buffer.toString());
            JSONObject object = new JSONObject(buffer.toString());
            logger.info(object.getString("value"),object.getString("type"));
            BookEntity bookEntity=bookService.getBookByISBN(object.getString("isbn"));
            switch (object.getString("type")){
                case "name":bookEntity.setName(object.getString("value"));break;
                case "author":bookEntity.setAuthor(object.getString("value"));break;
                case "press":bookEntity.setPress(object.getString("value"));break;
                case "price":bookEntity.setPrice(Integer.parseInt(object.getString("value")));break;
                case "publish_date":
                    DateFormat fmt=new SimpleDateFormat("yyyy-MM-dd");
                    bookEntity.setPublishDate((java.sql.Date) fmt.parse(object.getString("value")));break;
            }
            bookService.updateBook(bookEntity);
            return  null;
        } catch (Exception e){
            e.printStackTrace();
            return ERROR;
        }
    }
    @Override
    public BookDescEntity getModel() {
        return null;
    }
}
