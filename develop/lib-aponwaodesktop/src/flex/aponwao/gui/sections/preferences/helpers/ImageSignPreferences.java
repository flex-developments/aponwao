package flex.aponwao.gui.sections.preferences.helpers;

/**
 *
 * @author yessica
 */
public class ImageSignPreferences {
    private String name = null;
    private String type = null;
    private String imageVisible = null;
    private String path = null;
    private String posX = null;
    private String posY = null;
    private String height = null;
    private String width = null;
    private String page = null;
    private String reason = null;
    private String locate = null;

    public ImageSignPreferences() {
    }
    
    public ImageSignPreferences(String name, String type, String imageVisible, String path, String posX,
            String posY, String height, String width, String page, String reason, String locate) {
        if(ifAceptar(name)) this.name = name;
        if(ifAceptar(type)) this.type = type;
        if(ifAceptar(imageVisible)) this.imageVisible = imageVisible;
        if(ifAceptar(path)) this.path = path;
        if(ifAceptar(posX)) this.posX = posX;
        if(ifAceptar(posY)) this.posY = posY;
        if(ifAceptar(height)) this.height = height;
        if(ifAceptar(width)) this.width = width;
        if(ifAceptar(page)) this.page = page;
        if(ifAceptar(reason)) this.reason = reason;
        if(ifAceptar(locate)) this.locate = locate;
    }
    
    public String getName() {
        return name;
    }

    public void setName(String name) {
        if(ifAceptar(name)) this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        if(ifAceptar(type)) this.type = type;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        if(ifAceptar(path)) this.path = path;
    }

    public String getImageVisible() {
        return imageVisible;
    }

    public void setImageVisible(String imageViisble) {
        if(ifAceptar(imageViisble)) this.imageVisible = imageViisble;
    }

    public String getPosX() {
        return posX;
    }

    public void setPosX(String posX) {
        if(ifAceptar(posX)) this.posX = posX;
    }

    public String getPosY() {
        return posY;
    }

    public void setPosY(String posY) {
        if(ifAceptar(posY)) this.posY = posY;
    }

    public String getHeight() {
        return height;
    }

    public void setHeight(String heigth) {
        if(ifAceptar(heigth)) this.height = heigth;
    }

    public String getWidth() {
        return width;
    }

    public void setWidth(String width) {
        if(ifAceptar(width)) this.width = width;
    }

    public String getPage() {
        return page;
    }

    public void setPage(String page) {
        if(ifAceptar(page)) this.page = page;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        if(ifAceptar(reason)) this.reason = reason;
    }

    public String getLocate() {
        return locate;
    }

    public void setLocate(String locate) {
        if(ifAceptar(locate)) this.locate = locate;
    }
    
    private boolean ifAceptar(String valor) {
        if((valor!=null)&&(!valor.equals(" "))&&(!valor.toUpperCase().equals("NULL"))&&(!valor.toUpperCase().equals(""))) return true;
        else return false;
    }
}
