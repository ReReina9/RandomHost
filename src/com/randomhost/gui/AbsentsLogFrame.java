package com.randomhost.gui;

import javax.swing.*;
import java.awt.*;

class AbsentsLogFrame extends JFrame {

    private JPanel panel;
    private JTextPane asbentsLogJTextPane;
    private JButton okJButton;
    private String absentsLog;
    private JScrollPane scrollPane;

    AbsentsLogFrame(String absentsLog){
        super("Absents Log");
        this.absentsLog = absentsLog;

        setComponents();
        setComponentsLayout();
        setFrame();
    }

    private void setComponents() {
        asbentsLogJTextPane = new JTextPane();
        asbentsLogJTextPane.setText(absentsLog);
        asbentsLogJTextPane.setEditable(false);
        asbentsLogJTextPane.setBorder(BorderFactory.createLineBorder(Color.WHITE, 5));

        okJButton = new JButton("Ok");
        okJButton.addActionListener(event -> closeFrame());

        scrollPane = new JScrollPane(asbentsLogJTextPane);
        scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);

        panel = new JPanel();
    }

    private void setComponentsLayout() {
        panel.setLayout(new BorderLayout());
        panel.add(scrollPane, BorderLayout.CENTER);
        panel.add(okJButton, BorderLayout.SOUTH);
    }

    private void setFrame() {
        add(panel);
        setSize( 600, 450);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setVisible(false);
    }

    private void closeFrame(){
        setVisible(false);
        setLocationRelativeTo(null);
    }

    String getAbsentsLog(){
        return absentsLog;
    }
}
