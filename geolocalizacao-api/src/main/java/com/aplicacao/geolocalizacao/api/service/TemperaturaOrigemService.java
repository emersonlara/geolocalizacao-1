package com.aplicacao.geolocalizacao.api.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.aplicacao.geolocalizacao.api.model.TemperaturaOrigem;
import com.aplicacao.geolocalizacao.api.repository.TemperaturaRespositoty;

@Service
public class TemperaturaOrigemService {

	@Autowired
	private TemperaturaRespositoty repositoty;
	
	@Transactional
	public TemperaturaOrigem saveTemperature(TemperaturaOrigem temperaturaOrigem) {
		
		return repositoty.save(temperaturaOrigem);
		
	}
	
}
