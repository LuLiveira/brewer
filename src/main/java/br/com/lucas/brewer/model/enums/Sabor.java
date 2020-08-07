package br.com.lucas.brewer.model.enums;

public enum Sabor {

	ADOCICADA(1, "Adocicada"), 
	AMARGA(2, "Amarga"), 
	FORTE(3, "Forte"), 
	FRUTADA(4, "Frutada"), 
	SUAVE(5, "Suave");

	private Integer valor;
	private String descricao;

	Sabor(Integer valor, String descricao) {
		this.valor = valor;
		this.descricao = descricao;

	}

	public Integer getValor() {
		return valor;
	}
	
	public String getDescricao() {
		return descricao;
	}
	
	public static String descricao(int valor) {
		String descricao = null;
		for (Sabor sabor : Sabor.values()) {
			if(sabor.getValor() == valor) {
				descricao = sabor.getDescricao();
			}
		}
		return descricao;
	}
	
	public static Sabor sabor(int valor) {
		Sabor sabor = null;
		for (Sabor s : Sabor.values()) {
			if(s.getValor() == valor) {
				sabor = s;
			}
		}
		return sabor;
	}
}
