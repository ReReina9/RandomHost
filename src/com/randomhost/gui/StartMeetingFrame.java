package com.randomhost.gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

class StartMeetingFrame extends JFrame {

    private JPanel panel;
    private JLabel descriptionJLabel;
    private JLabel hostJLabel;
    private JButton okJButton;
    private SpringLayout layout;

    StartMeetingFrame(){
        super("Meeting Started");

        setComponents();
        setComponentsLayout();
        setPanel();
        setFrame();
    }

    private void setComponents(){
        descriptionJLabel = new JLabel("The selected host is:",SwingConstants.CENTER);
        hostJLabel = new JLabel("",SwingConstants.CENTER);
        hostJLabel.setFont(new Font("", Font.PLAIN, 40));
        okJButton = new JButton("Ok");
        getRootPane().setDefaultButton(okJButton);
        okJButton.requestFocusInWindow();
        panel = new JPanel();
    }

    private void setComponentsLayout() {
        layout = new SpringLayout();
        setDescriptionJLabelLayout();
        setHostJLabelLayout();
        setOkJButtonLayout();
    }

    private void setDescriptionJLabelLayout() {
        layout.putConstraint(SpringLayout.WEST, descriptionJLabel, 20, SpringLayout.WEST, panel);
        layout.putConstraint(SpringLayout.NORTH, descriptionJLabel, 20, SpringLayout.NORTH, panel);
        layout.putConstraint(SpringLayout.EAST, descriptionJLabel, -20, SpringLayout.EAST, panel);
    }

    private void setHostJLabelLayout() {
        layout.putConstraint(SpringLayout.WEST, hostJLabel, 20, SpringLayout.WEST, panel);
        layout.putConstraint(SpringLayout.NORTH, hostJLabel, 40, SpringLayout.SOUTH, descriptionJLabel);
        layout.putConstraint(SpringLayout.EAST, hostJLabel, -20, SpringLayout.EAST, panel);
    }

    private void setOkJButtonLayout() {
        layout.putConstraint(SpringLayout.WEST, okJButton, 20, SpringLayout.WEST, panel);
        layout.putConstraint(SpringLayout.NORTH, okJButton, 40, SpringLayout.SOUTH, hostJLabel);
        layout.putConstraint(SpringLayout.EAST, okJButton, -20, SpringLayout.EAST, panel);
        layout.putConstraint(SpringLayout.SOUTH, okJButton, -20, SpringLayout.SOUTH, panel);
    }

    private void setPanel() {
        panel.setLayout(layout);
        panel.add(descriptionJLabel);
        panel.add(hostJLabel);
        panel.add(okJButton);
    }

    private void setFrame() {
        add(panel);
        setSize( 600, 250);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setVisible(false);
    }

    private void closeFrame(){
        setVisible(false);
        setLocationRelativeTo(null);
    }

    void setHost(String host){
        hostJLabel.setText(host);
        setVisible(true);
    }

    void setokJButtonAction(ActionListener action){
        okJButton.addActionListener(action);
        okJButton.addActionListener(event -> closeFrame());
    }

}
