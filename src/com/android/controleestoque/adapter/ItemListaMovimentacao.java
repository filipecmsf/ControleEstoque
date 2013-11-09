package com.android.controleestoque.adapter;

public class ItemListaMovimentacao {
	private String nome;
	private String tipo;
	private String data;
	private Integer quant;
	private Float valor;

	public ItemListaMovimentacao(String nome, Integer quant, String tipo, Float valor, String data) {
		this.nome = nome;
		this.tipo = tipo;
		this.data = data;
		this.quant = quant;
		this.valor = valor;
	}

	public ItemListaMovimentacao() {
		this.nome = null;
		this.data = null;
		this.tipo = null;
		this.valor = null;
		this.quant = null;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	public String getData() {
		return data;
	}

	public void setData(String data) {
		this.data = data;
	}

	public Integer getQuant() {
		return quant;
	}

	public void setQuant(Integer quant) {
		this.quant = quant;
	}

	public Float getValor() {
		return valor;
	}

	public void setValor(Float valor) {
		this.valor = valor;
	}


}
