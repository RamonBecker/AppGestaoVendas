package com.gvendas.gestaovendas.dto.categoria;

import com.gvendas.gestaovendas.entidades.Categoria;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel("CategoriaRetornoDTO")
public class CategoriaResponseDTO {

	public CategoriaResponseDTO(Long codigo, String nome) {
		this.codigo = codigo;
		this.nome = nome;
	}

	public static CategoriaResponseDTO conversaoParaCategoriaDTO(Categoria categoria) {
		return new CategoriaResponseDTO(categoria.getCodigo(), categoria.getNome());
	}

	@ApiModelProperty(value = "Código")
	private Long codigo;

	@ApiModelProperty(value = "Nome")
	private String nome;

	public Long getCodigo() {
		return codigo;
	}

	public void setCodigo(Long codigo) {
		this.codigo = codigo;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

}
