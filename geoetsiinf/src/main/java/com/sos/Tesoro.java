package com.sos;
import java.util.Date;


public class Tesoro {
	
	private int id;
	private Date fecha;
	private long latitud;
	private long longitud;
	private String tamaño;
	private String dificultad;
	private String tipo_terreno;
	private String ID_usuario;
	
	public Tesoro(int id, Date fecha, long latitud, long longitud, String tamaño, String dificultad,
			String tipo_terreno, String iD_usuario) {
		super();
		//this.id = id;
		this.fecha = fecha;
		this.latitud = latitud;
		this.longitud = longitud;
		this.tamaño = tamaño;
		this.dificultad = dificultad;
		this.tipo_terreno = tipo_terreno;
		ID_usuario = iD_usuario;
	}
	
	public Tesoro() {
		
	}
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public Date getFecha() {
		return fecha;
	}
	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}
	public long getLatitud() {
		return latitud;
	}
	public void setLatitud(long latitud) {
		this.latitud = latitud;
	}
	public long getLongitud() {
		return longitud;
	}
	public void setLongitud(long longitud) {
		this.longitud = longitud;
	}
	public String getTamaño() {
		return tamaño;
	}
	public void setTamaño(String tamaño) {
		this.tamaño = tamaño;
	}
	public String getDificultad() {
		return dificultad;
	}
	public void setDificultad(String dificultad) {
		this.dificultad = dificultad;
	}
	public String getTipo_terreno() {
		return tipo_terreno;
	}
	public void setTipo_terreno(String tipo_terreno) {
		this.tipo_terreno = tipo_terreno;
	}
	public String getID_usuario() {
		return ID_usuario;
	}
	public void setID_usuario(String iD_usuario) {
		ID_usuario = iD_usuario;
	}
}
