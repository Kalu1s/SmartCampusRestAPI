/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.smartcampus.resource;

import com.smartcampus.model.Room;
import com.smartcampus.repository.DataStore;
import com.smartcampus.exception.RoomNotEmptyException;

import javax.ws.rs.*;
import javax.ws.rs.core.*;
import java.util.*;

// Handles all operations related to Room resources
@Path("/rooms")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class RoomResource {

    // retrieve all rooms
    @GET
    public Collection<Room> getAll() {
        return DataStore.rooms.values();
    }

    // create a new room
    @POST
    public Response create(Room room) {
        DataStore.rooms.put(room.getId(), room);
        return Response.status(201).entity(room).build();
    }

    // retrieve a room by id
    @GET
    @Path("/{id}")
    public Room get(@PathParam("id") String id) {
        return DataStore.rooms.get(id);
    }
    
    // delete a room
    @DELETE
    @Path("/{id}")
    public Response delete(@PathParam("id") String id) {

        // check sensors directly connected to the room
        boolean hasSensors = DataStore.sensors.values().stream()
            .anyMatch(s -> id.equals(s.getRoomId()));

        if (hasSensors) {
            throw new RoomNotEmptyException();
        }

        // remove room if condition is safe
        DataStore.rooms.remove(id);
        return Response.ok().build();
    }
}
