package com.gvendas.gestaovendas.servico;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gvendas.gestaovendas.dto.venda.ClienteVendaResponseDTO;
import com.gvendas.gestaovendas.dto.venda.VendaResponseDTO;
import com.gvendas.gestaovendas.entidades.Cliente;
import com.gvendas.gestaovendas.entidades.ItemVenda;
import com.gvendas.gestaovendas.entidades.Venda;
import com.gvendas.gestaovendas.excecao.RegraNegocioException;
import com.gvendas.gestaovendas.repository.ItemVendaRepositorio;
import com.gvendas.gestaovendas.repository.VendaRepositorio;

@Service
public class VendaServico extends AbstractVendaServico {

	private ClienteServico clienteServico;

	private VendaRepositorio vendaRepositorio;

	private ItemVendaRepositorio itemVendaRepositorio;

	@Autowired
	public VendaServico(ClienteServico clienteServico, VendaRepositorio vendaRepositorio,
			ItemVendaRepositorio itemVendaRepositorio) {
		this.clienteServico = clienteServico;
		this.vendaRepositorio = vendaRepositorio;
		this.itemVendaRepositorio = itemVendaRepositorio;
	}

	public ClienteVendaResponseDTO listarVendaPorCliente(Long codigoCliente) {
		Cliente cliente = validarClienteExistente(codigoCliente);

		List<VendaResponseDTO> vendaResponseDTOLIST = vendaRepositorio.findByClienteCodigo(codigoCliente).stream()
				.map(venda -> criacaoVendaReponseDTO(venda, itemVendaRepositorio.findByVendaCodigo(venda.getCodigo()))).collect(Collectors.toList());

		return new ClienteVendaResponseDTO(cliente.getNome(), vendaResponseDTOLIST);

	}

	public ClienteVendaResponseDTO listarVendaPorCodigo(Long codigoVenda) {
		Venda venda = validarVendaExistente(codigoVenda);
		List<ItemVenda> listaItemVenda = itemVendaRepositorio.findByVendaCodigo(codigoVenda);
		ClienteVendaResponseDTO clienteVendaResponseDTO = new ClienteVendaResponseDTO(venda.getCliente().getNome(),
				Arrays.asList(criacaoVendaReponseDTO(venda, listaItemVenda)));
		return clienteVendaResponseDTO;

	}

	private Venda validarVendaExistente(Long codigoVenda) {
		Optional<Venda> venda = vendaRepositorio.findById(codigoVenda);

		if (venda.isEmpty() || venda == null) {
			throw new RegraNegocioException(String.format("Venda de codigo %s não encontrada", codigoVenda));
		}
		return venda.get();
	}

	private Cliente validarClienteExistente(Long codigoCliente) {
		Optional<Cliente> cliente = clienteServico.buscarPorCodigo(codigoCliente);

		if (cliente.isEmpty() || cliente == null) {
			throw new RegraNegocioException(
					String.format("O Cliente de código %s informado não existe no cadastro", codigoCliente));
		}
		return cliente.get();
	}

}
