package Outliner.Model.OutlineData;

import java.io.Serializable;

public class Description implements Serializable{
    
    private String text;

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    } 

    public Description(String text) {
        this.text = text;
    }
}
