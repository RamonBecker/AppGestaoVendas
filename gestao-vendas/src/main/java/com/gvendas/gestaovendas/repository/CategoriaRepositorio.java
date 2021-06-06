package com.gvendas.gestaovendas.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import com.gvendas.gestaovendas.entidades.Categoria;

public interface CategoriaRepositorio extends JpaRepository<Categoria, Long> {

	Categoria findByNome(String nome);

}
