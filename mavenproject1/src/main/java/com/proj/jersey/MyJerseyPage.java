package com.proj.jersey;
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author somyagoel
 */
import static java.lang.System.out;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import javax.servlet.http.HttpServlet;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;
 import java.io.IOException;
import java.net.URI;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.UUID;
 
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.ResultSetMetaData;
import static jdk.nashorn.internal.objects.ArrayBufferView.length;

@Path("/hello2")

public class MyJerseyPage extends HttpServlet {
    
    private static final long serialVersionUID = 1L;

    private final String items ;
    private String name;
    private Item bk = new Item();
    private static String dbURL = "jdbc:derby://localhost:1527/Users;create=false;user=somya;password=somya";
    private static String tableName = "USER_LOGIN_TABLE";
    // jdbc Connection
    private static Connection conn = null;
    private static Statement stmt = null;

  //  private String currentElement;
    

    public MyJerseyPage() {
     //   setName("Czech Bookstore");
        items = "maths";
 
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
   
   private static void insertRestaurants(int id, String restName)
    {
        try
        {
            stmt = conn.createStatement();
            stmt.execute("insert into " + tableName + " values (" +
                    id + ",'" + restName +"')");
            stmt.close();
        }
        catch (SQLException sqlExcept)
        {
            sqlExcept.printStackTrace();
        }
    }
   
   private static void selectRestaurants()
    {
        try
        {
            stmt = conn.createStatement();
            ResultSet results = stmt.executeQuery("select * from " + tableName);
            ResultSetMetaData rsmd = results.getMetaData();
            int numberCols = rsmd.getColumnCount();
            for (int i=1; i<=numberCols; i++)
            {
                //print Column Names
                System.out.print(rsmd.getColumnLabel(i)+"\t\t");  
            }

            System.out.println("\n-------------------------------------------------");

            while(results.next())
            {
                int id = results.getInt(1);
                String restName = results.getString(2);
              //  String cityName = results.getString(3);
                System.out.println(id + "\t\t" + restName + "\t\t");
            }
            results.close();
            stmt.close();
        }
        catch (SQLException sqlExcept)
        {
            sqlExcept.printStackTrace();
        }
    }
    
    private static void shutdown()
    {
        try
        {
            if (stmt != null)
            {
                stmt.close();
            }
            if (conn != null)
            {
                DriverManager.getConnection(dbURL + ";shutdown=true");
                conn.close();
            }           
        }
        catch (SQLException sqlExcept)
        {
            
        }

    }
    
@GET
@Produces(MediaType.TEXT_HTML)
public String sayHtmlHello() {
return "Hello from Jersey";             //runs at http://localhost:8080/rest2/hello2/
}
@GET
@Path("/{param}")                   //runs at http://localhost:8080/rest2/hello2/somya/
	public Response getMsg(@PathParam("param") String msg) {

		String output = "Jersey say : " + msg;
                
                createConnection();
                out.println("conn created");
          //      char aa;
        //        aa = "ziva";
                insertRestaurants(9, msg);
                selectRestaurants();
          //      shutdown();
        
		return Response.status(200).entity(output).build();

	}
        
        
        @GET
        @Path("/items")
    public String getItem() {
        String i = getItems();
        
        return i;
    }
    
    
    public String getItems() {
    //    return null;
        return items;
    }
    

    @POST
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    public Response createMessage(
                                @FormParam("op") String op,
                                @FormParam("search") String search                               
                                

                                ) throws IOException, FileNotFoundException, ParseException {
         out.println("option : "+op);
         if(op.equals("book"))
         {
       //     out.println("author  : "+message);
            List<String> j = bk.search_book(search);
           
           return Response.created(URI.create("/bookstore/" + String.valueOf(UUID.randomUUID()))).entity(
                     search + "  ,   " +j.get(0)+ "  ,  "+j.get(1)+ "   ,  "+j.get(2)+ "   ,   "+"$"+j.get(3) ).build();
             
            // This is a more real world "return"
            //return Response.created(URI.create("/messages/" + String.valueOf(UUID.randomUUID()))).build();            
        }
         
         if(op.equals("author")){
           
            List<String> j = bk.search_author(search);
         //   j.get(0).append(System.getProperty("line.separator"));
           
           return Response.created(URI.create("/bookstore/" + String.valueOf(UUID.randomUUID()))).entity(
                     search+System.getProperty("line.separator") + " ,  " +j.get(0)+ " ,  " +j.get(1)+ " ,  " +j.get(2)+ " ,  " +"$"+j.get(3) ).build();
             
            // This is a more real world "return"
            //return Response.created(URI.create("/messages/" + String.valueOf(UUID.randomUUID()))).build();            
        }
          if(op.equals("publisher")) {

            List<String> j = bk.search_publisher(search);
            String output=String.join(",", j);
            
         //  return Response.created(URI.create("/bookstore/" + String.valueOf(UUID.randomUUID()))).entity(
         //            search + "  ,  " +j.get(0)+ " ,  " +j.get(1)+ " ,  " +j.get(2)+ " ,  " +"$"+j.get(3) ).build();
             return Response.status(200).entity(output).build();
            // This is a more real world "return"
            //return Response.created(URI.create("/messages/" + String.valueOf(UUID.randomUUID()))).build();            
        }
          
        return Response.status(Response.Status.PRECONDITION_FAILED).build();
    }     


   }
