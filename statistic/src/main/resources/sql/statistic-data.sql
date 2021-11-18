SET FOREIGN_KEY_CHECKS=0;

DELETE FROM `sys_perm_group` WHERE id='1461211162149453826';
INSERT INTO `sys_perm_group` (`id`, `org_id`, `pid`, `identifier`, `name`) VALUES
('1461211162149453826', '100000000000000001', '100000000000000101', 'report', '报表管理');

DELETE FROM `sys_perm_group` WHERE id='1461211250296967169';
INSERT INTO `sys_perm_group` (`id`, `org_id`, `pid`, `identifier`, `name`) VALUES
('1461211250296967169', '100000000000000001', '1461211162149453826', 'report.management', '报表管理');

DELETE FROM `sys_perm` WHERE id='1461211327577051138';
INSERT INTO `sys_perm` (`id`, `group_id`, `identifier`, `name`, `tag`) VALUES
('1461211327577051138', '1461211250296967169', 'defaultReport.view', '默认报表权限', '0');
