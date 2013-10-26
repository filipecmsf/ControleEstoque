package com.android.controleestoque.db;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.android.controleestoque.VO.CategoriaVO;
import com.android.controleestoque.VO.MovimentacaoVO;
import com.android.controleestoque.VO.ProdutoVO;

public class DatabaseOpenHelper extends SQLiteOpenHelper {

	public static final String DATABASE_NAME = "ControleEstoque";
	public static final int DATABASE_VERSION = 1;

	private final String TableProdutoNome = "PRODUTO";
	private final String TableMovimentacaoNome = "MOVIMENTACAO";
	private final String TableCategoriaNome = "CATEGORIA";
	private String[] sql = new String[3];
	

	public DatabaseOpenHelper(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		
		sql[0] = "CREATE TABLE " + TableProdutoNome
				+ "(ID INTEGER PRIMARY KEY" +
				", ID_CATEGORIA INTEGER" + 
				", NUM_PRODUTO INTEGER" + 
				", MAX_QUANT REAL" + 
				", MIN_QUANT REAL" + 
				", NOME TEXT" + 
				");";

		sql[1] = "CREATE TABLE " + TableMovimentacaoNome + 
				"(ID INTEGER PRIMARY KEY" + 
				", ID_PRODUTO INTEGER" + 
				", QUANT REAL" + 
				", TIPO INTEGER" + 
				", VALOR REAL" + 
				");";

		sql[2] = "CREATE TABLE " + TableCategoriaNome + 
				"(ID INTEGER PRIMARY KEY" + 
				", DESCRICAO TEXT" + 
				", NOME TEXT" + 
				");";

		for(int i = 0; i < sql.length; i++){
			db.execSQL(sql[i]);
		}
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		Log.w("Example",
				"Upgrading database, this will drop tables and recreate.");
		db.execSQL("DROP TABLE IF EXISTS " + TableCategoriaNome);
		db.execSQL("DROP TABLE IF EXISTS " + TableMovimentacaoNome);
		db.execSQL("DROP TABLE IF EXISTS " + TableProdutoNome);
		onCreate(db);
	}

	public long insertMovimentacao(MovimentacaoVO movimentacaoVO) {
		SQLiteDatabase db = getWritableDatabase();
		long resultado = 0;

		ContentValues values = new ContentValues();
		values.put("VALOR", movimentacaoVO.getValor());
		values.put("TIPO", movimentacaoVO.getTipo());
		values.put("QUANT", 0);
		values.put("ID_PRODUTO", movimentacaoVO.getProduto());

		resultado = db.insert(TableMovimentacaoNome, null, values);
		db.close();

		return resultado;
	}

	public long insertProduto(ProdutoVO produtoVO) {
		SQLiteDatabase db = getWritableDatabase();
		long resultado = 0;

		ContentValues values = new ContentValues();
		values.put("ID_CATEGORIA", produtoVO.getIdCategoria());
		values.put("MAX_QUANT", produtoVO.getMaxQuant());
		values.put("MIN_QUANT", produtoVO.getMinQuant());
		values.put("NOME", produtoVO.getNome());
		values.put("NUM_PRODUTO", produtoVO.getNumProduto());

		resultado = db.insert(TableProdutoNome, null, values);
		db.close();

		return resultado;
	}

	public long insertCategoria(CategoriaVO categoriaVO) {
		SQLiteDatabase db = getWritableDatabase();
		long resultado = 0;
		Log.d("database","entro no metodo de cadastro de categoria");
		ContentValues values = new ContentValues();
		values.put("NOME", categoriaVO.getNome());
		values.put("DESCRICAO", categoriaVO.getDescricao());

		Log.d("database","criou contentVales");
		
		resultado = db.insert(TableCategoriaNome, null, values);
		
		Log.d("database","inseriu o valor");
		
		db.close();

		return resultado;
	}

	public JSONArray selectCategorias() {
		JSONArray jsonArray = new JSONArray();
		String jsonString = new String();
		String selectQuery = "SELECT ID, NOME, DESCRICAO FROM " + TableCategoriaNome;

		SQLiteDatabase db = this.getWritableDatabase();
		Cursor cursor = db.rawQuery(selectQuery, null);

			if (cursor.moveToFirst()) {
				try {
					jsonString = "{\"categorias\":[";
					do {
						jsonString += "{";
						jsonString += "\"ID\":" + cursor.getString(0);
						jsonString += ",\"NOME\":\"" + cursor.getString(1) + "\"";
						jsonString += ",\"DESCRICAO\":\"" + cursor.getString(2) + "\"";
						jsonString += "}";
						if(!cursor.isLast()){
							jsonString += ",";
						}
					} while (cursor.moveToNext());
					jsonString += "]}";
					Log.d("database", "categoria: " + jsonString);
					
					jsonArray = new JSONArray(new JSONObject(jsonString).getString("categorias"));
				
				} catch (JSONException e) {
					e.printStackTrace();
				}
				
			}

		return jsonArray;
	}
	
	public JSONArray selectMovimentacao() {
		JSONArray jsonArray = new JSONArray();
		String jsonString = new String();
		String selectQuery = "SELECT MOV.ID, MOV.ID_PRODUTO, MOV.QUANT, MOV.TIPO, MOV.VALOR, PRO.NOME FROM " + TableMovimentacaoNome + " AS MOV " +
				"LEFT JOIN " + TableProdutoNome + " AS PRO ON PRO.ID = MOV.ID_PRODUTO";

		SQLiteDatabase db = this.getWritableDatabase();
		Cursor cursor = db.rawQuery(selectQuery, null);

			if (cursor.moveToFirst()) {
				try {
					jsonString = "{\"movimentacao\":[";
					do {
						jsonString += "{";
						jsonString += "\"ID\":" + cursor.getString(0);
						jsonString += ",\"ID_PRODUTO\":" + cursor.getString(1);
						jsonString += ",\"QUANT\":" + cursor.getString(2);
						jsonString += ",\"TIPO\":" + cursor.getString(3);
						jsonString += ",\"VALOR\":" + cursor.getString(4);
						jsonString += ",\"NOME\":\"" + cursor.getString(5) + "\"";
						jsonString += "}";
						if(!cursor.isLast()){
							jsonString += ",";
						}
						
					} while (cursor.moveToNext());
					jsonString += "]}";
					Log.d("database", "movimentacao: " + jsonString);

					jsonArray = new JSONArray(new JSONObject(jsonString).getString("movimentacao"));

				} catch (JSONException e) {
					e.printStackTrace();
				} finally{
					db.close();
				}
			}

		return jsonArray;
	}
	
	public JSONArray selectProduto() {
		JSONArray jsonArray = new JSONArray();
		String jsonString = new String();
		String selectQuery = "SELECT ID, ID_CATEGORIA, NUM_PRODUTO, MIN_QUANT, MAX_QUANT, NOME FROM " + TableProdutoNome;

		SQLiteDatabase db = this.getWritableDatabase();
		Cursor cursor = db.rawQuery(selectQuery, null);

			if (cursor.moveToFirst()) {
				
				try {
					jsonString = "{\"produtos\":[";
					do {
						jsonString += "{";
						jsonString += "\"ID\":" + cursor.getString(0);
						jsonString += ",\"ID_CATEGORIA\":" + cursor.getString(1);
						jsonString += ",\"NUM_PRODUTO\":" + cursor.getString(2);
						jsonString += ",\"MIN_QUANT\":" + cursor.getString(3);
						jsonString += ",\"MAX_QUANT\":" + cursor.getString(4);
						jsonString += ",\"NOME\":\"" + cursor.getString(5) + "\"";
						jsonString += "}";
						if(!cursor.isLast()){
							jsonString += ",";
						}
					} while (cursor.moveToNext());
					jsonString += "]}";
					Log.d("database", "produto: " + jsonString);
					
					jsonArray = new JSONArray(new JSONObject(jsonString).getString("produtos"));
	
				} catch (JSONException e) {
					e.printStackTrace();
				}
			}

		return jsonArray;
	}
}
