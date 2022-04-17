package com.sos;
import java.util.Date;
import java.time.*;
import javax.json.bind.annotation.JsonbDateFormat;


public class Tesoro {
	
	private int id;
	private Date fecha;
	private Float latitud;
	private Float longitud;
	private String tamaño;
	private String dificultad;
	private String tipo_terreno;
	private String pista;
	private String ID_usuario;
	
	public Tesoro(int id, Date fecha, float f, float g, String tamaño, String dificultad,
			String tipo_terreno,String pista ,String iD_usuario) {
		super();
		this.id = id;
		this.fecha = fecha;
		this.latitud = f;
		this.longitud = g;
		this.tamaño = tamaño;
		this.dificultad = dificultad;
		this.tipo_terreno = tipo_terreno;
		this.pista = pista;
		this.ID_usuario = iD_usuario;
	}
	
	public Tesoro() {
		
	}
	public boolean conNull(){
		boolean hasNull=(this.fecha==null) || (this.latitud==null) || (this.longitud==null) || (this.tamaño==null) ||
		(this.dificultad==null) || (this.tipo_terreno==null) || (this.ID_usuario==null);

		return hasNull;
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
	public Float getLatitud() {
		return latitud;
	}
	public void setLatitud(Float latitud) {
		this.latitud = latitud;
	}
	public Float getLongitud() {
		return longitud;
	}
	public void setLongitud(Float longitud) {
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
