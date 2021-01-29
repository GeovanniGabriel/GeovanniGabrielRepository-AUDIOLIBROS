package com.example.audiolibros;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class AdaptadorLibros extends RecyclerView.Adapter<AdaptadorLibros.ViewHolder> {

    private View.OnClickListener onClickListener;
    private View.OnLongClickListener onLongClickListener;
    private LayoutInflater inflador;
    protected List<Libro> listaLibros;
    private Context contexto;

    public AdaptadorLibros(Context contexto, List<Libro> listaLibros) {
        inflador = (LayoutInflater) contexto.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.listaLibros = listaLibros;
        this.contexto = contexto;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = inflador.inflate(R.layout.elemento_selector, null);
        v.setOnClickListener(onClickListener);
        v.setOnLongClickListener(onLongClickListener);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Libro libro = listaLibros.get(position);
        holder.portada.setImageResource(libro.recursoImagen);
        holder.titulo.setText(libro.titulo);
    }

    @Override
    public int getItemCount() {
        return listaLibros.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        public ImageView portada;
        public TextView titulo;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            portada = (ImageView) itemView.findViewById(R.id.portada);
            titulo = (TextView) itemView.findViewById(R.id.titulo);
        }
    }

    public void setOnClickListener(View.OnClickListener onClickListener) {
        this.onClickListener = onClickListener;
    }

    public void setOnLongClickListener(View.OnLongClickListener onLongClickListener) {
        this.onLongClickListener = onLongClickListener;
    }
}
