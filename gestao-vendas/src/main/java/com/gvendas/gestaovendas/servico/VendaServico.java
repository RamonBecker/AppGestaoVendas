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
	
	

	// Verificando se existe uma transação
	// ReadOnly = false : não é apenas leitura
	// Rollbackfor
	// Utilizar esta anotação quando for alterar ou salvar dados em mais de uma
	// tabela
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false, rollbackFor = Exception.class)
	public ClienteVendaResponseDTO salvar(Long codigoCliente, VendaRequestDTO vendaDTO) {
		Cliente cliente = validarClienteExistente(codigoCliente);
		validarProdutoExistenteEAtualizarQuantidade(vendaDTO.getItemVendaDTO());
		Venda vendaSalva = salvarVenda(cliente, vendaDTO);

		List<ItemVenda> listaItemVenda = itemVendaRepositorio.findByVendaPorCodigo(vendaSalva.getCodigo());

		return retornarClienteVendaResponseDTO(vendaSalva, listaItemVenda);
	}
	
	public ClienteVendaResponseDTO atualizar(Long codigoVenda, Long codigoCliente, VendaRequestDTO vendaDTO) {
		validarVendaExistente(codigoVenda);
		Cliente cliente = validarClienteExistente(codigoCliente);
		
		List<ItemVenda> listaItemVenda = itemVendaRepositorio.findByVendaPorCodigo(codigoVenda);
		validarProdutoExisente_e_DevolverEstoque(listaItemVenda);
		validarProdutoExistenteEAtualizarQuantidade(vendaDTO.getItemVendaDTO());
		itemVendaRepositorio.deleteAll(listaItemVenda);
		Venda vendaAtualizada = atualizarVenda(codigoVenda, cliente, vendaDTO);
		
		List<ItemVenda> listaItemVendaAtualizada = itemVendaRepositorio.findByVendaPorCodigo(vendaAtualizada.getCodigo());
		return retornarClienteVendaResponseDTO(vendaAtualizada, listaItemVendaAtualizada);
		
	}

	@Transactional(propagation = Propagation.REQUIRED, readOnly = false, rollbackFor = Exception.class)
	// Este funcionalidade de deletar é como um estorno, onde a quantidade do
	// produto é devolvida para o estoque original
	public void deletar(Long codigoVenda) {
		validarVendaExistente(codigoVenda);
		List<ItemVenda> itemVenda = itemVendaRepositorio.findByVendaPorCodigo(codigoVenda);
		validarProdutoExisente_e_DevolverEstoque(itemVenda);
		itemVendaRepositorio.deleteAll(itemVenda);
		vendaRepositorio.deleteById(codigoVenda);
	}

	private void validarProdutoExisente_e_DevolverEstoque(List<ItemVenda> itemVenda) {
		itemVenda.forEach(item -> {
			Produto produto = produtoServico.validarProdutoExistente(item.getProduto().getCodigo());
			produto.setQuantidade(produto.getQuantidade() + item.getQuantidade());
			produtoServico.atualizarQuantidadeEmEstoque(produto);

		});
	}

	
	
	private Venda salvarVenda(Cliente cliente, VendaRequestDTO vendaDTO) {

		Venda venda = new Venda(vendaDTO.getData(), cliente);
		Venda vendaSalva = vendaRepositorio.save(venda);

		vendaDTO.getItemVendaDTO().stream().map(itemVendaDTO -> criarItemVenda(itemVendaDTO, vendaSalva))
				.forEach(itemVendaRepositorio::save);

		return vendaSalva;

	}
	
	private Venda atualizarVenda(Long codigoVenda,Cliente cliente, VendaRequestDTO vendaDTO) {

		Venda venda = new Venda(codigoVenda, vendaDTO.getData(), cliente);
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
			produtoServico.atualizarQuantidadeEmEstoque(produto);
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
