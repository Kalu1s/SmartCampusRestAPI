/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.smartcampus.resource;

import com.smartcampus.model.*;
import com.smartcampus.repository.DataStore;
import com.smartcampus.exception.SensorUnavailableException;

import javax.ws.rs.*;
import javax.ws.rs.core.*;
import java.util.*;

public class SensorReadingResource {

    private String sensorId;

    public SensorReadingResource(String sensorId) {
        this.sensorId = sensorId;
    }
    
    // retrieve all sensor readingss
    @GET
    public List<SensorReading> getAll() {
        return DataStore.readings.getOrDefault(sensorId, new ArrayList<>());
    }

    // Add a new reading for the sensor
    @POST
    public Response add(SensorReading reading) {

        Sensor sensor = DataStore.sensors.get(sensorId);

        // prevent adding readings if sensor is under maintenance
        if (sensor != null && "MAINTENANCE".equals(sensor.getStatus())) {
            throw new SensorUnavailableException();
        }

        // storing reading in map
        DataStore.readings
                .computeIfAbsent(sensorId, k -> new ArrayList<>())
                .add(reading);

        if (sensor != null) {
            sensor.setCurrentValue(reading.getValue());
        }

        return Response.status(201).build();
    }
}