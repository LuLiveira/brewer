"use strict";

var Brewer = Brewer || {};

Brewer.EstiloCadastroRapido = (function (){
	
	function EstiloCadastroRapido(){
		this.modal = $("#modal-cadastro-rapido-estilo");
		this.btnSalvar = this.modal.find(".js-modal-cadastro-estilo-btn-salvar");
		this.form = this.modal.find('form');
		this.url = this.form.attr('action');
		this.inputNomeEstilo = $("#nome-estilo");
		this.msgErro = $(".js-mensagem-cadastro-rapido-estilo");
	}

	function onModalShow() {
		this.inputNomeEstilo.focus();
	}
	
	EstiloCadastroRapido.prototype.iniciar = function () {
		this.form.on('submit', function(e) {
			e.preventDefault();
		});
		this.modal.on('shown.bs.modal', onModalShow);
//		this.modal.on('hide.bs.modal', onModalHide);
//		this.btnSalvar.on('click', onBtnSalvarClick);
	}
	
	return EstiloCadastroRapido;
	
})();

$(function() {

	var estiloCadastroRapido = new Brewer.EstiloCadastroRapido();
	estiloCadastroRapido.iniciar();

//	function onModalHide() {
//		inputNomeEstilo.val('');
//		msgErro.html('');
//		msgErro.addClass('hidden');
//		form.find('.form-group').removeClass('has-error');
//	}
//
//	function onBtnSalvarClick() {
//		var nomeEstilo = inputNomeEstilo.val().trim();
//
//		$.ajax({
//			url : url,
//			method : 'POST',
//			contentType : 'application/json',
//			data : JSON.stringify({
//				'nome' : nomeEstilo
//			}),
//			error : onErrorSalvandoEstilo,
//			success : onSuccessSalvandoEstilo
//		});
//	}
//
//	function onErrorSalvandoEstilo(obj) {
//		msgErro.removeClass('hidden');
//		msgErro.html('<span>' + obj.responseText + '</span>');
//		form.find('.form-group').addClass('has-error');
//	}
//
//	function onSuccessSalvandoEstilo(estilo) {
//		var selectEstilo = $('#estilo');
//		selectEstilo.append('<option value="' + estilo.id + '" >' + estilo.nome
//				+ '</option>');
//		selectEstilo.val(estilo.id);
//		modal.modal('hide');
//	}
});