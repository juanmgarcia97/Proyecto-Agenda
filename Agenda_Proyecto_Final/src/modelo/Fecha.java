package modelo;

import java.io.Serializable;

public class Fecha implements Serializable{

	private int fechaDia;
	private int fechaMes;
	private int fechaAnnio;
	private String dia;
	
	
	private Fecha siguiente;
	private Fecha Anterior;
	
	
	public Fecha(int fechaDia, int fechaMes, int fechaAnnio, String dia) {
		this.fechaDia = fechaDia;
		this.fechaMes = fechaMes;
		this.fechaAnnio = fechaAnnio;
		this.dia = dia;
	}


	public int getFechaDia() {
		return fechaDia;
	}


	public void setFechaDia(int fechaDia) {
		this.fechaDia = fechaDia;
	}


	public int getFechaMes() {
		return fechaMes;
	}


	public void setFechaMes(int fechaMes) {
		this.fechaMes = fechaMes;
	}


	public int getFechaAnnio() {
		return fechaAnnio;
	}


	public void setFechaAnnio(int fechaAnnio) {
		this.fechaAnnio = fechaAnnio;
	}


	public String getDia() {
		return dia;
	}


	public void setDia(String dia) {
		this.dia = dia;
	}


	public Fecha getSiguiente() {
		return siguiente;
	}


	public void setSiguiente(Fecha siguiente) {
		this.siguiente = siguiente;
	}


	public Fecha getAnterior() {
		return Anterior;
	}


	public void setAnterior(Fecha anterior) {
		Anterior = anterior;
	}
	
	
	
}
