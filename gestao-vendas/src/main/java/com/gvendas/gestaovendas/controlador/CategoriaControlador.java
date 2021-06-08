package com.gvendas.gestaovendas.controlador;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.gvendas.gestaovendas.entidades.Categoria;
import com.gvendas.gestaovendas.servico.CategoriaServico;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;


@Api(tags = "Categoria")
@RestController
@RequestMapping("/categoria")
public class CategoriaControlador {

	@Autowired
	private CategoriaServico categoriaServico;

	@ApiOperation(value = "Listar todas as categorias", nickname = "listarTodas")
	@GetMapping
	public List<Categoria> listarTodas() {
		return categoriaServico.listarTodas();
	}

	@ApiOperation(value = "Listar por código", nickname = "buscarPorId" )
	@GetMapping("/{codigo}")
	public ResponseEntity<Optional<Categoria>> buscarPorId(@PathVariable Long codigo) {
		Optional<Categoria> categoria = categoriaServico.buscarPorCodigo(codigo);

		// Verificando se a categoria que será buscado existe no banco de dados
		if (categoria.isPresent()) {
			return ResponseEntity.ok(categoria);
		} else {
			return ResponseEntity.notFound().build();
		}
	}
	
	@ApiOperation(value = "Salvar", nickname = "salvarCategoria")

	@PostMapping
	public ResponseEntity<Categoria> salvar(@Valid @RequestBody Categoria categoria) {
		Categoria categoriaSalva = categoriaServico.salvar(categoria);

		return ResponseEntity.status(HttpStatus.CREATED).body(categoriaSalva);
	}
	
	@ApiOperation(value = "AtualizarCategoria", nickname = "AtualizarCategoria")
	@PutMapping("/{codigo}")
	public ResponseEntity<Categoria> atualizar(@PathVariable Long codigo,@Valid @RequestBody Categoria categoria) {
		return ResponseEntity.ok(categoriaServico.atualizar(codigo, categoria));
	}
	
	@ApiOperation(value = "Deletar", nickname = "Deletar")
	@DeleteMapping("/{codigo}")
	@ResponseStatus(code = HttpStatus.NO_CONTENT)
	public void deletar(@PathVariable Long codigo) {
		categoriaServico.deletar(codigo);
	}
	
}
