package flex.aponwao.gui.application;

import flex.aponwao.gui.sections.preferences.helpers.PreferencesHelper;
import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ExportHelper {

	private static final Logger logger = Logger.getLogger(ExportHelper.class.getName());
	
	public static final String PDF_TYPE = ".pdf";

	public static List<List<String>> importCSV(String fileName) {

		List<List<String>> list = new ArrayList<List<String>>();

		try {
			// Open the file that is the first command line parameter
			FileInputStream fstream = new FileInputStream(fileName);
			// Get the object of DataInputStream
			DataInputStream in = new DataInputStream(fstream);
			BufferedReader br = new BufferedReader(new InputStreamReader(in));
			String line;

			boolean header = true;
			int max_size = 0;
			// Read File Line By Line
			while ((line = br.readLine()) != null) {

				List<String> l = new ArrayList<String>();

				String[] splitLine = line.split(";");
                                l.addAll(Arrays.asList(splitLine));

				if (header) {
					// Supongo que es la primera linea y que es la cabecera
					max_size = l.size();
					header = false;
					list.add(l);
				} else if (l.size() == max_size) {
					list.add(l);
				} else {
					logger.log(Level.SEVERE, "El registro no cumple el patron, no se a\u00f1adir\u00e1 (size) {0} Tama\u00f1o {1}", new Object[]{l.size(), max_size});
					LoggingDesktopController.printError("El registro no cumple el patron, no se añadirá (size) " + l.size() + " (CIF) "
							+ l.get(l.size() - 1));
				}

			}
			// Close the input stream
			in.close();

		} catch (FileNotFoundException e) {
			String m = MessageFormat.format(LanguageResource.getLanguage().getString("error.importing_csv"), fileName);
			logger.log(Level.SEVERE, m, e);
			LoggingDesktopController.printError(m);

		} catch (IOException e) {
			String m = MessageFormat.format(LanguageResource.getLanguage().getString("error.importing_csv"), fileName);
			logger.log(Level.SEVERE, m, e);
			LoggingDesktopController.printError(m);
		}

		return list;

	}

	public static void exportCSV(String fileName, List<List<String>> array) {

		try {
			FileWriter writer = new FileWriter(fileName);

			for (List<String> list : array) {
				for (String s : list) {
					writer.append(s);
					writer.append(';');
				}
				writer.append('\n');
			}
			writer.flush();
			writer.close();

		} catch (IOException e) {
			String m = MessageFormat.format(LanguageResource.getLanguage().getString("error.exporting_csv"), fileName);
			logger.log(Level.SEVERE, m, e);
			LoggingDesktopController.printError(m);
		}
	}

	public static void copyFile(String srFile, String dtFile) {

		try {
			File f1 = new File(srFile);
			File f2 = new File(dtFile);
			InputStream in = new FileInputStream(f1);

			// For Append the file.
			// OutputStream out = new FileOutputStream(f2,true);

			// For Overwrite the file.
			OutputStream out = new FileOutputStream(f2);

			byte[] buf = new byte[1024];
			int len;
			while ((len = in.read(buf)) > 0) {
				out.write(buf, 0, len);
			}
			in.close();
			out.close();

		} catch (FileNotFoundException e) {
			logger.log(Level.SEVERE, e.getMessage() + " in the specified directory.", e);
			
		} catch (IOException e) {
			logger.log(Level.SEVERE, "", e);
		}
	}

	public static void deleteFile(String fileName) {

		// A File object to represent the filename
		File f = new File(fileName);

		// Make sure the file or directory exists and isn't write protected
		if (!f.exists())
			logger.severe("Delete: no such file or directory: " + fileName);

		if (!f.canWrite())
			logger.severe("Delete: write protected: " + fileName);

		// If it is a directory, make sure it is empty
		if (f.isDirectory()) {
			String[] files = f.list();
			if (files.length > 0)
				logger.severe("Delete: directory not empty: " + fileName);
		}

		// Attempt to delete it
		boolean success = f.delete();

		if (!success)
			logger.severe("Delete: deletion failed");
	}

	public static String getExtension(String s) {

		return (s.substring(s.lastIndexOf('.'), s.length()));
	}
	
	public static String getOutputName(String name) {
		
		return (name.substring(0, name.lastIndexOf("."))
				+ PreferencesHelper.getPreferences().getString(PreferencesHelper.SAVE_EXTENSION) + PDF_TYPE);
		
	}
	
	public static String getOutputDir(File file) {
		
		if (PreferencesHelper.getPreferences().getBoolean(PreferencesHelper.OUTPUT_AUTO_ENABLE)) {
			
			return file.getParentFile().getPath();
			
		} else {
			
			return PreferencesHelper.getPreferences().getString(PreferencesHelper.APLICATION_PATH_FINAL_FOLDER_VALUE);
		}
		
	}

}
