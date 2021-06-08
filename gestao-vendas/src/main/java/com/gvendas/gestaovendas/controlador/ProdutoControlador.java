package com.gvendas.gestaovendas.controlador;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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

import com.gvendas.gestaovendas.dto.produto.ProdutoResponseDTO;
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
	public List<ProdutoResponseDTO> listarTodos(Long codigoCategoria) {
		return produtoServico.listarTodos(codigoCategoria).stream()
				.map(produto -> ProdutoResponseDTO.conversaoParaProdutoDTO(produto)).collect(Collectors.toList());
	}

	@ApiOperation(value = "Listar por código", nickname = "buscarPorId")
	@GetMapping("/{codigo}")
	public ResponseEntity<ProdutoResponseDTO> buscarPorId(@PathVariable Long codigoCategoria,
			@PathVariable Long codigo) {
		Optional<Produto> produtos = produtoServico.buscarPorCodigo(codigo, codigoCategoria);

//
//		// Verificando se a categoria que será buscado existe no banco de dados
		if (produtos.isPresent()) {
			return ResponseEntity.ok(ProdutoResponseDTO.conversaoParaProdutoDTO(produtos.get()));
		} else {
			return ResponseEntity.notFound().build();
		}
	}

	@ApiOperation(value = "Salvar", nickname = "salvarProduto")
	@PostMapping
	public ResponseEntity<Produto> salvar(@PathVariable Long codigoCategoria, @Valid @RequestBody Produto produto) {
		return ResponseEntity.status(HttpStatus.CREATED).body(produtoServico.salvar(codigoCategoria, produto));
	}

	@ApiOperation(value = "AtualizarProduto", nickname = "atualizarProduto")
	@PutMapping("/{codigo}")
	public ResponseEntity<Produto> atualizar(@PathVariable Long codigoCategoria, @PathVariable Long codigo,
			@Valid @RequestBody Produto produto) {
		return ResponseEntity.ok(produtoServico.atualizar(codigoCategoria, codigo, produto));
	}

	@ApiOperation(value = "DeletarProduto", nickname = "deletarProduto")
	@DeleteMapping("/{codigo}")
	@ResponseStatus(code = HttpStatus.NO_CONTENT)
	public void deletar(@PathVariable Long codigoCategoria, @PathVariable Long codigo) {
		produtoServico.deletar(codigoCategoria, codigo);
	}
}
