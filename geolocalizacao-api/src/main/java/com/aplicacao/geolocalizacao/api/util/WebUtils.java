package com.aplicacao.geolocalizacao.api.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Calendar;

import javax.servlet.http.HttpServletRequest;

import com.aplicacao.geolocalizacao.api.mapper.MapperGeolocation;
import com.aplicacao.geolocalizacao.api.mapper.MapperTemperatureGeolocation;
import com.aplicacao.geolocalizacao.api.mapper.WoeidMapperGeolocator;
import com.google.gson.Gson;


public class WebUtils {
	

    public static String getClientIp(HttpServletRequest request) {

        String remoteAddr = "";

        if (request != null) {
            remoteAddr = request.getHeader("X-FORWARDED-FOR");
            if (remoteAddr == null || "".equals(remoteAddr)) {
                remoteAddr = request.getRemoteAddr();
            }
        }

        return remoteAddr;
    } 
   	
}
