package br.com.lucas.brewer.model.enums;

public enum Origem {

	NACIONAL(1,"Nacional"), 
	INTERNACIONAL(2,"Internacional");

	private Integer valor;
	private String descricao;

	Origem(Integer valor, String descricao) {
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
		for (Origem origem : Origem.values()) {
			if(origem.getValor() == valor) {
				descricao = origem.getDescricao();
			}
		}
		return descricao;
	}
	
    public static Origem origem(int valor) {
        Origem origem = null;
        for (Origem o: Origem.values()) {
            if (o.getValor() == valor)
                origem = o;
        }
        return origem;
    }
}
