package com.example.audiolibros.fragments;

import android.content.Context;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.MediaController;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.audiolibros.AdaptadorLibrosFiltro;
import com.example.audiolibros.Aplicacion;
import com.example.audiolibros.Libro;
import com.example.audiolibros.R;

import java.io.IOException;

public class DetalleFragment extends Fragment implements View.OnTouchListener, MediaPlayer.OnPreparedListener, MediaController.MediaPlayerControl {

    public static String ARG_ID_LIBRO = "id_libro";
    private int position = 0;
    MediaPlayer mediaPlayer;
    MediaController mediaController;
    AdaptadorLibrosFiltro adaptador;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflador, @Nullable ViewGroup contenedor, @Nullable Bundle savedInstanceState) {
        View vista = inflador.inflate(R.layout.fragment_detalle, contenedor, false);
        Bundle args = getArguments();
        if (args != null) {
            position = args.getInt(ARG_ID_LIBRO);
            ponInfoLibro(position, vista);
        } else {
            ponInfoLibro(0, vista);
        }
        return vista;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
       // adaptador = ((Aplicacion) getContext()).getAdaptador();
        //adaptador.setLeido(position);
    }

    private void ponInfoLibro(int id, View vista) {
        Libro libro = ((Aplicacion) getActivity().getApplication()).getListaLibros().get(id);
        ((TextView) vista.findViewById(R.id.titulo)).setText(libro.titulo);
        ((TextView) vista.findViewById(R.id.autor)).setText(libro.autor);
        ((ImageView) vista.findViewById(R.id.portada)).setImageResource(libro.recursoImagen);
        vista.setOnTouchListener(this);

        if (mediaPlayer != null) {
            mediaPlayer.release();
        }

        mediaPlayer = new MediaPlayer();
        mediaPlayer.setOnPreparedListener(this);
        mediaController = new MediaController(getActivity());

        Uri audio = Uri.parse(libro.urlAudio);

        try {
            mediaPlayer.setDataSource(getActivity(), audio);
            mediaPlayer.prepareAsync();
        } catch (IOException e) {
            Log.e("Audiolibros", "ERROR: No se puede reproducir" + audio, e);
        }
    }

    public void ponInfoLibro(int id) {
        ponInfoLibro(id, getView());
    }

    @Override
    public void onPrepared(MediaPlayer mp) {
        Log.d("Audiolibros", "Entramos en onPrepared de MediaPlayer");

        mediaPlayer.start();
        mediaController.setMediaPlayer(this);
        mediaController.setAnchorView(getView().findViewById(R.id.fragment_detalle));
        mediaController.setEnabled(true);
        mediaController.show();

    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        mediaController.show();
        return false;
    }

    @Override
    public void onStop() {
        super.onStop();
        mediaController.hide();
        try {
            mediaPlayer.stop();
            mediaPlayer.release();
        } catch (Exception e) {
        }
    }

    @Override
    public int getBufferPercentage() {
        return 0;
    }

    @Override
    public boolean canPause() {
        return true;
    }

    @Override
    public boolean canSeekBackward() {
        return true;
    }

    @Override
    public boolean canSeekForward() {
        return true;
    }

    @Override
    public int getCurrentPosition() {
        try {
            return mediaPlayer.getCurrentPosition();
        } catch (Exception e) {
            return 0;
        }
    }

    @Override
    public int getDuration() {
        return mediaPlayer.getDuration();
    }

    @Override
    public boolean isPlaying() {
        return mediaPlayer.isPlaying();
    }

    @Override
    public void pause() {
        mediaPlayer.pause();
    }

    @Override
    public void seekTo(int pos) {
        mediaPlayer.seekTo(pos);
    }

    @Override
    public void start() {
        mediaPlayer.start();
    }

    @Override
    public int getAudioSessionId() {
        return 0;
    }
}
