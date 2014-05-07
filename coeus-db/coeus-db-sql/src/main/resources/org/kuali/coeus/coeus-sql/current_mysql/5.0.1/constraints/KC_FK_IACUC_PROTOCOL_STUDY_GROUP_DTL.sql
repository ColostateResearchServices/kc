DELIMITER /
ALTER TABLE IACUC_PROTOCOL_STUDY_GROUP_DTL 
ADD CONSTRAINT FK_STUD_GRP_HDR_PAIN_CAT_CODE 
FOREIGN KEY (MAX_PAIN_CATEGORY_CODE) 
REFERENCES IACUC_PAIN_CATEGORY (PAIN_CATEGORY_CODE)
/

ALTER TABLE IACUC_PROTOCOL_STUDY_GROUP_DTL 
ADD CONSTRAINT FK_IACUC_PROT_STUD_GRP_HDR_ID 
FOREIGN KEY (IACUC_PROTO_STUDY_GRP_HDR_ID) 
REFERENCES IACUC_PROTOCOL_STUDY_GROUP_HDR (IACUC_PROTO_STUDY_GRP_HDR_ID)
/

ALTER TABLE IACUC_PROTOCOL_STUDY_GROUP_DTL 
ADD CONSTRAINT FK_PROT_STUD_GRP_HDR_SPEC_CODE 
FOREIGN KEY (SPECIES_CODE) 
REFERENCES IACUC_SPECIES (SPECIES_CODE)
/

DELIMITER ;