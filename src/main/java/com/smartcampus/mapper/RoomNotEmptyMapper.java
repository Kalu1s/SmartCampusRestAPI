/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.smartcampus.mapper;

import com.smartcampus.exception.RoomNotEmptyException;

import javax.ws.rs.core.*;
import javax.ws.rs.ext.*;

// Handles attempts to delete a room that still contains sensors
@Provider
public class RoomNotEmptyMapper implements ExceptionMapper<RoomNotEmptyException> {
    // Returns HTTP 409 Conflict indicating business rule violation
    @Override
    public Response toResponse(RoomNotEmptyException ex) {
        return Response.status(409)
                .entity("{\"error\":\"Room has sensors\"}")
                .build();
    }
}
