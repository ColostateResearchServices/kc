-----------------------------------------------
-- DDL for new CGB maint tables 
-------------------------------------------------

DELIMITER /
 
--------------------------------------------------------
--   CG_BILL_FREQUENCY_T
--------------------------------------------------------
--------------------------------------------------------
--  DDL for Table CG_BILL_FREQUENCY_T
--------------------------------------------------------



CREATE TABLE CG_BILL_FREQUENCY_T 
   (	
    BILL_FREQ_ID DECIMAL(22),
	CODE VARCHAR(15 ), 
	DESCRIPTION VARCHAR(60 ), 
	OBJ_ID VARCHAR(36 ), 
	VER_NBR DECIMAL(8,0) DEFAULT 1, 
	ACTV_IND VARCHAR(1 )
   ) ENGINE InnoDB CHARACTER SET utf8 COLLATE utf8_bin 
/

   


CREATE TABLE CG_BILL_FREQUENCY_S (
  id bigint(19) not null auto_increment, primary key (id)
) ENGINE MyISAM
/

ALTER TABLE CG_BILL_FREQUENCY_S auto_increment = 1
/
   

--------------------------------------------------------
--  DDL for Index CG_BILL_FREQUENCY_TC0
--------------------------------------------------------
CREATE UNIQUE INDEX CG_BILL_FREQUENCY_TC0 ON CG_BILL_FREQUENCY_T (OBJ_ID)  
/
  
--------------------------------------------------------
--  DDL for Index CG_BILL_FREQUENCY_TP1
--------------------------------------------------------
CREATE UNIQUE INDEX CG_BILL_FREQUENCY_TP1 ON CG_BILL_FREQUENCY_T (BILL_FREQ_ID) 
/

--------------------------------------------------------
--  Constraints for Table CG_BILL_FREQUENCY_T
--------------------------------------------------------
ALTER TABLE CG_BILL_FREQUENCY_T ADD CONSTRAINT CG_BILL_FREQUENCY_TP1 PRIMARY KEY (BILL_FREQ_ID)
/ 
ALTER TABLE CG_BILL_FREQUENCY_T MODIFY ACTV_IND VARCHAR(1) NOT NULL
/
ALTER TABLE CG_BILL_FREQUENCY_T MODIFY VER_NBR DECIMAL(8,0) NOT NULL
/
ALTER TABLE CG_BILL_FREQUENCY_T MODIFY OBJ_ID VARCHAR(36) NOT NULL 
/

--------------------------------------------------------
--  DDL for Table CG_LTRCR_FNDGRP_T
--------------------------------------------------------


CREATE TABLE CG_LTRCR_FNDGRP_T 
   (	
    FNDGRP_ID DECIMAL(22),
    CG_LTRCR_FNDGRP_CD VARCHAR(4 ), 
	OBJ_ID VARCHAR(36 ), 
	VER_NBR DECIMAL(8,0) DEFAULT 1, 
	LTRCR_FNDGRP_DESC VARCHAR(40 ), 
	ROW_ACTV_IND VARCHAR(1 )
   ) 
/



CREATE TABLE CG_LTRCR_FNDGRP_S (
  id bigint(19) not null auto_increment, primary key (id)
) ENGINE MyISAM
/

ALTER TABLE CG_LTRCR_FNDGRP_S auto_increment = 1
/
   

--------------------------------------------------------
--  DDL for Index CG_LTRCR_FNDGRP_TC0
--------------------------------------------------------
CREATE UNIQUE INDEX CG_LTRCR_FNDGRP_TC0 ON CG_LTRCR_FNDGRP_T (OBJ_ID)  
/
  
--------------------------------------------------------
--  DDL for Index CG_LTRCR_FNDGRP_TP1
--------------------------------------------------------
CREATE UNIQUE INDEX CG_LTRCR_FNDGRP_TP1 ON CG_LTRCR_FNDGRP_T (FNDGRP_ID) 
/

CREATE UNIQUE INDEX CG_LTRCR_FNDGRP_TP2 ON CG_LTRCR_FNDGRP_T (CG_LTRCR_FNDGRP_CD) 
/

--------------------------------------------------------
--  Constraints for Table CG_LTRCR_FNDGRP_T
--------------------------------------------------------
ALTER TABLE CG_LTRCR_FNDGRP_T ADD CONSTRAINT CG_LTRCR_FNDGRP_TP1 PRIMARY KEY (FNDGRP_ID)
/     
ALTER TABLE CG_LTRCR_FNDGRP_T MODIFY CG_LTRCR_FNDGRP_CD VARCHAR(4) NOT NULL
/
ALTER TABLE CG_LTRCR_FNDGRP_T ADD CONSTRAINT FNDGRP_CD_U1 UNIQUE CG_LTRCR_FNDGRP_CD
/
ALTER TABLE CG_LTRCR_FNDGRP_T MODIFY ROW_ACTV_IND VARCHAR(1) NOT NULL
/
ALTER TABLE CG_LTRCR_FNDGRP_T MODIFY VER_NBR DECIMAL(8,0) NOT NULL
/
ALTER TABLE CG_LTRCR_FNDGRP_T MODIFY OBJ_ID VARCHAR(36) NOT NULL 
/

--------------------------------------------------------
--  DDL for Table CG_LTRCR_FND_T
--------------------------------------------------------


CREATE TABLE CG_LTRCR_FND_T 
   (	
    FUND_ID DECIMAL(22),
    CG_LTRCR_FND_CD VARCHAR(15 ), 
	OBJ_ID VARCHAR(36 ), 
	VER_NBR DECIMAL(8,0) DEFAULT 1, 
	CG_LTRCR_FND_DESC VARCHAR(60 ), 
	CG_LTRCR_FNDGRP_CD VARCHAR(4 ), 
	CG_LTRCR_AMT DECIMAL(19,2), 
	CG_LTRCR_START_DT DATE, 
	CG_LTRCR_EXPIRATION_DT DATE, 
	ACTV_IND VARCHAR(1 )
   ) ENGINE InnoDB CHARACTER SET utf8 COLLATE utf8_bin;
/


CREATE TABLE CG_LTRCR_FND_S (
  id bigint(19) not null auto_increment, primary key (id)
) ENGINE MyISAM
/

ALTER TABLE CG_LTRCR_FND_S auto_increment = 1
/
--------------------------------------------------------
--  DDL for Index CG_LTRCR_FND_TC0
--------------------------------------------------------
CREATE UNIQUE INDEX CG_LTRCR_FND_TC0 ON CG_LTRCR_FND_T (OBJ_ID)
/
  
--------------------------------------------------------
--  DDL for Index CG_LTRCR_FND_TP1
--------------------------------------------------------
CREATE UNIQUE INDEX CG_LTRCR_FND_TP1 ON CG_LTRCR_FNDGRP_T (FNDGRP_ID) 
/
CREATE UNIQUE INDEX CG_LTRCR_FND_TP2 ON CG_LTRCR_FND_T (CG_LTRCR_FND_CD)
/
  
--------------------------------------------------------
--  Constraints for Table CG_LTRCR_FND_T
--------------------------------------------------------

ALTER TABLE CG_LTRCR_FND_T ADD CONSTRAINT CG_LTRCR_FND_TP1 PRIMARY KEY (FNDGRP_ID)
/
ALTER TABLE CG_LTRCR_FND_T MODIFY CG_LTRCR_FND_CD VARCHAR(15) NOT NULL 
/
ALTER TABLE CG_LTRCR_FND_T ADD CONSTRAINT FND_CD_U1 UNIQUE CG_LTRCR_FND_CD
/
ALTER TABLE CG_LTRCR_FND_T MODIFY ACTV_IND VARCHAR(1) NOT NULL 
/
ALTER TABLE CG_LTRCR_FND_T MODIFY VER_NBR  DECIMAL(8,0) DEFAULT 1 NOT NULL 
/
ALTER TABLE CG_LTRCR_FND_T MODIFY OBJ_ID VARCHAR(36 ) NOT NULL 
/
  
--------------------------------------------------------
--  Ref Constraints for Table CG_LTRCR_FND_T
--------------------------------------------------------
ALTER TABLE CG_LTRCR_FND_T ADD CONSTRAINT CG_LTRCR_FND_TR1 FOREIGN KEY (CG_LTRCR_FNDGRP_CD)
	  REFERENCES CG_LTRCR_FNDGRP_T (CG_LTRCR_FNDGRP_CD) 
/
	  


 



--------------------------------------------------------
--  Alter Award cgb to accomodate new elements
--------------------------------------------------------
ALTER TABLE AWARD_CGB ADD COLUMN LOC_FUND_CODE VARCHAR(15)
/
ALTER TABLE AWARD_CGB ADD COLUMN BILLING_FREQ_ID DECIMAL(22)
/

DELIMITER ;     
   