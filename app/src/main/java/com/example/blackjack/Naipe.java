package com.example.blackjack;

import android.content.Context;
import android.util.Log;

public class Naipe {

    private int imagen;

    public Naipe(String carta, Context context) {
        StringBuilder idCarta = new StringBuilder();
        char palo = ' ';

        if (!carta.isEmpty() && carta.replaceAll("\\w+", "").length() > 0) {
            palo = carta.replaceAll("\\w+", "").charAt(0);
        }

        if (carta.matches("[2-9KQJA].*")) {
            switch (carta.charAt(0)) {
                case 'A':
                    idCarta.append("na");
                    break;
                case 'J':
                    idCarta.append("nj");
                    break;
                case 'Q':
                    idCarta.append("nq");
                    break;
                case 'K':
                    idCarta.append("nk");
                    break;
                default:
                    idCarta.append("n").append(carta.charAt(0));
            }
        } else if (carta.matches("10.*")) {
            idCarta.append("n10");
        }

        switch (palo) {
            case '\u2660':
                idCarta.append("p");
                break;
            case '\u2665':
                idCarta.append("c");
                break;
            case '\u2666':
                idCarta.append("d");
                break;
            case '\u2663':
                idCarta.append("t");
                break;
            default:
                idCarta.append("reversorojo");
        }

        imagen = context.getResources().getIdentifier(idCarta.toString(), "drawable", context.getPackageName());
    }

    public int getImagen() {
        Log.i("Naipe", "ID de imagen: " + imagen);
        return imagen;
    }
}
