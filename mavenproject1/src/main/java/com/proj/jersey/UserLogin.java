/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.proj.jersey;

/**
 *
 * @author somyagoel
 */
import static com.sun.jersey.api.model.Parameter.Source.PATH;
import java.awt.Button;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import static java.lang.System.out;
import java.net.URI;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.http.HttpServlet;

import javax.ws.rs.HeaderParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import javax.xml.bind.DatatypeConverter;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.ResultSetMetaData;
import static java.time.Clock.system;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.UUID;
import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.MediaType;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import java.time.format.DateTimeFormatter;  
import java.time.LocalDateTime;   
import java.util.Date;

@Path("/login")
public class UserLogin extends HttpServlet {
    
    private static String dbURL = "jdbc:derby://localhost:1527/Users;create=true;user=somya;password=somya";
    private static String tableName = "USER_LOGIN_TABLE";
    private static String orders = "ORDERS";
    // jdbc Connection
    private static Connection conn = null;
    private static Statement stmt = null;
    
     @POST
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    public Response Login(
                                @FormParam("loginid") String loginid,
                                @FormParam("password") String password
                                ) 
            throws IOException, FileNotFoundException, ParseException {
        
      //  String resName = null;
      //  String resPass = null;
                        String active_user = null;
                createConnection();
                out.println("conn created");
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
                String restName = results.getString(4);
                String restPass = results.getString(3);
                if(restName.equals(loginid))
                {
                    if(restPass.equals(password))
                    {
                        System.out.println(id + "\t\t" + restName + "\t\t");
                        active_user = results.getString(2);
                    }
                }
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
	//	return Response.status(200).entity(output).build();
return Response.created(URI.create("/bookstore/" + String.valueOf(UUID.randomUUID()))).entity(active_user+" have successfully logged in").build();
             
	
        
        
        
       // return null;
    }
//    @POST
//    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
//    public Response createMessage(
//                                @FormParam("textarea") String name                                
//                                ) 
//            throws IOException, FileNotFoundException, ParseException {
//         out.println("option : "+name);
//         String output = name;
//         String login_id="meeta@gmail.com";
//         String book = "The Time Keeper";
//         insertOrders(book, login_id);
//	 return Response.status(200).entity(output).build();     
//    }
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
   
   private static void insertOrders(String book, String login_id) throws FileNotFoundException, IOException, ParseException
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
                    shareList.add((String) data.get("available_copies"));
              //      System.out.println(shareList.add((String) data.get("available_copies")));
                    List<String> authors = new ArrayList<String>();
                    authors = (List<String>) data.get("authors");
                    System.out.println("list" + authors);
                    shareList.add((String) authors.get(0));
                 //   shareList.add((String) data.get("authors"));
                    shareList.add((String) data.get("category_id"));
                  //  Object x =data.get("price"));
                    shareList.add((String) data.get("price"));
                    System.out.println("Nameeeee: " + data.get("category_id") + " and type: " + data.get("publisher"));
                 //   System.out.println("list" + shareList.get(data.get("publisher")));
                    if(shareList.get(1).equals("0") )
                                {
                                System.out.println("no books available");
                                }
                    else
                                {
               //                 data.get("available_copies")=data.get("available_copies")-1;
                stmt.execute("insert into " + orders + " values ('"+ book + "','" + login_id+ "','"+ dtf.format(now)+"',"+750+")");
                
                stmt.close();

                                  }
                }
            }      
            System.out.println("list" + shareList);
            
        }
        catch (SQLException sqlExcept)
        {
            sqlExcept.printStackTrace();
        }
    }
   
   private static void selectUser()
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
    
    
     private static void insertuser(int id, String restName)
    {
        try
        {
            stmt = conn.createStatement();
            String t = "geetika";
            String t2 = "kjh123";
            String cc = "insert into ORDERS (" +id +"," + t + ","+ t2 +","+ restName  +")";
            System.out.println(cc);
            stmt.execute(cc);
            stmt.execute("insert into " + tableName + " values (" +
                    id + ",'" + t + ",'"+ t2 +",'"+ restName  +"')");
            
            stmt.close();
        }
        catch (SQLException sqlExcept)
        {
            sqlExcept.printStackTrace();
        }
    }
     
    @GET
@Path("/{userid}/{pass}")                   //runs at http://localhost:8080/rest2/login/...../
	public Response getMsg(@PathParam("userid") String msg,@PathParam("pass") String msg2) {

		String output = "Jersey say : " + msg;
                String restName = null;
                String active_user = null;
                String active_number = null;
                String active_add = null;
                String restPass = null;
                String password = "asd123";
                createConnection();
                out.println("conn created");
          //      char aa;
        //        aa = "ziva";
          //      insertuser(9, msg);
          //      selectUser();
          //      shutdown();
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
                restName = results.getString(4);
                restPass = results.getString(3);
                if(restName.equals(msg))
                {
                    if(restPass.equals(msg2))
                    {
                        System.out.println(id + "\t\t" + restName + "\t\t");
                        active_user = results.getString(2);
                        active_number = results.getString(5);
                        active_add = results.getString(6);
                    }
                }
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
	//	return Response.status(200).entity(output).build();
return Response.created(URI.create("/bookstore/" + String.valueOf(UUID.randomUUID()))).entity("Welcome"+active_user+ "    "+"Phone number:"+active_number+"       Delivery Address:"+active_add).build();
             
	}
        
        
   
        
    
}