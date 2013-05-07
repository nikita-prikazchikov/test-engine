package selenium.log;

/**
 * Created by IntelliJ IDEA.
 * User: nprikazchikov
 * Date: 26.01.12
 * Time: 10:26
 * To change this template use File | Settings | File Templates.
 */
public class Screenshot {
    
    private String name;
    private String url;

    public Screenshot(String name, String url) {
        this.name = name;
        this.url = url;
    }

    @Override
    public String toString() {
        return String.format( "{\"name\":\"%s\",\"url\":\"%s\"}",  this.getName(), this.getUrl() );
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
