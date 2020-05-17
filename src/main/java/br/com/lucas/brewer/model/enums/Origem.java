package br.com.lucas.brewer.model.enums;

public enum Origem {

	NACIONAL("Nacional"), 
	INTERNACIONAL("Internacional");

	private String descricao;

	Origem(String descricao) {
		this.setDescricao(descricao);
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

}
