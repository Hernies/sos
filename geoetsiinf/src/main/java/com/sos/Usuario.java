package com.sos;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "usuario")
public class Usuario {
	private int id;
	private String nombre;
	private String apellidos;
	private String localidad;
	private String correa;
	private int edad;
	private List<Tesoro> tesoros_añadidos;
	private List<Tesoro> tesoros_descubiertos;
	
	public Usuario(int id, String nombre, String apellidos, String localidad, String correa, int edad) {
		super();
		//this.id = id;
		this.nombre = nombre;
		this.apellidos = apellidos;
		this.localidad = localidad;
		this.correa = correa;
		this.edad = edad;
		tesoros_añadidos = new ArrayList<Tesoro>();
		tesoros_descubiertos = new ArrayList<Tesoro>();
	}
	
	public Usuario() {
		
	}
	@XmlAttribute
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	@XmlAttribute
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	@XmlAttribute
	public String getApellidos() {
		return apellidos;
	}
	public void setApellidos(String apellidos) {
		this.apellidos = apellidos;
	}
	@XmlAttribute
	public String getLocalidad() {
		return localidad;
	}
	public void setLocalidad(String localidad) {
		this.localidad = localidad;
	}
	@XmlAttribute
	public String getCorrea() {
		return correa;
	}
	public void setCorrea(String correa) {
		this.correa = correa;
	}
	@XmlAttribute
	public int getEdad() {
		return edad;
	}
	public void setEdad(int edad) {
		this.edad = edad;
	}
	@XmlAttribute
    public List<Tesoro> getTesoros_añadidos() {
        return tesoros_añadidos;
    }
    public void setTesoros_añadidos(List<Tesoro> tesoros_añadidos) {
        this.tesoros_añadidos = tesoros_añadidos;
    }
	@XmlAttribute
    public List<Tesoro> getTesoros_descubiertos() {
        return tesoros_descubiertos;
    }
    public void setTesoros_descubiertos(List<Tesoro> tesoros_descubiertos) {
        this.tesoros_descubiertos = tesoros_descubiertos;
    }
	@XmlAttribute
	public void addTesoro_añadidos(List<Tesoro> tesoros_añadidos, Tesoro tesoro){
		
	}
}