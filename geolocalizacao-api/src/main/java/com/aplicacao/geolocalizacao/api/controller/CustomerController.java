package com.aplicacao.geolocalizacao.api.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.aplicacao.geolocalizacao.api.mapper.MapperTemperatureGeolocation;
import com.aplicacao.geolocalizacao.api.model.CustomerEntity;
import com.aplicacao.geolocalizacao.api.model.DTO.ClienteEntityDTO;
import com.aplicacao.geolocalizacao.api.repository.RepositoryCutomer;
import com.aplicacao.geolocalizacao.api.service.ServiceCustomer;

@RestController
@RequestMapping(path = "/cliente")
public class CustomerController {
	
	@Autowired
	private RepositoryCutomer repository;
	
	@Autowired
	private ServiceCustomer service;
	
	@Autowired
    private HttpServletRequest request;
	
	public Iterable<CustomerEntity> findByAllCliente() {
		return  repository.findAll();
	}
	
	
	@PostMapping
	public ResponseEntity<?> saveCustomer(@RequestBody  CustomerEntity clienteEntity){
		
		MapperTemperatureGeolocation temperature = service.customerProcess(request); 
	
		CustomerEntity clienteSalvo = service.saveCustomer(clienteEntity, temperature);
		return ResponseEntity.status(HttpStatus.CREATED).body(clienteSalvo);
	}
	
	@PutMapping("/{codigo}")
	public ResponseEntity<CustomerEntity> upgradeClient(@PathVariable Long codigo, @RequestBody CustomerEntity clienteEntity){
		CustomerEntity clienteSalvo = service.upgradeClient(codigo, clienteEntity);
		return ResponseEntity.ok(clienteSalvo);
	}
	
	
	@GetMapping("/{codigo}")
	public ResponseEntity<?> searchByCode(@PathVariable Long codigo){
		ClienteEntityDTO clienteEntityDTO = service.searchCustomerByCode(codigo);
		return clienteEntityDTO!=null ? ResponseEntity.ok(clienteEntityDTO) : ResponseEntity.notFound().build();
	}
	
	@GetMapping
	public List<?> findByAll() {
		List<ClienteEntityDTO> clienteEntityDTO = service.searchCustomerAll();
		return clienteEntityDTO;
	}
	
	
	@DeleteMapping("/{codigo}")
	@ResponseStatus(HttpStatus.NO_CONTENT) 
	public void remove(@PathVariable Long codigo) {
		repository.delete(codigo);
	}

}
