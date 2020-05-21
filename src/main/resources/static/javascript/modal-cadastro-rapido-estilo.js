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
	
	EstiloCadastroRapido.prototype.iniciar = function () {
		this.form.on('submit', function(e) {
			e.preventDefault();
		});
		this.modal.on('shown.bs.modal', onModalShow.bind(this));
		this.modal.on('hide.bs.modal', onModalHide.bind(this));
		this.btnSalvar.on('click', onBtnSalvarClick.bind(this));
	}
	
	function onModalShow() {
		this.inputNomeEstilo.focus();
	}
	
	function onModalHide() {
		this.inputNomeEstilo.val('');
		this.msgErro.html('');
		this.msgErro.addClass('hidden');
		this.form.find('.form-group').removeClass('has-error');
	}
	
	function onBtnSalvarClick() {
		var nomeEstilo = this.inputNomeEstilo.val().trim();

		$.ajax({
			url : this.url,
			method : 'POST',
			contentType : 'application/json',
			data : JSON.stringify({
				'nome' : nomeEstilo
			}),
			error : onErrorSalvandoEstilo.bind(this),
			success : onSuccessSalvandoEstilo.bind(this)
		});
	}

	function onErrorSalvandoEstilo(obj) {
		this.msgErro.removeClass('hidden');
		this.msgErro.html('<span>' + obj.responseText + '</span>');
		this.form.find('.form-group').addClass('has-error');
	}

	function onSuccessSalvandoEstilo(estilo) {
		var selectEstilo = $('#estilo');
		selectEstilo.append('<option value="' + estilo.id + '" >' + estilo.nome + '</option>');
		selectEstilo.val(estilo.id);
		this.modal.modal('hide');
	}
	
	return EstiloCadastroRapido;
	
})();

$(function() {

	var estiloCadastroRapido = new Brewer.EstiloCadastroRapido();
	estiloCadastroRapido.iniciar();

});