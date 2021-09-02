DELETE
FROM `ge_interfaces`
WHERE interface_id in
      ('815923836075589690', '815923836075589691', '815923836075589692', '815923836075589693', '815923836075589694',
       '815923836075589695', '815923836075589696');
INSERT INTO `ge_interfaces`
VALUES ('815923836075589690', '/job/addjob', 'POST', 'IFrame', '添加定时任务', '添加定时任务', 1, 'token', '0');
INSERT INTO `ge_interfaces`
VALUES ('815923836075589691', '/job/deletejob', 'POST', 'IFrame', '删除任务', '删除任务', 1, 'token', '0');
INSERT INTO `ge_interfaces`
VALUES ('815923836075589692', '/job/pausejob', 'POST', 'IFrame', '暂停任务', '暂停任务', 1, 'token', '0');
INSERT INTO `ge_interfaces`
VALUES ('815923836075589693', '/job/queryjob', 'GET', 'IFrame', '查询任务', '查询任务', 1, 'token', '0');
INSERT INTO `ge_interfaces`
VALUES ('815923836075589694', '/job/reschedulejob', 'POST', 'IFrame', '重新触发任务', '重新触发任务', 1, 'token', '0');
INSERT INTO `ge_interfaces`
VALUES ('815923836075589695', '/job/resumejob', 'POST', 'IFrame', '继续任务', '继续任务', 1, 'token', '0');
INSERT INTO `ge_interfaces`
VALUES ('815923836075589696', '/job/triggerJob', 'POST', 'IFrame', '运行任务', '运行任务', 1, 'token', '0');
