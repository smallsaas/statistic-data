SET FOREIGN_KEY_CHECKS=0;

INSERT INTO `sys_perm_group` (`id`, `org_id`, `pid`, `identifier`, `name`) VALUES
('1001', '100000000000000001', '100000000000000101', 'report', '报表管理');
INSERT INTO `sys_perm_group` (`id`, `org_id`, `pid`, `identifier`, `name`) VALUES
('1001001', '100000000000000001', '1001', 'report.management', '报表管理');

INSERT INTO `sys_perm` (`id`, `group_id`, `identifier`, `name`, `tag`) VALUES
('1001001001', '1001001', 'defaultReport.view', '默认报表权限', '0');
