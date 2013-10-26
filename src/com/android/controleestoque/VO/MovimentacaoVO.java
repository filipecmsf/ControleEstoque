package com.android.controleestoque.VO;

public class MovimentacaoVO {
	private Integer idProduto;
	private Integer idMovimento;
	private Integer quantidade;
	private Integer tipo;
	private Float valor;
	
	public Integer getIdMovimento() {
		return idMovimento;
	}
	public void setIdMovimento(Integer idMovimento) {
		this.idMovimento = idMovimento;
	}
	public Integer getQuantidade() {
		return quantidade;
	}
	public void setQuantidade(Integer quantidade) {
		this.quantidade = quantidade;
	}
	public Integer getTipo() {
		return tipo;
	}
	public void setTipo(Integer tipo) {
		this.tipo = tipo;
	}
	public Float getValor() {
		return valor;
	}
	public void setValor(Float valor) {
		this.valor = valor;
	}
	public Integer getProduto() {
		return idProduto;
	}
	public void setProduto(Integer produto) {
		this.idProduto = produto;
	}
	
}
