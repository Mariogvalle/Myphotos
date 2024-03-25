package com.tareas2p.myphotos;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.RequestQueue;

import java.util.ArrayList;
import java.util.List;

import Configuracion.ListAdapter;
import Configuracion.SQLiteConexion;
import Configuracion.Transacciones;
import Modelos.Photos;

public class ActivityLista extends AppCompatActivity implements ListAdapter.OnItemDoubleClickListener {
    SQLiteConexion conexion = new SQLiteConexion(this, Transacciones.DBName, null, 1);
    private RequestQueue requestQueue;
    List<Photos> listPhotos;
    SearchView searchView;
    ListAdapter listAdapter;
    Button eliminar, actualizar;
    ListView listphotos;
    ArrayList<Photos> lista;
    ArrayList<String> Arreglo;
    ArrayAdapter<String> ad;
    ArrayList<String> list;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista);
        searchView = (SearchView) findViewById(R.id.busqueda);
        searchView.clearFocus();
        eliminar = (Button) findViewById(R.id.btnEliminar);
        actualizar = (Button) findViewById(R.id.btnActualizar);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                filter(newText);
                return true;
            }
        });

        eliminar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ListAdapter.getSelectedItem() != -1) {
                    // Mostrar un diálogo de confirmación de eliminación
                    alertaEliminar();
                } else {
                    // Mostrar un mensaje si no se ha seleccionado ningún contacto
                    Toast.makeText(ActivityLista.this, "Selecciona photo a eliminar", Toast.LENGTH_SHORT).show();
                }
            }
        });
        ObtenerDatos();
    }

    private void filter(String text) {
        List<Photos> filteredList = new ArrayList<>();
        for (Photos photos : listPhotos) {
            if (photos.getDescripcion().toLowerCase().contains(text.toLowerCase())) {
                filteredList.add(photos);
            }
        }

        if (!filteredList.isEmpty()) {
            listAdapter.setFilteredList(filteredList);
        }
    }

    private void ObtenerDatos() {
        listPhotos = new ArrayList<>();
        SQLiteDatabase db =  conexion.getReadableDatabase();
        Photos photos = null;
        lista = new ArrayList<>();
        Arreglo = new ArrayList<>();
        Cursor cursor = db.rawQuery(Transacciones.SelectAllPhotos,null);
        if (cursor.moveToFirst()) {
            do {
//                int id = cursor.getInt(cursor.getColumnIndex("id"));
//                String descripcion = cursor.getString(cursor.getColumnIndex("descripcion"));
//                byte[] imageBlob = cursor.getBlob(cursor.getColumnIndex("photo"));
//                listPhotos.add(new Photos(id, descripcion,imageBlob));
                photos = new Photos();
                photos.setId(cursor.getInt(0));
                photos.setDescripcion(cursor.getString(1));
                photos.setPhoto(cursor.getBlob(2));
                lista.add(photos);

            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        llenarLista();
    }
    private void llenarLista() {

        Arreglo = new ArrayList<String>();
        for (int i = 0; i < lista.size(); i++) {
            Arreglo.add(lista.get(i).getId() + "\n" +
                    lista.get(i).getDescripcion() + "\n"+
                    lista.get(i).getPhoto() + "\n");
        }

        list = new ArrayList<>(Arreglo);
        ad = new ArrayAdapter(this, android.R.layout.simple_list_item_1, list);
        listphotos.setAdapter(ad);

    }
    @Override
    public void onItemDoubleClick(Photos photos) {
        Toast.makeText(getApplicationContext(), "Hola  ", Toast.LENGTH_SHORT).show();
    }


    private void alertaEliminar() {

    }
}