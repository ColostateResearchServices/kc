DELIMITER /

INSERT INTO KREW_DOC_HDR_T (DOC_HDR_ID, DOC_TYP_ID, DOC_HDR_STAT_CD, TTL, 
						INITR_PRNCPL_ID, RTE_PRNCPL_ID, DOC_VER_NBR, RTE_LVL, 
						CRTE_DT, RTE_STAT_MDFN_DT, APRV_DT, FNL_DT, STAT_MDFN_DT, 
						OBJ_ID, VER_NBR) 
VALUES ('KCCGB0031', (SELECT MAX(DOC_TYP_ID) FROM KREW_DOC_TYP_T WHERE DOC_TYP_NM = 'InstitutionalProposalDocument'), 'F', 'PD FOR KFSPRJQ-1198',
		(SELECT PRNCPL_ID FROM KRIM_PRNCPL_T WHERE PRNCPL_NM = 'quickstart'), (SELECT PRNCPL_ID FROM KRIM_PRNCPL_T WHERE PRNCPL_NM = 'quickstart'), 1, 4,
		NOW(), NOW(), NOW(), NOW(), NOW(),
		UUID(), 1)
/

INSERT INTO KREW_DOC_HDR_CNTNT_T (DOC_HDR_ID,DOC_CNTNT_TXT)
    VALUES ('KCCGB0031','')
/




INSERT INTO KREW_DOC_HDR_T (DOC_HDR_ID, DOC_TYP_ID, DOC_HDR_STAT_CD, TTL, 
						INITR_PRNCPL_ID, RTE_PRNCPL_ID, DOC_VER_NBR, RTE_LVL, 
						CRTE_DT, RTE_STAT_MDFN_DT, APRV_DT, FNL_DT, STAT_MDFN_DT, 
						OBJ_ID, VER_NBR) 
VALUES ('KCCGB0032', (SELECT MAX(DOC_TYP_ID) FROM KREW_DOC_TYP_T WHERE DOC_TYP_NM = 'InstitutionalProposalDocument'), 'F', 'QUARTERLY by award',
		(SELECT PRNCPL_ID FROM KRIM_PRNCPL_T WHERE PRNCPL_NM = 'quickstart'), (SELECT PRNCPL_ID FROM KRIM_PRNCPL_T WHERE PRNCPL_NM = 'quickstart'), 1, 4,
		NOW(), NOW(), NOW(), NOW(), NOW(),
		UUID(), 1)
/

INSERT INTO KREW_DOC_HDR_CNTNT_T (DOC_HDR_ID,DOC_CNTNT_TXT)
    VALUES ('KCCGB0032','')
/


INSERT INTO KREW_DOC_HDR_T (DOC_HDR_ID, DOC_TYP_ID, DOC_HDR_STAT_CD, TTL, 
						INITR_PRNCPL_ID, RTE_PRNCPL_ID, DOC_VER_NBR, RTE_LVL, 
						CRTE_DT, RTE_STAT_MDFN_DT, APRV_DT, FNL_DT, STAT_MDFN_DT, 
						OBJ_ID, VER_NBR) 
VALUES ('KCCGB0033', (SELECT MAX(DOC_TYP_ID) FROM KREW_DOC_TYP_T WHERE DOC_TYP_NM = 'InstitutionalProposalDocument'), 'F', 'SEMI-ANNUALLY by acct',
		(SELECT PRNCPL_ID FROM KRIM_PRNCPL_T WHERE PRNCPL_NM = 'quickstart'), (SELECT PRNCPL_ID FROM KRIM_PRNCPL_T WHERE PRNCPL_NM = 'quickstart'), 1, 4,
		NOW(), NOW(), NOW(), NOW(), NOW(),
		UUID(), 1)
/

INSERT INTO KREW_DOC_HDR_CNTNT_T (DOC_HDR_ID,DOC_CNTNT_TXT)
    VALUES ('KCCGB0033','')
/


INSERT INTO KREW_DOC_HDR_T (DOC_HDR_ID, DOC_TYP_ID, DOC_HDR_STAT_CD, TTL, 
						INITR_PRNCPL_ID, RTE_PRNCPL_ID, DOC_VER_NBR, RTE_LVL, 
						CRTE_DT, RTE_STAT_MDFN_DT, APRV_DT, FNL_DT, STAT_MDFN_DT, 
						OBJ_ID, VER_NBR) 
VALUES ('KCCGB0034', (SELECT MAX(DOC_TYP_ID) FROM KREW_DOC_TYP_T WHERE DOC_TYP_NM = 'InstitutionalProposalDocument'), 'F', 'ANNUALLY by cc acct',
		(SELECT PRNCPL_ID FROM KRIM_PRNCPL_T WHERE PRNCPL_NM = 'quickstart'), (SELECT PRNCPL_ID FROM KRIM_PRNCPL_T WHERE PRNCPL_NM = 'quickstart'), 1, 4,
		NOW(), NOW(), NOW(), NOW(), NOW(),
		UUID(), 1)
/

INSERT INTO KREW_DOC_HDR_CNTNT_T (DOC_HDR_ID,DOC_CNTNT_TXT)
    VALUES ('KCCGB0034','')
/


INSERT INTO KREW_DOC_HDR_T (DOC_HDR_ID, DOC_TYP_ID, DOC_HDR_STAT_CD, TTL, 
						INITR_PRNCPL_ID, RTE_PRNCPL_ID, DOC_VER_NBR, RTE_LVL, 
						CRTE_DT, RTE_STAT_MDFN_DT, APRV_DT, FNL_DT, STAT_MDFN_DT, 
						OBJ_ID, VER_NBR) 
VALUES ('KCCGB0035', (SELECT MAX(DOC_TYP_ID) FROM KREW_DOC_TYP_T WHERE DOC_TYP_NM = 'InstitutionalProposalDocument'), 'F', 'MILE - to test KFSPRJQ-1150',
		(SELECT PRNCPL_ID FROM KRIM_PRNCPL_T WHERE PRNCPL_NM = 'quickstart'), (SELECT PRNCPL_ID FROM KRIM_PRNCPL_T WHERE PRNCPL_NM = 'quickstart'), 1, 4,
		NOW(), NOW(), NOW(), NOW(), NOW(),
		UUID(), 1)
/

INSERT INTO KREW_DOC_HDR_CNTNT_T (DOC_HDR_ID,DOC_CNTNT_TXT)
    VALUES ('KCCGB0035','')
/


INSERT INTO KREW_DOC_HDR_T (DOC_HDR_ID, DOC_TYP_ID, DOC_HDR_STAT_CD, TTL, 
						INITR_PRNCPL_ID, RTE_PRNCPL_ID, DOC_VER_NBR, RTE_LVL, 
						CRTE_DT, RTE_STAT_MDFN_DT, APRV_DT, FNL_DT, STAT_MDFN_DT, 
						OBJ_ID, VER_NBR) 
VALUES ('KCCGB0036', (SELECT MAX(DOC_TYP_ID) FROM KREW_DOC_TYP_T WHERE DOC_TYP_NM = 'InstitutionalProposalDocument'), 'F', 'MONTHLY by award AUTOAPPROVE',
		(SELECT PRNCPL_ID FROM KRIM_PRNCPL_T WHERE PRNCPL_NM = 'quickstart'), (SELECT PRNCPL_ID FROM KRIM_PRNCPL_T WHERE PRNCPL_NM = 'quickstart'), 1, 4,
		NOW(), NOW(), NOW(), NOW(), NOW(),
		UUID(), 1)
/

INSERT INTO KREW_DOC_HDR_CNTNT_T (DOC_HDR_ID,DOC_CNTNT_TXT)
    VALUES ('KCCGB0036','')
/

DELIMITER ;
