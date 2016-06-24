package Adaptadores;

import java.util.ArrayList;

import com.example.appchat.Base_64;
import com.example.appchat.R;
import Datos.ContactoTelefonico;
import Datos.UsuarioSqlServer;
import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class AdapterContactos extends BaseAdapter{
	protected Activity activity;
    protected ArrayList<UsuarioSqlServer> listaContactoTelefonicos;
    private UsuarioSqlServer usuario;
    private Base_64 base;
 
    public AdapterContactos(Activity activity, ArrayList<UsuarioSqlServer> listaContactoTelefonicos) {
        this.activity = activity;
        this.listaContactoTelefonicos = listaContactoTelefonicos;
        base = new Base_64();
    }
 
    @Override
    public int getCount() {
        return listaContactoTelefonicos.size();
    }
 
    @Override
    public Object getItem(int arg0) {
        return listaContactoTelefonicos.get(arg0);
    }
    
  
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
 
        // Generamos una convertView por motivos de eficiencia
        View v = convertView;
 
        //Asociamos el layout de la lista que hemos creado
        if(convertView == null){
            LayoutInflater inf = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = inf.inflate(R.layout.lista_contactos, null);
        }
        //creamos objeto contacto telefonico
        usuario = listaContactoTelefonicos.get(position);
        //Rellenamos el nombre 
        TextView nombre = (TextView) v.findViewById(R.id.tvNombre);
        nombre.setText(usuario.getNombre().trim()+" ");
        //Rellenamos el estado
        TextView telefono = (TextView) v.findViewById(R.id.tvEstado);
        telefono.setText(usuario.getEstado()+"");
        // Retornamos la vista
        ImageView foto = (ImageView) v.findViewById(R.id.ivFoto);
        Bitmap bm = base.decodeImage(usuario.getFoto());
        foto.setImageBitmap(bm);
        return v;
    }

	@Override
	public long getItemId(int position) {
		return -1;
	}

}
