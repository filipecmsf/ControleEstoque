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
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.controleestoque.R;
import com.android.controleestoque.VO.MovimentacaoVO;
import com.android.controleestoque.db.DatabaseOpenHelper;

public class DetalheMovimentacaoActivity extends ActionBarActivity {

	private EditText valor;
	private Spinner sp_produto;
	private RadioButton entrada;
	private DatabaseOpenHelper db;

	private ArrayList<String> listaProdutos;
	private JSONArray jsonarray;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.detalhes_movimentacao);

		valor = (EditText) findViewById(R.id.et_valor_mov);
		sp_produto = (Spinner) findViewById(R.id.sp_produto_mov);
		entrada = (RadioButton) findViewById(R.id.radio_entrada);

		db = new DatabaseOpenHelper(getApplicationContext());

		populaSpinnerProduto();

	}

	private void populaSpinnerProduto() {
		listaProdutos = new ArrayList<String>();

		jsonarray = db.selectProduto();
		for (int i = 0; i < jsonarray.length(); i++) {
			try {
				JSONObject json = jsonarray.getJSONObject(i);
				listaProdutos.add(json.getString("NOME"));
			} catch (JSONException e) {
				e.printStackTrace();
			}
		}

		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
				android.R.layout.simple_spinner_item, listaProdutos);
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		sp_produto.setAdapter(adapter);

	}

	public void cadastrar(View v) {

		String texto = "";
		
		MovimentacaoVO movimentacaoVO = new MovimentacaoVO();
		// VALOR
		movimentacaoVO.setValor(Float.valueOf(valor.getText().toString()));

		// TIPO
		if (entrada.isChecked()) {
			movimentacaoVO.setTipo(1);
		} else {
			movimentacaoVO.setTipo(0);
		}

		// PRODUTO
		for (int i = 0; i < jsonarray.length(); i++) {
			JSONObject json;
			try {
				json = jsonarray.getJSONObject(i);
				if (sp_produto.getSelectedItem().toString() == json
						.getString("NOME")) {
					movimentacaoVO.setProduto(Integer.valueOf(json.getInt("ID")));
				}
			} catch (JSONException e) {
				e.printStackTrace();
			}
		}
		long result = db.insertMovimentacao(movimentacaoVO);
		if (result > 0){
			texto = "Movimentação cadastrada com sucesso !";
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
