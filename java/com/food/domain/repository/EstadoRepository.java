package com.food.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.food.domain.model.Estado;


@Repository
public interface EstadoRepository extends JpaRepository<Estado, Long> {

}
