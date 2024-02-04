package com.example.blackjack;

import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class Cliente {

    private Socket s;
    private BufferedReader in;
    private PrintWriter out;
    private String hash;

    public Cliente() {
    }

    private void conectar() throws IOException {
        s = new Socket("10.0.2.2", 9999);
        in = new BufferedReader(new InputStreamReader(s.getInputStream()));
        out = new PrintWriter(s.getOutputStream());
    }

    public boolean iniciar(String usuario) throws IOException {
        conectar();
        out.println("nueva:"+usuario);
        out.flush();
        String respuesta = in.readLine();
        if (respuesta.startsWith("OK#")) {
            hash = respuesta.split("#")[1];
            s.close();
            return true;
        }
        else {
            Log.w("Cliente", "Error al iniciar la partida: " + respuesta);
            s.close();
            return false;
        }
    }

    private String leerRespuesta() throws IOException {
        String linea;
        String respuesta = "";
        while ((linea = in.readLine()) != null) {
            if (!respuesta.isEmpty()) respuesta += "\n";
            respuesta += linea;
        }
        return respuesta;
    }

    public String repartir() throws IOException {
        conectar();
        out.println("repartir#"+hash);
        out.flush();
        String respuesta = leerRespuesta();
        s.close();
        if (!respuesta.startsWith("ERROR:")) {
            return respuesta;
        }
        else {
            Log.w("Cliente", "Error al repartir: " + respuesta);
            return null;
        }
    }

    public String pedir() throws IOException {
        conectar();
        out.println("pedir#"+hash);
        out.flush();
        String respuesta = leerRespuesta();
        s.close();
        if (!respuesta.startsWith("ERROR:")) {
            return respuesta;
        }
        else {
            Log.w("Cliente", "Error al pedir: " + respuesta);
            return null;
        }
    }
    public String plantarse() throws IOException {
        conectar();
        out.println("plantarse#"+hash);
        out.flush();
        String respuesta = leerRespuesta();
        s.close();
        if (!respuesta.startsWith("ERROR:")) {
            return respuesta;
        }
        else {
            Log.w("Cliente", "Error al plantarse: " + respuesta);
            return null;
        }
    }
    public String fin() throws IOException {
        conectar();
        out.println("fin#"+hash);
        out.flush();
        String respuesta = leerRespuesta();
        s.close();
        if (!respuesta.startsWith("ERROR:")) {
            return respuesta;
        }
        else {
            Log.w("Cliente", "Error al finalizar: " + respuesta);
            return null;
        }
    }
}