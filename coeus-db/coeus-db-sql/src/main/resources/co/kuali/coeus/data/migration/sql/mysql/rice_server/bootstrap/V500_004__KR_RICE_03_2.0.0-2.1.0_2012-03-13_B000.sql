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

DELIMITER /

--
-- KULRICE-6916 KRIM_ENTITY_CACHE_T.PRSN_NM is too small
--

ALTER TABLE `krim_entity_cache_t`
CHANGE COLUMN `PRSN_NM` `PRSN_NM` VARCHAR(255)
CHARACTER SET 'utf8' COLLATE 'utf8_bin' NULL DEFAULT NULL
/
DELIMITER ;