package br.alg.gifoodapi.core.jackson;

import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.module.SimpleModule;

import br.alg.gifoodapi.domain.model.Cidade;
import br.alg.gifoodapi.domain.model.Cozinha;
import br.alg.gifoodapi.domain.model.mixin.CidadeMixin;
import br.alg.gifoodapi.domain.model.mixin.CozinhaMixin;

@Component
public class JacksonMixinModule extends SimpleModule {

	private static final long serialVersionUID = 1L;

	public JacksonMixinModule() {
		setMixInAnnotation(Cidade.class, CidadeMixin.class);
		setMixInAnnotation(Cozinha.class, CozinhaMixin.class);
	}
	
}
