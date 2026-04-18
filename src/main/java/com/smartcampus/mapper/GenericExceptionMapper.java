/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.smartcampus.mapper;

import javax.ws.rs.core.*;
import javax.ws.rs.ext.*;


@Provider
public class GenericExceptionMapper implements ExceptionMapper<Throwable> {

    // Prevents exposure of internal stack traces for security reasons
    @Override
    public Response toResponse(Throwable ex) {
        return Response.status(500)
                .entity("{\"error\":\"Internal Server Error\"}")
                .build();
    }
}
