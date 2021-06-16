package com.gvendas.gestaovendas.servico;

import java.util.List;
import java.util.stream.Collectors;

import com.gvendas.gestaovendas.dto.venda.ItemVendaResponseDTO;
import com.gvendas.gestaovendas.dto.venda.VendaResponseDTO;
import com.gvendas.gestaovendas.entidades.ItemVenda;
import com.gvendas.gestaovendas.entidades.Venda;

public abstract class AbstractVendaServico {
	
	protected VendaResponseDTO criacaoVendaReponseDTO(Venda venda, List<ItemVenda> itemVendaList) {
		List<ItemVendaResponseDTO> itemVendaResponseDTO = itemVendaList
				.stream().map(this::criacaoItemVendaResponseDTO).collect(Collectors.toList());

		return new VendaResponseDTO(venda.getCodigo(), venda.getData(), itemVendaResponseDTO);

	}

	protected ItemVendaResponseDTO criacaoItemVendaResponseDTO(ItemVenda itemVenda) {
		return new ItemVendaResponseDTO(itemVenda.getCodigo(), itemVenda.getQuantidade(), itemVenda.getPrecoVendido(),
				itemVenda.getProduto().getCodigo(), itemVenda.getProduto().getDescricao());
	}
}
