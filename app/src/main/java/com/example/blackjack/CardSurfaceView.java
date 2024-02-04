package com.example.blackjack;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import androidx.core.content.ContextCompat;

import java.util.ArrayList;

public class CardSurfaceView extends SurfaceView implements SurfaceHolder.Callback {
    private ArrayList<Naipe> cartas;

    public CardSurfaceView(Context context, AttributeSet attrs) {
        super(context, attrs);
        getHolder().addCallback(this);
    }

    public void setCartas(Naipe naipe) {
        this.cartas = new ArrayList<>();
        this.cartas.add(naipe);
        draw();
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        draw();
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
        draw();
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {}

    private void draw() {
        if (cartas != null) {
            Canvas canvas = getHolder().lockCanvas();
            if (canvas != null) {
                try {
                    canvas.drawColor(Color.WHITE);
                    for (int i = 0; i < cartas.size(); i++) {
                        Drawable drawable = ContextCompat.getDrawable(getContext(), cartas.get(i).getImagen());

                        // Calcular la posición de la carta en la fila
                        int cardX = i * (drawable.getIntrinsicWidth() + 15);

                        // Dibujar la carta en el superficie
                        drawable.setBounds(cardX, 0, cardX + drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight());
                        drawable.draw(canvas);
                    }
                } finally {
                    getHolder().unlockCanvasAndPost(canvas);
                }
            }
        }
    }
}

