package com.harryup.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.harryup.model.Moneda;

public interface MonedaRepository extends JpaRepository<Moneda, Long> {
  List<Moneda> findByNombre(String nombre);
  Moneda findOneByNombre(String nombre);
}
