insert into tms_rsrv_timer_config (c_created, c_ol, c_pk, c_updated, c_created_by, c_cron_expression, c_description, c_name, c_pid, c_updated_by) values (now(), 0, 999999999, now(), 'SYSTEM', '10 * * * * *', 'Test workflow', 'WF01', '999999999', 'SYSTEM');

insert into tms_rsrv_timer_config_vars (c_tc_pk, c_key, c_value) values (999999999, 'param1', 'value1');
