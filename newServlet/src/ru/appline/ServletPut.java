package ru.appline;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import ru.appline.logic.Model;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet(urlPatterns = "/put")
public class ServletPut extends HttpServlet {
    Model model = Model.getInstance();

    protected void doPut(HttpServletRequest httpRequest, HttpServletResponse httpResponse) throws IOException {
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
        int id = jsonObject.get("id").getAsInt();
        String name = jsonObject.get("name").getAsString();
        String surName = jsonObject.get("surName").getAsString();
        double salary = jsonObject.get("salary").getAsDouble();
        model.getFromList().get(id).setName(name);
        model.getFromList().get(id).setSurName(surName);
        model.getFromList().get(id).setSalary(salary);
        httpResponse.setContentType("application/json;charset=utf-8");
        PrintWriter printWriter = httpResponse.getWriter();
        printWriter.print(gson.toJson(model.getFromList()));
    }
}
