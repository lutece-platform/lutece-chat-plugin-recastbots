
--
-- Data for table core_admin_right
--
DELETE FROM core_admin_right WHERE id_right = 'RECASTBOTS_MANAGEMENT';
INSERT INTO core_admin_right (id_right,name,level_right,admin_url,description,is_updatable,plugin_name,id_feature_group,icon_url,documentation_url, id_order ) VALUES 
('RECASTBOTS_MANAGEMENT','recastbots.adminFeature.ManageBots.name',1,'jsp/admin/plugins/recastbots/ManageRecastBots.jsp','recastbots.adminFeature.ManageBots.description',0,'recastbots',NULL,NULL,NULL,4);


--
-- Data for table core_user_right
--
DELETE FROM core_user_right WHERE id_right = 'RECASTBOTS_MANAGEMENT';
INSERT INTO core_user_right (id_right,id_user) VALUES ('RECASTBOTS_MANAGEMENT',1);

