package com.gvendas.gestaovendas.dto.produto;

import java.math.BigDecimal;

import com.gvendas.gestaovendas.dto.categoria.CategoriaResponseDTO;

import io.swagger.annotations.ApiModelProperty;

public class ProdutoResponseDTO {

	@ApiModelProperty(value = "Código")
	private Long codigo;

	@ApiModelProperty(value = "Descrição")
	private String descricao;

	@ApiModelProperty(value = "Quantidade")
	private Integer quantidade;

	@ApiModelProperty(value = "Preço custo")
	private BigDecimal precoCusto;

	@ApiModelProperty(value = "Preço venda")
	private BigDecimal precoVenda;

	@ApiModelProperty(value = "Observação")
	private String observacao;

	@ApiModelProperty(value = "Categoria")
	private CategoriaResponseDTO categoria;
}
