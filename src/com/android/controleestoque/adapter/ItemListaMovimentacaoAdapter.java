package com.android.controleestoque.adapter;

import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.android.controleestoque.R;

public class ItemListaMovimentacaoAdapter extends BaseAdapter {

	private LayoutInflater mInflater;
	private ArrayList<ItemListaMovimentacao> itens;

	public ItemListaMovimentacaoAdapter(Context context,ArrayList<ItemListaMovimentacao> itens) {
		this.itens = itens;
		mInflater = LayoutInflater.from(context);
	}

	@Override
	public int getCount() {
		return itens.size();
	}

	@Override
	public ItemListaMovimentacao getItem(int position) {
		return itens.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {

		//Pega o item de acordo com a posção.
    	ItemListaMovimentacao item = itens.get(position);
        //infla o layout para podermos preencher os dados
        convertView = mInflater.inflate(R.layout.item_lista_movimentacao, null);

        //atravez do layout pego pelo LayoutInflater, pegamos cada id relacionado
        //ao item e definimos as informações.
        String nome  = item.getNome();
        String tipo  = item.getTipo();
        String data  = item.getData();
        Integer quant= item.getQuant();
//        Float valor  = item.getValor();  
        
        ((TextView) convertView.findViewById(R.id.nome_item_mov)).setText(nome);
        ((TextView) convertView.findViewById(R.id.tv_tipo_mov)).setText(tipo);
        ((TextView) convertView.findViewById(R.id.tv_quant_mov)).setText(quant.toString());
        ((TextView) convertView.findViewById(R.id.tv_data_mov)).setText(data);
//        ((TextView) convertView.findViewById(R.id.tv_valor_mov)).setText(valor.toString());
        
        return convertView;
	}

}
