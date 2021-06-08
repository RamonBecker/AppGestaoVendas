package com.gvendas.gestaovendas.dto.produto;

import java.math.BigDecimal;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;

import com.gvendas.gestaovendas.entidades.Categoria;
import com.gvendas.gestaovendas.entidades.Produto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel("ProdutoRequestDTO")
public class ProdutoRequestDTO {

	public ProdutoRequestDTO(
			@NotBlank(message = "Descrição") @Length(min = 3, max = 100, message = "Descrição") String descricao,
			@NotNull(message = "Quantidade") Integer quantidade,
			@NotNull(message = "Preço custo") BigDecimal precoCusto,
			@NotNull(message = "Preço venda") BigDecimal precoVenda,
			@Length(max = 500, message = "Observação") String observacao) {
		super();
		this.descricao = descricao;
		this.quantidade = quantidade;
		this.precoCusto = precoCusto;
		this.precoVenda = precoVenda;
		this.observacao = observacao;
	}

	@ApiModelProperty(value = "Descrição")
	@NotBlank(message = "Descrição")
	// Colocando o minimo e o maximo de caracteres para serem inseridos
	@Length(min = 3, max = 100, message = "Descrição")
	private String descricao;

	@ApiModelProperty(value = "Quantidade")
	@NotNull(message = "Quantidade")
	private Integer quantidade;

	@ApiModelProperty(value = "Preço custo")
	@NotNull(message = "Preço custo")
	private BigDecimal precoCusto;

	@ApiModelProperty(value = "Preço venda")
	@NotNull(message = "Preço venda")
	private BigDecimal precoVenda;

	@ApiModelProperty(value = "Observação")
	@Length(max = 500, message = "Observação")
	private String observacao;

	public Produto conversaoParaEntidade(Long codigoCategoria) {
		return new Produto(descricao, quantidade, precoCusto, precoVenda, observacao, new Categoria(codigoCategoria));
	}

	public Produto conversaoParaEntidade(Long codigoCategoria, Long codigoProduto) {
		return new Produto(codigoProduto, descricao, quantidade, precoCusto, precoVenda, observacao,
				new Categoria(codigoCategoria));
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public Integer getQuantidade() {
		return quantidade;
	}

	public void setQuantidade(Integer quantidade) {
		this.quantidade = quantidade;
	}

	public BigDecimal getPrecoCusto() {
		return precoCusto;
	}

	public void setPrecoCusto(BigDecimal precoCusto) {
		this.precoCusto = precoCusto;
	}

	public BigDecimal getPrecoVenda() {
		return precoVenda;
	}

	public void setPrecoVenda(BigDecimal precoVenda) {
		this.precoVenda = precoVenda;
	}

	public String getObservacao() {
		return observacao;
	}

	public void setObservacao(String observacao) {
		this.observacao = observacao;
	}

}
