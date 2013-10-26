package com.android.controleestoque.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;

import com.android.controleestoque.R;

public class Loading extends ActionBarActivity {
	private Thread thread;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.loading);

		thread = new Thread() {
			@Override
			public void run() {
				try {
					synchronized (this) {
						wait(3 * 1000);
						Intent it = new Intent(Loading.this, ListaProdutoActivity.class);
						startActivity(it);
						finish();
					}
				} catch (InterruptedException ex) {
					finish();
				}

			}
		};

		thread.start();

	}

}
