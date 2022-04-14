package com.sos;
import java.util.Date;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "tesoro")
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
	@XmlAttribute
	public int getId() {
		return id;
	}
	@XmlAttribute
	public void setId(int id) {
		this.id = id;
	}
	@XmlAttribute
	public Date getFecha() {
		return fecha;
	}
	@XmlAttribute
	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}
	@XmlAttribute
	public long getLatitud() {
		return latitud;
	}
	@XmlAttribute
	public void setLatitud(long latitud) {
		this.latitud = latitud;
	}
	@XmlAttribute
	public long getLongitud() {
		return longitud;
	}
	@XmlAttribute
	public void setLongitud(long longitud) {
		this.longitud = longitud;
	}
	@XmlAttribute
	public String getTamaño() {
		return tamaño;
	}
	@XmlAttribute
	public void setTamaño(String tamaño) {
		this.tamaño = tamaño;
	}
	@XmlAttribute
	public String getDificultad() {
		return dificultad;
	}
	@XmlAttribute
	public void setDificultad(String dificultad) {
		this.dificultad = dificultad;
	}
	@XmlAttribute
	public String getTipo_terreno() {
		return tipo_terreno;
	}
	@XmlAttribute
	public void setTipo_terreno(String tipo_terreno) {
		this.tipo_terreno = tipo_terreno;
	}
	@XmlAttribute
	public String getID_usuario() {
		return ID_usuario;
	}
	@XmlAttribute
	public void setID_usuario(String iD_usuario) {
		ID_usuario = iD_usuario;
	}
}
