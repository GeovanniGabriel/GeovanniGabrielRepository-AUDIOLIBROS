package com.example.audiolibros;

import android.content.Context;

import java.util.ArrayList;
import java.util.List;

public class AdaptadorLibrosFiltro extends AdaptadorLibros {

    private List<Libro> listaSinFiltro;
    private List<Integer> indiceFiltro;
    private boolean allItems;

    public AdaptadorLibrosFiltro(Context contexto, List<Libro> listaLibros) {
        super(contexto, listaLibros);
        listaSinFiltro = listaLibros;
        allItems = true;
        recalculaFiltro();
    }

    public void recalculaFiltro() {

        listaLibros = new ArrayList<Libro>();
        indiceFiltro = new ArrayList<Integer>();

        for (int i = 0; i < listaSinFiltro.size(); i++) {
            Libro libro = listaSinFiltro.get(i);

            if (libro.novedad || libro.leido || allItems) {
                listaLibros.add(libro);
                indiceFiltro.add(i);
            }
        }
    }

    public Libro getItem(int posicion) {
        return listaSinFiltro.get(indiceFiltro.get(posicion));
    }

    public long getItemId(int posicion) {
        return indiceFiltro.get(posicion);
    }

    public void borrar(int posicion) {
        listaSinFiltro.remove(posicion);
        recalculaFiltro();
    }

    public void insertar(Libro libro) {
        libro.novedad = true;
        listaSinFiltro.add(libro);
        recalculaFiltro();
    }

    public void setLeido(int posicion) {
        listaSinFiltro.get(indiceFiltro.get(posicion)).leido = true;
    }

    public void setAllItems(boolean mostrarTodos) {
        allItems = mostrarTodos;
        recalculaFiltro();
    }

}
