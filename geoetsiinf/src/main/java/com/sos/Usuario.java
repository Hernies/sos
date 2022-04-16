package com.sos;

import java.util.ArrayList;
import java.util.List;



public class Usuario {
	private String id;
	private String nombre;
	private String apellidos;
	private String localidad;
	private String correo;
	private int edad;
	private List<Tesoro> tesoros_añadidos;
	private List<Tesoro> tesoros_descubiertos;
	
	public Usuario(String ID, String nombre, String apellidos, String localidad, String correo, int edad) {
		super();
		this.id = ID;
		this.nombre = nombre;
		this.apellidos = apellidos;
		this.localidad = localidad;
		this.correo = correo;
		this.edad = edad;
		tesoros_añadidos = new ArrayList<Tesoro>();
		tesoros_descubiertos = new ArrayList<Tesoro>();
	}
	
	public Usuario() {
		
	}
	public boolean conNull(){
		boolean hasNull=(this.id==null)||(this.nombre==null)||(this.apellidos==null)||(this.localidad==null)||
		(this.correo==null);

		return hasNull;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public String getApellidos() {
		return apellidos;
	}
	public void setApellidos(String apellidos) {
		this.apellidos = apellidos;
	}
	public String getLocalidad() {
		return localidad;
	}
	public void setLocalidad(String localidad) {
		this.localidad = localidad;
	}
	public String getCorreo() {
		return correo;
	}
	public void setCorreo(String correo) {
		this.correo = correo;
	}
	public int getEdad() {
		return edad;
	}
	public void setEdad(int edad) {
		this.edad = edad;
	}
    public List<Tesoro> getTesoros_añadidos() {
        return tesoros_añadidos;
    }
    public void setTesoros_añadidos(List<Tesoro> tesoros_añadidos) {
        this.tesoros_añadidos = tesoros_añadidos;
    }
    public List<Tesoro> getTesoros_descubiertos() {
        return tesoros_descubiertos;
    }
    public void setTesoros_descubiertos(List<Tesoro> tesoros_descubiertos) {
        this.tesoros_descubiertos = tesoros_descubiertos;
    }
}