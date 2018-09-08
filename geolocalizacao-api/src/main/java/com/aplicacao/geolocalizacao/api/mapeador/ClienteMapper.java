package com.aplicacao.geolocalizacao.api.mapeador;

import com.aplicacao.geolocalizacao.api.model.ClienteEntity;
import com.aplicacao.geolocalizacao.api.model.DTO.ClienteEntityDTO;

public class ClienteMapper {
	
	
	public static ClienteEntityDTO converteClienteDTO(ClienteEntity clienteEntity) {
		return ClienteEntityDTO.builder().
				idade(clienteEntity.getIdade())
				.maxTemp(clienteEntity.getTemperatura().getTemperaturaMaxima())
				.minTemp(clienteEntity.getTemperatura().getTemperaturaMinima())
				.nomeCliente(clienteEntity.getNome())
				.build();	
	}
	
	
}
