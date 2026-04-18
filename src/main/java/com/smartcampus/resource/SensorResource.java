/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.smartcampus.resource;

import com.smartcampus.model.*;
import com.smartcampus.repository.DataStore;
import com.smartcampus.exception.LinkedResourceNotFoundException;

import javax.ws.rs.*;
import javax.ws.rs.core.*;
import java.util.*;

@Path("/sensors")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class SensorResource {

    // create new sensors
    @POST
    public Response create(Sensor sensor) {

        if (!DataStore.rooms.containsKey(sensor.getRoomId())) {
            throw new LinkedResourceNotFoundException();
        }

        DataStore.sensors.put(sensor.getId(), sensor);

        DataStore.rooms.get(sensor.getRoomId())
                .getSensorIds().add(sensor.getId());

        return Response.status(201).entity(sensor).build();
    }

    // retrieve sensors
    @GET
    public Collection<Sensor> getAll(@QueryParam("type") String type) {

        if (type == null) return DataStore.sensors.values();

        List<Sensor> result = new ArrayList<>();
        for (Sensor s : DataStore.sensors.values()) {
            if (s.getType().equalsIgnoreCase(type)) {
                result.add(s);
            }
        }
        return result;
    }

    // Sub-resource locator for sensor readings
    @Path("/{id}/readings")
    public SensorReadingResource getReadings(@PathParam("id") String id) {
        return new SensorReadingResource(id);
    }
}