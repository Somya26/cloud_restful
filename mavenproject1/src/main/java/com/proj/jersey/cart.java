/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.proj.jersey;

import static com.sun.jersey.api.model.Parameter.Source.PATH;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import static java.lang.System.out;
import java.net.URI;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.UUID;
import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

/**
 *
 * @author somyagoel
 */
@Path("/cart")
public class cart {
    
        private static String dbURL = "jdbc:derby://localhost:1527/Users;create=true;user=somya;password=somya";
    private static String tableName = "USER_LOGIN_TABLE";
    private static String orders = "ORDERS_NEW";
    // jdbc Connection
    private static Connection conn = null;
    private static Statement stmt = null;
    
    @POST
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    public Response createMessage(
                                @FormParam("book_button") String name                                
                                ) 
            throws IOException, FileNotFoundException, ParseException {
         out.println("option : "+name);
         String output = name;
         String login_id="meeta@gmail.com";
       //  String book = "The Time Keeper";
         boolean b = insertOrders(output, login_id);
         return Response.created(URI.create("/bookstore/" + String.valueOf(UUID.randomUUID()))).entity("You have successfully placed an order for "+
                     output).build();
	// return Response.status(200).entity(output).build();     
    }
    
    private static void createConnection()
    {
        try
        {
            Class.forName("org.apache.derby.jdbc.ClientDriver").newInstance();
            //Get a connection
            conn = DriverManager.getConnection(dbURL); 
        }
        catch (Exception except)
        {
            except.printStackTrace();
        }
    }
     private static boolean insertOrders(String book, String login_id) throws FileNotFoundException, IOException, ParseException
    {
        JSONParser parser = new JSONParser();   
        Object obj = parser.parse(new FileReader("/Users/somyagoel/mavenproject1/mavenproject1/src/main/resources/db/books.txt"));
        JSONObject jsonObject = (JSONObject) obj;
         //   HashMap<Object, Object> shareList = new HashMap<Object, Object>();
        HashMap<String, Double> listMap = new HashMap<String, Double>();  
        List<String> shareList = new ArrayList<String>();
        JSONArray array = (JSONArray) jsonObject.get("books"); // it should be any array name
    
        try
        {
            createConnection();
            stmt = conn.createStatement();
            DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd");  
            LocalDateTime now = LocalDateTime.now();  
            Date date = new Date();  

            System.out.println(dtf.format(now));  
            System.out.println(book + "   "+ login_id);
             
            Iterator<Object> iterator = array.iterator();
             while (iterator.hasNext())
            {
                Object it = iterator.next();
                JSONObject data = (JSONObject) it;
                if(data.get("title").equals(book))
                {
                    shareList.add((String) data.get("publisher"));
                    System.out.println(data.get("publisher"));
                    shareList.add((String) data.get("available_copies"));
                    System.out.println(data.get("available_copies"));
                    List<String> authors = new ArrayList<String>();
                    authors = (List<String>) data.get("authors");
                    System.out.println("list" + authors);
                    shareList.add((String) authors.get(0));
                 //   shareList.add((String) data.get("authors"));
                  //  shareList.add((String) data.get("category_id"));
                  //  System.out.println(data.get("category_id"));
                  //  Object x =data.get("price"));
                    shareList.add((String) data.get("price"));
                    System.out.println("Nameeeee: " + data.get("price") + data.get("publisher"));
                 //   System.out.println("list" + shareList.get(data.get("publisher")));
                 
                    if(shareList.get(1).equals("0") )
                                {
                                System.out.println("no books available");
                                }
                    else
                                {
                                    int a = Integer.parseInt(shareList.get(1));
                                    a = a-1;  
                                    System.out.println(a);
                                    data.put("available_copies", Integer.toString(a));
               //                 data.get("available_copies")=data.get("available_copies")-1;
                stmt.execute("insert into " + orders + " values ('"+ book + "','" + login_id+ "','"+ dtf.format(now)+"',"+data.get("price")+")");
                
                stmt.close();

                                  }
                }
            }      
            System.out.println("list" + shareList);
              
        }
        catch (SQLException sqlExcept)
        {
            sqlExcept.printStackTrace();
                return false;
        }
        return true;

    }
}
