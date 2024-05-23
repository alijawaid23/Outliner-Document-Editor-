package Outliner.Model;


import java.io.Serializable;
import java.util.*;


public class Outline implements Serializable {
    private List<OutlineItem> items;

    public Outline() {
        items = new ArrayList<>();
    }

    public List<OutlineItem> getItems() {
        return items;
    }

    public void setItems(List<OutlineItem> items) {
        this.items = items;
    }

    public void erase(OutlineItem toRemove) {
        items.remove(toRemove);
    }


    public void addTopLevelItem(OutlineItem newItem) {
        items.add(newItem);
    }

}
