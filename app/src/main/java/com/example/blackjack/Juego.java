package com.example.blackjack;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.IOException;
import java.util.ArrayList;

//import java.util.regex.Matcher;
//import java.util.regex.Pattern;


public class Juego extends AppCompatActivity {

    private Cliente cliente = new Cliente();

    private Button buttonPedir;
    private Button buttonPlantarse;
    private Button buttonRepartir;
    private Button buttonFin;

    private ImageView iv;
    private GridLayout gridLayout;


//    private Map<String, Integer> cartasImagenesMap = new HashMap<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_juego);

        new Thread() {
            @Override
            public void run() {
                try {
                    if (cliente.iniciar(getIntent().getStringExtra("usuario"))) {
                        String juego = String.valueOf(cliente.repartir());
                        pintarJuego(juego);
                    }
                    else {
                        Toast.makeText(Juego.this, "No se pudo crear la partida", Toast.LENGTH_LONG).show();
                        Juego.this.finish();
                    }
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        }.start();

        buttonPedir = findViewById(R.id.buttonPedir);
        buttonPedir.setOnClickListener((v) -> this.pedir());
        buttonPlantarse = findViewById(R.id.buttonPlantarse);
        buttonPlantarse.setOnClickListener((v -> this.plantarse()));
        buttonRepartir = findViewById(R.id.buttonRepartir);
        buttonRepartir.setOnClickListener(v -> this.repartir());
        buttonFin = findViewById(R.id.buttonFin);
        buttonFin.setOnClickListener(v -> this.fin());
//        iv = findViewById(R.id.imagenCarta);

//        cardSurfaceView = findViewById(R.id.cardSurfaceView);
        gridLayout = findViewById(R.id.gridLayout);
        //putextra y getextra para el nombre de usuario
        //repartir + #hash

    }


    private void pedir() {
        new Thread() {
            @Override
            public void run() {
                String juego = null;
                try {
                    juego = cliente.pedir();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                pintarJuego(juego);
            }
        }.start();
    }
    private void plantarse() {
        new Thread() {
            @Override
            public void run() {
                String juego = null;
                try {
                    juego = cliente.plantarse();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                pintarJuego(juego);
            }
        }.start();
    }
    private void repartir() {
        new Thread() {
            @Override
            public void run() {
                String juego = null;
                try {
                    juego = cliente.repartir();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                pintarJuego(juego);
            }
        }.start();
    }
    private void fin() {
        new Thread() {
            @Override
            public void run() {
                String juego = null;
                try {
                    juego = cliente.fin();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                pintarJuego(juego);
            }
        }.start();
    }

//    boolean crupier;
//    boolean jugador;
    private void pintarJuego(String juego) {
        Log.i("Juego", "Estado del juego: " + juego);

//        String regex = "Crupier: ([2-9TJQKA][♠♥♦♣]\\*\\*|[2-9TJQKA][♠♥♦♣][2-9TJQKA][♠♥♦♣]:[0-9+]+)\n" +
//                "Jugador: ([2-9TJQKA][♠♥♦♣][2-9TJQKA][♠♥♦♣][2-9TJQKA][♠♥♦♣]:[0-9+]+)\n" +
//                "(Empate|La banca gana)";
//
//        Matcher m = Pattern.compile(regex).matcher(juego);
//        while (m.find()) {
//            String crupierCards = m.group(1);
//            String jugadorCards = m.group(2);
//            String resultado = m.group(3);
//
//            if (crupierCards != null) {
//                crupier = true;
//                jugador = false;
//                pintarCartas(crupierCards);
//            } else if (jugadorCards != null) {
//                jugador = true;
//                crupier = false;
//                pintarCartas(jugadorCards);
//            }
//
//            if (resultado != null) {
//                pintarResultado(resultado);
//            }
        String cartasCrupier = juego.split("\n")[0].split(":")[1].split(" ")[1];
        String cartasJugador = juego.toString().split("\n")[1].split(":")[1].split(" ")[1];
        ArrayList<Naipe> naipes = new ArrayList<>();
        for (int i = 0; i < cartasJugador.length(); i+=2){
            naipes.add(new Naipe(cartasJugador.substring(i, i+2), this));
        }
//        iv.setImageResource(naipes.get(0).getImagen());
//        if(cardSurfaceView != null){
//            cardSurfaceView.setCartas(naipes);
//            cardSurfaceView.invalidate();
//        }
        runOnUiThread(() -> {
            gridLayout.removeAllViews();
            for (Naipe naipe : naipes) {
                CardSurfaceView cardSurfaceView = new CardSurfaceView(Juego.this, null);
                cardSurfaceView.setCartas(naipe);

                GridLayout.LayoutParams params = new GridLayout.LayoutParams();
                params.width = ViewGroup.LayoutParams.WRAP_CONTENT;
                params.height = ViewGroup.LayoutParams.WRAP_CONTENT;
                cardSurfaceView.setLayoutParams(params);

                gridLayout.addView(cardSurfaceView);
            }
        });

    }

    private void pintarCartas(String cartas) {
        Log.i("Cartas", cartas);

    }

    private void pintarResultado(String resultado) {
        Log.i("Resultado", resultado);
    }




}