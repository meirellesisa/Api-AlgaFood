package com.algaworks.algafood.api.exceptionhandler;

import lombok.Getter;

@Getter
public enum ProblemType {
	
	MENSAGEM_INCONPREENSIVEL("/mensagem_incompreensivel","Mensagem incompreensível"),
	RECURSO_NAO_ENCONTRADA("/recurso-nao-encontrada", "Recurso não encontrada" ),
	ERRO_NEGOCIO("/erro-negocio", "Violação de regra de negócio"),
	PARMETRO_INVALIDO("/parametro-invalido", "Parâmetro inválido"),
	ERRO_DE_SISTEMA("/erro-de-sistema", "Erro de sistema"),
	DADOS_INVALIDOS("/dados-invalidos", "Dados Inválidos"),
	ENTIDADE_EM_USO("/entidade_em_uso", "Entidade em uso");

	
	private String title;
	private String uri;
	
	private ProblemType(String path, String title) {
		this.uri = "https://algafood.com.br" + path;
		this.title=title;
	}

}
