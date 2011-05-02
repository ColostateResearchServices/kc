set define off
set sqlblanklines on
spool KC-RELEASE-3_1-Demo-ORACLE-Install.log
@ORACLE/KC_DEMO1_COMMITTEE_DOCUMENT.sql
@ORACLE/KC_DEMO1_DOCUMENT_NEXTVALUE.sql
@ORACLE/KC_DEMO1_INV_CREDIT_TYPE.sql
@ORACLE/KC_DEMO1_JOB_CODE.sql
@ORACLE/KC_DEMO1_ORGANIZATION.sql
@ORACLE/KC_DEMO1_ORGANIZATION_TYPE.sql
@ORACLE/KC_DEMO1_ORGANIZATION_YNQ.sql
@ORACLE/KC_DEMO1_PERSON_APPOINTMENT.sql
@ORACLE/KC_DEMO1_PERSON_EXT_T.sql
@ORACLE/KC_DEMO1_PROTOCOL_DOCUMENT.sql
@ORACLE/KC_DEMO1_PROTOCOL_ONLN_RVW_DOCUMENT.sql
@ORACLE/KC_DEMO1_QUESTIONNAIRE.sql
@ORACLE/KC_DEMO1_RATE_TYPE.sql
@ORACLE/KC_DEMO1_ROLODEX.sql
@ORACLE/KC_DEMO1_SCIENCE_KEYWORD.sql
@ORACLE/KC_DEMO1_UNIT.sql
@ORACLE/KC_DEMO1_VALID_RATES.sql
@ORACLE/KC_DEMO2_COMMITTEE.sql
@ORACLE/KC_DEMO2_CUSTOM_ATTRIBUTE.sql
@ORACLE/KC_DEMO2_INSTITUTE_LA_RATES.sql
@ORACLE/KC_DEMO2_INSTITUTE_RATES.sql
@ORACLE/KC_DEMO2_KRA_USER.sql
@ORACLE/KC_DEMO2_PROTOCOL.sql
@ORACLE/KC_DEMO2_QUESTIONNAIRE_QUESTIONS.sql
@ORACLE/KC_DEMO2_QUESTIONNAIRE_USAGE.sql
@ORACLE/KC_DEMO2_RATE_CLASS.sql
@ORACLE/KC_DEMO2_SPONSOR.sql
@ORACLE/KC_DEMO2_UNIT_ADMINISTRATOR.sql
@ORACLE/KC_DEMO2_VALID_SP_REV_APPROVAL.sql
@ORACLE/KC_DEMO3_AWARD_TEMPLATE.sql
@ORACLE/KC_DEMO3_COMM_MEMBERSHIPS.sql
@ORACLE/KC_DEMO3_COMM_RESEARCH_AREAS.sql
@ORACLE/KC_DEMO3_COMM_SCHEDULE.sql
@ORACLE/KC_DEMO3_COST_ELEMENT.sql
@ORACLE/KC_DEMO3_CUSTOM_ATTRIBUTE_DOCUMENT.sql
@ORACLE/KC_DEMO3_CUSTOM_ATTRIBUTE_DOC_VALUE.sql
@ORACLE/KC_DEMO3_PROTOCOL_LOCATION.sql
@ORACLE/KC_DEMO3_PROTOCOL_PERSONS.sql
@ORACLE/KC_DEMO3_PROTOCOL_RESEARCH_AREAS.sql
@ORACLE/KC_DEMO3_ROLODEX_SPONSOR.sql
@ORACLE/KC_DEMO3_SPONSOR_HIERARCHY.sql
@ORACLE/KC_DEMO4_AWARD_TEMPLATE_COMMENTS.sql
@ORACLE/KC_DEMO4_AWARD_TEMPLATE_CONTACT.sql
@ORACLE/KC_DEMO4_AWARD_TEMPLATE_REPORT_TERMS.sql
@ORACLE/KC_DEMO4_AWARD_TEMPLATE_TERMS.sql
@ORACLE/KC_DEMO4_COMM_MEMBER_EXPERTISE.sql
@ORACLE/KC_DEMO4_COMM_MEMBER_ROLES.sql
@ORACLE/KC_DEMO4_COMM_SCHEDULE_ACT_ITEMS.sql
@ORACLE/KC_DEMO4_COMM_SCHEDULE_ATTENDANCE.sql
@ORACLE/KC_DEMO4_PROTOCOL_SUBMISSION.sql
@ORACLE/KC_DEMO4_PROTOCOL_UNITS.sql
@ORACLE/KC_DEMO4_SPONSOR_FORM_TEMPLATES.sql
@ORACLE/KC_DEMO4_VALID_CE_JOB_CODES.sql
@ORACLE/KC_DEMO4_VALID_CE_RATE_TYPES.sql
@ORACLE/KC_DEMO5_AWARD_TEMPL_REP_TERMS_RECNT.sql
@ORACLE/KC_DEMO5_COMM_SCHEDULE_MINUTES.sql
@ORACLE/KC_DEMO5_PROTOCOL_ACTIONS.sql
@ORACLE/KC_DEMO5_PROTOCOL_REVIEWERS.sql
@ORACLE/KC_DEMO6_PROTOCOL_ONLN_RVWS.sql
@ORACLE/KC_DEMO6_UNIT.sql
@ORACLE/KC_DEMO6_YNQ.sql
@ORACLE/KC_SEQUENCE_RESET.sql
commit;
exit
