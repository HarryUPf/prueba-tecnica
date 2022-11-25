package com.harryup.model;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
public class Detalle {
	private Double monto;
	private Double tipocambio;
	private Double montocambio;
	private String monedaorigen;
	private String monedadestino;
}
