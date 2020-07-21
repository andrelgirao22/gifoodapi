package br.alg.gifoodapi.domain.exception;

public class RestauranteNaoEncontradaException extends HandleEntidadeNaoEncontradaException {

	private static final long serialVersionUID = 1L;
	private static final String MSG_ENTIDADE_NAO_ENCONTRADA = "Não existe um restaurante com o código %d";
	
	public RestauranteNaoEncontradaException(String msg) {
		super(msg);
	}
	
	public RestauranteNaoEncontradaException(Long id) {
		super(String.format(MSG_ENTIDADE_NAO_ENCONTRADA, id));
	}
}
