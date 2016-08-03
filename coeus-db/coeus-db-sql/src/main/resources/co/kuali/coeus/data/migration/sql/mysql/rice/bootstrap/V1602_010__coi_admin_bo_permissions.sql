--
-- Kuali Coeus, a comprehensive research administration system for higher education.
--
-- Copyright 2005-2016 Kuali, Inc.
--
-- This program is free software: you can redistribute it and/or modify
-- it under the terms of the GNU Affero General Public License as
-- published by the Free Software Foundation, either version 3 of the
-- License, or (at your option) any later version.
--
-- This program is distributed in the hope that it will be useful,
-- but WITHOUT ANY WARRANTY; without even the implied warranty of
-- MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
-- GNU Affero General Public License for more details.
--
-- You should have received a copy of the GNU Affero General Public License
-- along with this program.  If not, see <http://www.gnu.org/licenses/>.
--

INSERT INTO KRIM_PERM_ID_S VALUES(NULL);
INSERT INTO KRIM_PERM_T (PERM_ID,PERM_TMPL_ID,NMSPC_CD,NM,DESC_TXT,ACTV_IND,OBJ_ID,VER_NBR)
VALUES (CONCAT('KC', (SELECT (MAX(ID)) FROM KRIM_PERM_ID_S)),(SELECT PERM_TMPL_ID FROM KRIM_PERM_TMPL_T WHERE NMSPC_CD = 'KC-SYS' AND NM = 'Read Class'),'KC-COMMON','Read PropAwardPersonRole','Read PropAwardPersonRole in the KC system','Y',UUID(),1);

INSERT INTO KRIM_ATTR_DATA_ID_S VALUES(NULL);
INSERT INTO KRIM_PERM_ATTR_DATA_T (ATTR_DATA_ID,PERM_ID,KIM_TYP_ID,KIM_ATTR_DEFN_ID,ATTR_VAL,OBJ_ID,VER_NBR)
VALUES (CONCAT('KC', (SELECT (MAX(ID)) FROM KRIM_ATTR_DATA_ID_S)),(SELECT PERM_ID FROM KRIM_PERM_T WHERE NMSPC_CD = 'KC-COMMON' AND NM = 'Read PropAwardPersonRole'),(SELECT KIM_TYP_ID FROM KRIM_TYP_T WHERE NMSPC_CD = 'KC-SYS' AND NM = 'Class Name'),(SELECT KIM_ATTR_DEFN_ID FROM KRIM_ATTR_DEFN_T WHERE NMSPC_CD = 'KC-SYS' AND NM = 'className'),'org.kuali.coeus.common.framework.person.PropAwardPersonRole',UUID(),1);

INSERT INTO KRIM_ROLE_PERM_ID_S VALUES(NULL);
INSERT INTO KRIM_ROLE_PERM_T (ROLE_PERM_ID, OBJ_ID, VER_NBR, ROLE_ID, PERM_ID, ACTV_IND)
VALUES (CONCAT('KC', (SELECT (MAX(ID)) FROM KRIM_ROLE_PERM_ID_S)), UUID(), 1, (SELECT ROLE_ID FROM KRIM_ROLE_T WHERE ROLE_NM = 'COI Administrator' AND NMSPC_CD='KC-COIDISCLOSURE'), (SELECT PERM_ID FROM KRIM_PERM_T WHERE NM = 'Read PropAwardPersonRole' AND NMSPC_CD='KC-COMMON'), 'Y');

INSERT INTO KRIM_PERM_ID_S VALUES(NULL);
INSERT INTO KRIM_PERM_T (PERM_ID,PERM_TMPL_ID,NMSPC_CD,NM,DESC_TXT,ACTV_IND,OBJ_ID,VER_NBR)
VALUES (CONCAT('KC', (SELECT (MAX(ID)) FROM KRIM_PERM_ID_S)),(SELECT PERM_TMPL_ID FROM KRIM_PERM_TMPL_T WHERE NMSPC_CD = 'KC-SYS' AND NM = 'Read Class'),'KC-PD','Read ProposalState','Read ProposalState in the KC system','Y',UUID(),1);

INSERT INTO KRIM_ATTR_DATA_ID_S VALUES(NULL);
INSERT INTO KRIM_PERM_ATTR_DATA_T (ATTR_DATA_ID,PERM_ID,KIM_TYP_ID,KIM_ATTR_DEFN_ID,ATTR_VAL,OBJ_ID,VER_NBR)
VALUES (CONCAT('KC', (SELECT (MAX(ID)) FROM KRIM_ATTR_DATA_ID_S)),(SELECT PERM_ID FROM KRIM_PERM_T WHERE NMSPC_CD = 'KC-PD' AND NM = 'Read ProposalState'),(SELECT KIM_TYP_ID FROM KRIM_TYP_T WHERE NMSPC_CD = 'KC-SYS' AND NM = 'Class Name'),(SELECT KIM_ATTR_DEFN_ID FROM KRIM_ATTR_DEFN_T WHERE NMSPC_CD = 'KC-SYS' AND NM = 'className'),'org.kuali.coeus.propdev.impl.state.ProposalState',UUID(),1);

INSERT INTO KRIM_ROLE_PERM_ID_S VALUES(NULL);
INSERT INTO KRIM_ROLE_PERM_T (ROLE_PERM_ID, OBJ_ID, VER_NBR, ROLE_ID, PERM_ID, ACTV_IND)
VALUES (CONCAT('KC', (SELECT (MAX(ID)) FROM KRIM_ROLE_PERM_ID_S)), UUID(), 1, (SELECT ROLE_ID FROM KRIM_ROLE_T WHERE ROLE_NM = 'COI Administrator' AND NMSPC_CD='KC-COIDISCLOSURE'), (SELECT PERM_ID FROM KRIM_PERM_T WHERE NM = 'Read ProposalState' AND NMSPC_CD='KC-PD'), 'Y');

INSERT INTO KRIM_PERM_ID_S VALUES(NULL);
INSERT INTO KRIM_PERM_T (PERM_ID,PERM_TMPL_ID,NMSPC_CD,NM,DESC_TXT,ACTV_IND,OBJ_ID,VER_NBR)
VALUES (CONCAT('KC', (SELECT (MAX(ID)) FROM KRIM_PERM_ID_S)),(SELECT PERM_TMPL_ID FROM KRIM_PERM_TMPL_T WHERE NMSPC_CD = 'KC-SYS' AND NM = 'Read Class'),'KC-AWARD','Read AwardStatus','Read AwardStatus in the KC system','Y',UUID(),1);

INSERT INTO KRIM_ATTR_DATA_ID_S VALUES(NULL);
INSERT INTO KRIM_PERM_ATTR_DATA_T (ATTR_DATA_ID,PERM_ID,KIM_TYP_ID,KIM_ATTR_DEFN_ID,ATTR_VAL,OBJ_ID,VER_NBR)
VALUES (CONCAT('KC', (SELECT (MAX(ID)) FROM KRIM_ATTR_DATA_ID_S)),(SELECT PERM_ID FROM KRIM_PERM_T WHERE NMSPC_CD = 'KC-AWARD' AND NM = 'Read AwardStatus'),(SELECT KIM_TYP_ID FROM KRIM_TYP_T WHERE NMSPC_CD = 'KC-SYS' AND NM = 'Class Name'),(SELECT KIM_ATTR_DEFN_ID FROM KRIM_ATTR_DEFN_T WHERE NMSPC_CD = 'KC-SYS' AND NM = 'className'),'org.kuali.kra.award.home.AwardStatus',UUID(),1);

INSERT INTO KRIM_ROLE_PERM_ID_S VALUES(NULL);
INSERT INTO KRIM_ROLE_PERM_T (ROLE_PERM_ID, OBJ_ID, VER_NBR, ROLE_ID, PERM_ID, ACTV_IND)
VALUES (CONCAT('KC', (SELECT (MAX(ID)) FROM KRIM_ROLE_PERM_ID_S)), UUID(), 1, (SELECT ROLE_ID FROM KRIM_ROLE_T WHERE ROLE_NM = 'COI Administrator' AND NMSPC_CD='KC-COIDISCLOSURE'), (SELECT PERM_ID FROM KRIM_PERM_T WHERE NM = 'Read AwardStatus' AND NMSPC_CD='KC-AWARD'), 'Y');

INSERT INTO KRIM_PERM_ID_S VALUES(NULL);
INSERT INTO KRIM_PERM_T (PERM_ID,PERM_TMPL_ID,NMSPC_CD,NM,DESC_TXT,ACTV_IND,OBJ_ID,VER_NBR)
VALUES (CONCAT('KC', (SELECT (MAX(ID)) FROM KRIM_PERM_ID_S)),(SELECT PERM_TMPL_ID FROM KRIM_PERM_TMPL_T WHERE NMSPC_CD = 'KC-SYS' AND NM = 'Read Class'),'KC-IP','Read ProposalStatus','Read ProposalStatus in the KC system','Y',UUID(),1);

INSERT INTO KRIM_ATTR_DATA_ID_S VALUES(NULL);
INSERT INTO KRIM_PERM_ATTR_DATA_T (ATTR_DATA_ID,PERM_ID,KIM_TYP_ID,KIM_ATTR_DEFN_ID,ATTR_VAL,OBJ_ID,VER_NBR)
VALUES (CONCAT('KC', (SELECT (MAX(ID)) FROM KRIM_ATTR_DATA_ID_S)),(SELECT PERM_ID FROM KRIM_PERM_T WHERE NMSPC_CD = 'KC-IP' AND NM = 'Read ProposalStatus'),(SELECT KIM_TYP_ID FROM KRIM_TYP_T WHERE NMSPC_CD = 'KC-SYS' AND NM = 'Class Name'),(SELECT KIM_ATTR_DEFN_ID FROM KRIM_ATTR_DEFN_T WHERE NMSPC_CD = 'KC-SYS' AND NM = 'className'),'org.kuali.kra.institutionalproposal.ProposalStatus',UUID(),1);

INSERT INTO KRIM_ROLE_PERM_ID_S VALUES(NULL);
INSERT INTO KRIM_ROLE_PERM_T (ROLE_PERM_ID, OBJ_ID, VER_NBR, ROLE_ID, PERM_ID, ACTV_IND)
VALUES (CONCAT('KC', (SELECT (MAX(ID)) FROM KRIM_ROLE_PERM_ID_S)), UUID(), 1, (SELECT ROLE_ID FROM KRIM_ROLE_T WHERE ROLE_NM = 'COI Administrator' AND NMSPC_CD='KC-COIDISCLOSURE'), (SELECT PERM_ID FROM KRIM_PERM_T WHERE NM = 'Read ProposalStatus' AND NMSPC_CD='KC-IP'), 'Y');

INSERT INTO KRIM_PERM_ID_S VALUES(NULL);
INSERT INTO KRIM_PERM_T (PERM_ID,PERM_TMPL_ID,NMSPC_CD,NM,DESC_TXT,ACTV_IND,OBJ_ID,VER_NBR)
VALUES (CONCAT('KC', (SELECT (MAX(ID)) FROM KRIM_PERM_ID_S)),(SELECT PERM_TMPL_ID FROM KRIM_PERM_TMPL_T WHERE NMSPC_CD = 'KC-SYS' AND NM = 'Read Class'),'KC-PROTOCOL','Read ProtocolPersonRole','Read ProtocolPersonRole in the KC system','Y',UUID(),1);

INSERT INTO KRIM_ATTR_DATA_ID_S VALUES(NULL);
INSERT INTO KRIM_PERM_ATTR_DATA_T (ATTR_DATA_ID,PERM_ID,KIM_TYP_ID,KIM_ATTR_DEFN_ID,ATTR_VAL,OBJ_ID,VER_NBR)
VALUES (CONCAT('KC', (SELECT (MAX(ID)) FROM KRIM_ATTR_DATA_ID_S)),(SELECT PERM_ID FROM KRIM_PERM_T WHERE NMSPC_CD = 'KC-PROTOCOL' AND NM = 'Read ProtocolPersonRole'),(SELECT KIM_TYP_ID FROM KRIM_TYP_T WHERE NMSPC_CD = 'KC-SYS' AND NM = 'Class Name'),(SELECT KIM_ATTR_DEFN_ID FROM KRIM_ATTR_DEFN_T WHERE NMSPC_CD = 'KC-SYS' AND NM = 'className'),'org.kuali.kra.irb.personnel.ProtocolPersonRole',UUID(),1);

INSERT INTO KRIM_ROLE_PERM_ID_S VALUES(NULL);
INSERT INTO KRIM_ROLE_PERM_T (ROLE_PERM_ID, OBJ_ID, VER_NBR, ROLE_ID, PERM_ID, ACTV_IND)
VALUES (CONCAT('KC', (SELECT (MAX(ID)) FROM KRIM_ROLE_PERM_ID_S)), UUID(), 1, (SELECT ROLE_ID FROM KRIM_ROLE_T WHERE ROLE_NM = 'COI Administrator' AND NMSPC_CD='KC-COIDISCLOSURE'), (SELECT PERM_ID FROM KRIM_PERM_T WHERE NM = 'Read ProtocolPersonRole' AND NMSPC_CD='KC-PROTOCOL'), 'Y');

INSERT INTO KRIM_PERM_ID_S VALUES(NULL);
INSERT INTO KRIM_PERM_T (PERM_ID,PERM_TMPL_ID,NMSPC_CD,NM,DESC_TXT,ACTV_IND,OBJ_ID,VER_NBR)
VALUES (CONCAT('KC', (SELECT (MAX(ID)) FROM KRIM_PERM_ID_S)),(SELECT PERM_TMPL_ID FROM KRIM_PERM_TMPL_T WHERE NMSPC_CD = 'KC-SYS' AND NM = 'Read Class'),'KC-PROTOCOL','Read ProtocolStatus','Read ProtocolStatus in the KC system','Y',UUID(),1);

INSERT INTO KRIM_ATTR_DATA_ID_S VALUES(NULL);
INSERT INTO KRIM_PERM_ATTR_DATA_T (ATTR_DATA_ID,PERM_ID,KIM_TYP_ID,KIM_ATTR_DEFN_ID,ATTR_VAL,OBJ_ID,VER_NBR)
VALUES (CONCAT('KC', (SELECT (MAX(ID)) FROM KRIM_ATTR_DATA_ID_S)),(SELECT PERM_ID FROM KRIM_PERM_T WHERE NMSPC_CD = 'KC-PROTOCOL' AND NM = 'Read ProtocolStatus'),(SELECT KIM_TYP_ID FROM KRIM_TYP_T WHERE NMSPC_CD = 'KC-SYS' AND NM = 'Class Name'),(SELECT KIM_ATTR_DEFN_ID FROM KRIM_ATTR_DEFN_T WHERE NMSPC_CD = 'KC-SYS' AND NM = 'className'),'org.kuali.kra.irb.actions.ProtocolStatus',UUID(),1);

INSERT INTO KRIM_ROLE_PERM_ID_S VALUES(NULL);
INSERT INTO KRIM_ROLE_PERM_T (ROLE_PERM_ID, OBJ_ID, VER_NBR, ROLE_ID, PERM_ID, ACTV_IND)
VALUES (CONCAT('KC', (SELECT (MAX(ID)) FROM KRIM_ROLE_PERM_ID_S)), UUID(), 1, (SELECT ROLE_ID FROM KRIM_ROLE_T WHERE ROLE_NM = 'COI Administrator' AND NMSPC_CD='KC-COIDISCLOSURE'), (SELECT PERM_ID FROM KRIM_PERM_T WHERE NM = 'Read ProtocolStatus' AND NMSPC_CD='KC-PROTOCOL'), 'Y');

INSERT INTO KRIM_PERM_ID_S VALUES(NULL);
INSERT INTO KRIM_PERM_T (PERM_ID,PERM_TMPL_ID,NMSPC_CD,NM,DESC_TXT,ACTV_IND,OBJ_ID,VER_NBR)
VALUES (CONCAT('KC', (SELECT (MAX(ID)) FROM KRIM_PERM_ID_S)),(SELECT PERM_TMPL_ID FROM KRIM_PERM_TMPL_T WHERE NMSPC_CD = 'KC-SYS' AND NM = 'Read Class'),'KC-IACUC','Read IacucProtocolPersonRole','Read IacucProtocolPersonRole in the KC system','Y',UUID(),1);

INSERT INTO KRIM_ATTR_DATA_ID_S VALUES(NULL);
INSERT INTO KRIM_PERM_ATTR_DATA_T (ATTR_DATA_ID,PERM_ID,KIM_TYP_ID,KIM_ATTR_DEFN_ID,ATTR_VAL,OBJ_ID,VER_NBR)
VALUES (CONCAT('KC', (SELECT (MAX(ID)) FROM KRIM_ATTR_DATA_ID_S)),(SELECT PERM_ID FROM KRIM_PERM_T WHERE NMSPC_CD = 'KC-IACUC' AND NM = 'Read IacucProtocolPersonRole'),(SELECT KIM_TYP_ID FROM KRIM_TYP_T WHERE NMSPC_CD = 'KC-SYS' AND NM = 'Class Name'),(SELECT KIM_ATTR_DEFN_ID FROM KRIM_ATTR_DEFN_T WHERE NMSPC_CD = 'KC-SYS' AND NM = 'className'),'org.kuali.kra.iacuc.personnel.IacucProtocolPersonRole',UUID(),1);

INSERT INTO KRIM_ROLE_PERM_ID_S VALUES(NULL);
INSERT INTO KRIM_ROLE_PERM_T (ROLE_PERM_ID, OBJ_ID, VER_NBR, ROLE_ID, PERM_ID, ACTV_IND)
VALUES (CONCAT('KC', (SELECT (MAX(ID)) FROM KRIM_ROLE_PERM_ID_S)), UUID(), 1, (SELECT ROLE_ID FROM KRIM_ROLE_T WHERE ROLE_NM = 'COI Administrator' AND NMSPC_CD='KC-COIDISCLOSURE'), (SELECT PERM_ID FROM KRIM_PERM_T WHERE NM = 'Read IacucProtocolPersonRole' AND NMSPC_CD='KC-IACUC'), 'Y');

INSERT INTO KRIM_PERM_ID_S VALUES(NULL);
INSERT INTO KRIM_PERM_T (PERM_ID,PERM_TMPL_ID,NMSPC_CD,NM,DESC_TXT,ACTV_IND,OBJ_ID,VER_NBR)
VALUES (CONCAT('KC', (SELECT (MAX(ID)) FROM KRIM_PERM_ID_S)),(SELECT PERM_TMPL_ID FROM KRIM_PERM_TMPL_T WHERE NMSPC_CD = 'KC-SYS' AND NM = 'Read Class'),'KC-IACUC','Read IacucProtocolStatus','Read IacucProtocolStatus in the KC system','Y',UUID(),1);

INSERT INTO KRIM_ATTR_DATA_ID_S VALUES(NULL);
INSERT INTO KRIM_PERM_ATTR_DATA_T (ATTR_DATA_ID,PERM_ID,KIM_TYP_ID,KIM_ATTR_DEFN_ID,ATTR_VAL,OBJ_ID,VER_NBR)
VALUES (CONCAT('KC', (SELECT (MAX(ID)) FROM KRIM_ATTR_DATA_ID_S)),(SELECT PERM_ID FROM KRIM_PERM_T WHERE NMSPC_CD = 'KC-IACUC' AND NM = 'Read IacucProtocolStatus'),(SELECT KIM_TYP_ID FROM KRIM_TYP_T WHERE NMSPC_CD = 'KC-SYS' AND NM = 'Class Name'),(SELECT KIM_ATTR_DEFN_ID FROM KRIM_ATTR_DEFN_T WHERE NMSPC_CD = 'KC-SYS' AND NM = 'className'),'org.kuali.kra.iacuc.actions.IacucProtocolStatus',UUID(),1);

INSERT INTO KRIM_ROLE_PERM_ID_S VALUES(NULL);
INSERT INTO KRIM_ROLE_PERM_T (ROLE_PERM_ID, OBJ_ID, VER_NBR, ROLE_ID, PERM_ID, ACTV_IND)
VALUES (CONCAT('KC', (SELECT (MAX(ID)) FROM KRIM_ROLE_PERM_ID_S)), UUID(), 1, (SELECT ROLE_ID FROM KRIM_ROLE_T WHERE ROLE_NM = 'COI Administrator' AND NMSPC_CD='KC-COIDISCLOSURE'), (SELECT PERM_ID FROM KRIM_PERM_T WHERE NM = 'Read IacucProtocolStatus' AND NMSPC_CD='KC-IACUC'), 'Y');
