package modelo;

public class FechaDisposicion  extends Fecha implements Comparable<FechaDisposicion> {

	private FechaDisposicion siguiente;
	private FechaDisposicion anterior;

	public FechaDisposicion(int fechaDia, int fechaMes, int fechaAnnio, String dia) {
		super(fechaDia, fechaMes, fechaAnnio, dia);
		// TODO Auto-generated constructor stub
	}

	public FechaDisposicion getSiguiente() {
		return siguiente;
	}

	public void setSiguiente(FechaDisposicion siguiente) {
		this.siguiente = siguiente;
	}

	public FechaDisposicion getAnterior() {
		return anterior;
	}

	public void setAnterior(FechaDisposicion anterior) {
		this.anterior = anterior;
	}
	
	public void agregarDespues(FechaDisposicion f) {
		f.siguiente = siguiente;
        if( siguiente != null )
            siguiente.anterior = f;
        f.anterior = this;
        siguiente = f;
	}
	
	public void agregarAntes(FechaDisposicion f) {
		if( anterior != null )
            anterior.siguiente = f;
        f.anterior = anterior;
        f.siguiente = this;
        anterior = f;
	}

	public void agregarDisposicion(FechaDisposicion f) {
		// TODO Auto-generated method stub
		int res = this.compareTo(f);
		if(res == -1) {
			if(anterior == null) {
				anterior = f;
			} else {
				anterior.agregarDisposicion(f);
			}
		} else if(res == 1 || res == 0) {
			if(siguiente == null) {
				siguiente = f;
			} else {
				siguiente.agregarDisposicion(f);
			}
		}
	}
	
	@Override
	public int compareTo(FechaDisposicion f) {
		if (super.getFechaAnnio() < f.getFechaAnnio()) {
			return -1;
		} else if (super.getFechaAnnio() > f.getFechaAnnio()) {
			return 1;
		} else {
			if (super.getFechaMes() < f.getFechaMes()) {
				return -1;
			} else if (super.getFechaMes() > f.getFechaMes()) {
				return 1;
			} else {
				if (super.getFechaDia() < f.getFechaDia()) {
					return -1;
				} else if (super.getFechaDia() > f.getFechaDia()) {
					return 1;
				} else {
					return 0;
				}
			}
		}
	}

	
}
