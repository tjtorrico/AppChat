package com.example.appchat;

import android.os.Bundle;
import android.app.Activity;
import android.content.pm.ActivityInfo;
import android.view.View;
import android.view.Window;
import android.widget.CheckBox;
import android.widget.CompoundButton;

public class Configuracion extends Activity {
	private CheckBox cbMensajeAutodestructible;
	private CheckBox cbEsteganografia;
	public static String Autodestructible;
	public static String Esteganografia;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_configuracion);
		
		cbMensajeAutodestructible = (CheckBox) findViewById(R.id.cbMensajeAutodestructible);
		
		cbMensajeAutodestructible.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
			
			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				if(buttonView.getId()==R.id.cbMensajeAutodestructible){
					if(isChecked==true){
						Autodestructible = "autodestructible";
					}else{
						Autodestructible = null;
					}
				}
				
			}
		});
		
		cbEsteganografia = (CheckBox) findViewById(R.id.cbEsteganografia);
		
		cbEsteganografia.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
			
			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				if(buttonView.getId()==R.id.cbEsteganografia){
					if(isChecked==true){
						Esteganografia = "esteganografia";
					}else{
						Esteganografia = null;
					}
				}
				
			}
		});
		
	}	

}
