package com.aplicacao.geolocalizacao.api.service;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import com.aplicacao.geolocalizacao.api.mapper.MapperCustomer;
import com.aplicacao.geolocalizacao.api.mapper.MapperTemperatureGeolocation;
import com.aplicacao.geolocalizacao.api.model.CustomerEntity;
import com.aplicacao.geolocalizacao.api.model.TemperatureSource;
import com.aplicacao.geolocalizacao.api.model.DTO.ClienteEntityDTO;
import com.aplicacao.geolocalizacao.api.repository.RepositoryCutomer;
import com.aplicacao.geolocalizacao.api.util.WebUtils;

@Service
public class ServiceCustomer {
	
	@Autowired
	private RepositoryCutomer repository;
	
	@Autowired
	private TemperatureServiceSource TemperaturaOrigemService;
	

	@Transactional
	public CustomerEntity saveCustomer(CustomerEntity clienteEntity, MapperTemperatureGeolocation temperature) {
		
		CustomerEntity clienteSalvo = new CustomerEntity();
		TemperatureSource temperaturaOrigem = new TemperatureSource();
		
		Float maxTemp = temperature.getMax_temp();
		Float minTempo = temperature.getMin_temp();
		
		temperaturaOrigem.setTemperaturaMaxima(maxTemp);
		temperaturaOrigem.setTemperaturaMinima(minTempo);
		
		clienteSalvo = repository.save(clienteEntity);
		
		temperaturaOrigem.setCliente(clienteSalvo);
		TemperaturaOrigemService.saveTemperature(temperaturaOrigem);
		
		
		return clienteSalvo;
		
	}
	
	public CustomerEntity upgradeClient(Long codigo, CustomerEntity clienteSalvo ) {
		CustomerEntity clienteOld = repository.findOne(codigo);
		
		if(clienteOld == null) {
			throw new EmptyResultDataAccessException(1);
		}
		
		BeanUtils.copyProperties(clienteSalvo, clienteOld, "id");
		
		return repository.save(clienteOld);
	}
	
	public ClienteEntityDTO searchCustomerByCode(Long codigo) {
		
		CustomerEntity clienteEntity = repository.findOne(codigo);
		if(clienteEntity == null) {
			throw new EmptyResultDataAccessException(1);
		}
		
		ClienteEntityDTO clienteEntityDTO = MapperCustomer.converteClienteDTO(clienteEntity);
		return clienteEntityDTO;
	}
	
	
	public List<ClienteEntityDTO> searchCustomerAll() {
		List<CustomerEntity> clienteEntity = (List<CustomerEntity>) repository.findAll();
		List<ClienteEntityDTO> clienteEntityDTOs = new ArrayList<ClienteEntityDTO>();
		
		clienteEntity.forEach(cliente -> {
			ClienteEntityDTO clienteEntityDTO = MapperCustomer.converteClienteDTO(cliente);
			clienteEntityDTOs.add(clienteEntityDTO);
		});
	
		return clienteEntityDTOs;
	}
	

	public MapperTemperatureGeolocation customerProcess(HttpServletRequest request) {

		ArrayList<String> geoLocalizacao = new ArrayList<String>();
		
		String ipCliente = WebUtils.getClientIp(request);
		
		geoLocalizacao = GeolocationService.geolocalizacao(ipCliente);
		
		MapperTemperatureGeolocation woeid= GeolocationService.woeid(geoLocalizacao);
		
		return woeid;
	}
	
}
