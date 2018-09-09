package com.aplicacao.geolocalizacao.api.mapper;

import com.aplicacao.geolocalizacao.api.model.CustomerEntity;
import com.aplicacao.geolocalizacao.api.model.DTO.ClienteEntityDTO;

public class MapperCustomer {
	
	
	public static ClienteEntityDTO converteClienteDTO(CustomerEntity clienteEntity) {
		return ClienteEntityDTO.builder().
				idade(clienteEntity.getIdade())
				.maxTemp(clienteEntity.getTemperatura().getTemperaturaMaxima())
				.minTemp(clienteEntity.getTemperatura().getTemperaturaMinima())
				.nomeCliente(clienteEntity.getNome())
				.build();	
	}
	
	
}
