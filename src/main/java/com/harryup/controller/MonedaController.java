package com.harryup.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.harryup.model.Detalle;
import com.harryup.model.Moneda;
import com.harryup.repository.MonedaRepository;

@CrossOrigin(origins = "http://freeaspect.com:4200")
@RestController
@RequestMapping("/api")
public class MonedaController {

	@Autowired
	MonedaRepository monedaRepository;

	@GetMapping("/listar")
	public ResponseEntity<List<Moneda>> getAllMonedas(@RequestParam(required = false) String nombre) {
		try {
			List<Moneda> monedas = new ArrayList<Moneda>();

			if (nombre == null)
				monedaRepository.findAll().forEach(monedas::add);
			else
				monedaRepository.findByNombre(nombre).forEach(monedas::add);

			if (monedas.isEmpty()) {
				return new ResponseEntity<>(HttpStatus.NO_CONTENT);
			}

			return new ResponseEntity<>(monedas, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@PostMapping("/crear")
	public ResponseEntity<Moneda> createMoneda(@RequestBody Moneda moneda) {
		try {
			Moneda _moneda = monedaRepository.save(new Moneda(moneda.getNombre(), moneda.getCambio()));
			return new ResponseEntity<>(_moneda, HttpStatus.CREATED);
		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@PostMapping("/calcular/{monedaorigen}/{monedadestino}")
	public ResponseEntity<Detalle> calcularMoneda(
		@PathVariable("monedaorigen") String monedaorigen,
		@PathVariable("monedadestino") String monedadestino,
		@RequestParam("monto") Double monto
	) {
		try {
			Detalle detalle = new Detalle();

			List<Moneda> monedaOrigenLst = new ArrayList<Moneda>();
			List<Moneda> monedaDestinoLst = new ArrayList<Moneda>();
			monedaRepository.findByNombre(monedaorigen).forEach(monedaOrigenLst::add);
			monedaRepository.findByNombre(monedadestino).forEach(monedaDestinoLst::add);
			
			Moneda monedaOrigenRes = monedaOrigenLst.get(0);
			Moneda monedaDestinoRes = monedaDestinoLst.get(0);

			detalle.setMonto(monto);
			detalle.setTipocambio(monedaOrigenRes.getCambio()/monedaDestinoRes.getCambio());
			detalle.setMontocambio(monto*detalle.getTipocambio());
			detalle.setMonedaorigen(monedaOrigenRes.getNombre());
			detalle.setMonedadestino(monedaDestinoRes.getNombre());

			return new ResponseEntity<>(detalle, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

}
