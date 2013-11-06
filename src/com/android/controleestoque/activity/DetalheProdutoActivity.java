package com.android.controleestoque.activity;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.controleestoque.R;
import com.android.controleestoque.VO.ProdutoVO;
import com.android.controleestoque.db.DatabaseOpenHelper;

public class DetalheProdutoActivity extends ActionBarActivity {

	private ArrayList<String> listaCategoria;
	private Spinner sp_categoria;
	private EditText ed_min;
	private EditText ed_max;
	private EditText ed_nome;
	private EditText ed_num;
	private JSONArray jsonarray;

	private DatabaseOpenHelper db;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.detalhe_produto);

		sp_categoria = (Spinner) findViewById(R.id.spinner_categoria_produto);
		ed_nome = (EditText) findViewById(R.id.et_nome_produto);
		ed_max = (EditText) findViewById(R.id.et_max_produto);
		ed_min = (EditText) findViewById(R.id.et_min_produto);
		ed_num = (EditText) findViewById(R.id.et_num_produto);

		db = new DatabaseOpenHelper(getApplicationContext());

//		db.selectProduto();
		
		populaSpinnerCategoria();
	}

	private void populaSpinnerCategoria() {
		listaCategoria = new ArrayList<String>();

		jsonarray = db.selectCategorias();
		for (int i = 0; i < jsonarray.length(); i++) {
			try {
				JSONObject json = jsonarray.getJSONObject(i);
				listaCategoria.add(json.getString("NOME"));
			} catch (JSONException e) {
				e.printStackTrace();
			}
		}

		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
				android.R.layout.simple_spinner_item, listaCategoria);
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		sp_categoria.setAdapter(adapter);

	}

	private void cadastrar() {
		Boolean completo = Boolean.TRUE;
		String texto = "";
		String cod = ed_num.getText().toString();
		String nome = ed_nome.getText().toString();
		String max_s = ed_max.getText().toString();
		String min_s =ed_min.getText().toString();
		Log.d("cadastro", cod + " " + cod.length());
		
		if(cod.trim().isEmpty()){
			Log.d("cadastro","cod");
			texto = "Informe o codigo do produto";
			completo = Boolean.FALSE;
		}
		
		if(nome.trim().isEmpty()){
			Log.d("cadastro","nome");
			texto = "Informe o codigo do nome";
			completo = Boolean.FALSE;
		}
		
		if(max_s.trim().isEmpty()){
			Log.d("cadastro","max");
			texto = "Informe o valor maximo";
			completo = Boolean.FALSE;
		}
		
		if(min_s.trim().isEmpty()){
			Log.d("cadastro","min");
			texto = "Informe o valor minimo";
			completo = Boolean.FALSE;
		}
		
		if(completo && (Integer.valueOf(max_s) < Integer.valueOf(min_s))){
			Log.d("cadastro","max < min");
			texto = "maximo nao pode ser menor que o minimo";
			completo = Boolean.FALSE;
		}
		
		if(completo){
			ProdutoVO produtoVO = new ProdutoVO();

			produtoVO.setMaxQuant(Integer.valueOf(max_s));
			produtoVO.setMinQuant(Integer.valueOf(min_s));
			produtoVO.setCodProduto(cod);
			produtoVO.setNome(nome);
			for (int i = 0; i < jsonarray.length(); i++) {
				JSONObject json;
				try {
					json = jsonarray.getJSONObject(i);
					if (sp_categoria.getSelectedItem().toString() == json.getString("NOME")) {
						produtoVO.setIdCategoria(json.getInt("ID"));
					}
				} catch (JSONException e) {
					e.printStackTrace();
				}
			}
			long result = db.insertProduto(produtoVO); 
			if (result > 0){
				texto = "Produto cadastrado com sucesso !";
			}
			else{
				texto = "Ocorreu um erro.";
			}
		}
		Toast.makeText(this, texto,Toast.LENGTH_LONG).show();
		
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.menu_form, menu);
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
		case R.id.done_menu:
        	Log.d("item","chamou o done_menu");
        	cadastrar();
        	break;
        	
		case R.id.cancel_menu:
        	Log.d("item","chamou o cancel_menu");
        	finish();
        	break;
		}
		return super.onOptionsItemSelected(item);
	}
}
