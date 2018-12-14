package com.algaworks.moneyapi.repository;

import com.algaworks.moneyapi.model.Categoria;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CategoriaRepository extends JpaRepository<Categoria, Long> {

    @Query("from Categoria c where  c.nome like %:nome%")
    List<Categoria> listarBy(@Param("nome") String param);

}