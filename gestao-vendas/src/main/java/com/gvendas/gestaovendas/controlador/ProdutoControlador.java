package com.gvendas.gestaovendas.controlador;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.gvendas.gestaovendas.entidades.Produto;
import com.gvendas.gestaovendas.servico.ProdutoServico;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api(tags = "Produto")
@RestController
@RequestMapping("/categoria/{codigoCategoria}/produto")
public class ProdutoControlador {

	@Autowired
	private ProdutoServico produtoServico;

	@ApiOperation(value = "Listar todos", nickname = "listarTodos")
	@GetMapping
	public List<Produto> listarTodos(Long codigoCategoria) {
		return produtoServico.listarTodos(codigoCategoria);
	}

	@ApiOperation(value = "Listar por código", nickname = "buscarPorId")
	@GetMapping("/{codigo}")
	public ResponseEntity<Optional<Produto>> buscarPorId(@PathVariable Long codigoCategoria,
			@PathVariable Long codigo) {
		Optional<Produto> produtos = produtoServico.buscarPorCodigo(codigo, codigoCategoria);

//
//		// Verificando se a categoria que será buscado existe no banco de dados
		if (produtos.isPresent()) {
			return ResponseEntity.ok(produtos);
		} else {
			return ResponseEntity.notFound().build();
		}
	}

	@ApiOperation(value = "Salvar", nickname = "salvarProduto")
	@PostMapping
	public ResponseEntity<Produto> salvar(@Valid @RequestBody Produto produto) {
		return ResponseEntity.status(HttpStatus.CREATED).body(produtoServico.salvar(produto));
	}

	@ApiOperation(value = "AtualizarProduto", nickname = "atualizarProduto")
	@PutMapping("/{codigo}")
	public ResponseEntity<Produto> atualizar(@PathVariable Long codigoCategoria, @PathVariable Long codigo,
			@Valid @RequestBody Produto produto) {
		return ResponseEntity.ok(produtoServico.atualizar(codigoCategoria, codigo, produto));
	}
}
