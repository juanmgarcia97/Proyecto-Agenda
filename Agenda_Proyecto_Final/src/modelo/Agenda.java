package modelo;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

public class Agenda {

	private int cantidadActividades;
	
	public Actividad raizActividad;
	public Actividad prueba;
	private String informaion;

	public ArrayList<Actividad> listas;
	
	public Agenda() {
		listas=new ArrayList<Actividad>();
	}
	
	public void agregarActividad(Actividad nuevo) {
		if(raizActividad==null) {
			raizActividad=nuevo;
			cantidadActividades++;
		}
		else {
			raizActividad.agregar(nuevo);
			cantidadActividades++;
		}
		
		
	}
	
	public void editar(Actividad editar, String titulo) {
		
		buscarActividadBinario(titulo).setDescripcion(editar.getDescripcion());
		buscarActividadBinario(titulo).setTipo(editar.getTipo());
		buscarActividadBinario(titulo).setProgreso(editar.getProgreso());
		buscarActividadBinario(titulo).setFechaEntrega(editar.getFechaEntrega());
		buscarActividadBinario(titulo).setTitulo(editar.getTitulo());

	}
	
	public void eliminar(String titulo) {
	        raizActividad = raizActividad.eliminar( titulo );
	        	    
	}
	
	public Actividad buscarActividadBinario(String titulo) {
		
		if(raizActividad!=null) {
			if(raizActividad.getTitulo().equals(titulo)) {
				return raizActividad;
			}
			else {
				return raizActividad.buscar(titulo);
					
				
			}
		}
		return null;
		
	}
	
	public void imprimirArchivoDeTexto(String nombre) {
		informacion(raizActividad);
		File f=new File("archivos/"+nombre);
		try {
			FileWriter fw=new FileWriter(f);
			BufferedWriter bf=new BufferedWriter(fw);
			bf.write(informaion);
			bf.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public void informacion(Actividad a) {
		if(a.getIzq()!=null) {
		informacion(a.getIzq());
		}
		informaion+="Titulo: "+a.getTitulo()+"\ny la fecha de entrega es: "+a.getFechaEntrega().getDia()+"\n\n";
		
		if(a.getDer()!=null) {
		informacion(a.getDer());
		
		}
	}
	
	
	public void guardarActividadesSerializable() {
		File f=new File("archivos/actividades.dat");
		try {
			FileOutputStream fos = new FileOutputStream(f);
			ObjectOutputStream ous=new ObjectOutputStream(fos);

			ous.writeObject(raizActividad);
			ous.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	
	public void cargarActividades() {
		File f=new File("archivos/actividades.dat");
			try {
				FileInputStream fis = new FileInputStream(f);
				ObjectInputStream ois=new ObjectInputStream(fis);
				
			Actividad recuperada=(Actividad)ois.readObject();
			raizActividad=recuperada;
			ois.close();
			
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.getStackTrace();
			} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		
	}
	

	public void ordenarPorTipo() {
		
		raizActividad=null;
		for (int i = 0; i < listas.size(); i++) {
			listas.get(i).setDer(null);
			listas.get(i).setIzq(null);
			if(raizActividad==null) {		
				raizActividad=listas.get(i);
			}
			else {
				raizActividad.agregarPorTipo(listas.get(i));
			}
		}
	}
	
	

	private ArrayList<Actividad> ordenarPorDisponibilidad() {
		// TODO Auto-generated method stub
		return null;
	}

	private ArrayList<Actividad> ordenarPorPrioridad() {
		// TODO Auto-generated method stub
		return null;
	}
	
	
	

	
	
	public Actividad getRaizActividad() {
		return raizActividad;
	}

	public void setRaizActividad(Actividad raizActividad) {
		this.raizActividad = raizActividad;
	}

	
	
	
	
	
//	public void comprobar1() {
//		PruebaAgregarActividades();
//	 //cargarActividades();
//	// imprimirPreOrden();
//	 
//	}
//
//	
//
//	
//	
//	
//	
//	public void comprobar2(String text, Actividad a) {
//		editar(a, text);
//	///	imprimirPreOrden();
//		
//	}
	
	
	
	public void PruebaAgregarActividades() {
		Actividad a1=new Actividad("titulo1", "descripcion1", 1, Actividad.INICIADO);
		Fecha e1=new Fecha(1, 2, 2018, "lunes");
		FechaDisposicion f1=new FechaDisposicion(10, 22, 2018, "Martes");
		a1.setFechaEntrega(e1);
		a1.agregarDisposicion(f1);
		
		Actividad a2=new Actividad("titulo2", "descripcion1", 5, Actividad.MAS_O_MENOS);
		Fecha e2=new Fecha(13, 22, 2018, "lunes");
		FechaDisposicion f2=new FechaDisposicion(15, 26, 2018, "Martes");
		a2.setFechaEntrega(e2);
		a2.agregarDisposicion(f2);
		
		Actividad a3=new Actividad("titulo3", "descripcion1", 1, Actividad.TERMINADO);
		Fecha e3=new Fecha(11, 12, 2018, "lunes");
		FechaDisposicion f3=new FechaDisposicion(15, 22, 2018, "Martes");
		a3.setFechaEntrega(e3);
		a3.agregarDisposicion(f3);
		
		Actividad a4=new Actividad("titulo4", "descripcion1", 2, Actividad.INICIADO);
		Fecha e4=new Fecha(1, 2, 2018, "lunes");
		FechaDisposicion f4=new FechaDisposicion(10, 22, 2018, "Martes");
		a4.setFechaEntrega(e4);
		a4.agregarDisposicion(f4);
		
		Actividad a5=new Actividad("titulo5", "descripcion1", 5, Actividad.MAS_O_MENOS);
		Fecha e5=new Fecha(1, 2, 2018, "lunes");
		FechaDisposicion f5=new FechaDisposicion(10, 22, 2018, "Martes");
		a5.setFechaEntrega(e5);
		a5.agregarDisposicion(f5);
		
		Actividad a6=new Actividad("titulo6", "descripcion1", 1, Actividad.AVANZADO);
		Fecha e6=new Fecha(1, 2, 2018, "lunes");
		FechaDisposicion f6=new FechaDisposicion(10, 22, 2018, "Martes");
		a6.setFechaEntrega(e6);
		a6.agregarDisposicion(f6);
		
		agregarActividad(a1);
		agregarActividad(a2);
		agregarActividad(a3);
		agregarActividad(a4);
		agregarActividad(a5);
		agregarActividad(a6);
		
		inorden(raizActividad);
//		añadirArbolALista(raizActividad);
//		ordenarPorTipo();
		System.out.println("-------------------");
//		inorden(raizActividad);
		
		eliminar("titulo3");
		inorden(raizActividad);
		
	
	}
 public void inorden(Actividad a) {
	 if(a!=null) {
	 inorden(a.getIzq());
	 System.out.println(a.getTitulo()+"   "+a.getTipo());
	 inorden(a.getDer());
	 }
 }
 
	
 public ArrayList<Actividad> añadirArbolALista(Actividad a) {
	 if(a!=null) {
		 añadirArbolALista(a.getIzq());
	 listas.add(a);
	 añadirArbolALista(a.getDer());
	 }
	 return listas;
 }
 
 public void imprimirLista() {
	 for (int i = 0; i < listas.size(); i++) {
		System.out.print(listas.get(i).getTitulo()+"   "+ listas.get(i).getTipo());
	}
 }
	
	
}