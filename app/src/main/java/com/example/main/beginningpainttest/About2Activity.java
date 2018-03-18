package com.example.main.beginningpainttest;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class About2Activity extends AppCompatActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_about2);

		Button returnButton = (Button) findViewById(R.id.returnButton);

		returnButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent i = new Intent();
				i.setClassName(getApplicationContext(), "com.example.main.beginningpainttest.MainActivity");
				startActivity(i);
			}
		});
	}
}
