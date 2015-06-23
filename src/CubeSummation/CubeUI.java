package CubeSummation;
import com.vaadin.annotations.Theme;
import com.vaadin.navigator.Navigator;
import com.vaadin.server.VaadinRequest;
import com.vaadin.ui.UI;


@Theme("cubesummation")
public class CubeUI extends UI {

	private static final long serialVersionUID = -4259183071147533520L;
    Navigator navegador;
	
	public Navigator getNavegador(){
		return navegador;
	}

    @Override
    protected void init(VaadinRequest request) {
    
    	navegador = new Navigator(this, this);
        getPage().setTitle("Cube Summation");
        // Create and register the views
        navegador.addView(Bienvenida.NAME, new Bienvenida());      
        
    }
	
}
