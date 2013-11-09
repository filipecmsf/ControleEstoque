package com.android.controleestoque.activity;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.ListView;
import android.widget.TextView;

import com.android.controleestoque.R;
import com.android.controleestoque.adapter.ItemListaMovimentacao;
import com.android.controleestoque.adapter.ItemListaMovimentacaoAdapter;
import com.android.controleestoque.db.DatabaseOpenHelper;

public class DadosProdutoActivity extends ActionBarActivity{

	private DatabaseOpenHelper db;
    private ArrayList<ItemListaMovimentacao> itens;
    private String id_produto;
    
    private TextView tv_nome;
    private TextView tv_minimo;
    private TextView tv_atual;
    private TextView tv_maximo;
    private LinearLayout ll_lista;
    private JSONArray jsonarray;
	
	public void onCreate(Bundle bundle){
		super.onCreate(bundle);
		setContentView(R.layout.dados_produto);
		
		db = new DatabaseOpenHelper(getApplicationContext());
		ll_lista = (LinearLayout) findViewById(R.id.ll_lista);
		tv_atual = (TextView) findViewById(R.id.tv_atual_dados);
		tv_nome = (TextView) findViewById(R.id.tv_nome_dados);
		tv_minimo = (TextView) findViewById(R.id.tv_minimo_dados);
		tv_maximo = (TextView) findViewById(R.id.tv_maximo_dados);
		
		Intent it = getIntent();
		if(it != null){
			Bundle bun = it.getExtras();
			if(bun != null){
				id_produto = bun.getString("id");
			}
		}
		
		criaLista();
		
		carregaInfoProduto();
	}
	
	private void carregaInfoProduto(){
		JSONObject json = db.selectDadosProduto(id_produto);
		Float valorAtual = 0F;
		
		try {
			for(int j = 0; j < jsonarray.length(); j++){
				JSONObject jsonMov = jsonarray.getJSONObject(j);
				if(jsonMov.getLong("ID_PRODUTO") == json.getLong("ID")){
					Integer tipo = jsonMov.getInt("TIPO");
					 if( tipo == 1 ){
						 valorAtual = valorAtual + Float.valueOf(jsonMov.getString("QUANT"));  
					 }
					 else{
						 valorAtual = valorAtual - Float.valueOf(jsonMov.getString("QUANT"));
					 }
						 
				}
			}
		
			tv_atual.setText(valorAtual.toString());
			tv_nome.setText(json.getString("NOME"));
			tv_minimo.setText(json.getString("MIN_QUANT"));
			tv_maximo.setText(json.getString("MAX_QUANT"));
		} catch (JSONException e) {
			e.printStackTrace();
		}
		
	}
	
	private void criaLista(){
		ListView list = (ListView) findViewById(R.id.lv_movimentacao_dados);
		itens = new ArrayList<ItemListaMovimentacao>();

		recuperaItensLista();
		
		ItemListaMovimentacaoAdapter adapter = new ItemListaMovimentacaoAdapter(this, itens);
		list.setAdapter(adapter);
	}
	
	private void recuperaItensLista(){
		jsonarray = db.selectMovimentacaoProduto(id_produto);
		
		Log.d("linearLayout","antes" + Integer.valueOf(ll_lista.getHeight()).toString());
		
		LayoutParams params = (LayoutParams) ll_lista.getLayoutParams();
		params.height = 30 + (145 * jsonarray.length());
		
		Log.d("linearLayout","depois" + Integer.valueOf(ll_lista.getHeight()).toString());
		for(int i = 0; i < jsonarray.length();i++){
			try {
				JSONObject json = jsonarray.getJSONObject(i);
				itens.add(new ItemListaMovimentacao(json.getString("NOME"),Integer.valueOf(json.getInt("QUANT")), (json.getInt("TIPO") == 1? "Entrada" : "Saída"),Float.valueOf(json.getString("VALOR")),json.getString("DATA_REGISTRO")));
			} catch (JSONException e) {
				e.printStackTrace();
			}
		}
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.menu_dados, menu);
		return super.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean onPrepareOptionsMenu(Menu menu) {
		return super.onPrepareOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		
		// Handle action buttons
		switch (item.getItemId()) {
		case R.id.add_menu:
			Intent it = new Intent(this, DetalheMovimentacaoActivity.class);
			startActivity(it);
        	break;
        	
		case R.id.cancel_menu:
        	Log.d("item","chamou o cancel_menu");
        	finish();
        	break;
		}
		return super.onOptionsItemSelected(item);
	}
	
}
