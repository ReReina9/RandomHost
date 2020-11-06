package com.randomhost.logic;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

public class Meeting {

    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
    private static final Random RANDOM = new Random();

    private List<Participant> participants;
    private LocalDateTime date;


    public Meeting(){
        participants = new LinkedList<>();
    }

    public List<Participant> getParticipants(){
        return participants;
    }

    public Participant getParticipant(int index){
        return participants.get(index);
    }

    public void addParticipant(Participant participant){
        participants.add(participant);
    }

    public void addParticipants(List<Participant> participants){
        this.participants.addAll(participants);
    }

    public boolean removeParticipant(Participant participant){
        if(!participants.contains(participant))
            return false;
        participants.remove(participant);
        return true;
    }

    private void removeParticipant(int index){
        if(participants.size() > index)
            participants.remove(index);
    }

    public void removeParticipants(int[] indices){
        for(int i = indices.length - 1 ; i >= 0 ; i--)
            removeParticipant(indices[i]);
    }

    public Participant getHost(){
        List<Participant> presentParticipants = getPresentParticipants();
        if(!presentParticipants.isEmpty())
            return presentParticipants.get(RANDOM.nextInt(presentParticipants.size()));
        return null;
    }

    public void setParticipant(int index, Participant participant){
        participants.set(index, participant);
    }

    public void setDate() {
        date = LocalDateTime.now();
    }

    public String getDate(){
        return DATE_TIME_FORMATTER.format(date);
    }

    public List<Participant> getAbsents(){
        List<Participant> absents = new LinkedList<>();
        for(Participant participant: participants){
            if(participant.isAbsent())
                absents.add(participant);
        }
        return absents;
    }

    public boolean noPresentParticipants(){
        return getAbsents().size() == size();
    }

    public int size(){
        return participants.size();
    }

    public void clear(){
        participants.clear();
    }

    private List<Participant> getPresentParticipants(){
        List<Participant> presentParticipants = new LinkedList<>();
        for(Participant participant : participants)
            if(!participant.isAbsent())
                presentParticipants.add(participant);
            return presentParticipants;
    }

    public String toString(){
        StringBuilder s = new StringBuilder();
        for(Participant participant: participants) {
            s.append("n:").append(participant.getName()).append("\n");
            s.append("s:").append(participant.getLastName()).append("\n");
        }
        return s.toString();
    }
}
