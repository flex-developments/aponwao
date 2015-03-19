/*
 * # Copyright 2008 zylk.net 
 * # 
 * # This file is part of Aponwao. 
 * # 
 * # Aponwao is free software: you can redistribute it and/or modify 
 * # it under the terms of the GNU General Public License as published by 
 * # the Free Software Foundation, either version 2 of the License, or 
 * # (at your option) any later version. 
 * # 
 * # Aponwao is distributed in the hope that it will be useful, 
 * # but WITHOUT ANY WARRANTY; without even the implied warranty of 
 * # MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the 
 * # GNU General Public License for more details. 
 * # 
 * # You should have received a copy of the GNU General Public License 
 * # along with Aponwao. If not, see <http://www.gnu.org/licenses/>. [^] 
 * # 
 * # See COPYRIGHT.txt for copyright notices and details. 
 * #
 */
package flex.aponwao.gui;

import flex.aponwao.gui.application.ImagesResource;
import flex.aponwao.gui.application.LanguageResource;
import flex.aponwao.gui.application.ResourceHelper;
import flex.aponwao.gui.sections.global.windows.FileDialogs;
import flex.aponwao.gui.sections.global.windows.InfoDialog;
import flex.aponwao.gui.sections.main.events.EnviarPDFListener;
import flex.aponwao.gui.sections.main.events.FirmarPDFListener;
import flex.aponwao.gui.sections.main.events.ValidarPDFListener;
import flex.aponwao.gui.sections.main.windows.TablePDF;
import flex.aponwao.gui.sections.main.windows.WindowMain;
import flex.aponwao.gui.sections.preferences.helpers.PreferencesHelper;
import flex.aponwao.gui.sections.preferences.windows.PreferencesManager;
import flex.eSign.helpers.exceptions.CertificateHelperException;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.Security;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.LogManager;
import java.util.logging.Logger;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Monitor;
import org.eclipse.swt.widgets.Shell;

/**
 * @author zylk.net
 */
public class Aponwao {
    private static final Logger logger = Logger.getLogger(Aponwao.class.getName());
    
        public static void main(String[] args) throws InterruptedException, CertificateHelperException {
            Security.addProvider(new BouncyCastleProvider());
		/*
		 * Before this is run, be sure to set up the launch configuration (Arguments->VM Arguments) for the correct SWT
		 * library path in order to run with the SWT dlls. The dlls are located in the SWT plugin jar. For example, on
		 * Windows the Eclipse SWT 3.1 plugin jar is: installation_directory\plugins\org.eclipse.swt.win32_3.1.0.jar
		 */
		
		// configuracion del logger
		try {

                        //OJO... Modificacion yessica
                        removeDriverLogs(true, ResourceHelper.getRootPath());

                        //OJO... Modificacion Felix
                        //InputStream file = new FileInputStream(ResourceHelper.RESOURCES_PATH + File.separatorChar + "logging" + File.separatorChar + "logger-configuration.properties");
			InputStream file = new FileInputStream(ResourceHelper.getRESOURCES_PATH() + File.separatorChar + "logging" + File.separatorChar
					+ "logger-configuration.properties");

			LogManager.getLogManager().readConfiguration(file);
		} catch (IOException e) {
			System.out.println("Error al cargar el fichero de configuracion del logger. " + e.getMessage());
		}
                       
                //OJO... Modificacion Felix
                int params = args.length;
                int accion = 0;
                String archivoPDF = null;
                String archivoRespuesta = null;
                for(int x=0; x<params; x++) {
                    if(args[x].equals("-f")) {
                        accion = 1;
                        archivoPDF = args[++x];
                        
                    } else if(args[x].equals("-fd")) {
                        accion = 2;
                        archivoPDF = args[++x];

                    } else if(args[x].equals("-v")) {
                        accion = 3;
                        archivoPDF = args[++x];
                        
                    } else if(args[x].equals("-p")) {
                        accion = 4;
                        
                    } else if(args[x].equals("-e")) {
                        accion = 5;
                        archivoPDF = args[++x];
                        
                    } else if(args[x].equals("-t")) {
                        archivoRespuesta = args[++x];

                    } else {
                        System.out.println(LanguageResource.getLanguage().getString("error.parametros_desconocidos"));
                        System.exit(0);
                    }
                }
                //Procesar la solicitud
                switch(accion) {
                    case 0: porDefecto(); break;
                    case 1: f(archivoPDF, archivoRespuesta, false); break;
                    case 2: f(archivoPDF, archivoRespuesta, true); break;
                    case 3: v(archivoPDF, archivoRespuesta); break;
                    case 4: p(); break;
                    case 5: s(archivoPDF, archivoRespuesta); break;
                }
		//**************************************************************
        }
        
        //OJO... Modificacion Felix
        private static Shell levantarShell() {
            //OJO... Modificacion Yesica
            Display.setAppName(PreferencesHelper.getPreferences().getString(PreferencesHelper.APPEARANCE_TITLE));
            //**************************************************************
            Display display = new Display();
            Shell mainShell = new Shell(SWT.APPLICATION_MODAL | SWT.SHELL_TRIM | SWT.CENTER);
            
            try {
                mainShell.setSize(new Point(0, 0));
                Monitor primary = display.getPrimaryMonitor();
                Rectangle bounds = primary.getBounds();
                Rectangle rect = mainShell.getBounds();
                int x = bounds.x + (bounds.width - rect.width) / 2;
                int y = bounds.y + (bounds.height - rect.height) / 2;
                mainShell.setLocation(x, y);

                mainShell.setText(PreferencesHelper.getPreferences().getString(PreferencesHelper.APPEARANCE_TITLE));
                mainShell.setImage(new Image(display, ImagesResource.APLICATION_LOGO));
                mainShell.open();
             } catch (RuntimeException e) {
                logger.log(Level.SEVERE, "Error general de la aplicacion (runtime)", e);
                InfoDialog id = new InfoDialog(mainShell);
                id.open(LanguageResource.getLanguage().getString("error.runtime"));
            }
            return mainShell;
        }
        
        private static void porDefecto() throws CertificateHelperException {
            WindowMain windowsMain = new WindowMain();
        }
        
        private static void f(String ruta, String arch, boolean duplicar) throws CertificateHelperException {
            Shell shell = levantarShell();
            TablePDF tablePDF = new TablePDF (shell, SWT.NONE);
            tablePDF.addFilesFromFolder(false, ruta);
            FirmarPDFListener ejec = new FirmarPDFListener(tablePDF, duplicar);
            ejec.setResponse(arch);
            ejec.firmarPDF();
        }
        
        private static void v(String ruta, String arch) throws CertificateHelperException {
            Shell shell = levantarShell();
            TablePDF tablePDF = new TablePDF (shell, SWT.NONE);
            tablePDF.addFilesFromFolder(false, ruta);
            ValidarPDFListener ejec = new ValidarPDFListener(tablePDF);
            ejec.setResponse(arch);
            ejec.validar();
        }
        
        private static void s(String ruta, String arch) throws CertificateHelperException {
            Shell shell = levantarShell();
            TablePDF tablePDF = new TablePDF (shell, SWT.NONE);
            tablePDF.addFilesFromFolder(false, ruta);
            EnviarPDFListener ejec = new EnviarPDFListener(tablePDF);
            ejec.setResponse(arch);
            ejec.enviar();
        }
        
        private static void p() {
            PreferencesManager ventanaPreferencias = new PreferencesManager();
            ventanaPreferencias.abrirVentana(null);
        }

        //OJO... Modificacion Yessica
        private static void removeDriverLogs (boolean dir, String ruta) {
                List<File> fileList = null;
                if (dir)
                    fileList = FileDialogs.listarArchivos(ruta, FileDialogs.LOG_DRIVER_TYPE);
                else {
                    File arch = new File(ruta);
                    fileList = new ArrayList<File>();
                    fileList.add(arch);
                }

                for (File file : fileList) {
                    if (file.getName().startsWith("hs_err_")){
                        file.delete();
                    }
                }
	}
        //**********************************************************************
}