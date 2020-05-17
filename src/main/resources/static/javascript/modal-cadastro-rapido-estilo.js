"use strict";
$(function() {
	var modal = $("#modal-cadastro-rapido-estilo");
	var btnSalvar = modal.find(".js-modal-cadastro-estilo-btn-salvar");
	var form = modal.find('form');
	var url = form.attr('action');
	var inputNomeEstilo = $("#nome-estilo");
	var msgErro = $(".js-mensagem-cadastro-rapido-estilo");

	form.on('submit', function(e) {
		e.preventDefault();
	});

	modal.on('shown.bs.modal', onModalShow);
	modal.on('hide.bs.modal', onModalHide);
	btnSalvar.on('click', onBtnSalvarClick);

	function onModalShow() {
		inputNomeEstilo.focus();
	}

	function onModalHide() {
		inputNomeEstilo.val('');
		msgErro.html('');
		msgErro.addClass('hidden');
		form.find('.form-group').removeClass('has-error');
	}

	function onBtnSalvarClick() {
		var nomeEstilo = inputNomeEstilo.val().trim();

		$.ajax({
			url : url,
			method : 'POST',
			contentType : 'application/json',
			data : JSON.stringify({
				'nome' : nomeEstilo
			}),
			error : onErrorSalvandoEstilo,
			success : onSuccessSalvandoEstilo
		});
	}

	function onErrorSalvandoEstilo(obj) {
		msgErro.removeClass('hidden');
		msgErro.html('<span>' + obj.responseText + '</span>');
		form.find('.form-group').addClass('has-error');
	}

	function onSuccessSalvandoEstilo(estilo) {
		var selectEstilo = $('#estilo');
		selectEstilo.append('<option value="' + estilo.id + '" >' + estilo.nome
				+ '</option>');
		selectEstilo.val(estilo.id);
		modal.modal('hide');
	}
});