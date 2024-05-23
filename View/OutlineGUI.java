package Outliner.View;

import java.util.ArrayList;
import java.util.List;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

import Outliner.Controller.*;
import Outliner.Model.*;
import Outliner.Model.OutlineData.*;

public class OutlineGUI extends JFrame implements ActionListener {
    private static JList<OutlineItem> itemList;
    private DefaultListModel<OutlineItem> itemModel;
    private static JLabel textArea;
    private JButton addButton;
    private JButton addChildButton;
    private JButton removeButton;
    private JButton editButton;
    private JButton saveButton;
    private JButton loadButton;
    private JButton tagsButton;
    private JButton toggleComplete;
    private JButton editDiscription;
    public static Outline outline;
    private OutlineManager manager;
    public static TagsWindow tagsWindow;
    public SaveandLoadWindow slWin;

    public OutlineGUI() {
        super("Outline");

        // Create the outline and initialize the GUI components
        outline = new Outline();
        itemModel = new DefaultListModel<>();
        itemList = new JList<>(itemModel);
        itemList.setFont(new Font("Verdana", Font.PLAIN, 20));
        itemList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        itemList.addListSelectionListener(e -> {
            OutlineItem selectedItem = itemList.getSelectedValue();
            if (selectedItem != null) {
                updateTextArea();
            }
        });
        JScrollPane listScrollPane = new JScrollPane(itemList);
        textArea = new JLabel ();

        
        JScrollPane textScrollPane = new JScrollPane(textArea);
        JToolBar toolbar = new JToolBar();
        toolbar.setFloatable(false);

        addButton = new JButton("Add Section");
        addButton.addActionListener(this);
        toolbar.add(addButton);

        addChildButton = new JButton("Add Sub-section");
        addChildButton.addActionListener(this);
        toolbar.add(addChildButton);

        editButton = new JButton("Edit Item");
        editButton.addActionListener(this);
        toolbar.add(editButton);

        removeButton = new JButton("Remove Item");
        removeButton.addActionListener(this);
        toolbar.add(removeButton);
        
        tagsButton = new JButton("Add Tags");
        tagsButton.addActionListener(this);
        toolbar.add(tagsButton);

        saveButton = new JButton("Save");
        saveButton.addActionListener(this);
        toolbar.add(saveButton);

        loadButton = new JButton("Load");
        loadButton.addActionListener(this);
        toolbar.add(loadButton);

        toggleComplete = new JButton("Togle Completion");
        toggleComplete.addActionListener(this);
        toolbar.add(toggleComplete);

        editDiscription = new JButton("Edit Discription");
        editDiscription.addActionListener(this);
        toolbar.add(editDiscription);

        // Set up the layout and add the components to the frame
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setPreferredSize(new Dimension(900, 550));

        GridBagConstraints c = new GridBagConstraints();
        c.gridx = 0;
        c.gridy = 0;
        c.gridwidth = 2;
        c.fill = GridBagConstraints.BOTH;
        panel.add(toolbar, c);

        c.gridx = 0;
        c.gridy = 2;
        c.weightx = 3.0;
        c.weighty = 3.0;
        c.gridwidth = 1;
        c.fill = GridBagConstraints.BOTH;
        panel.add(listScrollPane, c);

        c.gridx = 1;
        c.gridy = 2;
        c.weightx = 3.0;
        c.weighty = 3.0;
        c.gridwidth = 1;
        c.fill = GridBagConstraints.BOTH;
        panel.add(textScrollPane, c);

        // Set up the frame
        add(panel);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }

    public static void updateTextArea() {
        OutlineItem selectedItem = itemList.getSelectedValue();
        textArea.setText(selectedItem.getText());
        textArea.setVerticalAlignment(JLabel.TOP);
        textArea.setFont(new Font("Verdana", Font.PLAIN, 14));
        List<String> tagsList = new ArrayList<String>();
        for (Tag tag : selectedItem.getTags()) {
            tagsList.add(tag.getText());
        }
        textArea.setText("<html> <font size=\"+2\">"+textArea.getText()+"</font><br/><b>Tags:"+tagsList+"</b><html/>");
        textArea.setText(textArea.getText()+"</font><br/><b>Status:"+(selectedItem.getCompleate() ? "<font color = green>Complete</font>" : "<font color = red>Not Complete</font>")+"</b><html/>");
        textArea.setText(textArea.getText()+"</font><br/><b>Discription:</b><br/>"+selectedItem.getDescription().getText());
        System.out.println(textArea.getText());
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        manager = new OutlineManager();
        if (e.getSource() == addButton) {
            String text = JOptionPane.showInputDialog(this, "Enter Section name:");
            if (text != null && !text.isEmpty()) {
                outline.addTopLevelItem(new OutlineItem(text, 0, null, false));
                updateItemList();
                textArea.setText(null);
            }
        } else if (e.getSource() == addChildButton) {
            OutlineItem selectedItem = itemList.getSelectedValue();
            if (selectedItem != null) {
                String text = JOptionPane.showInputDialog(this, "Enter Sub-Section name:");
                if (text != null && !text.isEmpty()) {
                    selectedItem.addChild(new OutlineItem(text, selectedItem.getLevel() + 1, selectedItem, false));
                    updateItemList();
                    updateTextArea();
                }
            }else {
                JOptionPane.showMessageDialog(this, "Please select a section first.");
            }
        } else if (e.getSource() == editButton) {
            OutlineItem selectedItem = itemList.getSelectedValue();
            if (selectedItem != null) {
                String text = JOptionPane.showInputDialog(this, "Enter new name:");
                if (text != null && !text.isEmpty()) {
                    selectedItem.changeItem(text);
                    updateItemList();
                    updateTextArea();
                }
            }else {
                JOptionPane.showMessageDialog(this, "Please select a section first.");
            }

        } else if (e.getSource() == tagsButton) {
            OutlineItem selectedItem = itemList.getSelectedValue();
            if (selectedItem != null) {
                TagsWindow tagsWindow = new TagsWindow(selectedItem);
                tagsWindow.setVisible(true);
                updateTextArea();
            }else {
                JOptionPane.showMessageDialog(this, "Please select a section first.");
            }

        } else if (e.getSource() == removeButton) {

            OutlineItem selectedItem = itemList.getSelectedValue();
            if (selectedItem != null) {
                if (selectedItem.getParent() == null)
                    outline.erase(selectedItem);
                else {
                    List<OutlineItem> newChildren = selectedItem.getParent().getChildren();
                    newChildren.remove(selectedItem);
                    selectedItem.getParent().setChildren(newChildren);
                }
                updateItemList();
                updateTextArea();

            }else {
                JOptionPane.showMessageDialog(this, "Please select a section first.");
            }
        } else if (e.getSource() == saveButton) {
            try {
                manager.saveOutline(outline);
            } catch (Exception j) {
                System.out.println(j);
            }
        } else if (e.getSource() == loadButton) {
            slWin = new SaveandLoadWindow();    
            manager.loadOutline(slWin.createSaveLoadWindow());
            updateItemList();
        } else if (e.getSource() == toggleComplete) {

            OutlineItem selectedItem = itemList.getSelectedValue();
            if (selectedItem != null) {
                if (selectedItem.getCompleate())
                selectedItem.setCompleate(false);
                else
                selectedItem.setCompleate(true);
                updateItemList();
                updateTextArea();

            }else {
                JOptionPane.showMessageDialog(this, "Please select a section first.");
            }
        } else if (e.getSource() == editDiscription) {

            OutlineItem selectedItem = itemList.getSelectedValue();
            if (selectedItem != null) {
                DiscriptionWindow descriptionWindow = new DiscriptionWindow(selectedItem);
                updateTextArea();
                
            }else {
                JOptionPane.showMessageDialog(this, "Please select a section first.");
            }
        }
    }

    private void updateItemList() {
        itemModel.clear();
        List<OutlineItem> items = outline.getItems();
        for (OutlineItem item : items) {
            itemModel.addElement(item);
            addSubItems(item.getChildren());
        }
    }


    private void addSubItems(List<OutlineItem> items) {
        for (OutlineItem item : items) {
            itemModel.addElement(item);
            addSubItems(item.getChildren());
        }
    }  
}