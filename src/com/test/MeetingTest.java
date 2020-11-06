package com.test;

import com.randomhost.logic.Participant;
import com.randomhost.logic.Meeting;
import org.junit.Test;

import java.util.LinkedList;
import java.util.List;

import static org.junit.Assert.*;

public class MeetingTest {
    @Test
    public void newMeeting(){
        Meeting meeting = new Meeting();
        assertNotNull(meeting);
    }

    @Test
    public void addParticipant(){
        Meeting meeting = new Meeting();
        meeting.addParticipant(new Participant("Mario", "Rossi"));
        assertEquals(1, meeting.size());
    }

    @Test
    public void addParticipants(){
        Meeting meeting = new Meeting();
        List<Participant> participants = new LinkedList<>();
        participants.add(new Participant("Mario", "Rossi"));
        participants.add(new Participant("Giorgio", "Verdi"));
        participants.add(new Participant("Luca", "Bianchi"));
        meeting.addParticipants(participants);
        assertEquals(3, meeting.size());
    }

    @Test
    public void removeParticipant(){
        Meeting meeting = new Meeting();
        Participant participant = new Participant("Mario", "Rossi");
        meeting.addParticipant(participant);
        meeting.removeParticipant(participant);
        assertEquals(0, meeting.size());
    }

    @Test
    public void removeParticipantNotContained(){
        Meeting meeting = new Meeting();
        Participant participant = new Participant("Mario", "Rossi");
        meeting.addParticipant(participant);
        meeting.removeParticipant(new Participant("Maria", "Rossi"));
        assertEquals(1, meeting.size());
    }

    @Test
    public void removeParticipants(){
        Meeting meeting = new Meeting();
        List<Participant> participants = new LinkedList<>();
        participants.add(new Participant("Mario", "Rossi"));
        participants.add(new Participant("Giorgio", "Verdi"));
        participants.add(new Participant("Luca", "Bianchi"));
        meeting.addParticipants(participants);
        int [] indices = {0 , 2};
        meeting.removeParticipants(indices);
        assertEquals(1, meeting.size());
    }

    @Test
    public void removeParticipantsNotContained(){
        Meeting meeting = new Meeting();
        List<Participant> participants = new LinkedList<>();
        participants.add(new Participant("Mario", "Rossi"));
        participants.add(new Participant("Giorgio", "Verdi"));
        participants.add(new Participant("Luca", "Bianchi"));
        meeting.addParticipants(participants);
        int [] indices = {3 , 60};
        meeting.removeParticipants(indices);
        assertEquals(3, meeting.size()); //It should remove only the participant in position 2
    }

    @Test
    public void getHost(){
        Meeting meeting = new Meeting();
        List<Participant> participants = new LinkedList<>();
        participants.add(new Participant("Mario", "Rossi"));
        participants.add(new Participant("Giorgio", "Verdi"));
        participants.add(new Participant("Luca", "Bianchi"));
        meeting.addParticipants(participants);
        Participant host = meeting.getHost();
        assertFalse(host.isAbsent());
    }

    @Test
    public void getHostAllAbsents(){
        Meeting meeting = new Meeting();
        Participant participant = new Participant("Mario", "Rossi");
        participant.setAbsent();
        meeting.addParticipant(participant);
        Participant host = meeting.getHost();
        assertNull(host);
    }

    @Test
    public void setParticipant(){
        Meeting meeting = new Meeting();
        Participant participant = new Participant("Mario", "Rossi");
        meeting.addParticipant(participant);
        meeting.setParticipant(0, new Participant("Giorgio", "Verdi"));
        assertEquals("Giorgio Verdi", meeting.getParticipant(0).toString());
    }

    @Test
    public void getAbsents(){
        Meeting meeting = new Meeting();
        List<Participant> participants = new LinkedList<>();
        participants.add(new Participant("Mario", "Rossi"));
        participants.add(new Participant("Giorgio", "Verdi"));
        participants.add(new Participant("Luca", "Bianchi"));
        Participant participantGV = participants.get(1);
        participantGV.setAbsent();
        participants.set(1, participantGV);
        meeting.addParticipants(participants);
        List<Participant> absents = meeting.getAbsents();
        assertEquals("Giorgio Verdi", absents.get(0).toString());
    }

    @Test
    public void getAbsentsEmpty(){
        Meeting meeting = new Meeting();
        List<Participant> participants = new LinkedList<>();
        participants.add(new Participant("Mario", "Rossi"));
        participants.add(new Participant("Giorgio", "Verdi"));
        participants.add(new Participant("Luca", "Bianchi"));
        meeting.addParticipants(participants);
        List<Participant> absents = meeting.getAbsents();
        assertTrue(absents.isEmpty());
    }

    @Test
    public void noPresentParticipants(){
        Meeting meeting = new Meeting();
        Participant participant = new Participant("Mario", "Rossi");
        participant.setAbsent();
        meeting.addParticipant(participant);
        assertTrue(meeting.noPresentParticipants());
    }

    @Test
    public void clear(){
        Meeting meeting = new Meeting();
        List<Participant> participants = new LinkedList<>();
        participants.add(new Participant("Mario", "Rossi"));
        participants.add(new Participant("Giorgio", "Verdi"));
        participants.add(new Participant("Luca", "Bianchi"));
        meeting.addParticipants(participants);
        meeting.clear();
        assertEquals(0, meeting.size());
    }

    @Test
    public void meetingToString(){
        Meeting meeting = new Meeting();
        List<Participant> participants = new LinkedList<>();
        participants.add(new Participant("Mario", "Rossi"));
        participants.add(new Participant("Giorgio", "Verdi"));
        participants.add(new Participant("Luca", "Bianchi"));
        meeting.addParticipants(participants);
        String string = "n:Mario\n" +
                "s:Rossi\n" +
                "n:Giorgio\n" +
                "s:Verdi\n" +
                "n:Luca\n" +
                "s:Bianchi\n";
        assertEquals(string, meeting.toString());
    }

}
