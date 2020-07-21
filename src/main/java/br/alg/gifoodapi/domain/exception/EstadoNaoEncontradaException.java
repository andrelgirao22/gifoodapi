package br.alg.gifoodapi.domain.exception;

public class EstadoNaoEncontradaException extends HandleEntidadeNaoEncontradaException {

	private static final long serialVersionUID = 1L;
	private static final String MSG_ESTADO_NAO_ENCONTRADO = "Não existe um cadastro de Estado com o codigo %d";
	
	public EstadoNaoEncontradaException(String msg) {
		super(msg);
	}
	
	public EstadoNaoEncontradaException(Long id) {
		super(String.format(MSG_ESTADO_NAO_ENCONTRADO, id));
	}
}
