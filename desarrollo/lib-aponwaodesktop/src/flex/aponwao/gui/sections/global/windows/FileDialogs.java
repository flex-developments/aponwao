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

package flex.aponwao.gui.sections.global.windows;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import flex.aponwao.gui.application.LanguageResource;
import flex.aponwao.gui.sections.main.events.ProgressWriter;
import flex.aponwao.gui.sections.preferences.helpers.PreferencesHelper;
import java.util.Arrays;
import java.util.Comparator;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.DirectoryDialog;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.Shell;

public class FileDialogs {
	private static final Logger logger = Logger.getLogger(FileDialogs.class.getName());
	// keys
	public static final String P12_TYPE = "p12";
	public static final String PDF_TYPE = "pdf";
	public static final String IMAGE_TYPE = "png";
	public static final String CERT_TYPE = "cer";
	public static final String ALL_TYPE = "*";
        //OJO... Modificacion yessica
        public static final String BIN_TYPE = "so";
        public static final String LOG_DRIVER_TYPE = "log";
        //**********************************************************************
	// maps
	private static Map<String, String[]> namesMap = new HashMap<String, String[]>();
	private static Map<String, String[]> extensionsMap = new HashMap<String, String[]>();

	static {
		namesMap.put(P12_TYPE, new String[] {"P12 Files (*.p12)"});
		namesMap.put(PDF_TYPE, new String[] {"PDF Files (*.pdf)"});
		namesMap.put(IMAGE_TYPE, new String[] {"PNG image files (*.png)", "JPG image files (*.jpg)", "GIF image files (*.gif)"});
		namesMap.put(CERT_TYPE, new String[] {"Certificate cer files (*.cer)"});
		namesMap.put(ALL_TYPE, new String[] {"All Files"});
		
		extensionsMap.put(P12_TYPE, new String[] {"*.p12"});
		extensionsMap.put(PDF_TYPE, new String[] {"*.pdf"});
		extensionsMap.put(IMAGE_TYPE, new String[] {"*.png", "*.jpg", "*.gif"});
		extensionsMap.put(CERT_TYPE, new String[] {"*.cer"});
		extensionsMap.put(ALL_TYPE, new String[] {"*.*"});
                extensionsMap.put(LOG_DRIVER_TYPE, new String[] {"*.log"});
	}

        // just one file
	public static String openFileDialog(Shell sShell, String tipo) {
		FileDialog archivoDialog = new FileDialog(sShell, SWT.OPEN);
		archivoDialog.setFilterPath(PreferencesHelper.getPreferences().getString(PreferencesHelper.FILEDIALOG_PATH));
		archivoDialog.setText(LanguageResource.getLanguage().getString("open.dialog.title"));
		archivoDialog.setFilterNames(namesMap.get(tipo));
		archivoDialog.setFilterExtensions(extensionsMap.get(tipo));
		String selectedFile = archivoDialog.open();
		PreferencesHelper.getPreferences().setValue(PreferencesHelper.FILEDIALOG_PATH, archivoDialog.getFilterPath());
		PreferencesHelper.savePreferences();
		
		if (selectedFile != null) {
			File f = new File(selectedFile);
			if (!f.exists()) {
				selectedFile = null;
			}
		}
		return selectedFile;
	}
	
        public static String openFolderDialog(Shell sShell) {
		DirectoryDialog dirDialog = new DirectoryDialog(sShell, SWT.OPEN);
		dirDialog.setFilterPath(PreferencesHelper.getPreferences().getString(PreferencesHelper.FILEDIALOG_PATH));
		dirDialog.setText(LanguageResource.getLanguage().getString("open.dialog.title"));
		String dir = dirDialog.open();
		PreferencesHelper.getPreferences().setValue(PreferencesHelper.FILEDIALOG_PATH, dirDialog.getFilterPath());
		PreferencesHelper.savePreferences();
		
		return dir;
	}
        
	// multiple files
	public static List<File> openFilesDialog(Shell sShell, String tipo) {

		FileDialog archivoDialog = new FileDialog(sShell, SWT.MULTI);
		archivoDialog.setFilterPath(PreferencesHelper.getPreferences().getString(PreferencesHelper.FILEDIALOG_PATH));
		archivoDialog.setText(LanguageResource.getLanguage().getString("open.dialog.title"));
		archivoDialog.setFilterNames(namesMap.get(tipo));
		archivoDialog.setFilterExtensions(extensionsMap.get(tipo));
		String selectedFile = archivoDialog.open();
		PreferencesHelper.getPreferences().setValue(PreferencesHelper.FILEDIALOG_PATH, archivoDialog.getFilterPath());
		PreferencesHelper.savePreferences();
                
		List<File> fileList = new ArrayList<File>();
		if (selectedFile != null) {
			
			String[] selectedFiles = archivoDialog.getFileNames();
			for (int i = 0; i < selectedFiles.length; i++) {
				File f = new File(archivoDialog.getFilterPath() + File.separatorChar + selectedFiles[i]);
				if (f.exists())
					fileList.add(f);
			}
		}
		return fileList;
	}

	
	public static List<File> openDirDialog(Shell sShell, String tipo) { // lo dejo como recursivo

		DirectoryDialog dirDialog = new DirectoryDialog(sShell, SWT.OPEN);
		dirDialog. setFilterPath(PreferencesHelper.getPreferences().getString(PreferencesHelper.FILEDIALOG_PATH));
		dirDialog.setText(LanguageResource.getLanguage().getString("open.dialog.title"));
		String dir = dirDialog.open();
		PreferencesHelper.getPreferences().setValue(PreferencesHelper.FILEDIALOG_PATH, dirDialog.getFilterPath());
		PreferencesHelper.savePreferences();
		
		List<File> fileList = new ArrayList<File>();
		
		if (dir != null) {
			
			File dirFile = new File(dir);
			fileList = getFilesFromDir(dirFile, tipo);
		}
		
		return fileList;
	}
	
	public static List<File> getFilesFromDir(File dirFile, String tipo) {
		
		List<File> fileList = new ArrayList<File>();
		
		File[] files = dirFile.listFiles();
		
		for (File file : files) {
			
			if (file.isDirectory()) {
				
				List<File> fileList2 = getFilesFromDir(file, tipo);
				for (File file2 : fileList2) {
					fileList.add(file2);
				}
				
			} else {
		
				for (String extension : extensionsMap.get(tipo)) {
				
					if (file.getName().toLowerCase().endsWith(extension.substring(1, extension.length()) )) {
						fileList.add(file);
						break;
					}
				}
			}
		}
		
		return fileList;
	}

    public static List<File> listarArchivos(String dir, String tipo) {
            List<File> fileList = new ArrayList<File>();
            if (dir != null) {
                try {
                    File[] files = new File(dir).listFiles();

                    //OJO... Modificacion Yessica
                    //Para ordenar alfabeticamente los archivos
                    Arrays.sort(files, new Comparator() {
                        public int compare(Object a, Object b) {
                                File filea = (File)a;
                                File fileb = (File)b;
                                //--- Sort directories before files,
                                //    otherwise alphabetical ignoring case.
                                if (filea.isDirectory() && !fileb.isDirectory()) {
                                    return -1;
                                } else if (!filea.isDirectory() && fileb.isDirectory()) {
                                    return 1;
                                } else {
                                    return filea.getName().compareToIgnoreCase(fileb.getName());
                                }
                            }
                        });
                    ////////////////////////////////////////////////////////////
                        for (File file : files) {
                                for (String extension : extensionsMap.get(tipo)) {
                                        if (file.getName().toLowerCase().endsWith(extension.substring(1, extension.length()) )) {
                                                fileList.add(file);
                                                break;
                                        }
                                }
                        }

    //                    java.util.Collections.sort(fileList);

                } catch (NullPointerException e){
                    String m = LanguageResource.getLanguage().getString("error.directorio_no_encontrado");
                    logger.log(Level.SEVERE, m, e);
                    Display.getDefault().syncExec(new ProgressWriter(ProgressWriter.ERROR, m));
                }
            }
            return fileList;
    }
    //**************************************************************

        //OJO... Modificacion Yessica
        public static String openFileDialog(Shell sShell) {
            DirectoryDialog dirDialog = new DirectoryDialog(sShell, SWT.OPEN);
            dirDialog. setFilterPath(PreferencesHelper.getPreferences().getString(PreferencesHelper.FILEDIALOG_PATH));
            String dir = dirDialog.open();
            return dir;
        }
        //////////////////////////////
}