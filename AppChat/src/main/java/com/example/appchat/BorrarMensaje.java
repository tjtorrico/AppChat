package com.example.appchat;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.TextView;

public class BorrarMensaje extends AsyncTask<TextView, TextView, Void>{
	private Context context;

	public BorrarMensaje(Context context){
		this.context = context;
	}

	@Override
	protected Void doInBackground(TextView... params) {
		try {
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		publishProgress(params[0]);
		return null;

	}

	@Override
	protected void onProgressUpdate(TextView... values) {
		super.onProgressUpdate(values);
		
		Chat.tableroMensajes.removeView(values[0]);
		
	}
	
	

}
