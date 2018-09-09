package com.aplicacao.geolocalizacao.api.repository;

import org.springframework.data.repository.CrudRepository;

import com.aplicacao.geolocalizacao.api.model.CustomerEntity;

public interface RepositoryCutomer extends CrudRepository<CustomerEntity, Long> {



}
