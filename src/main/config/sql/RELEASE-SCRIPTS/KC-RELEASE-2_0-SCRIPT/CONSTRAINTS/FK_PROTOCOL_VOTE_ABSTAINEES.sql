-- Foreign Key Constraint(s) 
ALTER TABLE PROTOCOL_VOTE_ABSTAINEES 
    ADD CONSTRAINT FK_PROTOCOL_VOTE_ABSTAINEE FOREIGN KEY (PROTOCOL_ID_FK) 
                REFERENCES PROTOCOL (PROTOCOL_ID) ;

ALTER TABLE PROTOCOL_VOTE_ABSTAINEES 
    ADD CONSTRAINT FK_PROTOCOL_VOTE_ABSTAINEE_2 FOREIGN KEY (SCHEDULE_ID_FK) 
                REFERENCES COMM_SCHEDULE (ID) ;