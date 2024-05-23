package Outliner.View;

import javax.swing.*;

import Outliner.Model.OutlineItem;
import Outliner.Model.OutlineData.Description;


public class DiscriptionWindow {
    
    private JTextArea textArea;
    private JScrollPane scrollPane;
    private JFrame frame;

    public DiscriptionWindow(OutlineItem item) {

        frame = new JFrame("Description Window");
        frame.setSize(400, 300);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        
        textArea = new JTextArea();
        scrollPane = new JScrollPane(textArea);
        textArea.setText(item.getDescription().getText().replace("<br/>", "\n"));
        
        JButton acceptButton = new JButton("Accept");
        acceptButton.addActionListener(e -> {
            item.setDescription(new Description(textArea.getText().replace("\n", "<br/>")));
            OutlineGUI.updateTextArea();
            frame.dispose();
        });
        
        JButton cancelButton = new JButton("Cancel");
        cancelButton.addActionListener(e -> {
            frame.dispose();
        });
        
        JPanel buttonPanel = new JPanel();
        buttonPanel.add(acceptButton);
        buttonPanel.add(cancelButton);
        
        frame.add(scrollPane);
        frame.add(buttonPanel, "South");
        
        frame.setVisible(true);
    }

}
