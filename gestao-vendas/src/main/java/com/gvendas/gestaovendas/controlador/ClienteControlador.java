package com.gvendas.gestaovendas.controlador;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.gvendas.gestaovendas.entidades.Cliente;
import com.gvendas.gestaovendas.servico.ClienteServico;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api(tags = "Cliente")
@RestController
@RequestMapping("/cliente")
public class ClienteControlador {

	@Autowired
	private ClienteServico clienteServico;

	@ApiOperation(value = "Listar todas as categorias", nickname = "listarTodas")
	@GetMapping
	public List<Cliente> listarTodas() {
		// 1 - Retornando as categorias do banco de dados
		// 2 - Chamando o metodo estatico da CategoriaResponseDTO
		// 3- E percorrendo a lista do banco e transformando cada objeto de Categoria
		// para CategoriaResponseDTO
		return clienteServico.listarTodos();
	}

	@ApiOperation(value = "Listar por código", nickname = "buscarPorId")
	@GetMapping("/{codigo}")
	public ResponseEntity<Cliente> buscarPorId(@PathVariable Long codigo) {
		Optional<Cliente> cliente = clienteServico.buscarPorCodigo(codigo);

		// Verificando se a categoria que será buscado existe no banco de dados
		if (cliente.isPresent()) {
			return ResponseEntity.ok(cliente.get());
		} else {
			return ResponseEntity.notFound().build();
		}
	}
}
