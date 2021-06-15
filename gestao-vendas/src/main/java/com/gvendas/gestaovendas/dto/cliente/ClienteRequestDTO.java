package com.gvendas.gestaovendas.dto.cliente;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.Length;

import com.gvendas.gestaovendas.entidades.Cliente;
import com.gvendas.gestaovendas.entidades.Endereco;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel("ClienteRequestDTO")
public class ClienteRequestDTO {

	@ApiModelProperty(value = "Nome")
	@NotBlank(message = "Nome")
	@Length(min = 5, max = 50, message = "Nome")
	private String nome;

	@ApiModelProperty(value = "Telefone")
	@NotBlank(message = "Telefone")
	@Pattern(regexp = "\\([\\d]{2}\\)[\\d]{5}[- .][\\d]{4}", message = "Telefone")
	private String telefone;

	@ApiModelProperty(value = "Ativo")
	@NotNull(message = "Ativo")
	private boolean ativo;

	@ApiModelProperty(value = "Endereço")
	@NotNull(message = "Endereço")
	@Valid
	private EnderecoRequestDTO enderecoRequestDTO;

	public Cliente conversaoParEntidade() {
		Endereco endereco = new Endereco(enderecoRequestDTO.getLogradouro(), enderecoRequestDTO.getNumero(),
				enderecoRequestDTO.getComplemento(), enderecoRequestDTO.getBairro(), enderecoRequestDTO.getCep(),
				enderecoRequestDTO.getCidade(), enderecoRequestDTO.getEstado());

		return new Cliente(nome, telefone, ativo, endereco);
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getTelefone() {
		return telefone;
	}

	public void setTelefone(String telefone) {
		this.telefone = telefone;
	}

	public boolean isAtivo() {
		return ativo;
	}

	public void setAtivo(boolean ativo) {
		this.ativo = ativo;
	}

	public EnderecoRequestDTO getEnderecoRequestDTO() {
		return enderecoRequestDTO;
	}

	public void setEnderecoRequestDTO(EnderecoRequestDTO enderecoRequestDTO) {
		this.enderecoRequestDTO = enderecoRequestDTO;
	}

}
