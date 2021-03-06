package com.android.controleestoque.activity;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.android.controleestoque.R;
import com.android.controleestoque.adapter.ItemListaProduto;
import com.android.controleestoque.adapter.ItemListaProdutoAdapter;
import com.android.controleestoque.db.DatabaseOpenHelper;

public class ListaProdutoActivity extends ActionBarActivity {
	private DrawerLayout drawerLayout;
	private ListView drawerList;
	private ActionBarDrawerToggle drawerToggle;
	private CharSequence drawerTitle;
	private CharSequence title;
	private String[] itemsTitles;
	
	private JSONArray jsonMovimentacao;
	private JSONArray jsonArray;
	
	private DatabaseOpenHelper db;
	
	private Boolean flag;

	private ArrayList<ItemListaProduto> itens;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.lista_produto);

		flag = false;
		
		criaNavigationDrawer(savedInstanceState);

		db = new DatabaseOpenHelper(getApplicationContext());
		
		criaLista();
	}

	private void criaLista() {
		ListView list = (ListView) findViewById(R.id.list_produto);
		itens = new ArrayList<ItemListaProduto>();

		recuperaItensLista();

		ItemListaProdutoAdapter adapter = new ItemListaProdutoAdapter(this, itens);
		list.setAdapter(adapter);
		
		list.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> adapter, View v, int position,long id2) {
				//TODO implementar o evento de click no item
				
				// retornar da lista jsonMovimentacao o item ( criar metodo ) 
				Integer id = retornaItem((ItemListaProduto) adapter.getItemAtPosition(position));
				// criar a intent 
				Intent it = new Intent(ListaProdutoActivity.this, DadosProdutoActivity.class);
				// salvar o id no bundle
				Bundle param = new Bundle();
				param.putString("id", id.toString());
				//adiciona os parametros na intent
				it.putExtras(param);
				// iniciar intent
				startActivity(it);
				
			}
		});
	}
	
	private Integer retornaItem(ItemListaProduto param){
		Integer id = -1;
		JSONObject json;
		for(int i = 0; i < jsonArray.length(); i++){
			try {
				json = jsonArray.getJSONObject(i);
				if(json.getString("NOME") == param.getNome()){
					id = json.getInt("ID");
				}
			} catch (JSONException e) {
				e.printStackTrace();
			}
		}
		return id;
		
	}

	private void recuperaItensLista() {

		Float valorAtual = 0F;
		jsonMovimentacao = db.selectMovimentacao();
		jsonArray = db.selectProduto();
		for(int i = 0; i < jsonArray.length();i++){
			try {
				valorAtual = 0F;
				JSONObject json = jsonArray.getJSONObject(i);
				for(int j = 0; j < jsonMovimentacao.length(); j++){
					JSONObject jsonMov = jsonMovimentacao.getJSONObject(j);
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
				
				itens.add(new ItemListaProduto(json.getString("NOME"), Integer.valueOf(json.getString("MIN_QUANT")), Integer.valueOf(json.getString("MAX_QUANT")),valorAtual));
			} catch (JSONException e) {
				e.printStackTrace();
			}
		}
		
	}

	private void criaNavigationDrawer(Bundle savedInstanceState) {
		title = drawerTitle = getTitle();
		itemsTitles = getResources().getStringArray(R.array.items_array);
		drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
		drawerList = (ListView) findViewById(R.id.left_drawer);

		// set a custom shadow that overlays the main content when the drawer
		// opens
		drawerLayout.setDrawerShadow(R.drawable.drawer_shadow,
				GravityCompat.START);

		// set up the drawer's list view with items and click listener
		drawerList.setAdapter(new ArrayAdapter<String>(this,
				R.layout.drawer_list_item, itemsTitles));

		drawerList.setOnItemClickListener(new DrawerItemClickListener());

		// enable ActionBar app icon to behave as action to toggle nav drawer
		getActionBar().setDisplayHomeAsUpEnabled(true); // minimo android 11
		getActionBar().setHomeButtonEnabled(true); // minimo android 14

		// ActionBarDrawerToggle ties together the the proper interactions
		// between the sliding drawer and the action bar app icon
		drawerToggle = new ActionBarDrawerToggle(this, /* host Activity */
		drawerLayout, /* DrawerLayout object */
		R.drawable.ic_drawer, /* nav drawer image to replace 'Up' caret */
		R.string.drawer_open, /* "open drawer" description for accessibility */
		R.string.drawer_close /* "close drawer" description for accessibility */
		) {
			public void onDrawerClosed(View view) {
				getActionBar().setTitle(title);
				invalidateOptionsMenu(); // creates call to
											// onPrepareOptionsMenu()
			}

			public void onDrawerOpened(View drawerView) {
				getActionBar().setTitle(drawerTitle);
				invalidateOptionsMenu(); // creates call to
											// onPrepareOptionsMenu()
			}
		};
		drawerLayout.setDrawerListener(drawerToggle);

		if (savedInstanceState == null) {
			selectItem(0);
		}
	}

	/* The click listner for ListView in the navigation drawer */
	private class DrawerItemClickListener implements
			ListView.OnItemClickListener {
		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position,
				long id) {
			Log.d("navigation", "click");
			flag = true;
			selectItem(position);
		}
	}

	private void selectItem(int position) {
		Log.d("navigation", itemsTitles[position]);
		Log.d("navigation", flag.toString());
		
		if (flag) {
			flag = false;
			Intent it;
			switch (position) {
			case 0:
				Toast.makeText(this, "Tela Atual", Toast.LENGTH_SHORT).show();
				break;
			case 1:
				it = new Intent(this, ListaCategoriaActivity.class);
				startActivity(it);
				finish();
				break;
			case 2:
				it = new Intent(this, ListaMovimentacaoActivity.class);
				startActivity(it);
				finish();
				break;
			default:
				break;
			}

			drawerLayout.closeDrawer(drawerList);
		}
	}

	@Override
	public void setTitle(CharSequence title) {
		this.title = title;
		getActionBar().setTitle(this.title);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.main, menu);
		return super.onCreateOptionsMenu(menu);
	}

	/* Called whenever we call invalidateOptionsMenu() */
	@Override
	public boolean onPrepareOptionsMenu(Menu menu) {
		// If the nav drawer is open, hide action items related to the content
		// view
//		boolean drawerOpen = drawerLayout.isDrawerOpen(drawerList);
		// menu.findItem(R.id.action_websearch).setVisible(!drawerOpen);
		return super.onPrepareOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// The action bar home/up action should open or close the drawer.
		// ActionBarDrawerToggle will take care of this.
		if (drawerToggle.onOptionsItemSelected(item)) {
			return true;
		}
		// Handle action buttons
		switch (item.getItemId()) {
		case R.id.add_menu:
        	Log.d("item","chamou o add_menu");
        	Intent it = new Intent(this, DetalheProdutoActivity.class);
    		startActivity(it);
        	break;
		}
		return super.onOptionsItemSelected(item);
	}
	
   @Override
   public void onBackPressed() {
	   AlertDialog dialog;
	   AlertDialog.Builder builder = new AlertDialog.Builder(this);
	   builder.setMessage("Deseja realmente sair da aplicação?").setTitle("COnfirmação");
	   builder.setPositiveButton("Confirmação", new DialogInterface.OnClickListener() {
           public void onClick(DialogInterface dialog, int id) {
        	   finish();
           }
       });
	   builder.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
           public void onClick(DialogInterface dialog, int id) {
        	   dialog.dismiss();
           }
       });

	   dialog = builder.create();
	   
	   dialog.show();
	}
}
