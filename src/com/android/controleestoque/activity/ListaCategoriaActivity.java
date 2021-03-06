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
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.android.controleestoque.R;
import com.android.controleestoque.adapter.ItemListaCategoria;
import com.android.controleestoque.adapter.ItemListaCategoriaAdapter;
import com.android.controleestoque.db.DatabaseOpenHelper;

public class ListaCategoriaActivity extends ActionBarActivity{
	private DrawerLayout drawerLayout;
    private ListView drawerList;
    private ActionBarDrawerToggle drawerToggle;

    private DatabaseOpenHelper db;
    private CharSequence drawerTitle;
    private CharSequence title;
    private String[] itemsTitles;
    
    private Boolean flag;
    
    private ArrayList<ItemListaCategoria> itens;
    
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.lista_categoria);
        
        flag = false;
        
        criaNavigationDrawer(savedInstanceState);
        
        db = new DatabaseOpenHelper(getApplicationContext());
		
        criaLista();
    }
	
	private void criaLista(){
		ListView list = (ListView) findViewById(R.id.list_categoria);
		itens = new ArrayList<ItemListaCategoria>();

		recuperaItensLista();
		
		ItemListaCategoriaAdapter adapter = new ItemListaCategoriaAdapter(this, itens);
		list.setAdapter(adapter);
	}
	
	private void recuperaItensLista(){
		JSONArray jsonarray = db.selectCategorias();
		for(int i = 0; i < jsonarray.length();i++){
			try {
				JSONObject json = jsonarray.getJSONObject(i);
				itens.add(new ItemListaCategoria(json.getString("NOME"),json.getString("DESCRICAO")));
			} catch (JSONException e) {
				e.printStackTrace();
			}
		}
	}
	
	private void criaNavigationDrawer(Bundle savedInstanceState){
        title = drawerTitle = getTitle();
        itemsTitles = getResources().getStringArray(R.array.items_array);
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawerList = (ListView) findViewById(R.id.left_drawer);
        
        // set a custom shadow that overlays the main content when the drawer opens
        drawerLayout.setDrawerShadow(R.drawable.drawer_shadow, GravityCompat.START);
        
        // set up the drawer's list view with items and click listener
        drawerList.setAdapter(new ArrayAdapter<String>(this,
                R.layout.drawer_list_item, itemsTitles));
        
        drawerList.setOnItemClickListener(new DrawerItemClickListener());

        // enable ActionBar app icon to behave as action to toggle nav drawer
        getActionBar().setDisplayHomeAsUpEnabled(true); // minimo android 11
        getActionBar().setHomeButtonEnabled(true); // minimo android 14
        
     // ActionBarDrawerToggle ties together the the proper interactions
        // between the sliding drawer and the action bar app icon
        drawerToggle = new ActionBarDrawerToggle(
                this,                  /* host Activity */
                drawerLayout,         /* DrawerLayout object */
                R.drawable.ic_drawer,  /* nav drawer image to replace 'Up' caret */
                R.string.drawer_open,  /* "open drawer" description for accessibility */
                R.string.drawer_close  /* "close drawer" description for accessibility */
                ) {
            public void onDrawerClosed(View view) {
                getActionBar().setTitle(title);
                invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
            }

            public void onDrawerOpened(View drawerView) {
                getActionBar().setTitle(drawerTitle);
                invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
            }
        };
        drawerLayout.setDrawerListener(drawerToggle);

        if (savedInstanceState == null) {
            selectItem(0);
        }
	}
	
	/* The click listner for ListView in the navigation drawer */
    private class DrawerItemClickListener implements ListView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        	flag = true;
            selectItem(position);
        }
    }
    
    private void selectItem(int position) {
    	if(flag){
    		flag = false;
	    	Log.d("navigation",itemsTitles[position]);
	    	Intent it;
	    	switch (position) {
			case 0:
				it = new Intent(this, ListaProdutoActivity.class);
				startActivity(it);
				finish();
				break;
			case 1:
				Toast.makeText(this, "Tela Atual", Toast.LENGTH_SHORT).show();
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
        // If the nav drawer is open, hide action items related to the content view
//        boolean drawerOpen = drawerLayout.isDrawerOpen(drawerList);
//        menu.findItem(R.id.action_websearch).setVisible(!drawerOpen);
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
        switch(item.getItemId()) {
//        case R.id.action_websearch:
//            // create intent to perform web search for this planet
//            Intent intent = new Intent(Intent.ACTION_WEB_SEARCH);
//            intent.putExtra(SearchManager.QUERY, getActionBar().getTitle());
//            // catch event that there's no activity to handle intent
//            if (intent.resolveActivity(getPackageManager()) != null) {
//                startActivity(intent);
//            } else {
//                Toast.makeText(this, R.string.app_not_available, Toast.LENGTH_LONG).show();
//            }
//            return true;
        	case R.id.add_menu:
            	Log.d("item","chamou o add_menu");
            	Intent it = new Intent(this, DetalheCategoriaActivity.class);
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
