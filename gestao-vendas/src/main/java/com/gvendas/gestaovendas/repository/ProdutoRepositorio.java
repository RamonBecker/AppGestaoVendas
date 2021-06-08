package com.gvendas.gestaovendas.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.gvendas.gestaovendas.entidades.Produto;

public interface ProdutoRepositorio extends JpaRepository<Produto, Long> {

	List<Produto> findByCategoriaCodigo(Long codigoCategoria);

//	@Query("select prod"
//		+ "from Produto prod"
//		+ "where prod.codigo = :codigo"
//		+ "and prod.categoria.codigo = :codigoCategoria")
//	Optional<Produto> buscarPorCodigo(Long codigo, Long codigoCategoria);

	// Outra forma de se resolver
	Optional<Produto> findByCodigoAndCategoriaCodigo(Long codigo, Long codigo_categoria);

	Optional<Produto> findByCategoriaCodigoAndDescricao(Long codigoCategoria, String descricao);

	
}
