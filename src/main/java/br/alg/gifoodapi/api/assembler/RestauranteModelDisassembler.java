package br.alg.gifoodapi.api.assembler;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.alg.gifoodapi.api.model.input.RestauranteInput;
import br.alg.gifoodapi.domain.model.Restaurante;

@Component
public class RestauranteModelDisassembler {
	
	@Autowired
	private ModelMapper modelMapper;

	public Restaurante toDomainObject(RestauranteInput restauranteInput) {
		return modelMapper.map(restauranteInput, Restaurante.class);
	}
	
}
