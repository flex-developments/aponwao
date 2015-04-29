package flex.aponwao.gui.application;


public class ValidatorHelper {
	public static String formatedTextButton(String s) {
		while (s.length() < 22)
			s = s + " ";
		
		return (s);
	}

	public static String formatedText(String s) {
		if (s == null) {
			return ("");
		}
		else {
			return (s);	
		}
	}
	
	public static String nonFormatedText(String s) {
		if (s.equals("")) {
			return (null);
		}
		else {
			return (s);	
		}
	}
}