package com.android.controleestoque.adapter;

import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.android.controleestoque.R;

public class ItemListaCategoriaAdapter extends BaseAdapter {

	private LayoutInflater mInflater;
	private ArrayList<ItemListaCategoria> itens;

	public ItemListaCategoriaAdapter(Context context,ArrayList<ItemListaCategoria> itens) {
		this.itens = itens;
		mInflater = LayoutInflater.from(context);
	}

	@Override
	public int getCount() {
		return itens.size();
	}

	@Override
	public ItemListaCategoria getItem(int position) {
		return itens.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {

		//Pega o item de acordo com a posção.
		ItemListaCategoria item = itens.get(position);

		//infla o layout para podermos preencher os dados
        convertView = mInflater.inflate(R.layout.item_lista_categoria, null);

        //atravez do layout pego pelo LayoutInflater, pegamos cada id relacionado
        //ao item e definimos as informações.
        String nome = item.getNome();
        String descricao = item.getDescricao();
        
        ((TextView) convertView.findViewById(R.id.nome_item_cat)).setText(nome);
        ((TextView) convertView.findViewById(R.id.descricao_cat)).setText(descricao);
        
        return convertView;
	}

}
