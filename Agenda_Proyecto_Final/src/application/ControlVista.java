package application;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Optional;

import excepciones.NoHayFechaDisposicionAnteriorException;
import excepciones.NoHayFechaDisposicionSiguienteException;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputDialog;
import javafx.scene.control.Toggle;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Duration;
import modelo.Actividad;
import modelo.Fecha;
import modelo.FechaDisposicion;

public class ControlVista {

	//imagen portada
	@FXML
	private Pane pBanner;
	
	
	
	// Panel con todad las actividades ingresadas
	@FXML
	private ScrollPane scPane;
	@FXML
	private GridPane listaActividades;
	
	private Button btSiguiente;
	private Button btAnterior;
	private Button btProgreso;
	private ProgressBar pb;
	private ProgressIndicator pi;
	private Timeline tl;
	private RadioButton rbA;

	
	// Panel con las opciones de editar agregar eliminar etc...
	@FXML
	private HBox hbOpciones;
	
	@FXML
	private ToggleGroup organizar;
	
	private RadioButton rbOrganizar;

	
	//panel para agregar una actividad
	@FXML
	private Pane pAgregarActividad;
	@FXML
	private TextField txtTitulo;
	@FXML
	private TextField txtDescripcion;
	@FXML
	private DatePicker dpEntrega;
	@FXML
	private MenuButton mbTipo;
	@FXML
	private ToggleGroup progreso;
	@FXML
	private DatePicker dpDisposicion;	
	@FXML
	private Button btAgregar;
	@FXML
	private Button btGuardar;
	@FXML
	private Button btCancelar;
	@FXML
	private HBox Aceptar_Cancelar;
	@FXML
	private GridPane gpDisposicion;
	
	
	//informacion extra
	private Label fechaDis;
	private Label diaDis;
	private Label elTipo;	
	private int tipo;
	private int fila = 0;
	private int filaDisp = 0;
	

	private ArrayList<FechaDisposicion> fechasDisposicion;
	
	private Stage dialogStage;
	private String ActividadEditar;
	
	private Main m;

	public void initialize() {
		
		m = new Main();
		m.inicializar();
		menuTipo2();
		Opcionprogreso();
		OpcionOrganizar();
		rbA = (RadioButton) progreso.getSelectedToggle();
		fechasDisposicion=new ArrayList<FechaDisposicion>();
	}
	
	public void guardarSerializable() {
		m.darAgenda().guardarActividadesSerializable();
	}
	
//	public void comprobar1() {
//		m.darAgenda().comprobar1();
//	}
//	public void comprobar2() {
//	//	Actividad a=new Actividad("titulo cambia", txtDescripcion.getText(), 2, "pr", "18 jul");
//	//	m.darAgenda().comprobar2(txtTitulo.getText(),a);
//	}
//	
	public void comprobar3() {
		m.darAgenda().PruebaAgregarActividades();
	}

	public void PanelopcionAgregar() {
		pBanner.setVisible(false);
		scPane.setVisible(false);
		hbOpciones.setVisible(false);
		pAgregarActividad.setVisible(true);
		ActionAgregar();
	}
	public void PanelopcionEditar() {
		pBanner.setVisible(false);
		scPane.setVisible(false);
		hbOpciones.setVisible(false);
		pAgregarActividad.setVisible(true);
		ActionEditar();
	}
	
	public void opcionCancelar() {
		pBanner.setVisible(true);
		scPane.setVisible(true);
		hbOpciones.setVisible(true);
		pAgregarActividad.setVisible(false);
	}
	
	
	public void ActionAgregar() {
		btGuardar.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				agregarActividad();
			}
		});
	}
	
	
	public void ActionEditar() {
		btGuardar.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				guardarEdicion();
			}
		});
	}
	
	
	public void agregarActividad() {
		mostrarDatosActividad(txtTitulo.getText(), txtDescripcion.getText(), mbTipo.getText(), rbA.getText(), dpEntrega.getValue().toString()+"-"+dpEntrega.getValue().getDayOfWeek().toString(),dpDisposicion.getValue().toString()+"-"+dpDisposicion.getValue().getDayOfWeek().toString());
	}
	
//	public void agregarFechasDisponibles() {
//		if(dpDisposicion!=null) {
//			fechasDisposicion.add(dpDisposicion.getValue().toString()+"-"+dpDisposicion.getValue().getDayOfWeek().toString());
//			}
//			
//	}
	
	
	public void editar() {
		TextInputDialog dialog = new TextInputDialog("walter");
		dialog.setTitle("Editar Actividad");
		dialog.setHeaderText("titulo");
		dialog.setContentText("Escribe el titulo de la actividad \n que desea modificar:");

		Optional<String> result = dialog.showAndWait();
		if (result.isPresent()){
			ActividadEditar=result.get();
		PanelopcionEditar();
		Editar();
}
		result.ifPresent(name -> System.out.println("Your name: " + name));
	}
	
	
	public void cargarActividad() {
		
		m.darAgenda().cargarActividades();
		Actividad raiz=m.darAgenda().getRaizActividad();
		 recorridoInOrden(raiz);

	}
	
	public void eliminar() {
		TextInputDialog dialog = new TextInputDialog();
		dialog.setTitle("Eliminar Actividad");
		dialog.setHeaderText("titulo");
		dialog.setContentText("Escribe el titulo de la actividad \nque desea eliminar:");

		Optional<String> result = dialog.showAndWait();
		if (result.isPresent()){
			m.darAgenda().eliminar(result.get());
			listaActividades.getChildren().clear();
			recorridoInOrden(m.darAgenda().getRaizActividad());	

}
		result.ifPresent(name -> System.out.println("Your name: " + name));
	}
	
	public void Editar() {
		Actividad ActEditable=m.darAgenda().buscarActividadBinario(ActividadEditar);
		txtTitulo.setText(ActEditable.getTitulo());
		txtDescripcion.setText(ActEditable.getTitulo());
		Fecha fechas=ActEditable.getFechaEntrega();
		LocalDate f=LocalDate.of((fechas.getFechaDia()), (fechas.getFechaMes()), (fechas.getFechaAnnio()));
		dpEntrega.setValue(f);
		rbA=new RadioButton(ActEditable.getProgreso());
		rbA.setText(ActEditable.getProgreso());
		Fecha fechasD=ActEditable.getPrimeraDisposicion();
		LocalDate f2=LocalDate.of((fechasD.getFechaDia()), (fechasD.getFechaMes()), (fechasD.getFechaAnnio()));
		dpDisposicion.setValue(f2);
	
		
	}
public void guardarEdicion() {
	String[] fEntrega= (dpEntrega.getValue().toString()+"-"+dpEntrega.getValue().getDayOfWeek().toString()).split("-");
	
	String[] fDisposicion=(dpDisposicion.getValue().toString()+"-"+dpDisposicion.getValue().getDayOfWeek().toString()).split("-");
	
	if(mbTipo.getText().equalsIgnoreCase("Examen")) tipo = 1;
	if(mbTipo.getText().equalsIgnoreCase("Tarea")) tipo = 2;
	if(mbTipo.getText().equalsIgnoreCase("Taller")) tipo = 3;
	if(mbTipo.getText().equalsIgnoreCase("Laboratorio")) tipo = 4;
	if(mbTipo.getText().equalsIgnoreCase("Lectura")) tipo = 5;
	
	Actividad acti = new Actividad(txtTitulo.getText(), txtDescripcion.getText(), tipo, rbA.getText());
	Fecha fe=new Fecha(Integer.parseInt(fEntrega[0]), Integer.parseInt(fEntrega[1]), Integer.parseInt(fEntrega[2]), fEntrega[3]);
	FechaDisposicion fd=new FechaDisposicion(Integer.parseInt(fDisposicion[0]), Integer.parseInt(fDisposicion[1]), Integer.parseInt(fDisposicion[2]), fDisposicion[3]);
	acti.agregarDisposicion(fd);
	acti.setFechaEntrega(fe);
	m.darAgenda().editar(acti, ActividadEditar);
	pBanner.setVisible(true);
	scPane.setVisible(true);
	hbOpciones.setVisible(true);
	pAgregarActividad.setVisible(false);
	listaActividades.getChildren().clear();
recorridoInOrden(m.darAgenda().getRaizActividad());
}

	
	public void recorridoInOrden(Actividad raizActividad) {
		if(raizActividad!=null) {
		
			recorridoInOrden(raizActividad.getIzq());
			Actividad a =raizActividad;
			Fecha fe=a.getFechaEntrega();
			FechaDisposicion fd=a.getPrimeraDisposicion();
			String fechasEntregas=fe.getFechaAnnio()+"-"+fe.getFechaMes()+"-"+fe.getFechaDia()+"-"+fe.getDia();
			String fechasDisposicion=fd.getFechaAnnio()+"-"+fd.getFechaMes()+"-"+fd.getFechaDia()+"-"+fd.getDia();
	
			rbA=new RadioButton(a.getProgreso());
			String tipo="";
			if(a.getTipo()==1) tipo = "Examen";
			if(a.getTipo()==2) tipo = "Tarea";
			if(a.getTipo()==3) tipo = "Taller";
			if(a.getTipo()==4) tipo = "Laboratorio";
			if(a.getTipo()==5) tipo = "Lectura";
			
			mostrarDatosActividad(a.getTitulo(), a.getDescripcion(), tipo, a.getProgreso(), fechasEntregas, fechasDisposicion);
	
		recorridoInOrden(raizActividad.getDer());
}
}
	
	public void mostrarDatosActividad(String titulo, String descripcion, String tipoS, String progreso1, String fechaEntrega, String fechaDisposicion ) {
			
			Label a, b;
			VBox a1;
			HBox a2;
			String fEntrega[]=fechaEntrega.split("-");
			String fDisposicion[]=fechaDisposicion.split("-");
	//System.out.println(titulo+"  "+descripcion+"    "+ tipoS+"   "+progreso1+fechaEntrega+"    "+fechaDisposicion);
			listaActividades.setGridLinesVisible(true);
			a = new Label(fEntrega[3]);
				b = new Label(fEntrega[0]+"/"+fEntrega[1]+"/"+fEntrega[2]);
				a1 = new VBox(a, b);
				listaActividades.add(a1, 0, fila);
				
				a = new Label(titulo);
				b = new Label(descripcion);
				a1 = new VBox(a, b);
				listaActividades.add(a1, 1, fila);
				
				
				a = new Label(tipoS);
				listaActividades.add(a, 2, fila);
	
//				elTipo = new Label(progreso1);
				pb = new ProgressBar(0);
				pi = new ProgressIndicator(0);
				btProgreso = new Button("Progreso");
				a2 = new HBox(btProgreso, pb, pi);
				a1 = new VBox( a2);
				listaActividades.add(a1, 3, fila);
				
				
				a = new Label(fDisposicion[3]);
				b = new Label(fDisposicion[0]+"/"+fDisposicion[1]+"/"+fDisposicion[2]);
				a1 = new VBox(a, b);
				listaActividades.add(a1, 4, fila);
				
				if(tipoS.equalsIgnoreCase("Examen")) tipo = 1;
				if(tipoS.equalsIgnoreCase("Tarea")) tipo = 2;
				if(tipoS.equalsIgnoreCase("Taller")) tipo = 3;
				if(tipoS.equalsIgnoreCase("Laboratorio")) tipo = 4;
				if(tipoS.equalsIgnoreCase("Lectura")) tipo = 5;
				
				//System.out.println(txtTitulo.getText()+"   "+ txtDescripcion.getText()+"   "+  mbTipo.getText()+"   "+  rbA.getText()+"   "+  dpEntrega.getValue().toString()+"-"+dpEntrega.getValue().getDayOfWeek().toString()+"   "+ dpDisposicion.getValue().toString()+"-"+dpDisposicion.getValue().getDayOfWeek().toString());

				Actividad acti = new Actividad(titulo, descripcion, tipo, progreso1);
				Fecha fe=new Fecha(Integer.parseInt(fEntrega[0]), Integer.parseInt(fEntrega[1]), Integer.parseInt(fEntrega[2]), fEntrega[3]);
				FechaDisposicion fd=new FechaDisposicion(Integer.parseInt(fDisposicion[0]), Integer.parseInt(fDisposicion[1]), Integer.parseInt(fDisposicion[2]), fDisposicion[3]);
				acti.agregarDisposicion(fd);
				acti.setFechaEntrega(fe);
				m.darAgenda().agregarActividad(acti);
				

				mostrarProgreso(btProgreso, pb, pi,acti);
				
				dpEntrega.setValue(null);
				txtTitulo.setText("");
				txtDescripcion.setText("");
				mbTipo.setText("");
				dpDisposicion.setValue(null);
				fila++;
				
	
			pBanner.setVisible(true);
			pAgregarActividad.setVisible(false);
			scPane.setVisible(true);
			hbOpciones.setVisible(true);
		
//		Label a, b;
//		VBox a1;
//		HBox a2;
//		String fEntrega[] = fechaEntrega.split("-");
//		String fDisposicion[] = fechaDisposicion.split("-");
//		Actividad acti = new Actividad(titulo, descripcion, tipo, progreso1);
//		Fecha fe = new Fecha(Integer.parseInt(fEntrega[0]), Integer.parseInt(fEntrega[1]),
//				Integer.parseInt(fEntrega[2]), fEntrega[3]);
//		FechaDisposicion fd = new FechaDisposicion(Integer.parseInt(fDisposicion[0]), Integer.parseInt(fDisposicion[1]),
//				Integer.parseInt(fDisposicion[2]), fDisposicion[3]);
//		m.darAgenda().agregarActividad(acti);
//		acti.setFechaEntrega(fe);
////		agregarDisp(fd, acti);
//		for (int i = 0; i < fechasDisposicion.size(); i++) {
//			acti.agregarDisposicion(fechasDisposicion.get(i));
//		}
//		listaActividades.setGridLinesVisible(true);
//
//		a = new Label(fEntrega[3]);
//		b = new Label(fEntrega[0] + "/" + fEntrega[1] + "/" + fEntrega[2]);
//		a1 = new VBox(a, b);
//		listaActividades.add(a1, 0, fila);
//
//		a = new Label(titulo);
//		b = new Label(descripcion);
//		a1 = new VBox(a, b);
//		listaActividades.add(a1, 1, fila);
//
//		a = new Label(tipoS);
//		listaActividades.add(a, 2, fila);
//
//		elTipo = new Label(progreso1);
//		pb = new ProgressBar(0);
//		pi = new ProgressIndicator(0);
//		btProgreso = new Button("Progreso");
//		a2 = new HBox(btProgreso, pb, pi);
//		a1 = new VBox(elTipo, a2);
//		listaActividades.add(a1, 3, fila);
//
//		diaDis = new Label(acti.getPrimeraDisposicion().getDia());
//		fechaDis = new Label(acti.getPrimeraDisposicion().toString());
//		btSiguiente = new Button("->");
//		btAnterior = new Button("<-");
//		HBox c = new HBox(btAnterior, btSiguiente);
//		a1 = new VBox(diaDis, fechaDis, c);
//		listaActividades.add(a1, 4, fila);
//		try {
//			mostrarDisposiciones(btAnterior, btSiguiente, acti.getPrimeraDisposicion(), fechaDis, diaDis);
//		} catch (NoHayFechaDisposicionAnteriorException | NoHayFechaDisposicionSiguienteException e) {
//			// TODO Auto-generated catch block
//			Alert alert = new Alert(AlertType.ERROR);
//			alert.setContentText(e.getMessage());
//			alert.show();
//		}
//
//		if (tipoS.equalsIgnoreCase("Examen"))
//			tipo = 1;
//		if (tipoS.equalsIgnoreCase("Tarea"))
//			tipo = 2;
//		if (tipoS.equalsIgnoreCase("Taller"))
//			tipo = 3;
//		if (tipoS.equalsIgnoreCase("Laboratorio"))
//			tipo = 4;
//		if (tipoS.equalsIgnoreCase("Lectura"))
//			tipo = 5;
//		
//		mostrarProgreso(btProgreso, pb, pi, acti);
//		dpEntrega.setValue(null);
//		txtTitulo.setText("");
//		txtDescripcion.setText("");
//		mbTipo.setText("");
//		dpDisposicion.setValue(null);
//		fila++;
//
//		pBanner.setVisible(true);
//		pAgregarActividad.setVisible(false);
//		scPane.setVisible(true);
//		hbOpciones.setVisible(true);
		}
	
	public void mostrarDisposiciones(Button btAnterior, Button btSiguiente, FechaDisposicion acti, Label fechaDis,
			Label diaDis) throws NoHayFechaDisposicionAnteriorException, NoHayFechaDisposicionSiguienteException{
		btAnterior.setOnMouseClicked(new EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent event) {
				// TODO Auto-generated method stub
				if (acti.getAnterior() != null) {
					FechaDisposicion anterior = acti.getAnterior();
					fechaDis.setText(
							anterior.getFechaAnnio() + "/" + anterior.getFechaMes() + "/" + anterior.getFechaDia());
					diaDis.setText(anterior.getDia());
//					mostrarDisposiciones(btAnterior, btSiguiente, acti.getAnterior(), fechaDis, diaDis);
				}
			}
		});
		btSiguiente.setOnMouseClicked(new EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent event) {
				// TODO Auto-generated method stub
				if (acti.getSiguiente() != null) {
					FechaDisposicion siguiente = acti.getSiguiente();
					fechaDis.setText(
							siguiente.getFechaAnnio() + "/" + siguiente.getFechaMes() + "/" + siguiente.getFechaDia());
					diaDis.setText(siguiente.getDia());
//					mostrarDisposiciones(btAnterior, btSiguiente, acti.getSiguiente(), fechaDis, diaDis);
				}
			}
		});

	}
	
//	public void agregarDisp(FechaDisposicion f, Actividad acti) {
//		String[] disposicion = dpDisposicion.getValue().toString().split("-");
//		FechaDisposicion fechad = new FechaDisposicion(Integer.parseInt(disposicion[0]),
//				Integer.parseInt(disposicion[1]), Integer.parseInt(disposicion[2]),
//				dpDisposicion.getValue().getDayOfWeek().toString());
//		f.agregarDisposicion(fechad);
//		fechasDisposicion.add(fechad);
//		Label ab = new Label(disposicion[0] + "/" + disposicion[1] + "/" + disposicion[2]);
//		gpDisposicion.add(ab, 0, filaDisp);
//		filaDisp++;
//	}

	
	
	public void buscar() {
		TextInputDialog dialog = new TextInputDialog();
		dialog.setTitle("Buscar Actividad");
		dialog.setHeaderText("titulo");
		dialog.setContentText("Escribe el titulo de la actividad \nque desea buscar:");

		Optional<String> result = dialog.showAndWait();
		if (result.isPresent()){
			infoBuscada(result.get());

}
		result.ifPresent(name -> System.out.println("Your name: " + name));
	}
	
	public void agregaListaDisposicion() {
		Actividad a = m.darAgenda().buscarActividadBinario(txtTitulo.getText());
		String[] disposicion = dpDisposicion.getValue().toString().split("-");
		FechaDisposicion fechad = new FechaDisposicion(Integer.parseInt(disposicion[0]),
				Integer.parseInt(disposicion[1]), Integer.parseInt(disposicion[2]),
				dpDisposicion.getValue().getDayOfWeek().toString());
		fechasDisposicion.add(fechad);
		Label ab = new Label(disposicion[0] + "/" + disposicion[1] + "/" + disposicion[2]);
		gpDisposicion.add(ab, 0, filaDisp);
		filaDisp++;
	}
	
	public void guardarTXT() {
		TextInputDialog dialog = new TextInputDialog();
		dialog.setTitle("Gurdar Actividadades");
		dialog.setHeaderText("nombre del archivo");
		dialog.setContentText("Escribe el nombre del archivo para guardar las actividades");

		Optional<String> result = dialog.showAndWait();
		if (result.isPresent()){
			guardarArchivo(result.get()+".txt");
}
		result.ifPresent(name -> System.out.println("Your name: " + name));
	}
	
	public void guardarArchivo(String nombre) {
		m.darAgenda().imprimirArchivoDeTexto(nombre);
	}
	
	
public void infoBuscada(String titulo) {
Actividad a=m.darAgenda().buscarActividadBinario(titulo);
			VBox ventana=new VBox();
			ventana.setPrefSize(400, 400);
			
			ventana.getChildren().add(new Label("Titulo: "+a.getTitulo()+"\n"));
			ventana.getChildren().add(new Label("Debe entregarlo el dia : "+a.getFechaEntrega().getDia()));
			

			dialogStage = new Stage();
	        dialogStage.setTitle("Actividad Encontrada");
	        dialogStage.initModality(Modality.WINDOW_MODAL);
	        Scene scene = new Scene(ventana);
	        dialogStage.setScene(scene);
	        
		
			    dialogStage.showAndWait();
}

//	
//	public void agregarActividad() {
//		
//		Label a, b;
//		VBox a1;
//		HBox a2;
//		System.out.println(listaActividades.getRowConstraints().size());
//		if (cont == 0) {
//			a = new Label(dpEntrega.getValue().getDayOfWeek().toString());
//			b = new Label(dpEntrega.getValue().toString());
//			a1 = new VBox(a, b);
//			listaActividades.add(a1, 0, listaActividades.getRowConstraints().size());
//			a = new Label(txtTitulo.getText());
//			b = new Label(txtDescripcion.getText());
//			a1 = new VBox(a, b);
//			listaActividades.add(a1, 1, listaActividades.getRowConstraints().size());
//			a = new Label(mbTipo.getText());
//			listaActividades.add(a, 2, listaActividades.getRowConstraints().size());
//			elTipo = new Label(rbA.getText());
//			pb = new ProgressBar(0);
//			pi = new ProgressIndicator(0);
//			btProgreso = new Button("Progreso");
//			a2 = new HBox(btProgreso, pb, pi);
//			a1 = new VBox(elTipo, a2);
//			listaActividades.add(a1, 3, listaActividades.getRowConstraints().size());
//			a = new Label(dpDisposicion.getValue().getDayOfWeek().toString());
//			b = new Label(dpDisposicion.getValue().toString());
//			a1 = new VBox(a, b);
//			listaActividades.add(a1, 4, listaActividades.getRowConstraints().size());
//			if(mbTipo.getText().equalsIgnoreCase("Examen")) tipo = 1;
//			if(mbTipo.getText().equalsIgnoreCase("Tarea")) tipo = 2;
//			if(mbTipo.getText().equalsIgnoreCase("Taller")) tipo = 3;
//			if(mbTipo.getText().equalsIgnoreCase("Laboratorio")) tipo = 4;
//			if(mbTipo.getText().equalsIgnoreCase("Lectura")) tipo = 5;
//			Actividad acti = new Actividad(txtTitulo.getText(), txtDescripcion.getText(), tipo, rbA.getText(), dpEntrega.getValue().toString());
//			m.darAgenda().agregarActividad(acti);
//			mostrarProgreso(btProgreso, pb, pi,acti);
//			dpEntrega.setValue(null);
//			txtTitulo.setText("");
//			txtDescripcion.setText("");
//			mbTipo.setText("");
//			dpDisposicion.setValue(null);
//			cont++;
//			
//		} else {
//			a = new Label(dpEntrega.getValue().getDayOfWeek().toString());
//			b = new Label(dpEntrega.getValue().toString());
//			a1 = new VBox(a, b);
//			listaActividades.addRow(listaActividades.getRowConstraints().size()+cont,a1);
//			a = new Label(txtTitulo.getText());
//			b = new Label(txtDescripcion.getText());
//			a1 = new VBox(a, b);
//			listaActividades.add(a1, 1, listaActividades.getRowConstraints().size()+cont);
//			a = new Label(mbTipo.getText());
//			listaActividades.add(a, 2, listaActividades.getRowConstraints().size()+cont);
//			elTipo = new Label(rbA.getText());
//			pb = new ProgressBar(0);
//			pi = new ProgressIndicator(0);
//			btProgreso = new Button("Progreso");
//			a2 = new HBox(btProgreso, pb, pi);
//			a1 = new VBox(elTipo, a2);
//			listaActividades.add(a1, 3, listaActividades.getRowConstraints().size()+cont);
//			a = new Label(dpDisposicion.getValue().getDayOfWeek().toString());
//			b = new Label(dpDisposicion.getValue().toString());
//			a1 = new VBox(a, b);
//			listaActividades.add(a1, 4, listaActividades.getRowConstraints().size()+cont);
//			if(mbTipo.getText().equalsIgnoreCase("Examen")) tipo = 1;
//			if(mbTipo.getText().equalsIgnoreCase("Tarea")) tipo = 2;
//			if(mbTipo.getText().equalsIgnoreCase("Taller")) tipo = 3;
//			if(mbTipo.getText().equalsIgnoreCase("Laboratorio")) tipo = 4;
//			if(mbTipo.getText().equalsIgnoreCase("Lectura")) tipo = 5;
//			Actividad acti = new Actividad(txtTitulo.getText(), txtDescripcion.getText(), tipo, rbA.getText(), dpEntrega.getValue().toString());
//			m.darAgenda().agregarActividad(acti);
//			mostrarProgreso(btProgreso, pb, pi,acti);
//			dpEntrega.setValue(null);
//			txtTitulo.setText("");
//			txtDescripcion.setText("");
//			mbTipo.setText("");
//			dpDisposicion.setValue(null);
//			cont++;
//		}
//		pBanner.setVisible(true);
//		pAgregarActividad.setVisible(false);
//		scPane.setVisible(true);
//		hbOpciones.setVisible(true);
//	}
//	
//	

	public void menuTipo2() {
		MenuItem t1 = new MenuItem("Examen");
		MenuItem t2 = new MenuItem("Tarea");
		MenuItem t3 = new MenuItem("Taller");
		MenuItem t4 = new MenuItem("Laboratorio");
		MenuItem t5 = new MenuItem("Lectura");
		mbTipo.getItems().add(t1);
		mbTipo.getItems().add(t2);
		mbTipo.getItems().add(t3);
		mbTipo.getItems().add(t4);
		mbTipo.getItems().add(t5);

		EventHandler<ActionEvent> a = new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				String opcion = ((MenuItem) event.getSource()).getText();

				if (opcion.equalsIgnoreCase(t1.getText())) {
					mbTipo.setText(t1.getText());
				} else if (opcion.equalsIgnoreCase(t2.getText())) {
					mbTipo.setText(t2.getText());
				} else if (opcion.equalsIgnoreCase(t3.getText())) {
					mbTipo.setText(t3.getText());
				} else if (opcion.equalsIgnoreCase(t4.getText())) {
					mbTipo.setText(t4.getText());
				} else {
					mbTipo.setText(t5.getText());
				}
			}
		};
		t1.setOnAction(a);
		t2.setOnAction(a);
		t3.setOnAction(a);
		t4.setOnAction(a);
		t5.setOnAction(a);

	}

	public void OpcionOrganizar() {
		organizar.selectedToggleProperty().addListener(new ChangeListener<Toggle>() {
			@Override
			public void changed(ObservableValue<? extends Toggle> observable, Toggle oldValue, Toggle newValue) {

				if (organizar.getSelectedToggle() != null) {
					rbOrganizar = (RadioButton) organizar.getSelectedToggle();
					System.out.println(rbOrganizar.getText());
				}
			}
		});
	}

	
	public void tipoOrdenamiento() {
		if(rbOrganizar.getText().equals("Tipo")) {
			System.out.println("entra");
			m.darAgenda().añadirArbolALista(m.darAgenda().getRaizActividad());
			m.darAgenda().ordenarPorTipo();
			listaActividades.getChildren().clear();
			recorridoInOrden(m.darAgenda().getRaizActividad());
		}
		if(rbOrganizar.getText().equals("Disponibilidad")) {
			
		}
		if(rbOrganizar.getText().equals("Prioridad")) {
	
}
	}
	
	
	public void Opcionprogreso() {
		progreso.selectedToggleProperty().addListener(new ChangeListener<Toggle>() {
			@Override
			public void changed(ObservableValue<? extends Toggle> observable, Toggle oldValue, Toggle newValue) {

				if (progreso.getSelectedToggle() != null) {
					rbA = (RadioButton) progreso.getSelectedToggle();
				}
			}
		});
	}

	public void mostrarProgreso(Button bt, ProgressBar pb, ProgressIndicator pi, Actividad acti) {

		if (rbA.getText().equalsIgnoreCase("Iniciado")) {
			pb.setProgress(0.25);
			pi.setProgress(0.25);
			acti.setAumento(26);
		}
		if (rbA.getText().equalsIgnoreCase("A la Mitad")) {
			pb.setProgress(0.50);
			pi.setProgress(0.50);
			acti.setAumento(51);
		}
		if (rbA.getText().equalsIgnoreCase("Avanzado")) {
			pb.setProgress(0.75);
			pi.setProgress(0.75);
			acti.setAumento(76);
		}
		if (rbA.getText().equalsIgnoreCase("Terminado")) {
			pb.setProgress(1);
			pi.setProgress(1);
			acti.setAumento(101);
		}
		bt.setOnMouseClicked(new EventHandler<MouseEvent>() {

			
			@Override
			public void handle(MouseEvent event) {
//				if (acti.getAumento() < 80)elTipo.setText("Terminado");
//				if (acti.getAumento() < 55)elTipo.setText("Avanzado");
//				if (acti.getAumento() < 30)elTipo.setText("A la Mitad");
//				if(acti.getAumento() < 5) elTipo.setText("Iniciado");
				tl = new Timeline(new KeyFrame(new Duration(50), new EventHandler<ActionEvent>() {

					@Override
					public void handle(ActionEvent event) {
						pb.setProgress((pb.getProgress() + acti.getAumento()) / 100);
						pi.setProgress((pi.getProgress() + acti.getAumento()) / 100);
						acti.incrementar();
						if (acti.getAumento() == 26 || acti.getAumento() == 51 || acti.getAumento() == 75 || acti.getAumento() == 101) {
							tl.stop();
						}
					}
				}));
				tl.setCycleCount(Timeline.INDEFINITE);
				tl.play();
			}
		});
	}

}
