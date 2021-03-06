--
-- Kuali Coeus, a comprehensive research administration system for higher education.
-- 
-- Copyright 2005-2015 Kuali, Inc.
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

begin
  Insert into KRCR_PARM_T (NMSPC_CD,CMPNT_CD,PARM_NM,OBJ_ID,VER_NBR,PARM_TYP_CD,VAL,PARM_DESC_TXT,EVAL_OPRTR_CD,APPL_ID) values
    ('KR-WKFLW','All','KIM_PRIORITY_ON_DOC_TYP_PERMS_IND',SYS_GUID(),2,'CONFG','N','Flag for enabling disabling document type permission checks to use KIM Permissions as priority over Document Type policies.','A','KC');
  exception
  when DUP_VAL_ON_INDEX then
  update krcr_parm_t set val = 'N' where appl_id = 'KC' and nmspc_cd = 'KR-WKFLW' and parm_nm = 'KIM_PRIORITY_ON_DOC_TYP_PERMS_IND' and cmpnt_cd = 'All';
end;
/

