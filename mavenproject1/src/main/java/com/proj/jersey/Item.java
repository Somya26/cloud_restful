

package com.proj.jersey;

import java.io.FileNotFoundException;
import static java.lang.System.out;
import javax.ws.rs.GET;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
 
//import org.json.simple.JSONArray;
//import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
//import org.glassfish.jersey.server.mvc.Template;

//@Template
@Produces(MediaType.TEXT_HTML)
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class Item {

    private String title;
    private String author;
    
    public Item() {
        this.title="cloud and web services";
        this.author="shiv";
        JSONParser parser = new JSONParser();
      //  JSONParser.Feature.ALLOW_UNQUOTED_FIELD_NAMES;
        try {
            Object obj = parser.parse(new FileReader("/Users/somyagoel/mavenproject1/mavenproject1/src/main/resources/db/books.txt"));
            JSONObject jsonObject = (JSONObject) obj;
            Map<Object, Object> shareList = new HashMap<Object, Object>();
            JSONArray array = (JSONArray) jsonObject.get("books"); // it should be any array name
            
            Iterator<Object> iterator = array.iterator();

             while (iterator.hasNext())
            {
                Object it = iterator.next();
                JSONObject data = (JSONObject) it;
                shareList.put(data.get("category_id"), data.get("publisher"));
            }
            Iterator it = shareList.entrySet().iterator();
            while (it.hasNext())
            {
                 Map.Entry value = (Map.Entry) it.next();
     //            System.out.println("Name: " + value.getKey() + " and type: " + value.getValue());
            }
        //    String name = (String) jsonObject.get("category_id");
       //     String copies = (String) jsonObject.get("available_copies");
        //    JSONArray authorList = (JSONArray) jsonObject.get("authors");
 
       ///     System.out.println("Category: " + name);
         //   System.out.println("copies: " + copies);
          //  System.out.println("\nauthor List:");
        //    Iterator<String> iterator = authorList.iterator();
       //     while (iterator.hasNext()) {
         //       System.out.println(iterator.next());
           // }
 
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Item(final String title, final String author) {
        this.title = title;
        this.author = author;
    }

    public String getTitle() {
        return title;
    }

    public String getAuthor() {
        return author;
    }

    @GET
    @Produces({MediaType.APPLICATION_XML, MediaType.TEXT_XML, MediaType.APPLICATION_JSON})
    public Item getXml() {
        return this;
    }

    @Override
    public String toString() {
        return "Item{"
               + "title='" + title + '\''
               + ", author='" + author + '\''
               + '}';
    }
    
    public List<String> search_book(String s) throws FileNotFoundException, IOException, ParseException
    {
        JSONParser parser = new JSONParser();   
            Object obj = parser.parse(new FileReader("/Users/somyagoel/mavenproject1/mavenproject1/src/main/resources/db/books.txt"));
            JSONObject jsonObject = (JSONObject) obj;
         //   HashMap<Object, Object> shareList = new HashMap<Object, Object>();
        HashMap<String, Double> listMap = new HashMap<String, Double>();  
         List<String> shareList = new ArrayList<String>();
            JSONArray array = (JSONArray) jsonObject.get("books"); // it should be any array name
    
      //  JSONParser.Feature.ALLOW_UNQUOTED_FIELD_NAMES;
        try {          
            Iterator<Object> iterator = array.iterator();
             while (iterator.hasNext())
            {
                Object it = iterator.next();
                JSONObject data = (JSONObject) it;
                if(data.get("title").equals(s))
                {
                    shareList.add((String) data.get("publisher"));
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
                 
                }
            }      
            System.out.println("list" + shareList);
             
        } catch (Exception e) {
            e.printStackTrace();
        }
    //    out.println("33333");
                             return shareList;

}
    
     public List<String> search_publisher(String s) throws FileNotFoundException, IOException, ParseException
    {
        JSONParser parser = new JSONParser();   
            Object obj = parser.parse(new FileReader("/Users/somyagoel/mavenproject1/mavenproject1/src/main/resources/db/books.txt"));
            JSONObject jsonObject = (JSONObject) obj;
         //   HashMap<Object, Object> shareList = new HashMap<Object, Object>();
         List<String> shareList = new ArrayList<String>();
            JSONArray array = (JSONArray) jsonObject.get("books"); // it should be any array name
    
      //  JSONParser.Feature.ALLOW_UNQUOTED_FIELD_NAMES;
        try {          
            Iterator<Object> iterator = array.iterator();
             while (iterator.hasNext())
            {
                Object it = iterator.next();
                JSONObject data = (JSONObject) it;
                if(data.get("publisher").equals(s))
                {
                    shareList.add((String) data.get("title"));
                    List<String> authors = new ArrayList<String>();
                    authors = (List<String>) data.get("authors");
                     System.out.println("list" + authors);
                     shareList.add((String) authors.get(0));
                //    shareList.add((String) data.get("authors"));
                    shareList.add((String) data.get("category_id"));
                    shareList.add((String) data.get("price"));
                    System.out.println("Nameeeee: " + data.get("category_id") + " and type: " + data.get("publisher"));
                 //   System.out.println("list" + shareList.get(data.get("publisher")));
                 
                }
            }      
            System.out.println("list" + shareList);
             
        } catch (Exception e) {
            e.printStackTrace();
        }
    //    out.println("33333");
                             return shareList;

}
      public List<String> search_author(String s) throws FileNotFoundException, IOException, ParseException
    {
        JSONParser parser = new JSONParser();   
            Object obj = parser.parse(new FileReader("/Users/somyagoel/mavenproject1/mavenproject1/src/main/resources/db/books.txt"));
            JSONObject jsonObject = (JSONObject) obj;
         //   HashMap<Object, Object> shareList = new HashMap<Object, Object>();
         List<String> shareList = new ArrayList<String>();
            JSONArray array = (JSONArray) jsonObject.get("books"); // it should be any array name
    
      //  JSONParser.Feature.ALLOW_UNQUOTED_FIELD_NAMES;
        try {          
            Iterator<Object> iterator = array.iterator();
             while (iterator.hasNext())
            {
                Object it = iterator.next();
                JSONObject data = (JSONObject) it;
                List<String> authors = new ArrayList<String>();
                authors = (List<String>) data.get("authors");
                System.out.println("list" + authors);
                if(authors.get(0).equals(s))
                {
                    shareList.add((String) data.get("publisher"));
                    shareList.add((String) data.get("title"));
                    shareList.add((String) data.get("category_id"));
                    shareList.add((String) data.get("price"));
                    System.out.println("Nameeeee: " + data.get("category_id") + " and type: " + data.get("publisher"));
                 //   System.out.println("list" + shareList.get(data.get("publisher")));
                 
                }
            }      
            System.out.println("list" + shareList);
             
        } catch (Exception e) {
            e.printStackTrace();
        }
    //    out.println("33333");
                             return shareList;

}
}
    

 //    String name = (String) jsonObject.get("category_id");
       //     String copies = (String) jsonObject.get("available_copies");
        //    JSONArray authorList = (JSONArray) jsonObject.get("authors");
 
       ///     System.out.println("Category: " + name);
         //   System.out.println("copies: " + copies);
          //  System.out.println("\nauthor List:");
        //    Iterator<String> iterator = authorList.iterator();
       //     while (iterator.hasNext()) {
         //       System.out.println(iterator.next());
           // }
