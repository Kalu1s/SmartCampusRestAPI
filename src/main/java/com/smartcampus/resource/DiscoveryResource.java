/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.smartcampus.resource;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.*;

@Path("/")
@Produces(MediaType.APPLICATION_JSON)
public class DiscoveryResource {
    
    @GET
    public Map<String, Object> getInfo() {
        Map<String, Object> res = new HashMap<>();
        
        // Basic API metadata
        res.put("name", "Smart Campus Sensor & Room Management API");
        res.put("version", "v1");
        
        // Contact / my info
        Map<String, String> contact = new HashMap<>();
        contact.put("module", "5COSC022C.2");
        contact.put("owner", "Theviru Amarasinghe");
        contact.put("email", "w2120304@westminster.ac.uk"); 
        
        res.put("contact", contact);
        
        // hyper media style resource links - HATEOAS concept
        Map<String, String> resource = new HashMap<>();
        resource.put("self", "/api/v1");
        resource.put("rooms", "/api/v1/rooms");
        resource.put("sensors", "/api/v1/sensors");
        
        res.put("Resources",resource);
        return res;
    }
}