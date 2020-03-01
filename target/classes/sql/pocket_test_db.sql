-- ------------------------------
-- Table structure for user_info
-- ------------------------------
DROP TABLE IF EXISTS `user_info`;
CREATE TABLE `user_info` (
                           `user_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '用户ID唯一标识',
                           `user_name` varchar(100) DEFAULT NULL COMMENT '用户名',
                           `user_phone` varchar(20) DEFAULT NULL COMMENT '用户手机号',
                           `user_mail`  varchar(200) DEFAULT NULL COMMENT '用户邮箱',
                           `user_head` text,
                           `section_id` int(11) COMMENT '所属部门ID',
                           PRIMARY KEY (`user_id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;
-- ---------------------
-- Records of user_info
-- ---------------------
INSERT INTO `user_info` VALUES ('1', '测试用户1', '17802592376', '1253150015@qq.com','123saxsqde23w3',1);
INSERT INTO `user_info` VALUES ('2', '测试用户2', '17802592502', '17626482635@qq.com','qgsw284gt3v',2);
INSERT INTO `user_info` VALUES ('3', '测试用户3', '17802593426', '27384614326@qq.com','dgwv2hdb3455ge',3);

-- ---------------------------------
-- Table structure for user_password
-- ---------------------------------
DROP TABLE IF EXISTS `user_password`;
CREATE TABLE `user_password` (
                               `user_id` int(11) NOT NULL COMMENT '用户唯一标识',
                               `user_password` varchar(50) NOT NULL COMMENT '用户密码',
                               PRIMARY KEY (`user_id`),
                               CONSTRAINT `user_password_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `user_info` (`user_id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
-- ------------------------
-- Records of user_password
-- ------------------------
INSERT INTO `user_password` VALUES ('1', '123456');
INSERT INTO `user_password` VALUES ('2', '123123');
INSERT INTO `user_password` VALUES ('3', '111111');


-- ---------------------------------
-- Table structure for user_role
-- ---------------------------------
DROP TABLE IF EXISTS `user_role`;
CREATE TABLE `user_role` (
                               `user_id` int(11) NOT NULL COMMENT '用户唯一标识ID',
                               `user_role` varchar(50) NOT NULL COMMENT '用户角色',
                               `user_permission` VARCHAR(1000) COMMENT '用户权限',
                               PRIMARY KEY (`user_id`),
                               CONSTRAINT `user_role_ibfk` FOREIGN KEY (`user_id`) REFERENCES `user_info` (`user_id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
-- ------------------------
-- Records of user_role
-- ------------------------
INSERT INTO `user_role` VALUES ('1', 'QA','acc_1');
INSERT INTO `user_role` VALUES ('2', 'RD','pv1,pv2');
INSERT INTO `user_role` VALUES ('3','PM', 'acc_1,acc_2,pv1,pv2');


-- ---------------------------------
-- Table structure for section_info
-- ---------------------------------
DROP TABLE IF EXISTS `section_info`;
CREATE TABLE `section_info` (
                              `section_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '部门ID唯一标识',
                              `section_name` VARCHAR(100) NOT NULL COMMENT '部门名称',
                              `level` int(4) NOT NULL COMMENT '所属等级',
                              `parent_id` int(4) NOT NULL COMMENT '上层部门ID',
                              PRIMARY KEY (`section_id`)
)ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;

-- ------------------------
-- Records of section_info
-- ------------------------
INSERT INTO `section_info` VALUES (1, '部门1', 0, 1);
INSERT INTO `section_info` VALUES (2, '部门2', 1, 1);
INSERT INTO `section_info` VALUES (3, '部门3', 2, 2);
INSERT INTO `section_info` VALUES (4, '部门4', 1, 1);


-- ---------------------------------
-- Table structure for project_info
-- ---------------------------------
DROP TABLE IF EXISTS `project_info`;
CREATE TABLE `project_info` (
                                `project_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '项目ID唯一标识',
                                `project_name` VARCHAR(100) NOT NULL COMMENT '项目名称',
                                `project_manager` int(11) NOT NULL COMMENT '项目经理ID',
                                `section_id` int(11) NOT NULL COMMENT '所属部门ID',
                                `describe` varchar(500) COMMENT '描述',
                                PRIMARY KEY (`project_id`),
                                CONSTRAINT `project_info_ibfk` FOREIGN KEY (`section_id`) REFERENCES `section_info` (`section_id`) ON DELETE CASCADE ON UPDATE CASCADE
)ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;

-- ------------------------
-- Records of project_info
-- ------------------------
INSERT INTO `project_info` VALUES (1, '项目1', 1, 1, '功能xxx');
INSERT INTO `project_info` VALUES (2, '项目2', 2, 7, '实现了xxx');
INSERT INTO `project_info` VALUES (3, '项目3', 3, 1, '实现了xxx');

-- ---------------------------------
-- Table structure for project_version
-- ---------------------------------
DROP TABLE IF EXISTS `project_version`;
CREATE TABLE `project_version` (
                                `version_id` int(8) NOT NULL AUTO_INCREMENT COMMENT '版本ID唯一标识',
                                `version_number` varchar(20) NOT NULL COMMENT '版本号',
                                `plan_launchdate` VARCHAR(20) COMMENT '计划上线日期',
                                `actually_launchdate` VARCHAR(20) COMMENT '实际上线日期',
                                `plan_devstart`       VARCHAR(20) COMMENT '计划开发开始时间',
                                `plan_devend`         VARCHAR(20) COMMENT '计划开发结束时间',
                                `actually_devstart`   VARCHAR(20) COMMENT '实际开发开始时间',
                                `actually_devend`     VARCHAR(20) COMMENT '实际开发结束时间',
                                `plan_teststart`      VARCHAR(20) COMMENT '计划测试开始时间',
                                `plan_testend`        VARCHAR(20) COMMENT '计划测试结束时间',
                                `actually_teststart`  VARCHAR(20) COMMENT '实际测试开始时间',
                                `actually_testend`    VARCHAR(20) COMMENT '实际测试结束时间',
                                `testcasenum`         int(10) COMMENT '测试用例数',
                                `imprint`             VARCHAR(500) COMMENT '版本说明',
                                `remark`              VARCHAR(500) COMMENT '备注',
                                `project_id`           int(10) COMMENT '项目ID',
                                primary key (version_id),
                                CONSTRAINT `project_version_ibfk` FOREIGN KEY (`project_id`) REFERENCES `project_info` (`project_id`) ON DELETE CASCADE ON UPDATE CASCADE
)ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;

-- ------------------------
-- Records of project_version
-- ------------------------
INSERT INTO `project_version` VALUES (1, '3.0.1231','2019-07-01', '2019-07-01', '2018-08-23', '2019-04-23', '2018-02-23', '2019-06-23','2018-08-23', '2019-05-10', '2018-02-18', '2019-06-20', 53, '此版本增加了...', 'xxx 变更为 xxx', 1);
-- ---------------------------------
-- Table structure for Test_Project
-- ---------------------------------
DROP TABLE IF EXISTS `Test_project`;
CREATE TABLE `Test_project` (
                            `testproject_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '测试项目的id',
                            `testproject_name` varchar(50) NOT NULL COMMENT '测试项目名称',
                            `testproject_path` varchar(200) NOT NULL COMMENT '测试项目路径',
                            `section_id` int(11) COMMENT '所属部门ID',
                            `project_id` int(11) COMMENT '所属项目的ID',
                            `version_id` int(8) comment '所属版本的ID',
                            `user_id` int(8) COMMENT '创建者ID',
                            `create_time` varchar(50) NOT NULL comment '创建时间',
                            `modify_time` varchar(50) NOT NULL comment '修改时间',
                            primary key (testproject_id)
                            )ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;
-- ------------------------
-- Records of Test_project
-- ------------------------
INSERT INTO `Test_project` VALUES (1, '测试项目一', '/User/mac/Project1', 2, 1, 2, 1, '2019-08-05','2019-08-06');
INSERT INTO `Test_project` VALUES (2, '测试项目二', '/User/mac/Project2', 1, 3, 2, 2, '2019-03-25','2019-04-21');
INSERT INTO `Test_project` VALUES (3, '测试项目三', '/User/mac/Project3', 4, 2, 1, 1, '2019-04-19','2019-05-04');


-- ---------------------------------
-- Table structure for Test_Module
-- ---------------------------------
DROP TABLE IF EXISTS `Test_Module`;
CREATE TABLE `Test_Module` (
                              `testmodule_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '测试模块的id',
                              `testmodule_name` varchar(50) NOT NULL COMMENT '测试模块名称',
                              `testmodule_path` varchar(200) NOT NULL COMMENT '测试模块路径',
                              `testproject_id` int(11) NOT NULL COMMENT '测试项目ID',
                              `create_time` varchar(50) NOT NULL comment '创建时间',
                              `modify_time` varchar(50) NOT NULL comment '修改时间',
                              primary key (testmodule_id)
)ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;
-- ------------------------
-- Records of Test_Module
-- ------------------------
INSERT INTO `Test_Module` VALUES (1, '测试模块一', '/User/mac/Project1/Module1', 2, '2019-08-05', '2019-08-06');
INSERT INTO `Test_Module` VALUES (2, '测试模块二', '/User/mac/Project1/Module2', 1, '2019-03-25', '2019-04-21');
INSERT INTO `Test_Module` VALUES (3, '测试模块三', '/User/mac/Project3/Module3', 3, '2019-04-19', '2019-05-04');

-- ---------------------------------
-- Table structure for Test_suite
-- ---------------------------------
DROP TABLE IF EXISTS `Test_suite`;
CREATE TABLE `Test_suite` (
                            `suite_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '测试套件的id',
                            `suite_name` varchar(50) NOT NULL COMMENT '测试套件名称',
                            `file_path` varchar(200) NOT NULL COMMENT '测试条件路径',
                            `testmodule_id` int(11) COMMENT '测试模块ID',
                            `testproject_id` int(11) COMMENT '测试项目ID',
                            `description` varchar(500) COMMENT '描述',
                            `create_time` varchar(50) NOT NULL COMMENT '创建时间',
                            `modify_time` varchar(50) NOT NULL COMMENT '修改时间',
                            primary key (suite_id)
)ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;
-- ------------------------
-- Records of Test_suite
-- ------------------------
INSERT INTO `Test_suite` VALUES (1, '测试套件一', '/User/mac/Project1/Module1/Suite1', 2, 1, '主要测试xxx..', '2019-05-02', '2019-06-23');
INSERT INTO `Test_suite` VALUES (2, '测试套件二', '/User/mac/Project1/Module2/suite2', 1, 3, '主要测试xxx..', '2019-03-18', '2019-03-21');
INSERT INTO `Test_suite` VALUES (3, '测试套件三', '/User/mac/Project3/Module3/suite3', 3, 2, '主要测试xxx..', '2019-03-18', '2019-03-21');

-- ---------------------------------
-- Table structure for Test_case
-- ---------------------------------
DROP TABLE IF EXISTS `Test_case`;
CREATE TABLE `Test_case` (
                            `case_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '测试用例的id',
                            `case_name` varchar(50) NOT NULL UNIQUE COMMENT '测试用例名称',
                            `suite_id` int(11) NOT NULL COMMENT '测试套件ID',
                            `pass_count` int(11) COMMENT '成功个数',
                            `fail_count` int(11) comment '失败个数',
                            `status` varchar(20) COMMENT '状态',
                            primary key (case_id)
)ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;
-- ------------------------
-- Records of Test_case
-- ------------------------
INSERT INTO `Test_case` VALUES (1, '测试用例一', 2, 3, 1, 'PASS');
INSERT INTO `Test_case` VALUES (2, '测试用例二', 1, 10, 4, 'PASS');
INSERT INTO `Test_case` VALUES (3, '测试用例三', 3, 8, 2, 'PASS');

-- ---------------------------------
-- Table structure for tag_info
-- ---------------------------------
DROP TABLE IF EXISTS `tag_info`;
CREATE TABLE `tag_info` (
                           `tag_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '测试用例的id',
                           `tag_name` varchar(50) NOT NULL COMMENT '测试用例名称',
                           `case_id` int(11) NOT NULL COMMENT '测试用例ID',
                           `suite_id` int(11) NOT NULL COMMENT '测试套件ID',
                           primary key (tag_id)
)ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;
-- ------------------------
-- Records of tag_info
-- ------------------------
INSERT INTO `tag_info` VALUES (1, '标签一', 2, 1);
INSERT INTO `tag_info` VALUES (2, '标签二', 1, 3);
INSERT INTO `tag_info` VALUES (3, '标签三', 3, 2);

-- ---------------------------------
-- Table structure for function_info
-- ---------------------------------
DROP TABLE IF EXISTS `function_info`;
CREATE TABLE `function_info` (
                          `function_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '函数的id',
                          `function_name` varchar(50) NOT NULL COMMENT '函数名称',
                          `type` int(4) NOT NULL COMMENT '类型，0为全局 1为局部',
                          `pyscript_id` int(11) NOT NULL COMMENT '所属脚本的ID',
                          primary key (function_id)
)ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;
-- ------------------------
-- Records of function_info
-- ------------------------
INSERT INTO `function_info` VALUES (1, '函数一', 1, 3);
INSERT INTO `function_info` VALUES (2, '函数二', 1, 3);
INSERT INTO `function_info` VALUES (3, '函数三', 1, 2);

-- ---------------------------------
-- Table structure for pyscript_info
-- ---------------------------------
DROP TABLE IF EXISTS `pyscript_info`;
CREATE TABLE `pyscript_info` (
                               `pyscript_id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'python脚本的id',
                               `pyscript_name` varchar(50) NOT NULL COMMENT 'python脚本名称',
                               `file_path` varchar(200) NOT NULL COMMENT '文件路径',
                               `testproject_id` int(11) COMMENT '所属测试工程的id',
                               `testmodule_id` int(11) COMMENT '所属模块的ID',
                               `suite_id` int(11) COMMENT '所属套件的id',
                               `type` int(4) NOT NULL COMMENT '类型，0为全局 1为局部',
                               `create_time` varchar(50) NOT NULL COMMENT '创建时间',
                               `modify_time` varchar(50) NOT NULL COMMENT '修改时间',
                               primary key (pyscript_id)
)ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;
-- ------------------------
-- Records of pyscript_info
-- ------------------------
INSERT INTO `pyscript_info` VALUES (1, '脚本一', '/User/mac/Documents/file1', 2, 3, 1, 1, '05-30','05-31');
INSERT INTO `pyscript_info` VALUES (2, '脚本二', '/User/mac/Documents/file2', 1, 3, 2, 1, '05-30','05-31');
INSERT INTO `pyscript_info` VALUES (3, '脚本三', '/User/mac/Documents/file3', 2, 1, 3, 1, '06-01','06-02');

DROP TABLE IF EXISTS `global_script`;
CREATE TABLE `global_script` (
                                  `globalscript_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '外部关键字的id',
                                  `globalscript_name` varchar(50) NOT NULL COMMENT '外部关键字名称',
                                  `section_id` int(11) NOT NULL COMMENT '所属部门ID',
                                  `create_time` varchar(50) NOT NULL COMMENT '创建时间',
                                  `modify_time` varchar(50) NOT NULL COMMENT '修改时间',
                                  `file_path` varchar(200) NOT NULL COMMENT '文件路径',
                                  primary key (globalscript_id)
)ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;
-- ------------------------
-- Records of external_keyword
-- ------------------------
INSERT INTO `global_script` VALUES (1, '全局脚本1.py', 1, '05-30', '05-31', '/User/mac/Documents/file1');
INSERT INTO `global_script` VALUES (2, '全局脚本2.py', 2, '06-01', '06-02', '/User/mac/Documents/file2');
INSERT INTO `global_script` VALUES (3, '全局脚本3.py', 3, '06-18', '07-09', '/User/mac/Documents/file3');



DROP TABLE IF EXISTS `external_keyword`;
CREATE TABLE `external_keyword` (
                               `exkeyword_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '外部关键字的id',
                               `exkeyword_name` varchar(50) NOT NULL COMMENT '外部关键字名称',
                               `section_id` int(11) NOT NULL COMMENT '所属部门ID',
                               `globalscript_id` int(11) NOT NULL COMMENT '对应脚本的ID',
                               primary key (exkeyword_id)
)ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;
-- ------------------------
-- Records of external_keyword
-- ------------------------
INSERT INTO `external_keyword` VALUES (1, '外部关键字一', 1, 1);
INSERT INTO `external_keyword` VALUES (2, '外部关键字二', 1, 2);
INSERT INTO `external_keyword` VALUES (3, '外部关键字三', 1, 1);

DROP TABLE IF EXISTS `buildin_keyword`;
CREATE TABLE `buildin_keyword` (
                                  `bldinkeyword_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '内部关键字的id',
                                  `bldinkeyword_name` varchar(50) NOT NULL COMMENT '内部关键字名称',
                                  `bldinkeyword_input` varchar(200) COMMENT '内部关键字入参',
                                  `bldinkeyword_output` varchar(200) COMMENT '内部关键字结果',
                                  primary key (bldinkeyword_id)
)ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;
-- ------------------------
-- Records of buildin_keyword
-- ------------------------
INSERT INTO `buildin_keyword` VALUES (1, '内部关键字一', '张三', 15);
INSERT INTO `buildin_keyword` VALUES (2, '内部关键字二', '李四', 21);
INSERT INTO `buildin_keyword` VALUES (3, '内部关键字三', '明明', 33);

DROP TABLE IF EXISTS `run_case_info`;
CREATE TABLE `run_case_info` (
                           `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '唯一标识',
                           `case_id` int(11) NOT NULL COMMENT '用例ID',
                           `suite_id` int(11) NOT NULL COMMENT '套件ID',
                           `user_id` int(11) NOT NULL COMMENT '测试人员ID',
                           `section_id` int(11) NOT NULL COMMENT '部门ID',
                           `case_result` varchar(20) NOT NULL COMMENT '用例结果',
                           `case_start_date` varchar(50) NOT NULL COMMENT '用例执行开始时间',
                           `case_end_date` varchar(50) NOT NULL COMMENT '用例执行结束时间',
                           PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;





