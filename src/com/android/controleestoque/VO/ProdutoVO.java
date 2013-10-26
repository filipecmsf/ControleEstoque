package com.android.controleestoque.VO;

public class ProdutoVO {
	private Integer idProduto;
	private Integer idCategoria;
	private Integer numProduto;
	private Integer minQuant;
	private Integer maxQuant;
	private String nome;
	
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public Integer getIdProduto() {
		return idProduto;
	}
	public void setIdProduto(Integer idProduto) {
		this.idProduto = idProduto;
	}
	public Integer getIdCategoria() {
		return idCategoria;
	}
	public void setIdCategoria(Integer idCategoria) {
		this.idCategoria = idCategoria;
	}
	public Integer getNumProduto() {
		return numProduto;
	}
	public void setNumProduto(Integer numProduto) {
		this.numProduto = numProduto;
	}
	public Integer getMinQuant() {
		return minQuant;
	}
	public void setMinQuant(Integer minQuant) {
		this.minQuant = minQuant;
	}
	public Integer getMaxQuant() {
		return maxQuant;
	}
	public void setMaxQuant(Integer maxQuant) {
		this.maxQuant = maxQuant;
	}
	
	
}
