package com.aplicacao.geolocalizacao.api.mapeador;

import java.util.Date;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor(access = AccessLevel.PACKAGE)
@NoArgsConstructor
public class GeolocalizacaoTemperaturaMapper {
	
	private Long id;
	private String weather_state_name;
	private String weather_state_abbr;
	private String wind_direction_compass;
	private Date created;
	private Date applicable_date;
	private Float min_temp;
	private Float max_temp;
	private Float the_temp;
	private Float wind_speed;
	private Float wind_direction;
	private Float air_pressure;
	private Float humidity;
	private Float visibility;
	private Float predictability;
	
}
