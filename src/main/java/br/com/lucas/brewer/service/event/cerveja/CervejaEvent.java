package br.com.lucas.brewer.service.event.cerveja;

import br.com.lucas.brewer.model.Cerveja;

public class CervejaEvent {

	private Cerveja cerveja;

	public CervejaEvent(Cerveja cerveja) {
		this.cerveja = cerveja;
	}

	public Cerveja getCerveja() {
		return cerveja;
	}

	public void setCerveja(Cerveja cerveja) {
		this.cerveja = cerveja;
	}

}
