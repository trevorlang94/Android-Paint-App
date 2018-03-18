package com.example.main.beginningpainttest;

import android.app.AlertDialog;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.app.ProgressDialog;
import android.content.ClipData;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.Gravity;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import static com.example.main.beginningpainttest.DrawingArea.clearCanvas;
import android.view.View.OnClickListener;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends AppCompatActivity {

    Button button1, brushSizeButton; //1-12 are color buttons, the others are various parts of toolbar
    Button button2;
    Button button3;
    Button button4;
    Button button5;
    Button button6;
    Button button7;
    Button button8;
    Button button9;
    Button button10;
    Button button11;
    Button button12;
	Button customColorBtn;
	Button currentColorButton;
	Button logOutButton;
	DrawingArea drawingArea;
	String username;
	int customButtonBGColor = Color.rgb(0, 0, 0);



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        setContentView(R.layout.activity_main);

        final Spinner brushSpinner = (Spinner) findViewById(R.id.brushSpinner);
		button1 = (Button) findViewById(R.id.button1);
		button2 = (Button) findViewById(R.id.button2);
		button3 = (Button) findViewById(R.id.button3);
		button4 = (Button) findViewById(R.id.button4);
		button5 = (Button) findViewById(R.id.button5);
		button6 = (Button) findViewById(R.id.button6);
		button7 = (Button) findViewById(R.id.button7);
		button8 = (Button) findViewById(R.id.button8);
		button9 = (Button) findViewById(R.id.button9);
		button10 = (Button) findViewById(R.id.button10);
		button11 = (Button) findViewById(R.id.button11);
		button12 = (Button) findViewById(R.id.button12);
		brushSizeButton = (Button) findViewById(R.id.brushSizeButton);
		customColorBtn = (Button) findViewById(R.id.customColorBtn);
		currentColorButton = (Button) findViewById(R.id.currentColorBtn);
		logOutButton = (Button) findViewById(R.id.logOutBtn);
		drawingArea = (DrawingArea) findViewById(R.id.drawingArea1);

		Bundle bundle = getIntent().getExtras();
		try {
			username = bundle.getString("name"); //passoff from
		}
		catch(Exception ex){
			username = "user";
		}

		//Stuff for brush size spinner
		List<String> sizes = new ArrayList<>();
		sizes.add("10dp");
		sizes.add("20dp");
		sizes.add("30dp");
		sizes.add("40dp");
		sizes.add("50dp");



		ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, sizes);
		dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		brushSpinner.setAdapter(dataAdapter);

		brushSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
				switch (position) {
					case 0:
						DrawingArea.strokeWidth = 10;
						break;
					case 1:
						DrawingArea.strokeWidth = 20;
						break;
					case 2:
						DrawingArea.strokeWidth = 30;
						break;
					case 3:
						DrawingArea.strokeWidth = 40;
						break;
					case 4:
						DrawingArea.strokeWidth = 50;
						break;

					case 5:
						break;

					default:
						break;
				}
			}

			@Override
			public void onNothingSelected(AdapterView<?> parent) {

			}
		});


		//For some reason this button was turning black on click.
		currentColorButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {

			}
		});

		//listener for custom colors button
		customColorBtn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				//instance of pop-up menu
				PopupMenu popup = new PopupMenu(MainActivity.this, customColorBtn);
				//inflating using xml
				popup.getMenuInflater().inflate(R.menu.customcolormenu, popup.getMenu());

				popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
					public boolean onMenuItemClick(MenuItem item) {
						int itemSelected = item.getItemId(); //gets the ID of menu item you click

						switch (itemSelected) {
							//item1 ie first item in the menu..
							//this does not work if used and then contentView is changed away and back to the main activity
							case R.id.item1:
								customColorDialogue(1);
								return true;

							case R.id.item2:
								customColorDialogue(2);
								return true;

							case R.id.item3:
								customColorDialogue(3);
								return true;

							case R.id.item4:
								customColorDialogue(4);
								return true;

							case R.id.item5:
								customColorDialogue(5);
								return true;

							case R.id.item6:
								customColorDialogue(6);
								return true;

							case R.id.item7:
								customColorDialogue(7);
								return true;

							case R.id.item8:
								customColorDialogue(8);
								return true;

							case R.id.item9:
								customColorDialogue(9);
								return true;

							case R.id.item10:
								customColorDialogue(10);
								return true;

							case R.id.item11:
								customColorDialogue(11);
								return true;

							case R.id.item12:
								customColorDialogue(12);
								return true;

							default:
								return false;
						}


					}
				});
				popup.show();
			}
		}

		);

		brushSizeButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				brushSpinner.performClick();
			}
		});

		logOutButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				AlertDialog.Builder builder1 = new AlertDialog.Builder(MainActivity.this);
				builder1.setMessage("Really log out, " + username +"? Your work will be lost.");
				builder1.setCancelable(true);

				builder1.setPositiveButton(
						"Yes",
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog, int id) {
								dialog.cancel();
								SessionManager logInManager = new SessionManager(getApplicationContext());
								logInManager.logoutUser();
							}
						});

				builder1.setNegativeButton(
						"No",
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog, int id) {
								dialog.cancel();
							}
						});

				AlertDialog alert11 = builder1.create();
				alert11.show();

			}
		});


    }


	//Each of these functions calls a function from the drawingArea class which changes brush color, the toast is just for me to test it
	//Probably wouldn't hurt to move all these to a separate class file
	public void blueButton(View view){
        DrawingArea.paintChangeBlue();
		updateCurrentColor();
    }

    public void redButton(View view){
        DrawingArea.paintChangeRed();
		updateCurrentColor();
    }

    public void greenButton(View view){
        DrawingArea.paintChangeGreen();
		updateCurrentColor();
    }

    public void purpleButton(View view){
        DrawingArea.paintChangePurple();
		updateCurrentColor();
    }

    public void eraserButton(View view){
        DrawingArea.paintChangeErase();
		updateCurrentColor();
    }

    public void whiteButton(View view){
        DrawingArea.paintChangeWhite();
		updateCurrentColor();

    }

    public void orangeButton(View view){
        DrawingArea.paintChangeOrange();
		updateCurrentColor();

    }

    public void brownButton(View view){
        DrawingArea.paintChangeBrown();
		updateCurrentColor();

    }

    public void pinkButton(View view){
        DrawingArea.paintChangePink();
		updateCurrentColor();
    }

    public void yellowButton(View view){
        DrawingArea.paintChangeYellow();
		updateCurrentColor();
    }

    public void greyButton(View view){
        DrawingArea.paintChangeGrey();
		updateCurrentColor();

    }

    public void darkGreenButton(View view){
        DrawingArea.paintChangeDarkGreen();
		updateCurrentColor();
    }

    public void blackButton(View view){
        DrawingArea.paintChangeBlack();
		updateCurrentColor();

    }

    //this is used for the clear button, not sure why it's saying it isn't. It clears the array of strokes.
    public void clearButton(View view){
        clearCanvas();
    }
	//same here
    public void aboutButton(View view) {
		Intent i = new Intent(getApplicationContext(), About2Activity.class);
		i.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
		i.setClassName(this, "com.example.main.beginningpainttest.About2Activity");
		startActivity(i);
    }


    public boolean customColorDialogue(final int buttonSelected){
		final Button colorBtns[] = {button1, button2, button3, button4, button5, button6, button7, button8, button9,
				button10, button11, button12};


		AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(MainActivity.this);
		LinearLayout layout = new LinearLayout(MainActivity.this); //building the layout for the alert
		LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
		layout.setOrientation(LinearLayout.VERTICAL);
		layout.setLayoutParams(params);

		layout.setGravity(Gravity.CLIP_VERTICAL);
		layout.setPadding(2, 2, 2, 2);

		TextView tv = new TextView(MainActivity.this);
		tv.setText("Custom Hexadecimal Colors");
		tv.setPadding(40, 40, 40, 40);
		tv.setGravity(Gravity.CENTER);
		tv.setTextSize(20);

		final EditText et = new EditText(MainActivity.this); //temporary solution for getting the inputted text for use
		TextView tv1 = new TextView(MainActivity.this);
		tv1.setText("Input Hex Value");

		LinearLayout.LayoutParams tv1Params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
		tv1Params.bottomMargin = 5;
		layout.addView(tv1,tv1Params);
		layout.addView(et, new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));

		alertDialogBuilder.setView(layout);
		alertDialogBuilder.setTitle("Custom Hex Colors");
		alertDialogBuilder.setCustomTitle(tv);
		alertDialogBuilder.setPositiveButton("Submit", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int id) {

				String hexValue = et.getText().toString();

				Context context = getApplicationContext();
				CharSequence text = "Value entered: " + hexValue;
				int duration = Toast.LENGTH_SHORT;
				//the toasts are just for input validation

				Toast toast = Toast.makeText(context, text, duration);
				toast.setGravity(Gravity.BOTTOM| Gravity.RIGHT, 0, 0);
				toast.show();
				int color;

				try { //tries to convert inputted text to an int hexadecimal value
					color = Integer.parseInt(hexValue, 16);
				}

				catch(Exception ex) { //makes the color black if your input is bad
					color = 000000;
				}
				//I had to borrow this technique from someone else, I know it's analyzing it bitwise. I think each 8 bits is 1/3 of the RGB in whole
				final int r = (color >> 16) & 0xFF;
				final int g = (color >> 8) & 0xFF;
				final int b = (color >> 0) & 0xFF;


				text = "RGB: " + r + " " + g + " " + " " + b;

				toast = Toast.makeText(context, text, duration);
				toast.setGravity(Gravity.BOTTOM| Gravity.RIGHT, 0, 0);
				toast.show();
				//int to hold converted values
				customButtonBGColor = Color.rgb(r, g, b);
				colorBtns[buttonSelected - 1].setBackgroundColor(customButtonBGColor);
				colorBtns[buttonSelected -1].setOnClickListener(new OnClickListener() {
					@Override
					public void onClick(View v) {
						DrawingArea.paintChangeCustom(r, g, b); //the buttons have onClick's for their default colors, otherwise they use this from then on
						updateCurrentColor();
					}
				});




			}

		});

		alertDialogBuilder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int id) {

				Context context = getApplicationContext();
				CharSequence text = "Cancel Button Pressed";
				int duration = Toast.LENGTH_SHORT;

				Toast toast = Toast.makeText(context, text, duration);
				toast.setGravity(Gravity.BOTTOM| Gravity.RIGHT, 0, 0);
				toast.show();

			}

		});
		alertDialogBuilder.show();
		return true;

	}

	public void updateCurrentColor(){
    	currentColorButton.setBackgroundColor(DrawingArea.paintColor);
	}


}
