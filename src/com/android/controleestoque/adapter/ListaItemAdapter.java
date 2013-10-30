package com.android.controleestoque.adapter;

import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.android.controleestoque.R;

public class ListaItemAdapter extends BaseAdapter {

	private LayoutInflater mInflater;
	private ArrayList<ListaItem> itens;

	public ListaItemAdapter(Context context,ArrayList<ListaItem> itens) {
		this.itens = itens;
		mInflater = LayoutInflater.from(context);
	}

	@Override
	public int getCount() {
		return itens.size();
	}

	@Override
	public ListaItem getItem(int position) {
		return itens.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {

		//Pega o item de acordo com a posção.
    	ListaItem item = itens.get(position);
        //infla o layout para podermos preencher os dados
        convertView = mInflater.inflate(R.layout.item_lista, null);

        //atravez do layout pego pelo LayoutInflater, pegamos cada id relacionado
        //ao item e definimos as informações.
        String nome = item.getNome();
        String tipo = item.getTipo();
        Integer min = item.getMinimo();
        Float atual = item.getAtual();
        Integer max = item.getMaximo();  
        
        ((TextView) convertView.findViewById(R.id.nome_item)).setText(nome);
        ((TextView) convertView.findViewById(R.id.min_quant)).setText(min == null? (tipo == null? "" : tipo.toString()) : min.toString());
        ((TextView) convertView.findViewById(R.id.max_quant)).setText(max == null? "" : max.toString());
        ((TextView) convertView.findViewById(R.id.atual_quant)).setText(atual == null? "" : atual.toString());
        
        return convertView;
	}

}
