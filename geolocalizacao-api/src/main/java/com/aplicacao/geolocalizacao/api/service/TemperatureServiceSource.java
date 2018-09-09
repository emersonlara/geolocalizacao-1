package com.aplicacao.geolocalizacao.api.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.aplicacao.geolocalizacao.api.model.TemperatureSource;
import com.aplicacao.geolocalizacao.api.repository.RespositotyTemperature;

@Service
public class TemperatureServiceSource {

	@Autowired
	private RespositotyTemperature repositoty;
	
	@Transactional
	public TemperatureSource saveTemperature(TemperatureSource temperaturaOrigem) {
		
		return repositoty.save(temperaturaOrigem);
		
	}
	
}
