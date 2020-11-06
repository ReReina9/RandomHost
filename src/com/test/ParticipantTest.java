package com.test;

import com.randomhost.logic.Participant;
import org.junit.Test;
import static org.junit.Assert.*;

public class ParticipantTest {
    @Test
    public void newParticipant(){
        Participant participant = new Participant("", "");
        assertNotNull(participant);
    }

    @Test
    public void ParticipantName(){
        Participant participant = new Participant("Mario", "Rossi");
        assertEquals("Mario", participant.getName());
    }

    @Test
    public void ParticipantLastName(){
        Participant participant = new Participant("Mario", "Rossi");
        assertEquals("Rossi", participant.getLastName());
    }

    @Test
    public void ParticipantToString(){
        Participant participant = new Participant("Mario", "Rossi");
        assertEquals("Mario Rossi", participant.toString());
    }

    @Test
    public void newParticipantAbsence(){
        Participant participant = new Participant("Mario", "Rossi");
        assertFalse(participant.isAbsent());
    }

    @Test
    public void ParticipantSetPresent(){
        Participant participant = new Participant("Mario", "Rossi");
        participant.setAbsent();
        assertTrue(participant.isAbsent());
    }
    
}
