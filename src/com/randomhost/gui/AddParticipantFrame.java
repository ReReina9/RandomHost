package com.randomhost.gui;

import com.randomhost.logic.Participant;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.event.ActionListener;

class AddParticipantFrame extends JFrame {

    private JPanel panel;

    private JLabel nameJLabel;
    private JLabel lastNameJLabel;

    private JTextField nameJTextField;
    private JTextField lastNameJTextField;

    private JButton addParticipantJButton;
    private JButton cancelJButton;

    private SpringLayout layout;


    AddParticipantFrame(){
        super("Add Participant");

        setComponents();
        setComponentsLayout();
        setPanel();
        setFrame();

    }

    private void setComponents() {
        nameJLabel = new JLabel();
        nameJLabel.setText("Name:");

        lastNameJLabel = new JLabel();
        lastNameJLabel.setText("Last Name:");

        nameJTextField = new JTextField();
        lastNameJTextField = new JTextField();

        addParticipantJButton = new JButton("Add");
        getRootPane().setDefaultButton(addParticipantJButton);
        cancelJButton = new JButton("Cancel");
        addParticipantJButton.setEnabled(false);
        dynamicEnablingAddParticipantJButton();
        cancelJButton.addActionListener(event -> closeFrame());

        panel = new JPanel();
    }

    private void setComponentsLayout() {
        layout = new SpringLayout();
        setNameLabelLayout();
        setNameJTextFieldLayout();
        setLastNameLabelLayout();
        setLastNameJTextFieldLayout();
        setCancelButtonLayout();
        setAddParticipantJButtonLayout();
        panel.setLayout(layout);
    }

    private void setNameLabelLayout() {
        layout.putConstraint(SpringLayout.WEST, nameJLabel, 45, SpringLayout.WEST, panel);
        layout.putConstraint(SpringLayout.NORTH, nameJLabel, 8, SpringLayout.NORTH, panel);
    }

    private void setNameJTextFieldLayout() {
        layout.putConstraint(SpringLayout.WEST, nameJTextField, 5, SpringLayout.EAST, nameJLabel);
        layout.putConstraint(SpringLayout.NORTH, nameJTextField, 5, SpringLayout.NORTH, panel);
        layout.putConstraint(SpringLayout.EAST, panel, 5, SpringLayout.EAST, nameJTextField);
    }

    private void setLastNameLabelLayout() {
        layout.putConstraint(SpringLayout.WEST, lastNameJLabel, 15, SpringLayout.WEST, panel);
        layout.putConstraint(SpringLayout.NORTH, lastNameJLabel, 8, SpringLayout.SOUTH, nameJTextField);
    }

    private void setLastNameJTextFieldLayout() {
        layout.putConstraint(SpringLayout.WEST, lastNameJTextField, 5, SpringLayout.EAST, lastNameJLabel);
        layout.putConstraint(SpringLayout.NORTH, lastNameJTextField, 5, SpringLayout.SOUTH, nameJTextField);
        layout.putConstraint(SpringLayout.EAST, lastNameJTextField, -5, SpringLayout.EAST, panel);
    }

    private void setCancelButtonLayout() {
        layout.putConstraint(SpringLayout.WEST, cancelJButton,  5, SpringLayout.WEST, panel);
        layout.putConstraint(SpringLayout.SOUTH, cancelJButton, -5, SpringLayout.SOUTH, panel);
        layout.putConstraint(SpringLayout.NORTH, cancelJButton, 15, SpringLayout.SOUTH, lastNameJLabel);
    }

    private void setAddParticipantJButtonLayout() {
        layout.putConstraint(SpringLayout.WEST, addParticipantJButton, 0, SpringLayout.EAST, cancelJButton);
        layout.putConstraint(SpringLayout.EAST, addParticipantJButton, -5, SpringLayout.EAST, panel);
        layout.putConstraint(SpringLayout.SOUTH, addParticipantJButton, -5, SpringLayout.SOUTH, panel);
        layout.putConstraint(SpringLayout.NORTH, addParticipantJButton, 15, SpringLayout.SOUTH, lastNameJLabel);
    }

    private void setPanel() {
        panel.add(nameJLabel);
        panel.add(lastNameJLabel);
        panel.add(nameJTextField);
        panel.add(lastNameJTextField);
        panel.add(cancelJButton);
        panel.add(addParticipantJButton);
    }

    private void setFrame() {
        add(panel);
        setSize( 350, 140);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setVisible(false);
    }

    Participant getParticipantInfo(){
        String name = nameJTextField.getText();
        String lastName = lastNameJTextField.getText();
        closeFrame();
        return new Participant(name, lastName);
    }

    private void closeFrame(){
        setVisible(false);
        setLocationRelativeTo(null);
        nameJTextField.setText("");
        nameJTextField.requestFocusInWindow();
        lastNameJTextField.setText("");
    }

    void setParticipantJButtonAction(ActionListener action) {
        addParticipantJButton.addActionListener(action);
    }

    private void dynamicEnablingAddParticipantJButton(){
        nameJTextField.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent documentEvent) {
                if(!lastNameJTextField.getText().isEmpty())
                    addParticipantJButton.setEnabled(true);
            }

            @Override
            public void removeUpdate(DocumentEvent documentEvent) {
                if(nameJTextField.getText().isEmpty() || lastNameJTextField.getText().isEmpty())
                    addParticipantJButton.setEnabled(false);
            }

            @Override
            public void changedUpdate(DocumentEvent documentEvent) {
                //not needed
            }
        });

        lastNameJTextField.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent documentEvent) {
                if(!nameJTextField.getText().isEmpty())
                    addParticipantJButton.setEnabled(true);
            }

            @Override
            public void removeUpdate(DocumentEvent documentEvent) {
                if(nameJTextField.getText().isEmpty() || lastNameJTextField.getText().isEmpty())
                    addParticipantJButton.setEnabled(false);
            }

            @Override
            public void changedUpdate(DocumentEvent documentEvent) {
                //not needed
            }
        });
    }
}
