package com.android.controleestoque.adapter;

public class ItemListaProduto {
	private String nome;
	private String tipo;
	private Integer minimo;
	private Integer maximo;
	private Float atual;

	public ItemListaProduto(String nome, Integer min, Integer max, Float valorAtual) {
		this.nome = nome;
		this.minimo = min;
		this.atual = valorAtual;
		this.maximo = max;
		this.tipo = null;
	}

	public ItemListaProduto(String nome) {
		this.nome = nome;
		this.minimo = null;
		this.atual = null;
		this.maximo = null;
		this.tipo = null;
	}

	public ItemListaProduto(String nome, String tipo, Float valor) {
		this.nome = nome;
		this.atual = valor;
		this.tipo = tipo;
		this.maximo = null;
		this.minimo = null;
	}

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public Integer getMinimo() {
		return minimo;
	}

	public void setMinimo(Integer minimo) {
		this.minimo = minimo;
	}

	public Integer getMaximo() {
		return maximo;
	}

	public void setMaximo(Integer maximo) {
		this.maximo = maximo;
	}

	public Float getAtual() {
		return atual;
	}

	public void setAtual(Float atual) {
		this.atual = atual;
	}

}
