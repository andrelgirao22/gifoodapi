package br.alg.gifoodapi.domain.exception;

public abstract class HandleEntidadeNaoEncontradaException extends NegocioException {

	private static final long serialVersionUID = 1L;

	
	public HandleEntidadeNaoEncontradaException(String msg) {
		super(msg);
	}
}
