package Outliner.View;

import javax.swing.*;

import Outliner.Model.*;
import Outliner.Model.OutlineData.*;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

public class TagsWindow extends JFrame {
    private JPanel tagsPanel;
    private List<Tag> tagsList;
    private JTextField tagTextField;
    private JList<String> tagsJList;
    private DefaultListModel<String> model;

    public TagsWindow(OutlineItem item) {
        super("Tags For "+item.getText());
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    
        tagsPanel = new JPanel(new BorderLayout());
        tagsList = item.getTags();
        if (tagsList == null) {
            tagsList = new ArrayList<>();
        }
        model = new DefaultListModel<String>();

    
        JLabel tagsLabel = new JLabel("Tags:");
        tagsJList = new JList<>();
        tagsJList.setModel(model);
        JScrollPane scrollPane = new JScrollPane(tagsJList);
    
        
        for (Tag tag : tagsList) {
            model.addElement(tag.getText());
        }
    
        tagsPanel.add(tagsLabel, BorderLayout.NORTH);
        tagsPanel.add(scrollPane, BorderLayout.CENTER);
    
        JPanel tagInputPanel = new JPanel(new FlowLayout());
        tagTextField = new JTextField(10);
        JButton addButton = new JButton("Add Tag");
        addButton.addActionListener(new AddButtonActionListener());
        JButton removeButton = new JButton("Remove Tag");
        removeButton.addActionListener(new RemoveButtonActionListener());
    
        tagInputPanel.add(tagTextField);
        tagInputPanel.add(addButton);
        tagInputPanel.add(removeButton);
    
        tagsPanel.add(tagInputPanel, BorderLayout.SOUTH);
    
        add(tagsPanel);
    }
    

    private class AddButtonActionListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String newTag = tagTextField.getText().trim();
            if (!newTag.isEmpty() && !model.contains(newTag)) {
                tagsList.add(new Tag(newTag));
                model = (DefaultListModel<String>) tagsJList.getModel();
                model.addElement(newTag);
                tagTextField.setText("");
                OutlineGUI.updateTextArea();
            }
        }
    }

    private class RemoveButtonActionListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            int selectedIndex = tagsJList.getSelectedIndex();
            if (selectedIndex != -1) {
                Tag selectedTag = tagsList.get(selectedIndex);
                tagsList.remove(selectedIndex);
                model = (DefaultListModel<String>) tagsJList.getModel();
                model.remove(selectedIndex);
                tagTextField.setText(selectedTag.getText());
            }
        }
    }
}
