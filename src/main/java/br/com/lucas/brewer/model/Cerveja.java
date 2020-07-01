package br.com.lucas.brewer.model;

import static javax.persistence.EnumType.STRING;
import static javax.persistence.GenerationType.IDENTITY;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Max;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import br.com.lucas.brewer.model.enums.Origem;
import br.com.lucas.brewer.model.enums.Sabor;
import br.com.validation.SKUValidation;

@Entity
@Table(name = "cerveja")
public class Cerveja implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = IDENTITY)
	private Long id;

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

	@Column(name = "teor_alcoolico")
	@NotNull(message = "O teor alcóolico é obrigatório")
	@DecimalMax(value = "100.0", message = "O valor do teor alcóolico deve ser menor que 100")
	private BigDecimal teorAlcoolico;

	@NotNull(message = "A comissão é obrigatóira")
	@DecimalMax(value = "100.0", message = "A comissão deve ser igual ou menor que 100")
	private BigDecimal comissao;

	@NotNull(message = "A quantidade em estoque é obrigatória")
	@Max(value = 9999, message = "A quantidade em estoque deve ser menor que 9.999")
	@Column(name = "quantidade_estoque")
	private Integer quantidadeEstoque;

	@NotNull(message = "A origem é obrigatória")
	@Enumerated(STRING)
	private Origem origem;

	@NotNull(message = "O sabor é obrigatório")
	@Enumerated(STRING)
	private Sabor sabor;

	@NotNull(message = "O estilo é obrigatório")
	@ManyToOne
	@JoinColumn(name = "id_estilo")
	private Estilo estilo;

	private String foto;

	@Column(name = "content_type")
	private String contentType;
	
	public Cerveja() {}

	public Cerveja(long id, String sku, String nome, String descricao, BigDecimal valor,
			BigDecimal teorAlcoolico, BigDecimal comissao, int quantidadeEstoque, String origem, String sabor, Estilo estilo,
			String foto, String contentType) {
				this.id = id;
				this.sku = sku;
				this.nome = nome;
				this.descricao = descricao;
				this.valor = valor;
				this.teorAlcoolico = teorAlcoolico;
				this.comissao = comissao;
				this.quantidadeEstoque = quantidadeEstoque;
				this.origem = Origem.valueOf(origem.toUpperCase());
				this.sabor = Sabor.valueOf(sabor.toUpperCase());
				this.estilo = estilo;
				this.foto = foto;
				this.contentType = contentType;
	}
	
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

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
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

	public Origem getOrigem() {
		return origem;
	}

	public void setOrigem(Origem origem) {
		this.origem = origem;
	}

	public Sabor getSabor() {
		return sabor;
	}

	public void setSabor(Sabor sabor) {
		this.sabor = sabor;
	}

	public Estilo getEstilo() {
		return estilo;
	}

	public void setEstilo(Estilo estilo) {
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

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Cerveja other = (Cerveja) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

}
