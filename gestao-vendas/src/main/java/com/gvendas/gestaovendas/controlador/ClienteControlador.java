package com.gvendas.gestaovendas.controlador;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.gvendas.gestaovendas.dto.cliente.ClienteRequestDTO;
import com.gvendas.gestaovendas.dto.cliente.ClienteResponseDTO;
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

	@ApiOperation(value = "Listar todos os cliente", nickname = "listarTodosCliente")
	@GetMapping
	public List<ClienteResponseDTO> listarTodas() {
		// 1 - Retornando as categorias do banco de dados
		// 2 - Chamando o metodo estatico da CategoriaResponseDTO
		// 3- E percorrendo a lista do banco e transformando cada objeto de Categoria
		// para CategoriaResponseDTO
		return clienteServico.listarTodos().stream()
				.map(cliente -> ClienteResponseDTO.conversaoParaClienteResponseDTO(cliente))
				.collect(Collectors.toList());
	}

	@ApiOperation(value = "Listar por código cliente", nickname = "buscarPorIdCliente")
	@GetMapping("/{codigo}")
	public ResponseEntity<ClienteResponseDTO> buscarPorId(@PathVariable Long codigo) {
		Optional<Cliente> cliente = clienteServico.buscarPorCodigo(codigo);

		// Verificando se a categoria que será buscado existe no banco de dados
		if (cliente.isPresent()) {
			ClienteResponseDTO aux_conversao_Cliente_DTO = ClienteResponseDTO
					.conversaoParaClienteResponseDTO(cliente.get());
			return ResponseEntity.ok(aux_conversao_Cliente_DTO);
		} else {
			return ResponseEntity.notFound().build();
		}
	}

	@ApiOperation(value = "Salvar", nickname = "salvarCliente")
	@PostMapping
	public ResponseEntity<ClienteResponseDTO> salvar(@Valid @RequestBody ClienteRequestDTO clienteDTO) {
		Cliente cliente = clienteDTO.conversaoParEntidade();
		Cliente clienteSalvo = clienteServico.salvar(cliente);

		ClienteResponseDTO aux_conversao_Cliente_DTO = ClienteResponseDTO.conversaoParaClienteResponseDTO(clienteSalvo);
		return ResponseEntity.status(HttpStatus.CREATED).body(aux_conversao_Cliente_DTO);
	}

}
