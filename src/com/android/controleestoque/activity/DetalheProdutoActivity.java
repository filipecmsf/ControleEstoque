package com.android.controleestoque.activity;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
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

	public void cadastrar(View v) {
		String texto = "";
		
		ProdutoVO produtoVO = new ProdutoVO();
		produtoVO.setMaxQuant(Integer.valueOf(ed_max.getText().toString()));
		produtoVO.setMinQuant(Integer.valueOf(ed_min.getText().toString()));
		produtoVO.setNumProduto(Integer.valueOf(ed_num.getText().toString()));
		produtoVO.setNome(ed_nome.getText().toString());

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
		Toast.makeText(this, texto,Toast.LENGTH_LONG).show();
	}
	
	public void voltar(View v){
		finish();
	}
}
