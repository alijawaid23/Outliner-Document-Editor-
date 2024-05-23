package Outliner.Model;


import java.io.Serializable;
import java.util.*;

import Outliner.Model.OutlineData.*;

public class OutlineItem implements Serializable{
    private String text;
    private int level;
    private OutlineItem parent;
    private List<OutlineItem> children;
    private List<Tag> tags;
    private boolean complete;
    private Description description;

    public Description getDescription() {
        return description;
    }

    public void setDescription(Description description) {
        this.description = description;
    }

    public boolean getCompleate() {
        return complete;
    }

    public void setCompleate(boolean value) {
        this.complete = value;
        
    }

    public List<Tag> getTags() {
        return tags;
    }

    public void clearTags() {
        this.tags.clear();
    }

    public OutlineItem getParent() {
        return parent;
    }

    public OutlineItem(String text, int level, OutlineItem parent, boolean complete) {
        this.text = text;
        this.level = level;
        this.parent = parent;
        this .complete = complete;
        children = new ArrayList<>();
        tags = new ArrayList<>();
        description = new Description("");
    }


    public String getText() {
        return text;
    }

    public int getLevel() {
        return level;
    }

    public List<OutlineItem> getChildren() {
        return children;
    }

    public void addChild(OutlineItem newChild) {
        children.add(newChild);
    }

    public void setChildren(List<OutlineItem> children) {
        this.children = children;
    }

    public void changeItem(String newText) {
        this.text= newText;
    }

    public void addTags(Tag tag) {
        tags.add(tag);

    }

    public void addTags(String tag) {

    }


    public boolean isComplete() {
        return complete;
    }

    public void setComplete(boolean complete) {
        this.complete = complete;
    }

    

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        String temp = text;
        if (this.getCompleate()){
            temp = text+"</s></html>";
            sb.append("<html><s>");
        }

        for (int i = 0; i < level; i++) {
            sb.append("---");
        }
        sb.append(temp);
        return sb.toString();
    }

}