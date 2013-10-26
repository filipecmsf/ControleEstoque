package com.android.controleestoque.adapter;

public class ListaItem {
	private String nome;
	private String tipo;
	private Integer minimo;
	private Integer maximo;
	private Long atual;

	public ListaItem(String nome, Integer min, Integer max, Long valorAtual) {
		this.nome = nome;
		this.minimo = min;
		this.atual = valorAtual;
		this.maximo = max;
		this.tipo = null;
	}

	public ListaItem(String nome) {
		this.nome = nome;
		this.minimo = null;
		this.atual = null;
		this.maximo = null;
		this.tipo = null;
	}

	public ListaItem(String nome, String tipo, Long valor) {
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

	public Long getAtual() {
		return atual;
	}

	public void setAtual(Long atual) {
		this.atual = atual;
	}

}
