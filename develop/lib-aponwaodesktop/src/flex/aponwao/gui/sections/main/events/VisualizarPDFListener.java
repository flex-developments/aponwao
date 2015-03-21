package flex.aponwao.gui.sections.main.events;


import java.util.logging.Logger;
import flex.aponwao.gui.application.LanguageResource;
import flex.aponwao.gui.application.LoggingDesktopController;
import flex.aponwao.gui.application.PDFInfo;
import flex.aponwao.gui.sections.main.windows.TablePDF;
import flex.aponwao.gui.sections.preferences.helpers.PreferencesHelper;
import flex.helpers.SystemHelper;

import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;


public class VisualizarPDFListener implements SelectionListener {
	
	private static Logger	logger	= Logger.getLogger(VisualizarPDFListener.class.getName());
	
	private TablePDF tablePDF = null;
	
	public VisualizarPDFListener (TablePDF t) {
		
		this.tablePDF = t;
	}
	
	public void widgetSelected(SelectionEvent event) {
		
		if (tablePDF.getSelectedPDF() != null) {
			
                        //OJO... Modificacion Felix
			PDFInfo o = tablePDF.getSelectedPDF();			
			//DesktopHelper.openDefaultPDFViewer(o.getOrigen());
                        //******************************************************
                        try 
                        { 
                           /* directorio/ejecutable es el path del ejecutable y un nombre */ 
                           //
                            Process p = null;
                            String os = System.getProperty("os.name");
                            os = os.substring(0, 3);

                            if(SystemHelper.isWindows()) {
                                p = Runtime.getRuntime().exec (PreferencesHelper.getPreferences().getString(PreferencesHelper.PDF_VIEWER) + " \"" + o.getOrigen() + "\"");
                            } else if(SystemHelper.isLinux()) {
//                                String fileOpen = "";
//                                String[] fileName = null;
//                                fileName = o.getOrigen().split(" ");
//
//                                if (fileName.length > 1){
//                                    for (int i=0; i<fileName.length-1; i++)
//                                        fileOpen = fileOpen + fileName[i] + "\\ ";
//                                    fileOpen = fileOpen + fileName[fileName.length-1];
//                                } else fileOpen = o.getOrigen();
//
//                                JOptionPane.showOptionDialog(
//                                      null,
//                                      PreferencesHelper.getPreferences().getString(PreferencesHelper.PDF_VIEWER) + " " + fileOpen,
//                                      "Aponwao - SUSCERTE",
//                                      JOptionPane.DEFAULT_OPTION,
//                                      JOptionPane.DEFAULT_OPTION,
//                                      null,
//                                      null,
//                                      null);
//
//                                p = Runtime.getRuntime().exec (PreferencesHelper.getPreferences().getString(PreferencesHelper.PDF_VIEWER) + " " + fileOpen);
                                p = Runtime.getRuntime().exec (PreferencesHelper.getPreferences().getString(PreferencesHelper.PDF_VIEWER) + " " + o.getOrigen());
                            }
                        } 
                        catch (Exception e) { }

		}
		else {
			logger.severe(LanguageResource.getLanguage().getString("error.no_selected_file"));
			LoggingDesktopController.printError(LanguageResource.getLanguage().getString("error.no_selected_file"));
		}
	}

	public void widgetDefaultSelected(SelectionEvent event) {
		widgetSelected(event);
	}
}
