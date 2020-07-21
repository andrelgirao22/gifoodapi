package br.alg.gifoodapi.api.model.input;

import java.math.BigDecimal;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;

import br.alg.gifoodapi.domain.model.Restaurante;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RestauranteInput {

	@NotBlank
	private String nome;
	
	
	@PositiveOrZero(message = "{TaxaFrete.invalida}")
	private BigDecimal taxaFrete;
	
	@Valid
	@NotNull
	private CozinhaInput cozinha;
	
	public RestauranteInput() {
	}
	
	public RestauranteInput(Restaurante restaurante) {
		setNome(restaurante.getNome());
		setTaxaFrete(restaurante.getTaxaFrete());
		setCozinha(new CozinhaInput(restaurante.getCozinha()));
		
	}
	
}
