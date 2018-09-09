package com.aplicacao.geolocalizacao.api.service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Calendar;

import org.springframework.stereotype.Service;

import com.aplicacao.geolocalizacao.api.mapper.MapperGeolocation;
import com.aplicacao.geolocalizacao.api.mapper.MapperTemperatureGeolocation;
import com.aplicacao.geolocalizacao.api.mapper.WoeidMapperGeolocator;
import com.google.gson.Gson;

@Service
public class GeolocationService {
	
	public static final String IPVISITANTE = "https://ipvigilante.com/";
	public static final String GEOLOCALIZACAO = "https://www.metaweather.com/api/location/search/?lattlong=";
	public static final String WOEID = "https://www.metaweather.com/api/location/";

	
	
	public static ArrayList<String> geolocalizacao(String ip) {
    	URL url;
    	MapperGeolocation geolocalizacaoMapper = null;
    	ArrayList<String> geoLocalizacao = new ArrayList<String>();
		try {
//			url = new URL(IPVISITANTE + ip);
			url = new URL(IPVISITANTE + "189.105.87.167");
			URLConnection conecta = url.openConnection();
			BufferedReader buffer = new BufferedReader(new InputStreamReader(conecta.getInputStream()));
			
			String jsonString  = buffer.readLine();
			
			Gson gson = new Gson();
			geolocalizacaoMapper = gson.fromJson(jsonString, MapperGeolocation.class);
			
			geoLocalizacao.add(geolocalizacaoMapper.getData().getLatitude());
			geoLocalizacao.add(geolocalizacaoMapper.getData().getLongitude());
			geoLocalizacao.add(geolocalizacaoMapper.getData().getSubdivision_1_name());
			geoLocalizacao.add(geolocalizacaoMapper.getData().getCity_name());
			
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return geoLocalizacao;
    	
    }


	public static MapperTemperatureGeolocation woeid(ArrayList<String> geoLocalizacao) {
		
		String latitude = geoLocalizacao.get(0);
		String longitude = geoLocalizacao.get(1);
		String cidade = geoLocalizacao.get(3);
		MapperTemperatureGeolocation temperaturaLocal = null;
		
		URL url;
		WoeidMapperGeolocator[] goGeolocalizadorWoeidMapper = null;
		
		try {
			url = new URL(GEOLOCALIZACAO + latitude+','+longitude);
			URLConnection conecta = url.openConnection();
			BufferedReader buffer = new BufferedReader(new InputStreamReader(conecta.getInputStream()));
			
			String jsonString  = buffer.readLine();
			
			Gson gson = new Gson();
			goGeolocalizadorWoeidMapper = gson.fromJson(jsonString, WoeidMapperGeolocator[].class);
			
			for(WoeidMapperGeolocator woeid : goGeolocalizadorWoeidMapper ) {
				
				if(woeid.getTitle().toLowerCase().equals(cidade.toLowerCase())) {
					temperaturaLocal = temperaturaLocal(woeid.getWoeid());
					break;
				}
				
			}
			
			
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
			
		return temperaturaLocal;
	}


	private static MapperTemperatureGeolocation  temperaturaLocal(String woeid) {
		Calendar calendar = Calendar.getInstance(); 
		
		URL url;
		MapperTemperatureGeolocation[] geolocalizacaoTemperaturaMapper = null;
		try {
			url = new URL(WOEID + woeid+'/'+calendar.get(Calendar.YEAR) +'/'+ (calendar.get(Calendar.MONTH) + 1) +'/'+ calendar.get(Calendar.DATE));
			URLConnection conecta = url.openConnection();
			BufferedReader buffer = new BufferedReader(new InputStreamReader(conecta.getInputStream()));
			
			String jsonString  = buffer.readLine();
			
			Gson gson = new Gson();
			geolocalizacaoTemperaturaMapper = gson.fromJson(jsonString, MapperTemperatureGeolocation[].class);
			
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return geolocalizacaoTemperaturaMapper[0];
	}

	
	
}
