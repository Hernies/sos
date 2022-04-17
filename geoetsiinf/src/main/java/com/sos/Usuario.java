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

	
	public Usuario(String ID, String nombre, String apellidos, String localidad, String correo, int edad) {
		super();
		this.id = ID;
		this.nombre = nombre;
		this.apellidos = apellidos;
		this.localidad = localidad;
		this.correo = correo;
		this.edad = edad;
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
}