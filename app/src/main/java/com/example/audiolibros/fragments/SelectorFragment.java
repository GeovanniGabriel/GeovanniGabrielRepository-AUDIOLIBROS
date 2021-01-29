package com.example.audiolibros.fragments;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.audiolibros.AdaptadorLibros;
import com.example.audiolibros.AdaptadorLibrosFiltro;
import com.example.audiolibros.Aplicacion;
import com.example.audiolibros.Libro;
import com.example.audiolibros.MainActivity;
import com.example.audiolibros.R;
import com.google.android.material.snackbar.Snackbar;

import java.util.List;

public class SelectorFragment extends Fragment {

    private Activity actividad;
    private RecyclerView recyclerView;
    private AdaptadorLibrosFiltro adaptador;

    @Override
    public void onAttach(Activity actividad) {
        super.onAttach(actividad);
        this.actividad = actividad;
        Aplicacion app = (Aplicacion) actividad.getApplication();
        adaptador = app.getAdaptador();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflador, @Nullable ViewGroup contenedor, @Nullable Bundle savedInstanceState) {
        View vista = inflador.inflate(R.layout.fragment_selector, contenedor, false);
        recyclerView = (RecyclerView) vista.findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new GridLayoutManager(actividad, 2));
        recyclerView.setAdapter(adaptador);
        adaptador.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((MainActivity) actividad).mostrarDetalle((int) adaptador.getItemId(recyclerView.getChildAdapterPosition(v)));

            }
        });
        adaptador.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(final View v) {
                final int id = recyclerView.getChildAdapterPosition(v);
                AlertDialog.Builder menu = new AlertDialog.Builder(actividad);
                final CharSequence[] opciones = {"Compartir", "Borrar", "Insertar"};
                final List<Libro> listaLibros = ((Aplicacion) getActivity().getApplication()).getListaLibros();
                menu.setItems(opciones, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int opcion) {
                        switch (opcion) {
                            case 0:
                                Libro libro = listaLibros.get(id);
                                Intent i = new Intent(Intent.ACTION_SEND);
                                i.setType("text/plain");
                                i.putExtra(Intent.EXTRA_SUBJECT, libro.titulo);
                                i.putExtra(Intent.EXTRA_TEXT, libro.urlAudio);
                                startActivity(Intent.createChooser(i, "Compartir"));
                                break;
                            case 1:
                                Snackbar.make(v, "¿Estás seguro?", Snackbar.LENGTH_LONG).setAction("SI", new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        adaptador.borrar(id);
                                        adaptador.notifyDataSetChanged();
                                    }
                                }).show();
                                break;
                            case 2:
                                int posicion = recyclerView.getChildLayoutPosition(v);
                                adaptador.insertar((Libro) adaptador.getItem(posicion));
                                adaptador.notifyDataSetChanged();
                                Snackbar.make(v, "Libro insertado", Snackbar.LENGTH_INDEFINITE).show();
                                break;
                        }
                    }
                });
                menu.create().show();
                return true;
            }
        });

        setHasOptionsMenu(true);

        return vista;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_selector, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.menu_ultimo) {
            ((MainActivity) actividad).irUltimoVisitado();
            return true;
        } else if (id == R.id.menu_buscar) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
