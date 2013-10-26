package com.android.controleestoque.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;

import com.android.controleestoque.R;

public class MainActivity extends ActionBarActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.login);
	}
	
	public void login(View v){
		Intent it = new Intent(this, Loading.class);
		startActivity(it);
	}
}
