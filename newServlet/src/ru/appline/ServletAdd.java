package ru.appline;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import ru.appline.logic.Model;
import ru.appline.logic.User;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.concurrent.atomic.AtomicInteger;

@WebServlet(urlPatterns = "/add")
public class ServletAdd extends HttpServlet {
    public static AtomicInteger counter = new AtomicInteger(5);

    Model model = Model.getInstance();
    protected void doPost(HttpServletRequest httpRequest, HttpServletResponse httpResponse) throws IOException {
        StringBuffer jb = new StringBuffer();
        String line;
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        try{
            BufferedReader reader = httpRequest.getReader();
            while ((line = reader.readLine())!= null){
                jb.append(line);
            }
        }catch (Exception e){
            System.out.println("ERROR!!!");
        }
        JsonObject jsonObject = gson.fromJson(String.valueOf(jb), JsonObject.class);
        httpRequest.setCharacterEncoding("UTF-8");
        String name = jsonObject.get("name").getAsString();
        String surName = jsonObject.get("surName").getAsString();
        double salary = jsonObject.get("salary").getAsDouble();
        User user = new User(name,surName,salary);
        model.add(user, counter.getAndIncrement());
        httpResponse.setContentType("application/json;charset=utf-8");
        PrintWriter printWriter = httpResponse.getWriter();
        printWriter.print(gson.toJson(model.getFromList()));
    }

}
