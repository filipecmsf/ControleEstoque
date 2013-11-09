package com.android.controleestoque.adapter;

public class ItemListaCategoria {
	private String nome;
	private String descricao;
	
	public ItemListaCategoria(String nome, String descricao) {
		this.setNome(nome);
		this.setDescricao(descricao);
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}
	
}
