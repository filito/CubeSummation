package CubeSummation;

import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Component;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Panel;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.ChameleonTheme;
import com.vaadin.ui.themes.Reindeer;

public class Bienvenida extends VerticalLayout implements View {

	private static final long serialVersionUID = -1975029227964724531L;
	public static final String NAME = ""; // primera pantalla

	public Bienvenida() {
		setSizeFull();
		Panel panel = new Panel("Sistema Cubos");
		panel.setStyleName(ChameleonTheme.PANEL_BUBBLE);
		panel.setSizeUndefined();
		FormLayout formulario = new FormLayout();
		formulario.setSizeUndefined();
		formulario.setMargin(true);
		Label l = new Label(
				"Bienvenidos al sistema de calculo de sumas de cubos haga clik en el enlace para iniciar:");
		Button b = new Button("Iniciar");
		b.setStyleName(Reindeer.BUTTON_LINK);
		formulario.addComponent(l);
		formulario.addComponent(b);
		formulario.setComponentAlignment(b, Alignment.MIDDLE_CENTER);

		b.addClickListener(new Button.ClickListener() {
			private static final long serialVersionUID = 1L;

			@Override
			public void buttonClick(ClickEvent event) {
				Component c = new Principal();
				removeAllComponents();
				addComponent(c);

			}

		});
		panel.setContent(formulario);
		addComponent(panel);
		setComponentAlignment(panel, Alignment.MIDDLE_CENTER);
	}

	@Override
	public void enter(ViewChangeEvent event) {

	}

}
