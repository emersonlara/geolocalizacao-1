package com.aplicacao.geolocalizacao.api.service;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import com.aplicacao.geolocalizacao.api.mapeador.ClienteMapper;
import com.aplicacao.geolocalizacao.api.mapeador.GeolocalizacaoTemperaturaMapper;
import com.aplicacao.geolocalizacao.api.model.ClienteEntity;
import com.aplicacao.geolocalizacao.api.model.TemperaturaOrigem;
import com.aplicacao.geolocalizacao.api.model.DTO.ClienteEntityDTO;
import com.aplicacao.geolocalizacao.api.repository.ClienteRepository;
import com.aplicacao.geolocalizacao.api.util.WebUtils;

@Service
public class ClienteService {
	
	@Autowired
	private ClienteRepository repository;
	
	@Autowired
	private TemperaturaOrigemService TemperaturaOrigemService;
	

	@Transactional
	public ClienteEntity saveCustomer(ClienteEntity clienteEntity, GeolocalizacaoTemperaturaMapper temperature) {
		
		ClienteEntity clienteSalvo = new ClienteEntity();
		TemperaturaOrigem temperaturaOrigem = new TemperaturaOrigem();
		
		Float maxTemp = temperature.getMax_temp();
		Float minTempo = temperature.getMin_temp();
		
		temperaturaOrigem.setTemperaturaMaxima(maxTemp);
		temperaturaOrigem.setTemperaturaMinima(minTempo);
		
		clienteSalvo = repository.save(clienteEntity);
		
		temperaturaOrigem.setCliente(clienteSalvo);
		TemperaturaOrigemService.saveTemperature(temperaturaOrigem);
		
		
		return clienteSalvo;
		
	}
	
	public ClienteEntity upgradeClient(Long codigo, ClienteEntity clienteSalvo ) {
		ClienteEntity clienteOld = repository.findOne(codigo);
		
		if(clienteOld == null) {
			throw new EmptyResultDataAccessException(1);
		}
		
		BeanUtils.copyProperties(clienteSalvo, clienteOld, "id");
		
		return repository.save(clienteOld);
	}
	
	public ClienteEntityDTO searchCustomerByCode(Long codigo) {
		
		ClienteEntity clienteEntity = repository.findOne(codigo);
		if(clienteEntity == null) {
			throw new EmptyResultDataAccessException(1);
		}
		
		ClienteEntityDTO clienteEntityDTO = ClienteMapper.converteClienteDTO(clienteEntity);
		return clienteEntityDTO;
	}
	
	
	public List<ClienteEntityDTO> searchCustomerAll() {
		List<ClienteEntity> clienteEntity = (List<ClienteEntity>) repository.findAll();
		List<ClienteEntityDTO> clienteEntityDTOs = new ArrayList<ClienteEntityDTO>();
		
		clienteEntity.forEach(cliente -> {
			ClienteEntityDTO clienteEntityDTO = ClienteMapper.converteClienteDTO(cliente);
			clienteEntityDTOs.add(clienteEntityDTO);
		});
	
		return clienteEntityDTOs;
	}
	

	public GeolocalizacaoTemperaturaMapper customerProcess(HttpServletRequest request) {

		ArrayList<String> geoLocalizacao = new ArrayList<String>();
		
		String ipCliente = WebUtils.getClientIp(request);
		
		geoLocalizacao = WebUtils.geolocalizacao(ipCliente);
		
		GeolocalizacaoTemperaturaMapper woeid= WebUtils.woeid(geoLocalizacao);
		
		return woeid;
	}


	
	
}