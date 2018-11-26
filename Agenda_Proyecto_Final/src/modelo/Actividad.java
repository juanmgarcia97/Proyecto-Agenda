package modelo;

import java.io.Serializable;

public class Actividad implements Comparable, Serializable{

	
	
	public final static int EXAMEN=1;
	public final static int TAREA=2;
	public final static int TALLER=3;
	public final static int LABORATORIO=4;
	public final static int LECTRURA=5;
	
	public final static String SIN_INICIAR="Sin Iniciar";
	public final static String INICIADO="Iniciado";
	public final static String MAS_O_MENOS="Mas o Menos";
	public final static String AVANZADO="Avanzado";
	public final static String TERMINADO="Terminado";
	
	private String titulo;
	private String descripcion;
	private int tipo;
	private String progreso;
	private double aumento;

	private FechaDisposicion primeraDisposicion;
	private Fecha fechaEntrega;

	
	private Actividad izq;
	private Actividad der;
	

	public Actividad(String titulo, String descripcion, int tipo, String progreso) {
		this.titulo = titulo;
		this.descripcion = descripcion;
		this.tipo = tipo;
		this.progreso = progreso;
	}



	

	public Fecha getFechaEntrega() {
		return fechaEntrega;
	}


	public void setFechaEntrega(Fecha fechaEntrega) {
		this.fechaEntrega = fechaEntrega;
	}
	public FechaDisposicion getPrimeraDisposicion() {
		return primeraDisposicion;
	}

	public void setPrimeraDisposicion(FechaDisposicion primeraDisposicion) {
		this.primeraDisposicion = primeraDisposicion;
	}

	public void agregarDisposicion(FechaDisposicion f) {
		if (primeraDisposicion == null) {
			primeraDisposicion = f;
			System.out.println("Primera");
		} else {
			int res = primeraDisposicion.compareTo(f);
			if (res > 0) {
				primeraDisposicion.agregarAntes(f);
				primeraDisposicion = f;
				System.out.println("Agrega antes");
			} else {
				FechaDisposicion fechaTemp0 = null;
				FechaDisposicion fechaTemp1 = primeraDisposicion;
				res = fechaTemp1.compareTo(f);
				while (fechaTemp1 != null && res < 0) {
					fechaTemp0 = fechaTemp1;
					fechaTemp1 = fechaTemp1.getSiguiente();
				}
				fechaTemp0.agregarDespues(f);
				System.out.println("Agrega despues");
			}
		}
	}

	public void agregar(Actividad nuevo) {

		if(compareTo(nuevo)>0) {
			if(izq==null) {
				izq=nuevo;
			}
			else{
				izq.agregar(nuevo);
			}
		}
		else if(compareTo(nuevo)<0) {
			if(der==null) {
				der=nuevo;
			//	System.out.println("Se agrega a la der a "+ nuevo.getTitulo());

			}
			else{
				der.agregar(nuevo);
			}
		}
		
	}
	
	
	
	public void agregarPorTipo(Actividad nuevo) {
		if(tipo==nuevo.getTipo()) {
			if(izq==null) {
				izq=nuevo;
			}	
			else{
				izq.agregarPorTipo(nuevo);
			}
		}
		if(tipo>nuevo.getTipo()) {
			if(izq==null) {
				izq=nuevo;
			}
			else{
	//			System.out.println(izq.getIzq().getTitulo());
				izq.agregarPorTipo(nuevo);
			}
		}
		else if(tipo<nuevo.getTipo()) {
			if(der==null) {
		//		System.out.println("Se agrega a la der "+nuevo.getTitulo()+"   "+nuevo.getTipo());

				der=nuevo;
			}
			else{
				der.agregarPorTipo(nuevo);
			}
		}
	}

	public String getTitulo() {
		return titulo;
	}



	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}



	public String getDescripcion() {
		return descripcion;
	}



	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}



	public int getTipo() {
		return tipo;
	}
	


	public void setTipo(int tipo) {
		this.tipo = tipo;
	}



	public String getProgreso() {
		return progreso;
	}



	public void setProgreso(String progreso) {
		this.progreso = progreso;
	}



	@Override
	public int compareTo(Object a) {
		Actividad nuevo=(Actividad) a;
		int comp=titulo.compareTo(nuevo.getTitulo());
	//	System.out.println(fechaEntrega+"   "+nuevo.getFechaEntrega()+"  ="+comp);
		return comp;
	}
	
	
	



	public Actividad buscar(String titulob) {
		int comp=0;
	try {	
		comp=titulo.compareTo(titulob);
	}catch(Exception e) {
		
	}
		
	if(comp==0) {

			return this;
		}
		
		else if(comp>0) {
			
			if(izq==null) {
				return null;
			}
			else {
				return	izq.buscar(titulob);
			}
		}
		else {
			if(der==null) {
				return null;
			}
			else {
				return	der.buscar(titulob);
			}
		}
	
	
	
	}
		
		
		
	

	 public Actividad darMenor( )
	    {
		 if(izq==null) {
			 return this;
		 }
		 else {
			return izq.darMenor();
		 }
	    }


	public double getAumento() {
		return aumento;
	}



	public void setAumento(double aumento) {
		this.aumento = aumento;
	}

	public void incrementar() {
		aumento++;
	}




//	public void recorridoPreOrden(Actividad raizActividad) {
//		if(raizActividad!=null) {
//			System.out.println(raizActividad.getTitulo());
//		recorridoPreOrden(raizActividad.getIzq());
//		recorridoPreOrden(raizActividad.getDer());
//}
//}
	
	public Actividad getIzq() {
		return izq;
	}



	public void setIzq(Actividad izq) {
		this.izq = izq;
	}



	public Actividad getDer() {
		return der;
	}



	public void setDer(Actividad der) {
		this.der = der;
	}



	  public Actividad eliminar( String tit ){
	    	if(esHoja()) {
	    		return null;
	    	}
	    	if(titulo.compareTo(tit)==0) {
	    		if(izq==null) {
	    			return der;
	    		}
	    		if(der==null) {
	    			return izq;
	    		}
	    		Actividad sucesor=der.darMenor();
	    		der=der.eliminar(sucesor.titulo);
	    		sucesor.izq = izq;
	    		sucesor.der = der;
	    		return sucesor;
	    	}
	    	else if(titulo.compareTo(tit)==1) {
	    		izq= izq.eliminar(tit);
	    	}
	    	else {
	    		der = der.eliminar(tit);			
	    	}
	    	return this;
	        }



	private boolean esHoja() {
		if(izq==null&&der==null) {
			return true;
		}
		else {
		return false;
		}
	}
	    
	
	
	

	
	

	



}
