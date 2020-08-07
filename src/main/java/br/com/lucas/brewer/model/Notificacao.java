//package br.com.lucas.brewer.model;
//
//import java.time.LocalDateTime;
//
//public class Notificacao {
//
//	private Long id;
//	private String mensagem;
//	private boolean lido;
//	private LocalDateTime data;
//	
//	
//	public Notificacao() {}
//	
//	public static Notificacao instanceOf() {
//		return new Notificacao().setMensagem("Nova venda realizada!").setLido(false).setData(LocalDateTime.now());
//	}
//
//	private Notificacao setData(LocalDateTime data) {
//		this.data = data;
//		return this;
//	}
//
//	private Notificacao setLido(boolean lido) {
//		this.lido = lido;
//		return this;
//	}
//
//	private Notificacao setMensagem(String mensagem) {
//		this.mensagem = mensagem;
//		return this;
//	}
//}
