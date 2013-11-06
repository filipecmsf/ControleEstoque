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
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.controleestoque.R;
import com.android.controleestoque.VO.MovimentacaoVO;
import com.android.controleestoque.db.DatabaseOpenHelper;

public class DetalheMovimentacaoActivity extends ActionBarActivity {

	private EditText etValor;
	private EditText etQuant;
	private Spinner spProduto;
	private RadioButton rbEntrada;
	private DatabaseOpenHelper db;

	private ArrayList<String> listaProdutos;
	private JSONArray jsonarray;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.detalhes_movimentacao);

		etQuant = (EditText) findViewById(R.id.et_quant_mov);
		etValor = (EditText) findViewById(R.id.et_valor_mov);
		spProduto = (Spinner) findViewById(R.id.sp_produto_mov);
		rbEntrada = (RadioButton) findViewById(R.id.radio_entrada);

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
		spProduto.setAdapter(adapter);

	}

	private void cadastrar() {

		String texto = "";
		Boolean completo = Boolean.TRUE;
		
		String valor = etValor.getText().toString();
		String quant = etQuant.getText().toString();
		
		if(valor.trim().isEmpty()){
			completo = Boolean.FALSE;
			texto = "Informe o valor";
		}
		
		if(quant.trim().isEmpty()){
			completo = Boolean.FALSE;
			texto = "Informe a quantidade";
		}
		
		if(completo){
			MovimentacaoVO movimentacaoVO = new MovimentacaoVO();
			
			movimentacaoVO.setValor(Float.valueOf(valor));
			movimentacaoVO.setQuantidade(Integer.valueOf(quant));
			
			//pega o id do produto pega o tipo de movimentacao
			if (rbEntrada.isChecked()) {
				movimentacaoVO.setTipo(1);
			} else {
				movimentacaoVO.setTipo(0);
			}
			
			//pega o id do produto
			for (int i = 0; i < jsonarray.length(); i++) {
				JSONObject json;
				try {
					json = jsonarray.getJSONObject(i);
					if (spProduto.getSelectedItem().toString() == json
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
