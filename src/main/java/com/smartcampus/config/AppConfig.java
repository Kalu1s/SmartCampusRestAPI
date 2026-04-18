/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.smartcampus.config;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;

// // Defines the base URI for all REST endpoints in the application
@ApplicationPath("/api/v1")
public class AppConfig extends Application {
}
