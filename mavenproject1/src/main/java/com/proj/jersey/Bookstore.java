
package com.proj.jersey;

import com.sun.jersey.api.NotFoundException;
import java.util.Map;
import java.util.TreeMap;

import javax.ws.rs.GET;
//import javax.ws.rs.NotFoundException;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.sun.jersey.spi.resource.Singleton;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

import java.net.URI;
import java.util.List;
import java.util.UUID;
 
import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;
import javax.ws.rs.Consumes;
import javax.ws.rs.core.MediaType;

//import org.glassfish.jersey.server.mvc.Template;

@Path("/bookstore")
@Singleton
//@Template
@Produces(MediaType.TEXT_HTML)
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public final class Bookstore {

   private final String items;
    private String name;

    public Bookstore() {
   //     this.items = new TreeMap<>();
        setName("Czech Bookstore");
    //    getItems().put("1", "Svejk Jaroslav Hasek"));
           items = "maths";
       // getItems().put("2", new Book("Krakatit", "Karel Capek"));
       // getItems().put("3", new CD("Ma Vlast 1", "Bedrich Smetana", new Track[] {new Track("Vysehrad", 180),new Track("Vltava", 172),new Track("Sarka", 32)}));
                        
        
    }

    @Path("items")
    public String getItem() {
        String i = getItems();
        
        if (i == null) {
    //        throw new NotFoundException(Response
      //              .status(Response.Status.NOT_FOUND)
        //            .entity("Item, " + itemid + ", is not found")
          //          .build());
        }

        return "yyayayaya";
    }

    @GET
    @Produces({MediaType.APPLICATION_XML, MediaType.TEXT_XML, MediaType.APPLICATION_JSON})
    public Bookstore getXml() {
        return this;
    }

    public long getSystemTime() {
        return System.currentTimeMillis();
    }

    public String getItems() {
    //    return null;
        return items;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    
      
    
}