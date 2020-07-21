package br.alg.gifoodapi.domain.model.mixin;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import br.alg.gifoodapi.domain.model.Estado;

public class CidadeMixin {

	@JsonIgnoreProperties(value = "nome", allowGetters = true)
	private Estado estado;
	
}
