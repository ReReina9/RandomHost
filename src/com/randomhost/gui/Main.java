package com.randomhost.gui;

import com.randomhost.logic.Meeting;
import com.randomhost.logic.Participant;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.*;
import java.util.List;
import java.util.function.BooleanSupplier;

public class Main {

    private static final String PARTICIPANTS_LOG_PATH = "participants.txt";
    private static final String ABSENTS_LOG_PATH = "absents.txt";

    private MainFrame mainFrame;
    private AddParticipantFrame addParticipantFrame;
    private AbsentsLogFrame absentsLogFrame;
    private StartMeetingFrame startMeetingFrame;

    private Meeting meeting;

    public static void main(String[] args) {
        new Main();
    }

    private Main() {
        meeting = new Meeting();

        setFrames();
        restoreParticipantsFromPreviousMeetings();
        setActions();
        setDynamicActions();
    }

    private void setDynamicActions() {
       BooleanSupplier noPresentParticipants = () -> meeting.noPresentParticipants();
        mainFrame.checkForDisablingButtons(noPresentParticipants);
        mainFrame.setDynamicDisablingButtons(noPresentParticipants);

        startMeetingFrame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent windowEvent) {
                saveMeetingInfo();
            }
        });
    }

    private void setFrames() {
        mainFrame = new MainFrame();

        addParticipantFrame = new AddParticipantFrame();

        startMeetingFrame = new StartMeetingFrame();

        String absentsHistory = getAbsentsFromPreviousMeetings();
        absentsLogFrame = new AbsentsLogFrame(absentsHistory);
    }

    private void setActions() {
        startMeetingFrame.setokJButtonAction(event -> saveMeetingInfo());

        addParticipantFrame.setParticipantJButtonAction(event -> addParticipantFromInput());

        mainFrame.setAddParticipantJButtonAction(event -> openAddParticipantJFrame());
        mainFrame.setRemoveParticipantJButtonAction(event -> removeParticipants());
        mainFrame.setAbsentParticipantJButtonAction(event -> setAbsentParticipants());
        mainFrame.setShowAbsentLogJButtonAction(event -> openAbsentsLogJFrame());
        mainFrame.setStartMeetingJButtonAction(event -> openStartMeetingJFrame());

        mainFrame.bindDeleteKey(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                removeParticipants();
            }
        });

        mainFrame.bindAKey(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setAbsentParticipants();
            }
        });
    }

    private void restoreParticipantsFromPreviousMeetings() {
        BufferedReader br = null;
        try {
            File participantsLocation = new File(PARTICIPANTS_LOG_PATH);
            br = new BufferedReader(new FileReader(participantsLocation));
            String participantInfo;
            String name = "";
            String lastName = "";
            while ((participantInfo = br.readLine()) != null) {
                if (participantInfo.charAt(0) == 'n') {
                    name = participantInfo.substring(2);
                }
                if (participantInfo.charAt(0) == 's') {
                    lastName = participantInfo.substring(2);
                    Participant participant = new Participant(name, lastName);
                    addParticipant(participant);
                }
            }
        } catch (Exception e) {
            System.out.println("Exception in reading file \"absents.txt\": " + e);
        }
        finally{
            try{
                if(br != null)
                    br.close();
            }catch(Exception e){
                System.out.println("Error in closing the BufferedReader " + e);
            }
        }
    }

    private String getAbsentsFromPreviousMeetings() {
        StringBuilder absentsLog = new StringBuilder();
        BufferedReader br = null;
        try {
            File participantsLocation = new File(ABSENTS_LOG_PATH);
            br = new BufferedReader(new FileReader(participantsLocation));
            String line;
            while ((line = br.readLine()) != null) {
                absentsLog.append(line).append("\n");
            }
        } catch (Exception e) {
            System.out.println("Exception in reading file \"absents.txt\": " + e);
        }
        finally{
            try{
                if(br != null)
                    br.close();
            }catch(Exception e){
                System.out.println("Error in closing the BufferedReader " + e);
            }
        }
        return absentsLog.toString();
    }

    private void setAbsentParticipants(){
        if(!mainFrame.nothingIsSelected()) {
            int[] indices = mainFrame.getSelectedIndices();
            for (int i = indices.length - 1; i >= 0; i--)
                setAbsentParticipant(indices[i]);
            mainFrame.refreshSelection(indices);
        }
    }

    private void setAbsentParticipant(int index){
        if(index != -1) {
            Participant participant = meeting.getParticipant(index);
            boolean absent = participant.isAbsent();
            if(absent) {
                mainFrame.setAbsentParticipant(index, participant.toString());
                participant.setPresent();
            }
            else{
                mainFrame.setAbsentParticipant(index, participant.toString()+ " [ABSENT]");
                participant.setAbsent();
            }
            meeting.setParticipant(index, participant);
        }
    }

    private void addParticipantFromInput(){
        Participant participant = addParticipantFrame.getParticipantInfo();
        addParticipant(participant);
    }

    private void addParticipant(Participant participant){
        mainFrame.addParticipant(participant);
        meeting.addParticipant(participant);
    }

    private void removeParticipants(){
        if(!mainFrame.nothingIsSelected()) {
            int[] indices = mainFrame.removeSelectedParticipants();
            meeting.removeParticipants(indices);
        }
    }

    private void openAddParticipantJFrame(){
        addParticipantFrame.setVisible(true);
    }

    private void openAbsentsLogJFrame(){
        absentsLogFrame.setVisible(true);
    }

    private void openStartMeetingJFrame(){
        Participant host = meeting.getHost();
        startMeetingFrame.setHost(host.toString());
        meeting.setDate();
    }

    private void saveMeetingInfo(){
        saveParticipantsLogToFile();
        String absentsLog = getFormattedAbsentsLog();
        saveAbsentsLogToFile(absentsLog);

        clearMeeting();
        String absentsHistory = getAbsentsFromPreviousMeetings();
        absentsLogFrame = new AbsentsLogFrame(absentsHistory);
        restoreParticipantsFromPreviousMeetings();
    }

    private void clearMeeting() {
        meeting.clear();
        mainFrame.clear();
    }

    private void saveParticipantsLogToFile() {
        BufferedWriter buffer = null;
        FileWriter writer = null;
        try {
            writer = new FileWriter(PARTICIPANTS_LOG_PATH);
            buffer = new BufferedWriter(writer);
            buffer.write(meeting.toString());
        }catch(Exception e){
            System.out.println("Exception in writing file: " + e);
        }
        finally {
            try{
                if(buffer != null)
                    buffer.close();
                if(writer != null)
                    writer.close();
            }catch(Exception e){
                System.out.println("Error in closing the BufferedWriter " + e);
            }
        }
    }

    private String getFormattedAbsentsLog() {
        List<Participant> absents = meeting.getAbsents();
        StringBuilder absentInfo = new StringBuilder(absentsLogFrame.getAbsentsLog());
        for(Participant absent : absents){
            absentInfo.append(absent.toString())
                    .append("  -  ")
                    .append(meeting.getDate())
                    .append("\n");
        }
        return absentInfo.toString();
    }

    private void saveAbsentsLogToFile(String absentsLog) {
        BufferedWriter buffer = null;
        FileWriter writer = null;
        try {
            writer = new FileWriter(ABSENTS_LOG_PATH);
            buffer = new BufferedWriter(writer);
            buffer.write(absentsLog);
        }catch(Exception e){
            System.out.println("Exception in writing file: " + e);
        }
        finally {
            try{
                if(buffer != null)
                    buffer.close();
                if(writer != null)
                    writer.close();
            }catch(Exception e){
                System.out.println("Error in closing the BufferedWriter " + e);
            }
        }
    }
}
