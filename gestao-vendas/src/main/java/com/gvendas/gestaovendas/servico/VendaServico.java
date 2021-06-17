package com.gvendas.gestaovendas.servico;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.gvendas.gestaovendas.dto.venda.ClienteVendaResponseDTO;
import com.gvendas.gestaovendas.dto.venda.ItemVendaRequestDTO;
import com.gvendas.gestaovendas.dto.venda.VendaRequestDTO;
import com.gvendas.gestaovendas.dto.venda.VendaResponseDTO;
import com.gvendas.gestaovendas.entidades.Cliente;
import com.gvendas.gestaovendas.entidades.ItemVenda;
import com.gvendas.gestaovendas.entidades.Produto;
import com.gvendas.gestaovendas.entidades.Venda;
import com.gvendas.gestaovendas.excecao.RegraNegocioException;
import com.gvendas.gestaovendas.repository.ItemVendaRepositorio;
import com.gvendas.gestaovendas.repository.VendaRepositorio;

@Service
public class VendaServico extends AbstractVendaServico {

	private ClienteServico clienteServico;

	private VendaRepositorio vendaRepositorio;

	private ItemVendaRepositorio itemVendaRepositorio;

	private ProdutoServico produtoServico;

	@Autowired
	public VendaServico(ClienteServico clienteServico, VendaRepositorio vendaRepositorio,
			ItemVendaRepositorio itemVendaRepositorio, ProdutoServico produtoServico) {
		this.clienteServico = clienteServico;
		this.vendaRepositorio = vendaRepositorio;
		this.itemVendaRepositorio = itemVendaRepositorio;
		this.produtoServico = produtoServico;
	}

	public ClienteVendaResponseDTO listarVendaPorCliente(Long codigoCliente) {
		Cliente cliente = validarClienteExistente(codigoCliente);

		List<VendaResponseDTO> vendaResponseDTOLIST = vendaRepositorio.findByClienteCodigo(codigoCliente).stream().map(
				venda -> criacaoVendaReponseDTO(venda, itemVendaRepositorio.findByVendaPorCodigo(venda.getCodigo())))
				.collect(Collectors.toList());

		return new ClienteVendaResponseDTO(cliente.getNome(), vendaResponseDTOLIST);

	}

	public ClienteVendaResponseDTO listarVendaPorCodigo(Long codigoVenda) {
		Venda venda = validarVendaExistente(codigoVenda);
		List<ItemVenda> listaItemVenda = itemVendaRepositorio.findByVendaPorCodigo(codigoVenda);

		return retornarClienteVendaResponseDTO(venda, listaItemVenda);

	}
	//Verificando se existe uma transação
	//ReadOnly = false : não é apenas leitura
	//Rollbackfor 
	//Utilizar esta anotação quando for alterar ou salvar dados em mais de uma tabela
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false, rollbackFor = Exception.class ) 
	public ClienteVendaResponseDTO salvar(Long codigoCliente, VendaRequestDTO vendaDTO) {
		Cliente cliente = validarClienteExistente(codigoCliente);
		validarProdutoExistenteEAtualizarQuantidade(vendaDTO.getItemVendaDTO());
		Venda vendaSalva = salvarVenda(cliente, vendaDTO);

		List<ItemVenda> listaItemVenda = itemVendaRepositorio.findByVendaPorCodigo(vendaSalva.getCodigo());

		return retornarClienteVendaResponseDTO(vendaSalva, listaItemVenda);
	}

	private Venda salvarVenda(Cliente cliente, VendaRequestDTO vendaDTO) {

		Venda venda = new Venda(vendaDTO.getData(), cliente);
		Venda vendaSalva = vendaRepositorio.save(venda);

		vendaDTO.getItemVendaDTO().stream().map(itemVendaDTO -> criarItemVenda(itemVendaDTO, vendaSalva))
				.forEach(itemVendaRepositorio::save);

		return vendaSalva;

	}

	private void validarProdutoExistenteEAtualizarQuantidade(List<ItemVendaRequestDTO> itemVendaDTO) {
		itemVendaDTO.forEach(item -> {
			Produto produto = produtoServico.validarProdutoExistente(item.getCodigoProduto());
			validarQuantidadeProdutoExistente(produto, item.getQuantidade());
			produto.setQuantidade(produto.getQuantidade() - item.getQuantidade());
			produtoServico.atualizarQuantidadeProdutoAposVenda(produto);
		});
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

	private void validarQuantidadeProdutoExistente(Produto produto, Integer quantidadeVendaDTO) {
		if (!(produto.getQuantidade() >= quantidadeVendaDTO)) {
			throw new RegraNegocioException(
					String.format("A quantidade %s informada para o produto %s não está disponível em estoque",
							quantidadeVendaDTO, produto.getDescricao()));
		}
	}

}
