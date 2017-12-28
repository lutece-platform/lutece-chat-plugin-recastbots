
--
-- Structure for table recastbots_bot
--

DROP TABLE IF EXISTS recastbots_bot;
CREATE TABLE recastbots_bot (
id_recast_bot int AUTO_INCREMENT,
bot_key varchar(50) default '' NOT NULL,
name varchar(255) default '' NOT NULL,
description varchar(255) default '' NOT NULL,
avatar_url varchar(255) default '',
language varchar(50) default '' NOT NULL,
bot_status int default '0' NOT NULL,
token varchar(255) default '' NOT NULL,
is_standalone int default '0' NOT NULL,
welcome_message varchar(255) NULL,
PRIMARY KEY (id_recast_bot)
);
