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
UPDATE KRCR_PARM_T SET VAL = '2,503' WHERE PARM_NM='PROPOSAL_TYPE_CODE_RESUBMISSION' AND NMSPC_CD = 'KC-S2S' AND VAL='6,503';
UPDATE KRCR_PARM_T SET VAL = '3,505' WHERE PARM_NM='PROPOSAL_TYPE_CODE_RENEWAL' AND NMSPC_CD = 'KC-S2S' AND VAL='5,505';
UPDATE KRCR_PARM_T SET VAL = '5,502' WHERE PARM_NM='PROPOSAL_TYPE_CODE_REVISION' AND NMSPC_CD = 'KC-S2S' AND VAL='4,502';
UPDATE KRCR_PARM_T a, (SELECT VAL FROM KRCR_PARM_T WHERE PARM_NM='PROPOSAL_TYPE_CODE_CONTINUATION' AND NMSPC_CD = 'KC-PD') b
    SET a.VAL = b.VAL
    WHERE PARM_NM='PROPOSAL_TYPE_CODE_CONTINUATION' AND NMSPC_CD = 'KC-S2S' AND a.VAL='3';
UPDATE KRCR_PARM_T a, (SELECT VAL FROM KRCR_PARM_T WHERE PARM_NM='PROPOSAL_TYPE_CODE_BUDGET_SOW_UPDATE' AND NMSPC_CD = 'KC-PD') b
    SET a.VAL = b.VAL
    WHERE PARM_NM='PROPOSAL_TYPE_CODE_BUDGET_SOW_UPDATE' AND NMSPC_CD = 'KC-S2S' AND a.VAL='@{#param("KC-PD", "Document", "PROPOSAL_TYPE_CODE_BUDGET_SOW_UPDATE")}';
UPDATE KRCR_PARM_T a, (SELECT VAL FROM KRCR_PARM_T WHERE PARM_NM='PROPOSAL_TYPE_CODE_NEW_CHANGE_CORRECTED' AND NMSPC_CD = 'KC-PD') b
    SET a.VAL = b.VAL
    WHERE PARM_NM='PROPOSAL_TYPE_CODE_NEW_CHANGE_CORRECTED' AND NMSPC_CD = 'KC-S2S' AND a.VAL='@{#param("KC-PD", "Document", "PROPOSAL_TYPE_CODE_NEW_CHANGE_CORRECTED")}';
UPDATE KRCR_PARM_T a, (SELECT VAL FROM KRCR_PARM_T WHERE PARM_NM='PROPOSAL_TYPE_CODE_PRE_PROPOSAL' AND NMSPC_CD = 'KC-PD') b
    SET a.VAL = b.VAL
    WHERE PARM_NM='PROPOSAL_TYPE_CODE_PRE_PROPOSAL' AND NMSPC_CD = 'KC-S2S' AND a.VAL='@{#param("KC-PD", "Document", "PROPOSAL_TYPE_CODE_PRE_PROPOSAL")}';
UPDATE KRCR_PARM_T a, (SELECT VAL FROM KRCR_PARM_T WHERE PARM_NM='PROPOSAL_TYPE_CODE_RENEWAL_CHANGE_CORRECTED' AND NMSPC_CD = 'KC-PD') b
    SET a.VAL = b.VAL
    WHERE PARM_NM='PROPOSAL_TYPE_CODE_RENEWAL_CHANGE_CORRECTED' AND NMSPC_CD = 'KC-S2S' AND a.VAL='@{#param("KC-PD", "Document", "PROPOSAL_TYPE_CODE_RENEWAL_CHANGE_CORRECTED")}';
UPDATE KRCR_PARM_T a, (SELECT VAL FROM KRCR_PARM_T WHERE PARM_NM='PROPOSAL_TYPE_CODE_RESUBMISSION_CHANGE_CORRECTED' AND NMSPC_CD = 'KC-PD') b
    SET a.VAL = b.VAL
    WHERE PARM_NM='PROPOSAL_TYPE_CODE_RESUBMISSION_CHANGE_CORRECTED' AND NMSPC_CD = 'KC-S2S' AND a.VAL='@{#param("KC-PD", "Document", "PROPOSAL_TYPE_CODE_RESUBMISSION_CHANGE_CORRECTED")}';
UPDATE KRCR_PARM_T a, (SELECT VAL FROM KRCR_PARM_T WHERE PARM_NM='PROPOSAL_TYPE_CODE_SUPPLEMENT_CHANGE_CORRECTED' AND NMSPC_CD = 'KC-PD') b
    SET a.VAL = b.VAL
    WHERE PARM_NM='PROPOSAL_TYPE_CODE_SUPPLEMENT_CHANGE_CORRECTED' AND NMSPC_CD = 'KC-S2S' AND a.VAL='@{#param("KC-PD", "Document", "PROPOSAL_TYPE_CODE_SUPPLEMENT_CHANGE_CORRECTED")}';