package flex.aponwao.gui.sections.preferences.helpers;

/**
 *
 * @author flopez
 */
public class TimeStampPreferences {
    private String name = null;
    private String url = null;
    private String user = null;
    private String password = null;

    public TimeStampPreferences() {
    }
    
    public TimeStampPreferences(String name, String url, String user, String password) {
        if(ifAceptar(name)) this.name = name;
        if(ifAceptar(url)) this.url = url;
        if(ifAceptar(user)) this.user = user;
        if(ifAceptar(password)) this.password = password;
    }
    
    public String getName() {
        return name;
    }

    public void setName(String name) {
        if(ifAceptar(name)) this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        if(ifAceptar(password)) this.password = password;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        if(ifAceptar(url)) this.url = url;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        if(ifAceptar(user)) this.user = user;
    }
    
    private boolean ifAceptar(String valor) {
        if((valor!=null)&&(!valor.equals(" "))&&(!valor.toUpperCase().equals("NULL"))&&(!valor.toUpperCase().equals(""))) return true;
        else return false;
    }
}
