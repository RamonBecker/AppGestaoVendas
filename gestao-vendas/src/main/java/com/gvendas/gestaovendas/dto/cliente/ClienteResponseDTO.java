package com.gvendas.gestaovendas.dto.cliente;

import com.gvendas.gestaovendas.entidades.Cliente;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel("ClienteResponseDTO")
public class ClienteResponseDTO {

	@ApiModelProperty(value = "Código")
	private Long codigo;

	@ApiModelProperty(value = "Nome")
	private String nome;

	@ApiModelProperty(value = "Telefone")
	private String telefone;

	@ApiModelProperty(value = "Ativo")
	private boolean ativo;

	private EnderecoResponseDTO enderecoResponseDTO;

	public ClienteResponseDTO(Long codigo, String nome, String telefone, boolean ativo, EnderecoResponseDTO enderecoResponseDTO) {
		this.codigo = codigo;
		this.nome = nome;
		this.telefone = telefone;
		this.ativo = ativo;
		this.enderecoResponseDTO = enderecoResponseDTO;
	}

	public static ClienteResponseDTO conversaoParaClienteResponseDTO(Cliente cliente) {
		EnderecoResponseDTO enderecoResponseDTO = new EnderecoResponseDTO(cliente.getEndereco().getLogradouro(),
				cliente.getEndereco().getNumero(), cliente.getEndereco().getComplemento(),
				cliente.getEndereco().getBairro(), cliente.getEndereco().getCep(), cliente.getEndereco().getCidade(),
				cliente.getEndereco().getEstado());
		
		return new ClienteResponseDTO(cliente.getCodigo(), cliente.getNome(), cliente.getTelefone(), cliente.isAtivo(),
				enderecoResponseDTO);
	}

	public EnderecoResponseDTO getEnderecoResponseDTO() {
		return enderecoResponseDTO;
	}

	public void setEnderecoResponseDTO(EnderecoResponseDTO enderecoResponseDTO) {
		this.enderecoResponseDTO = enderecoResponseDTO;
	}

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

}
