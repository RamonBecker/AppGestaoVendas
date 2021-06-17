package com.gvendas.gestaovendas.controlador;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.gvendas.gestaovendas.dto.venda.ClienteVendaResponseDTO;
import com.gvendas.gestaovendas.dto.venda.VendaRequestDTO;
import com.gvendas.gestaovendas.servico.VendaServico;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api(tags = "Venda")
@RestController
@RequestMapping("/venda")
public class VendaControlador {

	@Autowired
	private VendaServico vendaServico;

	@ApiOperation(value = "listar vendas por cliente", nickname = "listarVendaPorCliente")
	@GetMapping("/cliente/{codigoCliente}")
	public ResponseEntity<ClienteVendaResponseDTO> listarVendaPorCliente(@PathVariable Long codigoCliente) {

		ClienteVendaResponseDTO listarVendaPorCliente = vendaServico.listarVendaPorCliente(codigoCliente);
		return ResponseEntity.ok(listarVendaPorCliente);
	}

	@ApiOperation(value = "listar venda por codigo", nickname = "listarVendaPorCodigo")
	@GetMapping("/{codigoVenda}")
	public ResponseEntity<ClienteVendaResponseDTO> listarVendaPorCodigo(@PathVariable Long codigoVenda) {
		ClienteVendaResponseDTO listarVendaPorCodigo = vendaServico.listarVendaPorCodigo(codigoVenda);

		return ResponseEntity.ok(listarVendaPorCodigo);
	}

	@ApiOperation(value = "Registrar venda", nickname = "salvarVenda")
	@PostMapping("/cliente/{codigoCliente}")
	public ResponseEntity<ClienteVendaResponseDTO> salvar(@PathVariable Long codigoCliente,
			@Valid @RequestBody VendaRequestDTO vendaDTO) {

		ClienteVendaResponseDTO vendaSalva = vendaServico.salvar(codigoCliente, vendaDTO);
		return ResponseEntity.status(HttpStatus.CREATED).body(vendaSalva);
	}
	
	@ApiOperation(value = "Deletarr venda", nickname = "deletarVenda")
	@DeleteMapping("/{codigoVenda}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void deletar(@PathVariable Long codigoVenda) {
		vendaServico.deletar(codigoVenda);
	}
}
