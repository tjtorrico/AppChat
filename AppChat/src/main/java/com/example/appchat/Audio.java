package com.example.appchat;

import android.content.Context;
import android.media.MediaPlayer;

public class Audio {

private static MediaPlayer mireproductor = null;

	public static void reproduce(Context context, int resource) {
		para_musica();
		
		if (Contactos.getMusic(context)) {
			mireproductor = MediaPlayer.create(context, resource);
			mireproductor.setLooping(true);
			mireproductor.start();
		}
		
		try {
			Thread.sleep(800);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		para_musica();
	}
	
	public static void para_musica() {
		if (mireproductor != null) {
		mireproductor.stop();
		mireproductor.release();
		mireproductor = null;
		}
	}
	
	public static MediaPlayer get_mireproductor(){
		return mireproductor;
	}

}
