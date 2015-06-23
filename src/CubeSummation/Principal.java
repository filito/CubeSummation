package CubeSummation;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.StringTokenizer;
import org.vaadin.dialogs.ConfirmDialog;
import com.vaadin.data.Property;
import com.vaadin.data.Container.Filterable;
import com.vaadin.data.Container.ItemSetChangeEvent;
import com.vaadin.data.Container.ItemSetChangeListener;
import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.data.Property.ValueChangeListener;
import com.vaadin.data.util.filter.SimpleStringFilter;
import com.vaadin.event.FieldEvents.TextChangeEvent;
import com.vaadin.event.FieldEvents.TextChangeListener;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.server.Page;
import com.vaadin.server.Resource;
import com.vaadin.server.ThemeResource;
import com.vaadin.server.VaadinSession;
import com.vaadin.ui.AbstractSingleComponentContainer;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.Component;
import com.vaadin.ui.Embedded;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Notification;
import com.vaadin.ui.Panel;
import com.vaadin.ui.Select;
import com.vaadin.ui.Table;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Component.Event;
import com.vaadin.ui.Window.CloseEvent;
import com.vaadin.ui.Window;
import com.vaadin.ui.Window.CloseListener;
import com.vaadin.ui.themes.ChameleonTheme;
import com.vaadin.ui.themes.Reindeer;

@SuppressWarnings({ "deprecation", "unused" })
public class Principal extends VerticalLayout implements View {

	private static final long serialVersionUID = 511085335415683713L;
	public static final String NAME = "PRINCIPAL";

	StringBuilder cadena;
	Table tablaResult = new Table();

	final TextField cubo = new TextField("Numero para elevar al cubo:");
	final TextField operaciones = new TextField("Numero de operaciones:");
	Button next = new Button("next");
	ComboBox queryOpe = new ComboBox("Update/Query");
	Button siguiente = new Button("Siguiente");
	int cop = 0;
	Button Procesar = new Button("Procesar");

	TextField up1 = new TextField("v1");
	TextField up2 = new TextField("v2");
	TextField up3 = new TextField("v3");
	TextField up4 = new TextField("v4");

	TextField q1 = new TextField("v1");
	TextField q2 = new TextField("v2");
	TextField q3 = new TextField("v3");
	TextField q4 = new TextField("v4");
	TextField q5 = new TextField("v5");
	TextField q6 = new TextField("v6");

	VerticalLayout layoutTabla = new VerticalLayout();
	ConfirmDialog Result = new ConfirmDialog();
	Properties propiedades = new Properties();
	String ruta = "";

	public Principal() {

		cadena = new StringBuilder();
		final VerticalLayout vlayout = new VerticalLayout();
		vlayout.setSizeUndefined();

		cubo.setMaxLength(3);
		cubo.setWidth("40");

		operaciones.setMaxLength(3);
		operaciones.setWidth("40");

		queryOpe.addItem("UPDATE");
		queryOpe.addItem("QUERY");

		Procesar.setEnabled(false);

		final HorizontalLayout hlayout1 = new HorizontalLayout();
		hlayout1.setSizeUndefined();
		up1.setWidth("40");
		up2.setWidth("40");
		up3.setWidth("40");
		up4.setWidth("40");
		hlayout1.addComponents(up1, up2, up3, up4);

		final HorizontalLayout hlayout2 = new HorizontalLayout();
		hlayout2.setSizeUndefined();
		q1.setWidth("40");
		q2.setWidth("40");
		q3.setWidth("40");
		q4.setWidth("40");
		q5.setWidth("40");
		q6.setWidth("40");
		hlayout2.addComponents(q1, q2, q3, q4, q5, q6);

		try {
			propiedades.load(Principal.class
					.getResourceAsStream("/CubeSummation/cube.properties"));
			ruta = propiedades.getProperty("dirArchivo");
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		queryOpe.addValueChangeListener(new ValueChangeListener() {

			private static final long serialVersionUID = 1L;

			public void valueChange(ValueChangeEvent event) {
				// TODO Auto-generated method stub
				if (queryOpe.getValue().toString().equals("UPDATE")) {
					removeComponent(hlayout2);
					removeComponent(Procesar);
					up1.setValue("");
					up2.setValue("");
					up3.setValue("");
					up4.setValue("");
					hlayout1.addComponent(siguiente);
					addComponent(hlayout1);
					addComponent(Procesar);
				} else {
					removeComponent(hlayout1);
					removeComponent(Procesar);
					q1.setValue("");
					q2.setValue("");
					q3.setValue("");
					q4.setValue("");
					q5.setValue("");
					q6.setValue("");
					hlayout2.addComponent(siguiente);
					addComponent(hlayout2);
					addComponent(Procesar);
				}
			}
		});

		next.setStyleName(Reindeer.BUTTON_LINK);

		vlayout.addComponents(cubo, operaciones, next);

		addComponent(vlayout);

		next.addClickListener(new ClickListener() {

			private static final long serialVersionUID = 1L;

			@Override
			public void buttonClick(ClickEvent event) {
				// TODO Auto-generated method stub
				try {
					int cub = Integer.parseInt(cubo.getValue());
					int operac = Integer.parseInt(operaciones.getValue());
					addComponent(queryOpe);

					cadena.append(cubo.getValue() + ";"
							+ operaciones.getValue() + "\n");
					cubo.setEnabled(false);
					operaciones.setEnabled(false);
					next.setEnabled(false);
					queryOpe.setEnabled(true);
					siguiente.setEnabled(true);
					Procesar.setEnabled(false);
				} catch (NumberFormatException ex) {
					Notification.show("El numero que ingreso es invalido");
					cubo.setValue("");
					operaciones.setValue("");
					cubo.focus();
				}

			}
		});

		siguiente.addClickListener(new ClickListener() {

			private static final long serialVersionUID = 1L;

			public void buttonClick(ClickEvent event) {

				FileWriter fichero = null;
				PrintWriter pw = null;

				try {
					fichero = new FileWriter(ruta);
					pw = new PrintWriter(fichero);

					if (cop < Integer.parseInt(operaciones.getValue()
							.toString())) {

						if (queryOpe.getValue().toString().equals("UPDATE")) {

							try {
								int upd1 = Integer.parseInt(up1.getValue());
								int upd2 = Integer.parseInt(up2.getValue());
								int upd3 = Integer.parseInt(up3.getValue());
								int upd4 = Integer.parseInt(up4.getValue());
								cadena.append(queryOpe.getValue().toString()
										+ ";" + up1.getValue() + ";"
										+ up2.getValue() + ";" + up3.getValue()
										+ ";" + up4.getValue() + "\n");
								up1.setValue("");
								up2.setValue("");
								up3.setValue("");
								up4.setValue("");
							} catch (NumberFormatException ex) {
								Notification
										.show("El numero que ingreso es invalido");
								up1.setValue("");
								up2.setValue("");
								up3.setValue("");
								up4.setValue("");
							}

						} else {
							try {
								int qu1 = Integer.parseInt(q1.getValue());
								int qu2 = Integer.parseInt(q2.getValue());
								int qu3 = Integer.parseInt(q3.getValue());
								int qu4 = Integer.parseInt(q4.getValue());
								int qu5 = Integer.parseInt(q5.getValue());
								int qu6 = Integer.parseInt(q6.getValue());
								cadena.append(queryOpe.getValue().toString()
										+ ";" + q1.getValue() + ";"
										+ q2.getValue() + ";" + q3.getValue()
										+ ";" + q4.getValue() + ";"
										+ q5.getValue() + ";" + q6.getValue()
										+ "\n");
								q1.setValue("");
								q2.setValue("");
								q3.setValue("");
								q4.setValue("");
								q5.setValue("");
								q6.setValue("");
							} catch (NumberFormatException ex) {
								Notification
										.show("El numero que ingreso es invalido");
								q1.setValue("");
								q2.setValue("");
								q3.setValue("");
								q4.setValue("");
								q5.setValue("");
								q6.setValue("");

							}
						}
						cop++;
						pw.print(cadena);
						if (cop == Integer.parseInt(operaciones.getValue()
								.toString())) {
							Notification.show("Procese la operacion");
							cubo.setEnabled(true);
							operaciones.setEnabled(true);
							next.setEnabled(true);
							cubo.setValue("");
							operaciones.setValue("");
							cop = 0;
							siguiente.setEnabled(false);
							queryOpe.setEnabled(false);
							Procesar.setEnabled(true);
						}

					} else {
						Notification
								.show("Ya ingreso el numero de operaciones establecido");

					}

				} catch (Exception e) {
					e.printStackTrace();
				} finally {
					try {
						// Nuevamente aprovechamos el finally para
						// asegurarnos que se cierra el fichero.
						if (null != fichero)
							fichero.close();
					} catch (Exception e2) {
						e2.printStackTrace();
					}
				}

			}
		});

		Procesar.addClickListener(new ClickListener() {

			private static final long serialVersionUID = 1L;

			@Override
			public void buttonClick(ClickEvent event) {
				// TODO Auto-generated method stub

				calcular();
				removeComponent(hlayout1);
				removeComponent(hlayout2);
				removeComponent(queryOpe);
				removeComponent(Procesar);
			}
		});

	}

	public int cuentaLineas(String ruta) {

		int cuenta = 0;
		String myLine = "";

		try {

			FileReader fichero = new FileReader(ruta);
			BufferedReader archEntrada = new BufferedReader(fichero);

			while ((myLine = archEntrada.readLine()) != null) {
				cuenta++;
			}
			archEntrada.close();
		} catch (IOException ioe) {
			System.out.println("No se pudo abrir el archivo.");
		}
		return cuenta;
	}

	public String readLine(String ruta, int numero) {
		String myLine = "";
		int count = 1;
		try {
			FileReader fichero = new FileReader(ruta);
			BufferedReader archEntrada = new BufferedReader(fichero);
			while ((myLine = archEntrada.readLine()) != null && count < numero) {
				count++;
			}
			archEntrada.close();
		} catch (IOException ioe) {
			System.err.println(ioe);
		}
		return myLine;
	}

	@SuppressWarnings("unchecked")
	public void calcular() {

		int fcon = 2;
		int in = 0;
		int op = 0;
		int tam = cuentaLineas(ruta);

		String oper = readLine(ruta, 1);
		String[] camoper;
		camoper = oper.split(";");
		op = Integer.parseInt(camoper[1]);

		@SuppressWarnings("rawtypes")
		List lisRe = new ArrayList();

		int[] x = new int[op];
		int[] y = new int[op];
		int[] z = new int[op];
		int[] delta = new int[op];

		while (fcon <= tam) {

			String lin = readLine(ruta, fcon);

			String[] campos;
			campos = lin.split(";");

			if (campos[0].equals("UPDATE")) {
				x[in] = Integer.parseInt(campos[1]);
				y[in] = Integer.parseInt(campos[2]);
				z[in] = Integer.parseInt(campos[3]);
				delta[in] = Integer.parseInt(campos[4]);
				for (int j = 0; j < in; j++) {
					if (x[j] == x[in] && y[j] == y[in] && z[j] == z[in])
						delta[j] = 0;
				}
			} else {
				long answer = 0;
				int x0 = Integer.parseInt(campos[1]);
				int y0 = Integer.parseInt(campos[2]);
				int z0 = Integer.parseInt(campos[3]);
				int x1 = Integer.parseInt(campos[4]);
				int y1 = Integer.parseInt(campos[5]);
				int z1 = Integer.parseInt(campos[6]);

				for (int j = 0; j < in; j++) {
					if (x[j] >= x0 && x[j] <= x1 && y[j] >= y0 && y[j] <= y1
							&& z[j] >= z0 && z[j] <= z1)
						answer += delta[j];

				}
				lisRe.add(answer);
				System.out.print("Respuesta query" + " " + answer + "\n");

			}

			fcon++;
			in++;
		}
		MostrarResultados(lisRe);
		try {
			BufferedWriter bw = new BufferedWriter(new FileWriter(ruta));
			bw.write("");
			bw.close();
			cadena.setLength(0);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	// }

	@Override
	public void enter(ViewChangeEvent event) {
		// TODO Auto-generated method stub

	}

	public ConfirmDialog MostrarResultados(
			@SuppressWarnings("rawtypes") List result) {

		Result = ConfirmDialog.getFactory()
				.create("Resultados", "", "", "", "");

		tablaResult.setStyleName(ChameleonTheme.TABLE_STRIPED);
		tablaResult.addContainerProperty("Resultado", Long.class, "");
		tablaResult.setPageLength(7);
		tablaResult.setSelectable(true);
		tablaResult.setImmediate(true);
		tablaResult.setColumnCollapsingAllowed(true);

		for (int i = 0; i < result.size(); i++)
			tablaResult.addItem(new Object[] { result.get(i) }, i);

		layoutTabla.addComponent(tablaResult);
		layoutTabla.setComponentAlignment(tablaResult, Alignment.MIDDLE_CENTER);

		Result.setContent(layoutTabla);
		Result.show(getUI(), null, true);

		Result.addCloseListener(new CloseListener() {

			private static final long serialVersionUID = 1L;

			public void windowClose(CloseEvent e) {
				// TODO Auto-generated method stub
				Notification.show("Ingrese nueva consulta");
			}
		});
		return Result;

	}

}
