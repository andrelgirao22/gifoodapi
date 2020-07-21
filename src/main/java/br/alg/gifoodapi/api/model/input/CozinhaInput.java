package br.alg.gifoodapi.api.model.input;

import javax.validation.constraints.NotNull;

import br.alg.gifoodapi.domain.model.Cozinha;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class CozinhaInput {

	public CozinhaInput() {
	}
	
		
	public CozinhaInput(Cozinha cozinha) {
		setId(cozinha.getId());
	}

	@NotNull
	private Long id;
	
}
