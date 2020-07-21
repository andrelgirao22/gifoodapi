package br.alg.gifoodapi.domain.exception;

public class CozinhaNaoEncontradaException extends HandleEntidadeNaoEncontradaException {

	private static final long serialVersionUID = 1L;
	private static final String MSG_COZINHA_NAO_ENCONTRADA = "Cozinha de código %s não encontrada";
	
	public CozinhaNaoEncontradaException(String msg) {
		super(msg);
	}
	
	public CozinhaNaoEncontradaException(Long id) {
		super(String.format(MSG_COZINHA_NAO_ENCONTRADA, id));
	}
}
