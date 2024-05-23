package Outliner.Controller;

import java.io.*;

import Outliner.Model.*;
import Outliner.View.*;


public class OutlineManager {

    public OutlineManager() {
        
    }

    private SaveandLoadWindow slWin;

    public void saveOutline(Outline outline) {
        try {
            slWin = new SaveandLoadWindow();
            FileOutputStream f = new FileOutputStream(new File(slWin.createSaveLoadWindow()));
		    ObjectOutputStream o = new ObjectOutputStream(f);

            o.writeObject(outline);
            o.close();
			f.close();
            
        } catch (Exception e) {
            System.out.println("Unable to save file");
            e.printStackTrace();
        }
        
    }

    public void loadOutline(String filepath) {
        try {
            FileInputStream fi = new FileInputStream(new File(filepath));
			ObjectInputStream oi = new ObjectInputStream(fi);

            OutlineGUI.outline = (Outline) oi.readObject();
            oi.close();
			fi.close();
        } catch (Exception e) {
            System.out.println("Unable to load file");
            e.printStackTrace();
        }
        
    }
    
}
