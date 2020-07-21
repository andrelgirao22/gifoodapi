package br.alg.gifoodapi.domain.exception;

public class CidadeNaoEncontradaException extends HandleEntidadeNaoEncontradaException {

	private static final long serialVersionUID = 1L;
	private static final String MSG_CIDADE_NAO_ENCONTRADA = "Cidade de código %s não encontrada";
	
	public CidadeNaoEncontradaException(String msg) {
		super(msg);
	}
	
	public CidadeNaoEncontradaException(Long id) {
		super(String.format(MSG_CIDADE_NAO_ENCONTRADA, id));
	}
}
