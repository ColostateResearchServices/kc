INSERT INTO KRIM_PERM_T (PERM_ID,PERM_TMPL_ID,NMSPC_CD,NM,DESC_TXT,ACTV_IND,OBJ_ID,VER_NBR)
    VALUES ('RES-BOOT1001',
    (SELECT PERM_TMPL_ID FROM KRIM_PERM_TMPL_T WHERE NMSPC_CD = 'KC-IDM' AND NM = 'Perform Document Action'),
    'KC-SYS','Post Award','Post Award information to the account table.','Y',SYS_GUID(),1);

insert into KRIM_ROLE_PERM_T values ('RES-BOOT1001', SYS_GUID(), 1,
(select ROLE_ID from KRIM_ROLE_T where ROLE_NM = 'KC Superuser'),
(select PERM_ID from KRIM_PERM_T where NM = 'Post Award'), 'Y');

insert into KRIM_ROLE_PERM_T values ('RES-BOOT1002', SYS_GUID(), 1,
(select ROLE_ID from KRIM_ROLE_T where ROLE_NM = 'OSP Administrator'),
(select PERM_ID from KRIM_PERM_T where NM = 'Post Award'), 'Y');
