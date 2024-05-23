package Outliner.Model.OutlineData;

import java.io.Serializable;

public class Tag implements Serializable{
    private String text;

    public Tag(String text) {
        this.text = text;
    }
    
    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
