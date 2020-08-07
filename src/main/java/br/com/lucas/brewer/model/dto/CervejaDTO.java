package br.com.lucas.brewer.model.dto;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Max;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.springframework.util.StringUtils;

import br.com.lucas.brewer.model.Cerveja;
import br.com.lucas.brewer.model.Estilo;
import br.com.lucas.brewer.model.enums.Origem;
import br.com.lucas.brewer.model.enums.Sabor;
import br.com.validation.SKUValidation;

public class CervejaDTO implements Serializable {
	private static final long serialVersionUID = 1L;

	@SKUValidation
	@NotBlank(message = "O SKU é obrigatório")
	private String sku;

	@NotBlank(message = "O nome é obrigatório")
	private String nome;

	@Size(max = 50, message = "Descrição deve conter até 50 caracteres")
	private String descricao;

	@DecimalMin(value = "0.01")
	@DecimalMax(value = "9999999.99", message = "O valor da cerveja deve ser menor que R$9.999.999,99")
	@NotNull(message = "O valor é obrigatório")
	private BigDecimal valor;

	@NotNull(message = "O teor alcóolico é obrigatório")
	@DecimalMax(value = "100.0", message = "O valor do teor alcóolico deve ser menor que 100")
	private BigDecimal teorAlcoolico;

	@NotNull(message = "A comissão é obrigatóira")
	@DecimalMax(value = "100.0", message = "A comissão deve ser igual ou menor que 100")
	private BigDecimal comissao;

	@NotNull(message = "A quantidade em estoque é obrigatória")
	@Max(value = 9999, message = "A quantidade em estoque deve ser menor que 9.999")
	private Integer quantidadeEstoque;

	@NotNull(message = "A origem é obrigatória")
	private Integer origem;

	@NotNull(message = "O sabor é obrigatório")
	private Integer sabor;

	@NotNull(message = "O estilo é obrigatório")
	private Long estilo;

	private String foto;

	private String contentType;
	
	public String getSku() {
		return sku;
	}

	public void setSku(String sku) {
		this.sku = sku.toUpperCase();
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
	public BigDecimal getValor() {
		return valor;
	}

	public void setValor(BigDecimal valor) {
		this.valor = valor;
	}

	public BigDecimal getTeorAlcoolico() {
		return teorAlcoolico;
	}

	public void setTeorAlcoolico(BigDecimal teorAlcoolico) {
		this.teorAlcoolico = teorAlcoolico;
	}

	public BigDecimal getComissao() {
		return comissao;
	}

	public void setComissao(BigDecimal comissao) {
		this.comissao = comissao;
	}

	public Integer getQuantidadeEstoque() {
		return quantidadeEstoque;
	}

	public void setQuantidadeEstoque(Integer quantidadeEstoque) {
		this.quantidadeEstoque = quantidadeEstoque;
	}

	public Integer getOrigem() {
		return origem;
	}

	public void setOrigem(Integer origem) {
		this.origem = origem;
	}

	public Integer getSabor() {
		return sabor;
	}

	public void setSabor(Integer sabor) {
		this.sabor = sabor;
	}

	public Long getEstilo() {
		return estilo;
	}

	public void setEstilo(Long estilo) {
		this.estilo = estilo;
	}

	public String getFoto() {
		return foto;
	}

	public void setFoto(String foto) {
		this.foto = foto;
	}

	public String getContentType() {
		return contentType;
	}

	public void setContentType(String contentType) {
		this.contentType = contentType;
	}
	
	public String getFotoOrMock() {
		return StringUtils.isEmpty(foto) ? "cerveja-mock.png" : foto;
	}

	public Cerveja fromDTOToEntity() {
		Estilo estil = new Estilo();
		estil.setId(this.estilo);
		return new Cerveja(this.sku, this.nome, this.descricao, this.valor, this.teorAlcoolico, comissao, quantidadeEstoque, Origem.descricao(this.origem), Sabor.descricao(this.sabor), estil, foto, contentType);
	}
}
