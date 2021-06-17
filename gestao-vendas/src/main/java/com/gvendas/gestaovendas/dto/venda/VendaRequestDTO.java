package com.gvendas.gestaovendas.dto.venda;

import java.time.LocalDate;
import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel("VendaRequestDTO")
public class VendaRequestDTO {

	@ApiModelProperty(value = "Data")
	@NotNull(message="Data")
	private LocalDate data;

	@ApiModelProperty(value = "Itens da venda")
	@NotNull(message="Itens venda")
	@Valid
	private List<ItemVendaRequestDTO> itemVendaDTO;

	public LocalDate getData() {
		return data;
	}

	public void setData(LocalDate data) {
		this.data = data;
	}

	public List<ItemVendaRequestDTO> getItemVendaDTO() {
		return itemVendaDTO;
	}

	public void setItemVendaDTO(List<ItemVendaRequestDTO> itemVendaDTO) {
		this.itemVendaDTO = itemVendaDTO;
	}

}
