package flex.aponwao.gui.sections.global.events;

import java.util.logging.Logger;

import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.widgets.Button;

public class BotonCancelarListener implements SelectionListener {

	private static Logger logger = Logger.getLogger(BotonCancelarListener.class.getName());

	public void widgetSelected(SelectionEvent event) {

		((Button) event.widget).getShell().dispose();
	}

	public void widgetDefaultSelected(SelectionEvent event) {

		widgetSelected(event);
	}
}