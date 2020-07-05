var Brewer = Brewer || {};

Brewer.UploadFoto = (function() {
	
	function UploadFoto() {			
		this.inputNomeFoto    = $('input[name=foto]');
		this.inputContentType = $('input[name=contentType]');
		this.divFotoCerveja   = $('#foto-cerveja').html();
		this.template 		  = Handlebars.compile(this.divFotoCerveja);
		this.uploadDrop       = $('#upload-drop');
		this.containerCerveja      = $('.js-container-foto-cerveja');
	}
	
	UploadFoto.prototype.iniciar = function(){
		var settings = {
			type: 'json',
			filelimit: 1,
			allow: '*.(jpg|jpeg|png)',
			url: this.containerCerveja.data('url-fotos'),
			complete: onUploadCompleto.bind(this)
		}	 
		
		UIkit.upload($('#upload-select'), settings);
		UIkit.upload(this.uploadDrop, settings);
		
		if (this.inputNomeFoto.val()){
			onUploadCompleto.call(this, {
				nome: this.inputNomeFoto.val(),
				contentType: this.inputContentType.val(),
			});
		}
	};
	
	function onUploadCompleto(json){
		if(json.response.nome == undefined && json.response.contentType == undefined){
			response = JSON.parse(json.response);
		}
		
		this.inputNomeFoto.val(response.nome);
		this.inputContentType.val(response.contentType);
		this.uploadDrop.addClass('hidden');
		
		var htmlFotoCerveja = this.template({
			nomeFoto: response.nome,
		});
		
		this.containerCerveja.append(htmlFotoCerveja);
		
		$('.js-remove-foto').on('click', onRemoverFoto.bind(this));
	}

	function onRemoverFoto(){
		$('.js-foto-cerveja').remove();
		this.uploadDrop.removeClass('hidden');
		$.ajax({
			url : 'http://localhost:8080/brewer/cervejas/cadastro/foto/remover', //temporario mudar link estatico futuramente
			method : 'DELETE',
			contentType : 'application/json',
			data : JSON.stringify({
				'nomeFotoJson' : this.inputNomeFoto.val(),
			}),
			error : onErrorRemovendoFoto.bind(this),
			success : onSuccessRemovendoFoto.bind(this)
		});
		this.inputNomeFoto.val('');
		this.inputContentType.val('');
	}
	
	function onErrorRemovendoFoto(){
		alert('Erro removendo foto');
	}
	
	function onSuccessRemovendoFoto(){
		alert('Success removendo foto');
	}
	
	return UploadFoto
	
})();

$(function(){
	var uploadFoto = new Brewer.UploadFoto();
	uploadFoto.iniciar();
});