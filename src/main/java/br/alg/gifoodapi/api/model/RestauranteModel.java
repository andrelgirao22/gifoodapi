package br.alg.gifoodapi.api.model;

import java.math.BigDecimal;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class RestauranteModel {

	private Long id;
	private String nome;
	private BigDecimal frete;
	private CozinhaModel cozinha;
	
	private String nomeCozinha;
}
