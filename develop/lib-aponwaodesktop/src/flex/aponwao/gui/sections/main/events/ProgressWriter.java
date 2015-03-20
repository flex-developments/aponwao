package flex.aponwao.gui.sections.main.events;

import flex.aponwao.gui.application.LoggingDesktopController;


/**
 * 
 * para escribir en la consola de la interfaz desde el hilo del progressbar
 *  
 */
public class ProgressWriter implements Runnable {

	private String m;
	private int i;
	
	public static final int INFO = 0;
	public static final int ERROR = 1;
        public static final int WARNING = 2;

	public ProgressWriter(int i, String m) {
		this.i = i;
		this.m = m;
	}

	public void run() {
            if (i == INFO) {
                LoggingDesktopController.printInfo(m);
            } else if (i == ERROR) {
                LoggingDesktopController.printError(m);
            } else if (i == WARNING) {
                LoggingDesktopController.printWarning(m);
            }
	}
}