package com.harryup.model;

import javax.persistence.*;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "monedas")
public class Moneda {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;

	@Column(name = "nombre")
	private String nombre;

	@Column(name = "cambio")
	private Double cambio;


	public Moneda(String nombre, Double cambio) {
		this.nombre = nombre;
		this.cambio = cambio;
	}
}
