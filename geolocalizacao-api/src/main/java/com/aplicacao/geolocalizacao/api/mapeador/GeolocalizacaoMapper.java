package com.aplicacao.geolocalizacao.api.mapeador;

import java.util.Map;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PACKAGE)
public class GeolocalizacaoMapper {

	private String status;
	private DadosGeolocalizacaoMapper data;
//	private Map<String, DadosGeolocalizacaoMapper> data;
	
	
	@Override
	public String toString() {
		return "GeolocalizacaoMapper [status=" + status + ", data=" + data + "]";
	}
	
	

}