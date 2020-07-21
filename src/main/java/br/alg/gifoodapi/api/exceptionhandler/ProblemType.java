package br.alg.gifoodapi.api.exceptionhandler;

import lombok.Getter;

@Getter
public enum ProblemType {

	MENSAGEM_IMCOMPREENSIVEL("/mensagem-incompreensivel","Mensagem Incompreensível"),
	RECURSO_NAO_ENCONTRADO("/recurso-nao-encontrado","Recurso não encontrado"),
	ENTIDADE_EM_USO("/entidade-em-uso", "Entidade em uso"),
	ERRO_NEGOCIO("/erro-negocio", "Violação de regra de negócio"), 
	PARAMETRO_INVALIDO("/parametro-invalido", "Parâmetro inválido"),	
	ERRO_DE_SISTEMA("/erro-de-sistema", "Erro de sistema"),
	DADOS_INVALIDOS("/dados-invalidos", "Dados inválidos");
	
	private String uri;
	private String title;
	
	private ProblemType(String uri ,String title) {
		this.title = title;
		this.uri  = "http://algafood.com.br/" + uri;
	}
	
}
