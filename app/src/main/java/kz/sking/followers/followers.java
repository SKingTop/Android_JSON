package kz.sking.followers;

import android.graphics.Bitmap;

public class followers {
    private String[] logName = new String[200];
    private String[] logUrl = new String[200];
    private Bitmap iconData;

    public void setLogName(String name, int index) {
        this.logName[index] = name;
    }
    public void setLogUrl(String name, int index) {
        this.logUrl[index] = name;
    }
    public void setIconData(Bitmap iconData) {
        this.iconData = iconData;
    }

    public String getLogName(int index) {
        return logName[index];
    }
    public String getLogUrl(int index) {
        return logUrl[index];
    }
    public Bitmap getIconData() {
        return iconData;
    }
}
