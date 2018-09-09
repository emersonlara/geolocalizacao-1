package com.aplicacao.geolocalizacao.api.model;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor(access = AccessLevel.PACKAGE)
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
@Entity
@Table(name = "TEMPERATURA")
public class TemperatureSource implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -1816999225659293893L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	private Float temperaturaMinima;
	private Float temperaturaMaxima;
	
	@OneToOne()
	@JoinColumn(name = "ID_CLIENTE")
	private CustomerEntity cliente;
	
	
}
