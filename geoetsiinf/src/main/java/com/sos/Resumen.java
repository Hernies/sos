package com.sos;

import java.util.ArrayList;

public class Resumen {

    private Usuario datos;
	private ArrayList<Tesoro> ultimos5tesorosEncontrados;
	private int nTesorosEncontrados;
    private int nAmigos;
    private int nTesorosA単adidos;


    
    public Resumen(Usuario user, ArrayList<Tesoro> ultimos5tesoros, int nTesorosEncontrados,
                    int nAmigos, int nTesorosA単adidos ) {
		
		this.datos = user;
		this.ultimos5tesorosEncontrados = ultimos5tesoros;
		this.nTesorosEncontrados = nTesorosEncontrados;
		this.nAmigos = nAmigos;
		this.nTesorosA単adidos = nTesorosA単adidos;
	}
}

