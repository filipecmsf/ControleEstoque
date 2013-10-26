package com.android.controleestoque.activity;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.android.controleestoque.R;
import com.android.controleestoque.VO.CategoriaVO;
import com.android.controleestoque.db.DatabaseOpenHelper;

public class DetalheCategoriaActivity extends ActionBarActivity {

	private DatabaseOpenHelper db;
	private EditText tvNome;
	private EditText tvDescricao;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.detalhe_categoria);

		tvNome = (EditText) findViewById(R.id.tv_nome_categoria);
		tvDescricao = (EditText) findViewById(R.id.tv_descricao_categoria);
		
		db = new DatabaseOpenHelper(getApplicationContext());
//		db.selectCategorias();
	}
	
	public void adicionar(View v){
		Log.d("cadastro", "entrou");
		CategoriaVO categoriaVO = new CategoriaVO();
		
		categoriaVO.setDescricao(tvDescricao.getText().toString());
		categoriaVO.setNome(tvNome.getText().toString());
		String texto = "";
		long  result = db.insertCategoria(categoriaVO);
		if (result > 0){
			texto = "Categoria cadastrada com sucesso !";
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
