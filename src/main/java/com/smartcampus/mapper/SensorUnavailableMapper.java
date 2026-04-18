/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.smartcampus.mapper;

import com.smartcampus.exception.SensorUnavailableException;

import javax.ws.rs.core.*;
import javax.ws.rs.ext.*;

// Handles cases where a sensor cannot accept readings
@Provider
public class SensorUnavailableMapper implements ExceptionMapper<SensorUnavailableException> {

    @Override
    public Response toResponse(SensorUnavailableException ex) {
        // Returns HTTP 403 Forbidden when sensor is not in a valid state
        return Response.status(403)
                .entity("{\"error\":\"Sensor in maintenance\"}")
                .build();
    }
}