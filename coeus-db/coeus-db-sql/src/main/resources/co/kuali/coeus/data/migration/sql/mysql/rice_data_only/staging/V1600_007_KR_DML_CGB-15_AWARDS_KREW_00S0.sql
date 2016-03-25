DELIMITER /

INSERT INTO KREW_DOC_HDR_T (DOC_HDR_ID, DOC_TYP_ID, DOC_HDR_STAT_CD, TTL, 
						INITR_PRNCPL_ID, RTE_PRNCPL_ID, DOC_VER_NBR, RTE_LVL, 
						CRTE_DT, RTE_STAT_MDFN_DT, APRV_DT, FNL_DT, STAT_MDFN_DT, 
						OBJ_ID, VER_NBR) 
VALUES ('KCCGB0037', (SELECT MAX(DOC_TYP_ID) FROM KREW_DOC_TYP_T WHERE DOC_TYP_NM = 'AwardDocument'), 'F', 'PD FOR KFSPRJQ-1198',
		(SELECT PRNCPL_ID FROM KRIM_PRNCPL_T WHERE PRNCPL_NM = 'quickstart'), (SELECT PRNCPL_ID FROM KRIM_PRNCPL_T WHERE PRNCPL_NM = 'quickstart'), 1, 4,
		NOW(), NOW(), NOW(), NOW(), NOW(),
		UUID(), 1)
/

INSERT INTO KREW_DOC_HDR_CNTNT_T (DOC_HDR_ID,DOC_CNTNT_TXT)
    VALUES ('KCCGB0037','')
/
INSERT INTO KREW_DOC_HDR_T (DOC_HDR_ID, DOC_TYP_ID, DOC_HDR_STAT_CD, TTL, 
						INITR_PRNCPL_ID, RTE_PRNCPL_ID, DOC_VER_NBR, RTE_LVL, 
						CRTE_DT, RTE_STAT_MDFN_DT, APRV_DT, FNL_DT, STAT_MDFN_DT, 
						OBJ_ID, VER_NBR) 
VALUES ('KCCGB0038', (SELECT MAX(DOC_TYP_ID) FROM KREW_DOC_TYP_T WHERE DOC_TYP_NM = 'AwardDocument'), 'F', 'QUARTERLY by award',
		(SELECT PRNCPL_ID FROM KRIM_PRNCPL_T WHERE PRNCPL_NM = 'quickstart'), (SELECT PRNCPL_ID FROM KRIM_PRNCPL_T WHERE PRNCPL_NM = 'quickstart'), 1, 4,
		NOW(), NOW(), NOW(), NOW(), NOW(),
		UUID(), 1)
/

INSERT INTO KREW_DOC_HDR_CNTNT_T (DOC_HDR_ID,DOC_CNTNT_TXT)
    VALUES ('KCCGB0038','')
/
INSERT INTO KREW_DOC_HDR_T (DOC_HDR_ID, DOC_TYP_ID, DOC_HDR_STAT_CD, TTL, 
						INITR_PRNCPL_ID, RTE_PRNCPL_ID, DOC_VER_NBR, RTE_LVL, 
						CRTE_DT, RTE_STAT_MDFN_DT, APRV_DT, FNL_DT, STAT_MDFN_DT, 
						OBJ_ID, VER_NBR) 
VALUES ('KCCGB0039', (SELECT MAX(DOC_TYP_ID) FROM KREW_DOC_TYP_T WHERE DOC_TYP_NM = 'AwardDocument'), 'F', 'QUARTERLY by award',
		(SELECT PRNCPL_ID FROM KRIM_PRNCPL_T WHERE PRNCPL_NM = 'quickstart'), (SELECT PRNCPL_ID FROM KRIM_PRNCPL_T WHERE PRNCPL_NM = 'quickstart'), 1, 4,
		NOW(), NOW(), NOW(), NOW(), NOW(),
		UUID(), 1)
/

INSERT INTO KREW_DOC_HDR_CNTNT_T (DOC_HDR_ID,DOC_CNTNT_TXT)
    VALUES ('KCCGB0039','')
/
INSERT INTO KREW_DOC_HDR_T (DOC_HDR_ID, DOC_TYP_ID, DOC_HDR_STAT_CD, TTL, 
						INITR_PRNCPL_ID, RTE_PRNCPL_ID, DOC_VER_NBR, RTE_LVL, 
						CRTE_DT, RTE_STAT_MDFN_DT, APRV_DT, FNL_DT, STAT_MDFN_DT, 
						OBJ_ID, VER_NBR) 
VALUES ('KCCGB0040', (SELECT MAX(DOC_TYP_ID) FROM KREW_DOC_TYP_T WHERE DOC_TYP_NM = 'AwardDocument'), 'F', 'QUARTERLY by award',
		(SELECT PRNCPL_ID FROM KRIM_PRNCPL_T WHERE PRNCPL_NM = 'quickstart'), (SELECT PRNCPL_ID FROM KRIM_PRNCPL_T WHERE PRNCPL_NM = 'quickstart'), 1, 4,
		NOW(), NOW(), NOW(), NOW(), NOW(),
		UUID(), 1)
/

INSERT INTO KREW_DOC_HDR_CNTNT_T (DOC_HDR_ID,DOC_CNTNT_TXT)
    VALUES ('KCCGB0040','')
/
INSERT INTO KREW_DOC_HDR_T (DOC_HDR_ID, DOC_TYP_ID, DOC_HDR_STAT_CD, TTL, 
						INITR_PRNCPL_ID, RTE_PRNCPL_ID, DOC_VER_NBR, RTE_LVL, 
						CRTE_DT, RTE_STAT_MDFN_DT, APRV_DT, FNL_DT, STAT_MDFN_DT, 
						OBJ_ID, VER_NBR) 
VALUES ('KCCGB0041', (SELECT MAX(DOC_TYP_ID) FROM KREW_DOC_TYP_T WHERE DOC_TYP_NM = 'AwardDocument'), 'F', 'SEMI-ANNUALLY by acct',
		(SELECT PRNCPL_ID FROM KRIM_PRNCPL_T WHERE PRNCPL_NM = 'quickstart'), (SELECT PRNCPL_ID FROM KRIM_PRNCPL_T WHERE PRNCPL_NM = 'quickstart'), 1, 4,
		NOW(), NOW(), NOW(), NOW(), NOW(),
		UUID(), 1)
/

INSERT INTO KREW_DOC_HDR_CNTNT_T (DOC_HDR_ID,DOC_CNTNT_TXT)
    VALUES ('KCCGB0041','')
/
INSERT INTO KREW_DOC_HDR_T (DOC_HDR_ID, DOC_TYP_ID, DOC_HDR_STAT_CD, TTL, 
						INITR_PRNCPL_ID, RTE_PRNCPL_ID, DOC_VER_NBR, RTE_LVL, 
						CRTE_DT, RTE_STAT_MDFN_DT, APRV_DT, FNL_DT, STAT_MDFN_DT, 
						OBJ_ID, VER_NBR) 
VALUES ('KCCGB0042', (SELECT MAX(DOC_TYP_ID) FROM KREW_DOC_TYP_T WHERE DOC_TYP_NM = 'AwardDocument'), 'F', 'SEMI-ANNUALLY by acct',
		(SELECT PRNCPL_ID FROM KRIM_PRNCPL_T WHERE PRNCPL_NM = 'quickstart'), (SELECT PRNCPL_ID FROM KRIM_PRNCPL_T WHERE PRNCPL_NM = 'quickstart'), 1, 4,
		NOW(), NOW(), NOW(), NOW(), NOW(),
		UUID(), 1)
/

INSERT INTO KREW_DOC_HDR_CNTNT_T (DOC_HDR_ID,DOC_CNTNT_TXT)
    VALUES ('KCCGB0042','')
/
INSERT INTO KREW_DOC_HDR_T (DOC_HDR_ID, DOC_TYP_ID, DOC_HDR_STAT_CD, TTL, 
						INITR_PRNCPL_ID, RTE_PRNCPL_ID, DOC_VER_NBR, RTE_LVL, 
						CRTE_DT, RTE_STAT_MDFN_DT, APRV_DT, FNL_DT, STAT_MDFN_DT, 
						OBJ_ID, VER_NBR) 
VALUES ('KCCGB0043', (SELECT MAX(DOC_TYP_ID) FROM KREW_DOC_TYP_T WHERE DOC_TYP_NM = 'AwardDocument'), 'F', 'ANNUALLY by cc acct',
		(SELECT PRNCPL_ID FROM KRIM_PRNCPL_T WHERE PRNCPL_NM = 'quickstart'), (SELECT PRNCPL_ID FROM KRIM_PRNCPL_T WHERE PRNCPL_NM = 'quickstart'), 1, 4,
		NOW(), NOW(), NOW(), NOW(), NOW(),
		UUID(), 1)
/

INSERT INTO KREW_DOC_HDR_CNTNT_T (DOC_HDR_ID,DOC_CNTNT_TXT)
    VALUES ('KCCGB0043','')
/
INSERT INTO KREW_DOC_HDR_T (DOC_HDR_ID, DOC_TYP_ID, DOC_HDR_STAT_CD, TTL, 
						INITR_PRNCPL_ID, RTE_PRNCPL_ID, DOC_VER_NBR, RTE_LVL, 
						CRTE_DT, RTE_STAT_MDFN_DT, APRV_DT, FNL_DT, STAT_MDFN_DT, 
						OBJ_ID, VER_NBR) 
VALUES ('KCCGB0044', (SELECT MAX(DOC_TYP_ID) FROM KREW_DOC_TYP_T WHERE DOC_TYP_NM = 'AwardDocument'), 'F', 'ANNUALLY by cc acct',
		(SELECT PRNCPL_ID FROM KRIM_PRNCPL_T WHERE PRNCPL_NM = 'quickstart'), (SELECT PRNCPL_ID FROM KRIM_PRNCPL_T WHERE PRNCPL_NM = 'quickstart'), 1, 4,
		NOW(), NOW(), NOW(), NOW(), NOW(),
		UUID(), 1)
/

INSERT INTO KREW_DOC_HDR_CNTNT_T (DOC_HDR_ID,DOC_CNTNT_TXT)
    VALUES ('KCCGB0044','')
/
INSERT INTO KREW_DOC_HDR_T (DOC_HDR_ID, DOC_TYP_ID, DOC_HDR_STAT_CD, TTL, 
						INITR_PRNCPL_ID, RTE_PRNCPL_ID, DOC_VER_NBR, RTE_LVL, 
						CRTE_DT, RTE_STAT_MDFN_DT, APRV_DT, FNL_DT, STAT_MDFN_DT, 
						OBJ_ID, VER_NBR) 
VALUES ('KCCGB0045', (SELECT MAX(DOC_TYP_ID) FROM KREW_DOC_TYP_T WHERE DOC_TYP_NM = 'AwardDocument'), 'F', 'ANNUALLY by cc acct',
		(SELECT PRNCPL_ID FROM KRIM_PRNCPL_T WHERE PRNCPL_NM = 'quickstart'), (SELECT PRNCPL_ID FROM KRIM_PRNCPL_T WHERE PRNCPL_NM = 'quickstart'), 1, 4,
		NOW(), NOW(), NOW(), NOW(), NOW(),
		UUID(), 1)
/

INSERT INTO KREW_DOC_HDR_CNTNT_T (DOC_HDR_ID,DOC_CNTNT_TXT)
    VALUES ('KCCGB0045','')
/
INSERT INTO KREW_DOC_HDR_T (DOC_HDR_ID, DOC_TYP_ID, DOC_HDR_STAT_CD, TTL, 
						INITR_PRNCPL_ID, RTE_PRNCPL_ID, DOC_VER_NBR, RTE_LVL, 
						CRTE_DT, RTE_STAT_MDFN_DT, APRV_DT, FNL_DT, STAT_MDFN_DT, 
						OBJ_ID, VER_NBR) 
VALUES ('KCCGB0046', (SELECT MAX(DOC_TYP_ID) FROM KREW_DOC_TYP_T WHERE DOC_TYP_NM = 'AwardDocument'), 'F', 'ANNUALLY by cc acct',
		(SELECT PRNCPL_ID FROM KRIM_PRNCPL_T WHERE PRNCPL_NM = 'quickstart'), (SELECT PRNCPL_ID FROM KRIM_PRNCPL_T WHERE PRNCPL_NM = 'quickstart'), 1, 4,
		NOW(), NOW(), NOW(), NOW(), NOW(),
		UUID(), 1)
/

INSERT INTO KREW_DOC_HDR_CNTNT_T (DOC_HDR_ID,DOC_CNTNT_TXT)
    VALUES ('KCCGB0046','')
/
INSERT INTO KREW_DOC_HDR_T (DOC_HDR_ID, DOC_TYP_ID, DOC_HDR_STAT_CD, TTL, 
						INITR_PRNCPL_ID, RTE_PRNCPL_ID, DOC_VER_NBR, RTE_LVL, 
						CRTE_DT, RTE_STAT_MDFN_DT, APRV_DT, FNL_DT, STAT_MDFN_DT, 
						OBJ_ID, VER_NBR) 
VALUES ('KCCGB0047', (SELECT MAX(DOC_TYP_ID) FROM KREW_DOC_TYP_T WHERE DOC_TYP_NM = 'AwardDocument'), 'F', 'ANNUALLY by cc acct',
		(SELECT PRNCPL_ID FROM KRIM_PRNCPL_T WHERE PRNCPL_NM = 'quickstart'), (SELECT PRNCPL_ID FROM KRIM_PRNCPL_T WHERE PRNCPL_NM = 'quickstart'), 1, 4,
		NOW(), NOW(), NOW(), NOW(), NOW(),
		UUID(), 1)
/

INSERT INTO KREW_DOC_HDR_CNTNT_T (DOC_HDR_ID,DOC_CNTNT_TXT)
    VALUES ('KCCGB0047','')
/
INSERT INTO KREW_DOC_HDR_T (DOC_HDR_ID, DOC_TYP_ID, DOC_HDR_STAT_CD, TTL, 
						INITR_PRNCPL_ID, RTE_PRNCPL_ID, DOC_VER_NBR, RTE_LVL, 
						CRTE_DT, RTE_STAT_MDFN_DT, APRV_DT, FNL_DT, STAT_MDFN_DT, 
						OBJ_ID, VER_NBR) 
VALUES ('KCCGB0048', (SELECT MAX(DOC_TYP_ID) FROM KREW_DOC_TYP_T WHERE DOC_TYP_NM = 'AwardDocument'), 'F', 'MILE - to test KFSPRJQ-1150',
		(SELECT PRNCPL_ID FROM KRIM_PRNCPL_T WHERE PRNCPL_NM = 'quickstart'), (SELECT PRNCPL_ID FROM KRIM_PRNCPL_T WHERE PRNCPL_NM = 'quickstart'), 1, 4,
		NOW(), NOW(), NOW(), NOW(), NOW(),
		UUID(), 1)
/

INSERT INTO KREW_DOC_HDR_CNTNT_T (DOC_HDR_ID,DOC_CNTNT_TXT)
    VALUES ('KCCGB0048','')
/
INSERT INTO KREW_DOC_HDR_T (DOC_HDR_ID, DOC_TYP_ID, DOC_HDR_STAT_CD, TTL, 
						INITR_PRNCPL_ID, RTE_PRNCPL_ID, DOC_VER_NBR, RTE_LVL, 
						CRTE_DT, RTE_STAT_MDFN_DT, APRV_DT, FNL_DT, STAT_MDFN_DT, 
						OBJ_ID, VER_NBR) 
VALUES ('KCCGB0049', (SELECT MAX(DOC_TYP_ID) FROM KREW_DOC_TYP_T WHERE DOC_TYP_NM = 'AwardDocument'), 'F', 'MONTHLY by award AUTOAPPROVE',
		(SELECT PRNCPL_ID FROM KRIM_PRNCPL_T WHERE PRNCPL_NM = 'quickstart'), (SELECT PRNCPL_ID FROM KRIM_PRNCPL_T WHERE PRNCPL_NM = 'quickstart'), 1, 4,
		NOW(), NOW(), NOW(), NOW(), NOW(),
		UUID(), 1)
/

INSERT INTO KREW_DOC_HDR_CNTNT_T (DOC_HDR_ID,DOC_CNTNT_TXT)
    VALUES ('KCCGB0049','')
/

DELIMITER ;

