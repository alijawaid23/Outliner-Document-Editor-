package Outliner.View;

import javax.swing.*;

public class SaveandLoadWindow {

    public String createSaveLoadWindow() {
        
        JFileChooser fileChooser = new JFileChooser();
        int option = fileChooser.showSaveDialog(null);
        if (option == JFileChooser.APPROVE_OPTION) {
            // User clicked "Save"
            String filePath = fileChooser.getSelectedFile().getPath();
            return filePath;
        }
        return null;
    }


    
}
