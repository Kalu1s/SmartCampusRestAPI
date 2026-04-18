/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.smartcampus.filter;

import javax.ws.rs.container.*;
import javax.ws.rs.ext.Provider;
import java.io.IOException;
import java.util.logging.Logger;

// Marks this class as a provider so JAX-RS automatically registers it
@Provider
public class LoggingFilter implements ContainerRequestFilter, ContainerResponseFilter {

    // creating a logger instance that is used to log request and response details
    private static final Logger LOGGER = Logger.getLogger(LoggingFilter.class.getName());

    //executed for every incoming request
    @Override
    public void filter(ContainerRequestContext requestContext) throws IOException {
        LOGGER.info("Request: " + requestContext.getMethod() + " " +
                requestContext.getUriInfo().getPath());
    }
    
    //executed for every outgoing request
    @Override
    public void filter(ContainerRequestContext requestContext,
                       ContainerResponseContext responseContext) throws IOException {

        LOGGER.info("Response Status: " + responseContext.getStatus());
    }
}
