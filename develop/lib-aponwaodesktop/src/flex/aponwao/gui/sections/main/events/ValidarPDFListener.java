package flex.aponwao.gui.sections.main.events;


import java.lang.reflect.InvocationTargetException;
import java.util.logging.Level;
import java.util.logging.Logger;


import flex.aponwao.gui.application.LanguageResource;
import flex.aponwao.gui.application.LoggingDesktopController;
import flex.aponwao.gui.sections.main.windows.TablePDF;

import org.eclipse.jface.dialogs.ProgressMonitorDialog;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;



public class ValidarPDFListener implements SelectionListener {
	//OJO... Modificacion Felix
        private String response = null;
        
        public void setResponse(String response) {
            this.response = response;
        }
        //**********************************************************************
        
	private static Logger	logger	= Logger.getLogger(ValidarPDFListener.class.getName());
	
	private TablePDF tablePDF = null;
	
	public ValidarPDFListener (TablePDF t) {
		
		this.tablePDF = t;
	}
	
	public void widgetSelected(SelectionEvent event) {
		validar();
	}

	public void widgetDefaultSelected(SelectionEvent event) {
		widgetSelected(event);
	}
        
        public void validar() {
            if (tablePDF.getSelectedPDFs().size() != 0) {

			try {
				ProgressMonitorDialog progressMonitorDialog = new ProgressMonitorDialog(tablePDF.getShell());
				progressMonitorDialog.run(true, true, new ValidarPDFProgress(tablePDF.getSelectedPDFs(), response));
				
			} catch (InvocationTargetException e) {
				
				// de momento no deberia de darse nunca
				logger.log(Level.SEVERE, "en el proceso de validacion.", e);
				
			} catch (InterruptedException e) {

				logger.log(Level.SEVERE, "en el proceso de validacion.", e);
				String m = LanguageResource.getLanguage().getString("error.operacion_cancelada");
				LoggingDesktopController.printError(m);
				logger.severe(m);					
			}
			
			tablePDF.reloadTable();

		} else {
			logger.severe(LanguageResource.getLanguage().getString("error.no_selected_file"));
			LoggingDesktopController.printError(LanguageResource.getLanguage().getString("error.no_selected_file"));
		}
        }
}