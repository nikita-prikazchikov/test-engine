package selenium;

public class Window {
    
    private long screenX;
    private long screenY;

    private long outerHeight;
    private long outerWidth;

    private long innerHeight;
    private long innerWidth;

    public Window(long screenX, long screenY, long outerHeight, long outerWidth, long innerHeight, long innerWidth) {
        this.screenX = screenX;
        this.screenY = screenY;
        this.outerHeight = outerHeight;
        this.outerWidth = outerWidth;
        this.innerHeight = innerHeight;
        this.innerWidth = innerWidth;
    }

    public long getScreenX() {
        return screenX;
    }

    public long getScreenY() {
        return screenY;
    }

    public long getOuterHeight() {
        return outerHeight;
    }

    public long getOuterWidth() {
        return outerWidth;
    }

    public long getInnerHeight() {
        return innerHeight;
    }

    public long getInnerWidth() {
        return innerWidth;
    }
}
