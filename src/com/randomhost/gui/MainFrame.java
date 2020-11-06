package com.randomhost.gui;

import com.randomhost.logic.Participant;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.util.function.BooleanSupplier;

import static javax.swing.JComponent.WHEN_FOCUSED;

class MainFrame extends JFrame {

    private JPanel participantsJPanel;
    private JPanel buttonsJPanel;

    private DefaultListModel<String> model;
    private JList<String> participantsJList;
    private JScrollPane scrollPane;

    private JButton addParticipantJButton;
    private JButton removeParticipantJButton;
    private JButton setAbsentParticipantJButton;
    private JButton showAbsentLogJButton;
    private JButton startMeetingJButton;


    MainFrame(){
        super("RandomHost");

        setComponents();
        setContainer();
        setFrame();
    }

    private void setComponents() {
        setParticipantsJList();
        setButtons();
        setParticipantsJPanel();
        setButtonsJPanel();
    }

    private void setFrame(){
        setSize( 500, 500);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }

    private void setContainer(){
        Container container = getContentPane();
        container.add(buttonsJPanel);
        container.add(participantsJPanel, BorderLayout.WEST);
        container.add(startMeetingJButton, BorderLayout.SOUTH);
    }

    private void setButtonsJPanel() {
        buttonsJPanel = new JPanel();
        buttonsJPanel.setLayout(new GridLayout(4,1));
        buttonsJPanel.add(addParticipantJButton);
        buttonsJPanel.add(removeParticipantJButton);
        buttonsJPanel.add(setAbsentParticipantJButton);
        buttonsJPanel.add(showAbsentLogJButton);
    }

    private void setParticipantsJPanel(){
        participantsJPanel = new JPanel();
        participantsJPanel.setBorder(BorderFactory.createTitledBorder("Participants:"));
        Dimension participantsJPanelSize = new Dimension();
        participantsJPanelSize.width = 300;
        participantsJPanel.setPreferredSize(participantsJPanelSize);
        participantsJPanel.setLayout(new BorderLayout());
        participantsJPanel.add(scrollPane);
    }

    private void setParticipantsJList(){
        model = new DefaultListModel<>();
        participantsJList = new JList<>(model);
        participantsJList.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
        scrollPane = new JScrollPane();
        scrollPane.setViewportView(participantsJList);
    }

    private void setButtons(){
        startMeetingJButton = new JButton("Start Meeting");
        addParticipantJButton = new JButton("Add Participant");
        removeParticipantJButton = new JButton("Remove Participant");
        setAbsentParticipantJButton = new JButton("Add/Remove Abscence");
        showAbsentLogJButton = new JButton("Show Absents Log");

        addParticipantJButton.setToolTipText("[ENTER]");
        removeParticipantJButton.setToolTipText("[DEL]");
        setAbsentParticipantJButton.setToolTipText("[1]");

        startMeetingJButton.setPreferredSize(new Dimension(Short.MAX_VALUE,120));
        getRootPane().setDefaultButton(addParticipantJButton);
    }

    int[] removeSelectedParticipants(){
        int[] indices = {};
        
        if(!participantsJList.isSelectionEmpty()) {
            indices = participantsJList.getSelectedIndices();

            //reverse cycle to avoid index matching errors
            for(int i = indices.length - 1 ; i >= 0 ; i--)
                model.remove(indices[i]);
        }
        
        //set selected item from participantJList
        if(model.getSize() <= indices[0])
            participantsJList.setSelectedIndex(indices[0] - 1);
        else
            participantsJList.setSelectedIndex(indices[0]);
        return indices;
    }

    private int getSelectedIndex(){
        if(!participantsJList.isSelectionEmpty())
            return participantsJList.getSelectedIndex();
        return -1;
    }

    int[] getSelectedIndices(){
        if(!participantsJList.isSelectionEmpty())
            return participantsJList.getSelectedIndices();
        return null;
    }

    void setAbsentParticipant(int index, String participantInfo){
        model.setElementAt(participantInfo, index);
    }

    void refreshSelection(int[] indices){
        participantsJList.setSelectedIndex(0);
        participantsJList.setSelectedIndices(indices);
    }

    void addParticipant(Participant participant){
        model.addElement(participant.toString());
        int size = model.size() - 1;
        participantsJList.setSelectedIndex(size);
    }

    void setStartMeetingJButtonAction(ActionListener action){
        startMeetingJButton.addActionListener(action);
    }

    void setAddParticipantJButtonAction(ActionListener action){
        addParticipantJButton.addActionListener(action);
    }

    void setRemoveParticipantJButtonAction(ActionListener action){
        removeParticipantJButton.addActionListener(action);
    }

    void setAbsentParticipantJButtonAction(ActionListener action){
        setAbsentParticipantJButton.addActionListener(action);
    }

    void setShowAbsentLogJButtonAction(ActionListener action){
        showAbsentLogJButton.addActionListener(action);
    }

    void refreshJList() {
        int size = model.size();
        addParticipant(new Participant("",""));
        participantsJList.setSelectedIndex(size);
        removeSelectedParticipants();
        participantsJList.setSelectedIndex(0);
    }

    void setDynamicDisablingButtons(BooleanSupplier noPresentParticipants) {
        participantsJList.addListSelectionListener(e -> checkForDisablingButtons(noPresentParticipants));
    }

    void checkForDisablingButtons(BooleanSupplier noPresentParticipantsSupplier){
        boolean noPresentParticipants = noPresentParticipantsSupplier.getAsBoolean();

        if (nothingIsSelected())
            enableButtons(false);
        else {
            enableButtons(true);
            if (noPresentParticipants)
                startMeetingJButton.setEnabled(false);
            else
                startMeetingJButton.setEnabled(true);
        }
    }

    boolean nothingIsSelected(){
        return getSelectedIndex() == -1;
    }

    private void enableButtons(boolean bool) {
        startMeetingJButton.setEnabled(bool);
        removeParticipantJButton.setEnabled(bool);
        setAbsentParticipantJButton.setEnabled(bool);
    }

    void bindDeleteKey(Action action){
        InputMap im = participantsJList.getInputMap(WHEN_FOCUSED);
        ActionMap am = participantsJList.getActionMap();

        im.put(KeyStroke.getKeyStroke(KeyEvent.VK_DELETE, 0), "Delete");
        im.put(KeyStroke.getKeyStroke(KeyEvent.VK_BACK_SPACE, 0), "Delete");
        am.put("Delete", action);
    }

    void bindAKey(Action action){
        InputMap im = participantsJList.getInputMap(WHEN_FOCUSED);
        ActionMap am = participantsJList.getActionMap();

        im.put(KeyStroke.getKeyStroke(KeyEvent.VK_1,0), "Absent");
        am.put("Absent", action);
    }

    void clear() {
        model.removeAllElements();
    }
}
