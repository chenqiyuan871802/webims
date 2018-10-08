/*
Navicat MySQL Data Transfer

Source Server         : mycon
Source Server Version : 50162
Source Host           : 127.0.0.1:3306
Source Database       : webims

Target Server Type    : MYSQL
Target Server Version : 50162
File Encoding         : 65001

Date: 2018-10-08 11:03:53
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for `sys_dept`
-- ----------------------------
DROP TABLE IF EXISTS `sys_dept`;
CREATE TABLE `sys_dept` (
  `dept_id` varchar(50) NOT NULL COMMENT '流水号',
  `cascade_id` varchar(255) NOT NULL COMMENT '节点语义ID',
  `dept_name` varchar(100) NOT NULL COMMENT '组织名称',
  `parent_id` varchar(50) NOT NULL COMMENT '父节点流水号',
  `dept_code` varchar(50) DEFAULT NULL COMMENT '机构代码',
  `manager` varchar(50) DEFAULT NULL COMMENT '主要负责人',
  `phone` varchar(50) DEFAULT NULL COMMENT '部门电话',
  `fax` varchar(50) DEFAULT NULL COMMENT '传真',
  `address` varchar(200) DEFAULT NULL COMMENT '地址',
  `is_auto_expand` varchar(10) DEFAULT NULL COMMENT '是否自动展开',
  `icon_name` varchar(50) DEFAULT NULL COMMENT '节点图标文件名称',
  `sort_no` int(10) DEFAULT NULL COMMENT '排序号',
  `remark` varchar(400) DEFAULT NULL COMMENT '备注',
  `is_del` varchar(10) DEFAULT '0' COMMENT '是否已删除 0有效 1删除',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `create_by` varchar(50) DEFAULT NULL COMMENT '创建人ID',
  `update_time` datetime DEFAULT NULL COMMENT '修改时间',
  `update_by` varchar(50) DEFAULT NULL COMMENT '修改用户ID',
  PRIMARY KEY (`dept_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='组织机构';

-- ----------------------------
-- Records of sys_dept
-- ----------------------------
INSERT INTO `sys_dept` VALUES ('0', '0', '组织机构', '-1', null, '', '', '', '', '1', 'dept_config', '1', '顶级机构不能进行移动和删除操作，只能进行修改', '0', '2018-09-23 09:40:39', null, '2018-10-02 08:45:54', null);
INSERT INTO `sys_dept` VALUES ('0310d7c44ea346638ebfeaa919b8616d', '0.0001', '广州研发中心', '0', '', '', '13802907704', '', '', '0', null, '1', '232', '0', '2018-09-23 10:01:08', null, '2018-09-23 12:38:30', null);
INSERT INTO `sys_dept` VALUES ('96086bf7ae1c4de4835673873b682242', '0.0001.0001.0001', '北京研发中心', 'b3e7e87fb2074d7390587f45bf87acb5', '', '', '', '', '', '0', null, '2', '', '0', '2018-09-23 10:01:25', null, '2018-09-23 12:35:24', null);
INSERT INTO `sys_dept` VALUES ('b3e7e87fb2074d7390587f45bf87acb5', '0.0001.0001', '天河区', '0310d7c44ea346638ebfeaa919b8616d', '', '', '', '', '', '0', null, '1', '', '0', '2018-09-23 10:01:43', null, '2018-09-23 12:39:04', null);
INSERT INTO `sys_dept` VALUES ('ed06c91df4d24aadbc7f5dff9eb399e8', '0.0001.0001.0001.0001', '海淀区', '96086bf7ae1c4de4835673873b682242', '', '', '', '', '12312', '1', null, '1', '', '0', '2018-09-23 10:01:53', null, '2018-09-28 17:25:18', null);

-- ----------------------------
-- Table structure for `sys_dict`
-- ----------------------------
DROP TABLE IF EXISTS `sys_dict`;
CREATE TABLE `sys_dict` (
  `dict_id` varchar(50) NOT NULL COMMENT '字典编号',
  `dict_index_id` varchar(255) DEFAULT NULL COMMENT '所属字典流水号',
  `dict_code` varchar(100) DEFAULT NULL COMMENT '字典对照码',
  `dict_value` varchar(100) DEFAULT NULL COMMENT '字典对照值',
  `show_color` varchar(50) DEFAULT NULL COMMENT '显示颜色',
  `status` varchar(10) DEFAULT '1' COMMENT '当前状态(0:停用;1:启用)',
  `edit_mode` varchar(10) DEFAULT '1' COMMENT '编辑模式(0:只读;1:可编辑)',
  `sort_no` int(10) DEFAULT NULL COMMENT '排序号',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `create_by` varchar(50) DEFAULT NULL COMMENT '创建用户编号',
  `update_time` datetime DEFAULT NULL COMMENT '修改时间',
  `update_by` varchar(50) DEFAULT NULL COMMENT '修改用户ID',
  PRIMARY KEY (`dict_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='数据字典';

-- ----------------------------
-- Records of sys_dict
-- ----------------------------
INSERT INTO `sys_dict` VALUES ('08d76c1845ab425498810e716a8621e6', 'a9f98697527e452eaa4540e60ae98dc6', '0', '未发送', null, '1', '1', '1', '2017-05-03 12:44:21', 'cb33c25f5c664058a111a9b876152317', '2017-05-03 12:44:21', 'cb33c25f5c664058a111a9b876152317');
INSERT INTO `sys_dict` VALUES ('0a4de242d4cf4945818fdaf15d09ae7a', 'be4c44e7d83a4f508caa3e893e4c3360', '6', '就业服务中心-员工', null, '1', '1', '6', '2017-05-03 12:44:23', 'cb33c25f5c664058a111a9b876152317', '2017-05-03 12:44:23', 'cb33c25f5c664058a111a9b876152317');
INSERT INTO `sys_dict` VALUES ('116e56b0d56f46c8a955533bc7072fa8', 'a1a2e8d035934d978e44bbd965db743e', '5', '花生镇', null, '1', '1', '6', '2017-05-03 12:44:20', 'cb33c25f5c664058a111a9b876152317', '2017-05-03 12:44:20', 'cb33c25f5c664058a111a9b876152317');
INSERT INTO `sys_dict` VALUES ('11b823f3b2e14e76bf94347a4a5e578e', 'c48507ef391d4e3d8d9b7720efe4841b', '0', '停用', null, '1', '0', '1', '2017-05-03 12:44:22', null, '2017-05-03 12:44:22', null);
INSERT INTO `sys_dict` VALUES ('14e6a623eeb14fd786e3c51e667b89e5', 'be4c44e7d83a4f508caa3e893e4c3360', '11', '就业服务中心-个体户', null, '1', '1', '11', '2017-05-03 12:44:22', 'cb33c25f5c664058a111a9b876152317', '2017-05-03 12:44:22', 'cb33c25f5c664058a111a9b876152317');
INSERT INTO `sys_dict` VALUES ('1c3537c22d2b4f72a59d4fcc14940c1c', 'a9f98697527e452eaa4540e60ae98dc6', '1', '已发送', null, '1', '1', '2', '2017-05-03 12:44:22', 'cb33c25f5c664058a111a9b876152317', '2017-05-03 12:44:22', 'cb33c25f5c664058a111a9b876152317');
INSERT INTO `sys_dict` VALUES ('293adbde400f457a8d947ff5c6341b04', '992a7d6dbe7f4009b30cbae97c3b64a9', '3', '锁定', '#FFA500', '1', '1', '3', '2017-05-03 12:44:22', null, '2017-05-03 12:44:22', null);
INSERT INTO `sys_dict` VALUES ('2ac97527c4924127b742dd953d8b53ba', '820d2a68425b4d8d9b423b81d6a0eec1', '3', '未知', null, '1', '1', '3', '2017-05-03 12:44:22', null, '2017-05-03 12:44:22', null);
INSERT INTO `sys_dict` VALUES ('2bfc90a6917545cd87d73fb491292e2b', 'aaec0092a25b485f90c20898e9d6765d', '1', '缺省', null, '1', '1', '1', '2017-05-03 12:44:22', null, '2017-05-03 12:44:22', 'cb33c25f5c664058a111a9b876152317');
INSERT INTO `sys_dict` VALUES ('3010501d2d47432ab63c8ac40a9a5c0c', 'a1a2e8d035934d978e44bbd965db743e', '0', '全区', null, '1', '1', '1', '2017-05-03 12:44:21', 'cb33c25f5c664058a111a9b876152317', '2017-05-03 12:44:21', 'cb33c25f5c664058a111a9b876152317');
INSERT INTO `sys_dict` VALUES ('3cf6af08f48e4cec913d09f67a0b3b43', '992a7d6dbe7f4009b30cbae97c3b64a9', '1', '正常', null, '1', '1', '1', '2017-05-03 12:44:22', null, '2017-05-03 12:44:22', null);
INSERT INTO `sys_dict` VALUES ('3dbd1442f9c04ea0adfa164df1e09d87', 'be4c44e7d83a4f508caa3e893e4c3360', '10', '居住证审核', null, '1', '1', '10', '2017-05-03 12:44:22', 'cb33c25f5c664058a111a9b876152317', '2017-05-03 12:44:22', 'cb33c25f5c664058a111a9b876152317');
INSERT INTO `sys_dict` VALUES ('4f9d305c5b3f44c8851a6f4b3107ee8f', 'be4c44e7d83a4f508caa3e893e4c3360', '3', '住房保障-租房', null, '1', '1', '3', '2017-05-03 12:44:22', 'cb33c25f5c664058a111a9b876152317', '2017-05-03 12:44:22', 'cb33c25f5c664058a111a9b876152317');
INSERT INTO `sys_dict` VALUES ('54b51e8f289d47adbcf9620f1fac05f8', 'be4c44e7d83a4f508caa3e893e4c3360', '9', '职称', null, '1', '1', '9', '2017-05-03 12:44:22', 'cb33c25f5c664058a111a9b876152317', '2017-05-03 12:44:22', 'cb33c25f5c664058a111a9b876152317');
INSERT INTO `sys_dict` VALUES ('54caa61a5d354c678bb7858023ff3e6c', '97ec40cfa09f4531ad1c8485885fe57b', '2', '验证邮件', null, '1', '1', '2', '2017-05-03 12:44:22', 'cb33c25f5c664058a111a9b876152317', '2017-05-03 12:44:22', 'cb33c25f5c664058a111a9b876152317');
INSERT INTO `sys_dict` VALUES ('554db4b2b16949e6a22b15cd5bbec2c2', '99fd0f3f2d1a49c1acd97ea22415e4a8', '3', '按钮', '', '1', '1', '3', '2018-09-28 15:22:37', null, '2018-09-28 15:22:37', null);
INSERT INTO `sys_dict` VALUES ('5bab085587df4cfd9ed9896bc83adae0', '305966b8892244f99b56af75f91f3622', '2', '业务 ', '', '1', '1', '2', '2018-05-13 09:46:36', null, '2018-05-13 13:11:14', null);
INSERT INTO `sys_dict` VALUES ('5c26404f35284e7a9bdb373fa4518303', 'be4c44e7d83a4f508caa3e893e4c3360', '2', '计生', null, '1', '1', '2', '2017-05-03 12:44:21', 'cb33c25f5c664058a111a9b876152317', '2017-05-03 12:44:21', 'cb33c25f5c664058a111a9b876152317');
INSERT INTO `sys_dict` VALUES ('5e311168372a433288e49a259334e8d7', 'be4c44e7d83a4f508caa3e893e4c3360', '7', '住房保障- 产权房', null, '1', '1', '7', '2017-05-03 12:44:23', 'cb33c25f5c664058a111a9b876152317', '2017-05-03 12:44:23', 'cb33c25f5c664058a111a9b876152317');
INSERT INTO `sys_dict` VALUES ('78107194c65146249c31f82a0e565bea', '6d38696cb3f0457cb69831c3ef3b02b2', '0', '投递失败', null, '1', '1', '1', '2017-05-03 12:44:21', 'cb33c25f5c664058a111a9b876152317', '2017-05-03 12:44:21', 'cb33c25f5c664058a111a9b876152317');
INSERT INTO `sys_dict` VALUES ('7d56a647ebb14ff895c1dcffbe5539e9', 'a1a2e8d035934d978e44bbd965db743e', '7', '赤坭镇', null, '1', '1', '8', '2017-05-03 12:44:20', 'cb33c25f5c664058a111a9b876152317', '2017-05-03 12:44:20', 'cb33c25f5c664058a111a9b876152317');
INSERT INTO `sys_dict` VALUES ('7ef5043975894e1ba33a80e4773e7d15', '99fd0f3f2d1a49c1acd97ea22415e4a8', '2', '子菜单', '', '1', '1', '2', '2018-09-28 15:22:08', null, '2018-09-28 15:22:08', null);
INSERT INTO `sys_dict` VALUES ('82afb0bda8944af3a0e5f82608294670', '0bf2a3cd7ed44516a261347d47995411', '2', '顶部布局', null, '1', '1', '2', '2017-05-03 12:44:23', 'cb33c25f5c664058a111a9b876152317', '2017-05-03 12:44:23', 'cb33c25f5c664058a111a9b876152317');
INSERT INTO `sys_dict` VALUES ('8dfd45e6ccf94460b1b979c21d1b1806', '99fd0f3f2d1a49c1acd97ea22415e4a8', '1', '父菜单', '', '1', '1', '1', '2017-05-03 12:44:21', 'cb33c25f5c664058a111a9b876152317', '2018-09-28 15:21:55', 'cb33c25f5c664058a111a9b876152317');
INSERT INTO `sys_dict` VALUES ('913ca1b4b49a434fb9591f6df0a52af8', 'c6f8b99b95c844b89dc86c143e04a294', '0', '否', null, '1', '0', '1', '2017-05-03 12:44:23', null, '2017-05-03 12:44:23', null);
INSERT INTO `sys_dict` VALUES ('93e4051729ac4586979a52bd5617740f', '97ec40cfa09f4531ad1c8485885fe57b', '4', '定时邮件', null, '1', '1', '4', '2017-05-03 12:44:21', 'cb33c25f5c664058a111a9b876152317', '2017-05-03 12:44:21', 'cb33c25f5c664058a111a9b876152317');
INSERT INTO `sys_dict` VALUES ('948aef72716c48bbac2e3234a7470618', '40569802279947f8a807fbaa2852be19', '1', '系统', '', '1', '1', '1', '2018-05-13 23:00:42', null, '2018-05-13 23:00:42', null);
INSERT INTO `sys_dict` VALUES ('9c63657b98c444e3bfd8a0a75128de2b', '7a7faf68a5ec4f3cb9f45d89c119b26b', '0', '只读', null, '1', '0', '1', '2017-05-03 12:44:23', null, '2017-05-03 12:44:23', null);
INSERT INTO `sys_dict` VALUES ('9daf8f79e6684c2281f53a05c3b21c6e', '305966b8892244f99b56af75f91f3622', '1', '系统', '', '1', '1', '1', '2018-05-12 23:46:29', null, '2018-05-12 23:46:29', null);
INSERT INTO `sys_dict` VALUES ('9fc8ed31830c48a7a32f613bce856d3f', 'be4c44e7d83a4f508caa3e893e4c3360', '4', '社保', null, '1', '1', '4', '2017-05-03 12:44:22', 'cb33c25f5c664058a111a9b876152317', '2017-05-03 12:44:22', 'cb33c25f5c664058a111a9b876152317');
INSERT INTO `sys_dict` VALUES ('a5be19d6d85b424d9e719df8aa4cfdb5', 'be4c44e7d83a4f508caa3e893e4c3360', '1', '教育', null, '1', '1', '1', '2017-05-03 12:44:21', 'cb33c25f5c664058a111a9b876152317', '2017-05-03 12:44:21', 'cb33c25f5c664058a111a9b876152317');
INSERT INTO `sys_dict` VALUES ('a6b6cce2517943508e1cacde7bb486a8', 'a1a2e8d035934d978e44bbd965db743e', '9', '炭步镇', null, '1', '1', '10', '2017-05-03 12:44:21', 'cb33c25f5c664058a111a9b876152317', '2017-05-03 12:44:21', 'cb33c25f5c664058a111a9b876152317');
INSERT INTO `sys_dict` VALUES ('a96dfb72b7b54e1989569a2b3c5f90ac', 'c48507ef391d4e3d8d9b7720efe4841b', '1', '启用', null, '1', '0', '1', '2017-05-03 12:44:23', null, '2017-05-03 12:44:23', null);
INSERT INTO `sys_dict` VALUES ('ae623964bbb8431d8f62d2adf7784b28', '97ec40cfa09f4531ad1c8485885fe57b', '3', '业务邮件', null, '1', '1', '3', '2017-05-03 12:44:19', 'cb33c25f5c664058a111a9b876152317', '2017-05-03 12:44:19', 'cb33c25f5c664058a111a9b876152317');
INSERT INTO `sys_dict` VALUES ('afc719397e394d858c6207c480a0759f', '40569802279947f8a807fbaa2852be19', '2', '业务', '', '1', '1', '2', '2018-05-13 23:00:57', null, '2018-05-13 23:00:57', null);
INSERT INTO `sys_dict` VALUES ('b5d9c2328ce749a9913a7cf4def3b743', '6d38696cb3f0457cb69831c3ef3b02b2', '1', '投递成功', null, '1', '1', '1', '2017-05-03 12:44:21', 'cb33c25f5c664058a111a9b876152317', '2017-05-03 12:44:21', 'cb33c25f5c664058a111a9b876152317');
INSERT INTO `sys_dict` VALUES ('b98e939f0c1a4a658bce8cbcf5c0dbaa', 'a1a2e8d035934d978e44bbd965db743e', '3', '花城街', null, '1', '1', '4', '2017-05-03 12:44:21', 'cb33c25f5c664058a111a9b876152317', '2017-05-03 12:44:21', 'cb33c25f5c664058a111a9b876152317');
INSERT INTO `sys_dict` VALUES ('c15b3e8b29a0406c81a4318d7bfaba39', 'a1a2e8d035934d978e44bbd965db743e', '1', '新华街', null, '1', '1', '2', '2017-05-03 12:44:21', 'cb33c25f5c664058a111a9b876152317', '2017-05-03 12:44:21', 'cb33c25f5c664058a111a9b876152317');
INSERT INTO `sys_dict` VALUES ('c6ccaf5afb0d46dcbe1ed50815047291', 'be4c44e7d83a4f508caa3e893e4c3360', '8', '公安局', null, '1', '1', '8', '2017-05-03 12:44:22', 'cb33c25f5c664058a111a9b876152317', '2017-05-03 12:44:22', 'cb33c25f5c664058a111a9b876152317');
INSERT INTO `sys_dict` VALUES ('ca40ef37acef49f8930fcf22356ba50e', 'c6f8b99b95c844b89dc86c143e04a294', '1', '是', null, '1', '0', '2', '2017-05-03 12:44:23', null, '2017-05-03 12:44:23', null);
INSERT INTO `sys_dict` VALUES ('d2cf230ce49040e3bf6e61a972659c09', '992a7d6dbe7f4009b30cbae97c3b64a9', '2', '停用', 'red', '1', '1', '2', '2017-05-03 12:44:23', null, '2017-05-03 12:44:23', null);
INSERT INTO `sys_dict` VALUES ('d404e540aab945df84a26e3d30b2dd47', '820d2a68425b4d8d9b423b81d6a0eec1', '2', '女', null, '1', '1', '2', '2017-05-03 12:44:23', null, '2017-05-03 12:44:23', null);
INSERT INTO `sys_dict` VALUES ('d60b600511bd4f1eaeb72c1aab35d229', 'a1a2e8d035934d978e44bbd965db743e', '8', '狮岭镇', null, '1', '1', '9', '2017-05-03 12:44:21', 'cb33c25f5c664058a111a9b876152317', '2017-05-03 12:44:21', 'cb33c25f5c664058a111a9b876152317');
INSERT INTO `sys_dict` VALUES ('d7f0c4a5480d4dc4b3e6e4c5b405d9cb', '820d2a68425b4d8d9b423b81d6a0eec1', '1', '男', null, '1', '1', '1', '2017-05-03 12:44:23', null, '2017-05-03 12:44:23', null);
INSERT INTO `sys_dict` VALUES ('d8642fc74f5e4824b1254705285f1264', '97ec40cfa09f4531ad1c8485885fe57b', '1', '普通邮件', null, '1', '1', '1', '2017-05-03 12:44:23', 'cb33c25f5c664058a111a9b876152317', '2017-05-03 12:44:23', 'cb33c25f5c664058a111a9b876152317');
INSERT INTO `sys_dict` VALUES ('ddf8fbadb1fc40f49fede49655985db5', 'a1a2e8d035934d978e44bbd965db743e', '10', '梯面镇', null, '1', '1', '11', '2017-05-03 12:44:21', 'cb33c25f5c664058a111a9b876152317', '2017-05-03 12:44:21', 'cb33c25f5c664058a111a9b876152317');
INSERT INTO `sys_dict` VALUES ('e0e59a52f42c4263aac3e9dbbdb496df', '0bf2a3cd7ed44516a261347d47995411', '1', '经典风格', null, '1', '1', '1', '2017-05-03 12:44:23', 'cb33c25f5c664058a111a9b876152317', '2017-05-03 12:44:23', 'cb33c25f5c664058a111a9b876152317');
INSERT INTO `sys_dict` VALUES ('e5adcaa5d9cb4612abd14bd935080b50', 'be4c44e7d83a4f508caa3e893e4c3360', '5', '医保', null, '1', '1', '5', '2017-05-03 12:44:23', 'cb33c25f5c664058a111a9b876152317', '2017-05-03 12:44:23', 'cb33c25f5c664058a111a9b876152317');
INSERT INTO `sys_dict` VALUES ('e664bd31823a4384ac3f3ce5938094cd', 'a1a2e8d035934d978e44bbd965db743e', '4', '秀全街', null, '1', '1', '5', '2017-05-03 12:44:20', 'cb33c25f5c664058a111a9b876152317', '2017-05-03 12:44:20', 'cb33c25f5c664058a111a9b876152317');
INSERT INTO `sys_dict` VALUES ('e823709cdff74b1dac9d6a3204af515f', 'a1a2e8d035934d978e44bbd965db743e', '11', '其他区', null, '1', '1', '12', '2017-05-03 12:44:21', 'cb33c25f5c664058a111a9b876152317', '2017-05-03 12:44:21', 'cb33c25f5c664058a111a9b876152317');
INSERT INTO `sys_dict` VALUES ('ee53ff692f384230b7da89eeef28f2c1', 'a1a2e8d035934d978e44bbd965db743e', '6', '花东镇', null, '1', '1', '7', '2017-05-03 12:44:20', 'cb33c25f5c664058a111a9b876152317', '2017-05-03 12:44:20', 'cb33c25f5c664058a111a9b876152317');
INSERT INTO `sys_dict` VALUES ('f1c0ae8844504f96836b904ce81ac1bc', '7a7faf68a5ec4f3cb9f45d89c119b26b', '1', '可编辑', null, '1', '0', '2', '2017-05-03 12:44:23', null, '2017-05-03 12:44:23', null);
INSERT INTO `sys_dict` VALUES ('f4309055e8dd450489a85d37b2a4003f', 'a1a2e8d035934d978e44bbd965db743e', '2', '新雅街', null, '1', '1', '3', '2017-05-03 12:44:21', 'cb33c25f5c664058a111a9b876152317', '2017-05-03 12:44:21', 'cb33c25f5c664058a111a9b876152317');

-- ----------------------------
-- Table structure for `sys_dict_index`
-- ----------------------------
DROP TABLE IF EXISTS `sys_dict_index`;
CREATE TABLE `sys_dict_index` (
  `dict_index_id` varchar(50) NOT NULL COMMENT '流水号',
  `dict_key` varchar(50) DEFAULT NULL COMMENT '字典标识',
  `dict_name` varchar(100) DEFAULT NULL COMMENT '字典名称',
  `dict_type` varchar(10) DEFAULT '1' COMMENT '字典分类 1系统2业务',
  `dict_remark` varchar(500) DEFAULT NULL COMMENT '备注',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `create_by` varchar(50) DEFAULT NULL COMMENT '创建用户编号',
  `update_time` datetime DEFAULT NULL COMMENT '修改时间',
  `update_by` varchar(50) DEFAULT NULL COMMENT '修改用户ID',
  PRIMARY KEY (`dict_index_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='数据字典索引表';

-- ----------------------------
-- Records of sys_dict_index
-- ----------------------------
INSERT INTO `sys_dict_index` VALUES ('0bf2a3cd7ed44516a261347d47995411', 'layout_style', '界面布局', '1', null, '2017-05-03 12:32:40', 'cb33c25f5c664058a111a9b876152317', '2017-05-03 12:32:40', 'cb33c25f5c664058a111a9b876152317');
INSERT INTO `sys_dict_index` VALUES ('305966b8892244f99b56af75f91f3622', 'dict_type', '字典分类', '1', '', '2018-05-12 23:16:40', null, '2018-05-13 13:10:35', null);
INSERT INTO `sys_dict_index` VALUES ('40569802279947f8a807fbaa2852be19', 'param_type', '参数分类', '1', '', '2018-05-13 23:00:28', null, '2018-05-13 23:00:28', null);
INSERT INTO `sys_dict_index` VALUES ('6d38696cb3f0457cb69831c3ef3b02b2', 'email_status', '投递状态', '1', null, '2017-05-03 12:32:40', 'cb33c25f5c664058a111a9b876152317', '2017-05-03 12:32:40', 'cb33c25f5c664058a111a9b876152317');
INSERT INTO `sys_dict_index` VALUES ('7a7faf68a5ec4f3cb9f45d89c119b26b', 'edit_mode', '编辑模式', '1', null, '2017-05-03 12:32:40', null, '2017-05-03 12:32:40', null);
INSERT INTO `sys_dict_index` VALUES ('820d2a68425b4d8d9b423b81d6a0eec1', 'sex', '性别', '1', null, '2017-05-03 12:32:40', null, null, null);
INSERT INTO `sys_dict_index` VALUES ('97ec40cfa09f4531ad1c8485885fe57b', 'email_type', '邮件类型', '1', null, '2017-05-03 12:32:40', 'cb33c25f5c664058a111a9b876152317', '2017-05-03 12:32:40', 'cb33c25f5c664058a111a9b876152317');
INSERT INTO `sys_dict_index` VALUES ('992a7d6dbe7f4009b30cbae97c3b64a9', 'user_status', '用户状态', '1', null, '2017-05-03 12:32:40', null, null, null);
INSERT INTO `sys_dict_index` VALUES ('99fd0f3f2d1a49c1acd97ea22415e4a8', 'menu_type', '菜单类型', '1', null, '2017-05-03 12:32:40', 'cb33c25f5c664058a111a9b876152317', '2017-05-03 12:32:40', 'cb33c25f5c664058a111a9b876152317');
INSERT INTO `sys_dict_index` VALUES ('a1a2e8d035934d978e44bbd965db743e', 'street_type', '街镇', '1', null, '2017-05-03 12:32:40', 'cb33c25f5c664058a111a9b876152317', '2017-05-03 12:32:40', 'cb33c25f5c664058a111a9b876152317');
INSERT INTO `sys_dict_index` VALUES ('a9f98697527e452eaa4540e60ae98dc6', 'is_send', '是否发送', '1', null, '2017-05-03 12:32:40', 'cb33c25f5c664058a111a9b876152317', '2017-05-03 12:32:40', 'cb33c25f5c664058a111a9b876152317');
INSERT INTO `sys_dict_index` VALUES ('aaec0092a25b485f90c20898e9d6765d', 'role_type', '角色类型', '1', null, '2017-05-03 12:32:40', null, null, null);
INSERT INTO `sys_dict_index` VALUES ('be4c44e7d83a4f508caa3e893e4c3360', 'unit_type', '职能单位类型', '1', null, '2017-05-03 12:32:40', 'cb33c25f5c664058a111a9b876152317', '2017-05-03 12:32:40', 'cb33c25f5c664058a111a9b876152317');
INSERT INTO `sys_dict_index` VALUES ('c48507ef391d4e3d8d9b7720efe4841b', 'status', '当前状态', '1', null, '2017-05-03 12:32:40', null, '2017-05-03 12:32:40', null);
INSERT INTO `sys_dict_index` VALUES ('c6f8b99b95c844b89dc86c143e04a294', 'is_auto_expand', '是否自动展开', '1', null, '2017-05-03 12:32:40', null, '2017-05-03 12:32:40', null);

-- ----------------------------
-- Table structure for `sys_menu`
-- ----------------------------
DROP TABLE IF EXISTS `sys_menu`;
CREATE TABLE `sys_menu` (
  `menu_id` varchar(50) NOT NULL DEFAULT '' COMMENT '菜单编号',
  `cascade_id` varchar(500) DEFAULT NULL COMMENT '分类科目语义ID',
  `menu_name` varchar(100) DEFAULT NULL COMMENT '菜单名称',
  `menu_code` varchar(100) DEFAULT NULL COMMENT '菜单编码',
  `menu_type` varchar(10) DEFAULT '1' COMMENT '菜单类型 1父级菜单2子菜单3按钮',
  `parent_id` varchar(50) DEFAULT NULL COMMENT '菜单父级编号',
  `icon_name` varchar(50) DEFAULT NULL COMMENT '图标名称',
  `is_auto_expand` varchar(10) DEFAULT '0' COMMENT '是否自动展开',
  `url` varchar(100) DEFAULT NULL COMMENT 'url地址',
  `remark` varchar(500) DEFAULT NULL COMMENT '备注',
  `status` varchar(10) DEFAULT '1' COMMENT '当前状态(0:停用;1:启用)',
  `edit_mode` varchar(10) DEFAULT '1' COMMENT '编辑模式(0:只读;1:可编辑)',
  `sort_no` int(10) DEFAULT NULL COMMENT '排序号',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `create_by` varchar(50) DEFAULT NULL COMMENT '创建人',
  `update_time` datetime DEFAULT NULL COMMENT '修改时间',
  `update_by` varchar(50) DEFAULT NULL COMMENT '更新用户',
  PRIMARY KEY (`menu_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='菜单配置';

-- ----------------------------
-- Records of sys_menu
-- ----------------------------
INSERT INTO `sys_menu` VALUES ('69c73a3c4c10415dab6282eb138271ec', '0.0001.0001', '组织机构', 'dept', '2', '803e9557efe4475c8739d3c321258def', 'fa fa-sitemap', '1', 'system/dept/init', '', '1', '1', '1', '2018-10-07 18:14:43', null, '2018-10-07 22:23:16', null);
INSERT INTO `sys_menu` VALUES ('7a09b4ea8f384332bb1c1dad71bb84e8', '0.0001.0003', '角色管理', 'role', '2', '803e9557efe4475c8739d3c321258def', 'fa fa-paw', '1', 'system/role/init', '', '1', '1', '3', '2018-10-07 18:16:24', null, '2018-10-07 22:24:11', null);
INSERT INTO `sys_menu` VALUES ('803e9557efe4475c8739d3c321258def', '0.0001', '系统管理', 'systemMange', '1', '0', 'fa fa-desktop', '1', null, '', '1', '1', '1', '2018-09-28 18:30:41', null, '2018-10-07 22:20:06', null);
INSERT INTO `sys_menu` VALUES ('acb483393285432494e5a5f1524822ee', '0.0001.0002', '用户管理', 'user', '2', '803e9557efe4475c8739d3c321258def', 'fa fa-user', '1', 'system/user/init', '', '1', '1', '2', '2018-10-07 18:15:19', null, '2018-10-07 22:23:49', null);
INSERT INTO `sys_menu` VALUES ('b27f57e1361e49259f596fa773d34198', '0.0001.0005', '通用字典', 'dict', '2', '803e9557efe4475c8739d3c321258def', 'fa fa-book', '1', 'system/dict/init', '', '1', '1', '5', '2018-10-07 18:17:41', null, '2018-10-07 22:21:28', null);
INSERT INTO `sys_menu` VALUES ('cacda1579dcb49eb972eed5256715b78', '0.0001.0004', '菜单管理', 'menu', '2', '803e9557efe4475c8739d3c321258def', 'fa fa-navicon', '1', 'system/menu/init', '', '1', '1', '4', '2018-10-07 18:16:56', null, '2018-10-07 22:22:50', null);
INSERT INTO `sys_menu` VALUES ('cae18585cbec48018042a506beda1ef9', '0.0001.0006', '键值参数', 'param', '2', '803e9557efe4475c8739d3c321258def', 'fa fa-leaf', '1', 'system/param/init', '', '1', '1', '6', '2018-10-07 18:18:25', null, '2018-10-07 22:24:58', null);

-- ----------------------------
-- Table structure for `sys_param`
-- ----------------------------
DROP TABLE IF EXISTS `sys_param`;
CREATE TABLE `sys_param` (
  `param_id` varchar(50) NOT NULL DEFAULT '' COMMENT '参数编号',
  `param_name` varchar(100) DEFAULT NULL COMMENT '参数名称',
  `param_key` varchar(50) DEFAULT NULL COMMENT '参数键名',
  `param_value` varchar(500) DEFAULT NULL COMMENT '参数键值',
  `param_remark` varchar(200) DEFAULT NULL COMMENT '参数备注',
  `param_type` varchar(10) DEFAULT '0' COMMENT '参数类型0:缺省;1:系统2:业务',
  `status` varchar(10) DEFAULT '1' COMMENT '当前状态(0:停用;1:启用)',
  `edit_mode` varchar(10) DEFAULT '1' COMMENT '编辑模式(0:只读;1:可编辑)',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `create_by` varchar(50) DEFAULT NULL COMMENT '创建用户',
  `update_time` datetime DEFAULT NULL COMMENT '修改时间',
  `update_by` varchar(50) DEFAULT NULL COMMENT '修改用户',
  PRIMARY KEY (`param_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='键值参数';

-- ----------------------------
-- Records of sys_param
-- ----------------------------
INSERT INTO `sys_param` VALUES ('48d61efcf98845fb833ca78bc955236c', '支付通知模板编号', 'pay_nodify_key', 'KU58D23fGtmA10KgDPnATX-7yNU48P9tVz2YXbEMTgE', '', '', '1', '1', '2017-06-20 01:46:50', 'cb33c25f5c664058a111a9b876152317', '2017-07-14 00:05:54', 'cb33c25f5c664058a111a9b876152317');
INSERT INTO `sys_param` VALUES ('4c8d5cfe75ae4ed5a87895f2237a1e2f', '订单IP地址', 'order_ip', '127.0.0.1', '订单产生IP地址', '', '1', '1', '2017-06-04 10:50:30', 'cb33c25f5c664058a111a9b876152317', '2017-06-04 10:50:30', 'cb33c25f5c664058a111a9b876152317');
INSERT INTO `sys_param` VALUES ('6010b7ea9a7f4bc7a908c02612e0ac9f', '自动回复信息', 'response_msg', '欢迎关注黄埔区来穗人员服务管理局', '', '', '1', '1', '2017-06-16 00:49:22', 'cb33c25f5c664058a111a9b876152317', '2017-07-03 22:27:37', 'cb33c25f5c664058a111a9b876152317');
INSERT INTO `sys_param` VALUES ('65693df209e84166b8f2015005051349', '礼包验证码键', 'bag_check_code1', 'SMS_712954051', '11111', '1', '1', '0', '2017-06-29 20:53:40', 'cb33c25f5c664058a111a9b876152317', '2018-05-14 01:40:35', 'cb33c25f5c664058a111a9b876152317');
INSERT INTO `sys_param` VALUES ('674b1071d90e42f4adc8e0ca011d78c1', '预约通知模板编号', 'subscribe_message_key', '1H0RB_Ti5Re_lGvZI0Oan49u42ym6FYrSLjRCkAj7JE', null, '', '1', '1', '2017-06-20 01:46:27', 'cb33c25f5c664058a111a9b876152317', '2017-06-20 01:46:27', 'cb33c25f5c664058a111a9b876152317');
INSERT INTO `sys_param` VALUES ('6a66db3230884d0bad1e46cabf2148ec', '可取消预定时间', 'cancel_time', '60', null, '', '1', '0', '2017-05-11 22:47:57', 'cb33c25f5c664058a111a9b876152317', '2017-07-01 21:54:47', 'cb33c25f5c664058a111a9b876152317');
INSERT INTO `sys_param` VALUES ('6cd477c4b2bc405c864b482d4e73dca8', '每两小时预约上限', 'every_two_limit', '1', null, '', '1', '0', '2017-05-11 22:47:57', 'cb33c25f5c664058a111a9b876152317', '2017-07-01 21:54:47', 'cb33c25f5c664058a111a9b876152317');
INSERT INTO `sys_param` VALUES ('73a3eeafd93e48a186b47ca2f233e6b5', '预约定金', 'subscribe_deposit', '0.01', null, '', '1', '0', '2017-05-11 22:47:57', 'cb33c25f5c664058a111a9b876152317', '2017-07-01 21:54:46', 'cb33c25f5c664058a111a9b876152317');
INSERT INTO `sys_param` VALUES ('788260e3e4fb4914bfb692279e20250a', '微信欢迎消息', 'welcome_msg', '欢迎关注黄埔区来穗人员服务管理局', '', '', '1', '1', '2017-04-29 15:04:45', 'cb33c25f5c664058a111a9b876152317', '2017-07-03 22:28:00', 'cb33c25f5c664058a111a9b876152317');
INSERT INTO `sys_param` VALUES ('798b6131843f42b59fcc96845c6794f6', '验证码有效时间', 'check_code_vaild', '10', ' 验证码有效时间 单位分钟', '', '1', '1', '2017-05-20 23:54:57', 'cb33c25f5c664058a111a9b876152317', '2017-05-20 23:54:57', 'cb33c25f5c664058a111a9b876152317');
INSERT INTO `sys_param` VALUES ('79d2aef92bb848fc9e810d7c6617cf1c', '取消按钮2', 'cancel_btn', 'on', null, '0', '1', '1', null, null, null, null);
INSERT INTO `sys_param` VALUES ('8dc4edfe1b9443d8a7b4d64a8a676730', '兑换码生成位数', 'cdkey_num', '8', '', '', '1', '1', '2017-05-15 22:31:31', 'cb33c25f5c664058a111a9b876152317', '2017-05-15 22:37:05', 'cb33c25f5c664058a111a9b876152317');
INSERT INTO `sys_param` VALUES ('919bfc40932e45018f8aa319187ae35f', '微信支付开关', 'wechat_pay', 'off', '微信支付开关 on 打开在填写opendid,off在测试', '', '1', '1', '2017-06-04 10:49:47', 'cb33c25f5c664058a111a9b876152317', '2017-07-01 21:54:38', 'cb33c25f5c664058a111a9b876152317');
INSERT INTO `sys_param` VALUES ('9bbeacb155174ea88fe6b6f113ce0457', '短信网关', 'sms_url', 'http://gw.api.taobao.com/router/rest', null, '', '1', '0', '2017-05-11 22:44:34', 'cb33c25f5c664058a111a9b876152317', '2017-07-02 10:15:40', 'cb33c25f5c664058a111a9b876152317');
INSERT INTO `sys_param` VALUES ('aedaf6f528014f8cbc158b07e9a8bf07', '短信模板的键', 'template_key', 'name', null, '', '1', '1', '2017-05-13 00:55:12', 'cb33c25f5c664058a111a9b876152317', '2017-05-13 00:55:12', 'cb33c25f5c664058a111a9b876152317');
INSERT INTO `sys_param` VALUES ('b4c234d0873f44efa1f6d231f8a69d30', '商家端系统标题', 'shop_sys_title', '美容管理商家端平台', null, '', '1', '1', '2017-05-06 15:19:49', 'cb33c25f5c664058a111a9b876152317', '2017-05-06 15:19:49', 'cb33c25f5c664058a111a9b876152317');
INSERT INTO `sys_param` VALUES ('bc11f098150c46a694ba44aac0282a52', '验证码模板键', 'check_code_key', 'checkCode', null, '', '1', '1', '2017-05-21 00:02:46', 'cb33c25f5c664058a111a9b876152317', '2017-05-21 00:02:46', 'cb33c25f5c664058a111a9b876152317');
INSERT INTO `sys_param` VALUES ('bc663ec2277e487280d547d79f1def9c', '验证码短信模板', 'check_sms_code', 'SMS_71295405', '使用同一个签名，对同一个手机号码发送短信验证码，支持1条/分钟，5条/小时，10条/天。一个手机号码通过阿里大于平台只能收到40条/天', '', '1', '1', '2017-05-20 23:49:10', 'cb33c25f5c664058a111a9b876152317', '2017-07-02 10:02:26', 'cb33c25f5c664058a111a9b876152317');
INSERT INTO `sys_param` VALUES ('c6712a77e35d48489ae6827929232beb', '短信签名', 'sms_signe', '美妍社移动应用发布', null, '', '1', '0', '2017-05-11 22:44:35', 'cb33c25f5c664058a111a9b876152317', '2017-07-02 10:15:41', 'cb33c25f5c664058a111a9b876152317');
INSERT INTO `sys_param` VALUES ('d517cdc7b49f4cc8b93f4ca07c332bc2', '美研币过期时间', 'beauty_overtime', '20', null, '', '1', '1', '2017-04-26 23:18:32', 'cb33c25f5c664058a111a9b876152317', '2017-07-29 09:07:19', 'cb33c25f5c664058a111a9b876152317');
INSERT INTO `sys_param` VALUES ('d5c2f39ab78e4b0eae83497db6eefed1', '短信AppKey', 'sms_app_key', '23812181', null, '', '1', '0', '2017-05-11 22:44:34', 'cb33c25f5c664058a111a9b876152317', '2017-07-02 10:15:41', 'cb33c25f5c664058a111a9b876152317');
INSERT INTO `sys_param` VALUES ('e0a180760b4b46f7ae12f09edf8f4514', '系统访问地址', 'request_url', 'http://toonan.ngrok.xiaomiqiu.cn/BMS', '', '', '1', '1', '2017-05-20 00:32:28', 'cb33c25f5c664058a111a9b876152317', '2017-08-13 17:44:18', 'cb33c25f5c664058a111a9b876152317');
INSERT INTO `sys_param` VALUES ('f19c714cc52c4113aab468cb90d0158a', '人民币兑换', 'exchange_beauty', '1', '一元人民币兑换多少个美研币', '', '1', '1', '2017-04-26 23:17:49', 'cb33c25f5c664058a111a9b876152317', '2017-07-29 09:07:18', 'cb33c25f5c664058a111a9b876152317');
INSERT INTO `sys_param` VALUES ('f36a07b39af64aa4a14bd3176b18191f', '短信AppSecret', 'sms_app_secret', 'e0c94368229a2383f0723ac381e1d895', null, '', '1', '0', '2017-05-11 22:44:34', 'cb33c25f5c664058a111a9b876152317', '2017-07-02 10:15:41', 'cb33c25f5c664058a111a9b876152317');
INSERT INTO `sys_param` VALUES ('ff0fa340423e4eee8dd04457873a402a', '短信模板编号', 'sms_template_code', 'SMS_66795136', null, '', '1', '0', '2017-05-11 22:44:35', 'cb33c25f5c664058a111a9b876152317', '2017-07-02 10:15:41', 'cb33c25f5c664058a111a9b876152317');

-- ----------------------------
-- Table structure for `sys_role`
-- ----------------------------
DROP TABLE IF EXISTS `sys_role`;
CREATE TABLE `sys_role` (
  `role_id` varchar(50) NOT NULL COMMENT ' 流水号',
  `role_name` varchar(100) NOT NULL COMMENT '角色名称',
  `status` varchar(10) DEFAULT '1' COMMENT '当前状态 1启用 0禁用',
  `role_type` varchar(10) DEFAULT '1' COMMENT '角色类型',
  `role_remark` varchar(400) DEFAULT NULL COMMENT '备注',
  `edit_mode` varchar(10) DEFAULT '1' COMMENT '编辑模式(0:只读;1:可编辑)',
  `create_by` varchar(50) DEFAULT NULL COMMENT '创建用户编号',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(50) DEFAULT NULL COMMENT '修改用户ID',
  `update_time` datetime DEFAULT NULL COMMENT '修改时间',
  PRIMARY KEY (`role_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='角色表';

-- ----------------------------
-- Records of sys_role
-- ----------------------------
INSERT INTO `sys_role` VALUES ('83abaf5c82f443d895345c9b23e03938', '管理员角色', '1', '1', '', '1', 'cb33c25f5c664058a111a9b876152317', '2018-07-14 07:31:13', 'cb33c25f5c664058a111a9b876152317', '2018-10-07 21:53:37');

-- ----------------------------
-- Table structure for `sys_role_menu`
-- ----------------------------
DROP TABLE IF EXISTS `sys_role_menu`;
CREATE TABLE `sys_role_menu` (
  `role_menu_id` varchar(50) NOT NULL DEFAULT '' COMMENT '角色与菜单关联编号',
  `role_id` varchar(50) NOT NULL COMMENT ' 角色流水号',
  `menu_id` varchar(50) NOT NULL COMMENT '功能模块流水号',
  `grant_type` varchar(10) DEFAULT NULL COMMENT '权限类型 1 经办权限 2管理权限',
  `create_user_id` varchar(50) DEFAULT NULL COMMENT '创建人ID',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`role_menu_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='菜单模块-角色关联表';

-- ----------------------------
-- Records of sys_role_menu
-- ----------------------------
INSERT INTO `sys_role_menu` VALUES ('37ac6ebc640d4b3f9a034a55a8140576', '83abaf5c82f443d895345c9b23e03938', 'cae18585cbec48018042a506beda1ef9', null, null, '2018-10-07 21:53:53');
INSERT INTO `sys_role_menu` VALUES ('39e97eba81084d2f805a7c1244fc4fef', '83abaf5c82f443d895345c9b23e03938', '803e9557efe4475c8739d3c321258def', null, null, '2018-10-07 21:53:53');
INSERT INTO `sys_role_menu` VALUES ('51a04225316243a5bf300425aec4a66e', '83abaf5c82f443d895345c9b23e03938', '69c73a3c4c10415dab6282eb138271ec', null, null, '2018-10-07 21:53:53');
INSERT INTO `sys_role_menu` VALUES ('5869573593d2431c89c2d81a031ea8bf', '83abaf5c82f443d895345c9b23e03938', 'cacda1579dcb49eb972eed5256715b78', null, null, '2018-10-07 21:53:53');
INSERT INTO `sys_role_menu` VALUES ('6b0cb208abfb4f7c9080d258b737a1d9', '83abaf5c82f443d895345c9b23e03938', 'acb483393285432494e5a5f1524822ee', null, null, '2018-10-07 21:53:53');
INSERT INTO `sys_role_menu` VALUES ('ee206c75fd0140aea58793326da2b68f', '83abaf5c82f443d895345c9b23e03938', '0', null, null, '2018-10-07 21:53:53');

-- ----------------------------
-- Table structure for `sys_role_user`
-- ----------------------------
DROP TABLE IF EXISTS `sys_role_user`;
CREATE TABLE `sys_role_user` (
  `role_user_id` varchar(50) NOT NULL DEFAULT '' COMMENT '角色与用户编号',
  `role_id` varchar(50) NOT NULL COMMENT '角色编号',
  `user_id` varchar(50) NOT NULL COMMENT '用户编号',
  `create_user_id` varchar(50) DEFAULT NULL COMMENT '创建用户编号',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`role_user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='角色与用户关联';

-- ----------------------------
-- Records of sys_role_user
-- ----------------------------
INSERT INTO `sys_role_user` VALUES ('', '83abaf5c82f443d895345c9b23e03938', '0e40390678954a778db4bef3bcf4eb7f', 'cb33c25f5c664058a111a9b876152317', '2018-07-14 07:42:02');
INSERT INTO `sys_role_user` VALUES ('eaceb29e0f09427ea36279a64fbefae2', '83abaf5c82f443d895345c9b23e03938', '21948e6d7cdf404fb0a5a320a86c3144', null, '2018-10-07 17:19:40');

-- ----------------------------
-- Table structure for `sys_user`
-- ----------------------------
DROP TABLE IF EXISTS `sys_user`;
CREATE TABLE `sys_user` (
  `user_id` varchar(50) NOT NULL COMMENT '用户编号',
  `account` varchar(50) NOT NULL COMMENT '用户登录帐号',
  `password` varchar(50) NOT NULL COMMENT '密码',
  `username` varchar(50) NOT NULL COMMENT '用户姓名',
  `lock_num` int(10) DEFAULT '5' COMMENT '锁定次数 默认5次',
  `error_num` int(10) DEFAULT '0' COMMENT '密码错误次数  如果等于锁定次数就自动锁定用户',
  `sex` varchar(10) DEFAULT '3' COMMENT '性别  1:男2:女3:未知',
  `status` varchar(10) DEFAULT '1' COMMENT '用户状态 1:正常2:停用 3:锁定',
  `user_type` varchar(10) DEFAULT '1' COMMENT '用户类型',
  `dept_id` varchar(50) DEFAULT NULL COMMENT '所属部门流水号',
  `mobile` varchar(50) DEFAULT NULL COMMENT '联系电话',
  `qq` varchar(50) DEFAULT NULL COMMENT 'QQ号码',
  `wechat` varchar(50) DEFAULT NULL COMMENT '微信',
  `email` varchar(50) DEFAULT NULL COMMENT '电子邮件',
  `idno` varchar(50) DEFAULT NULL COMMENT '身份证号',
  `style` varchar(10) DEFAULT '1' COMMENT '界面风格',
  `address` varchar(200) DEFAULT NULL COMMENT '联系地址',
  `remark` varchar(400) DEFAULT NULL COMMENT '备注',
  `is_del` varchar(10) DEFAULT '0' COMMENT '是否已删除 0有效 1删除',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `create_by` varchar(50) DEFAULT NULL COMMENT '创建人ID',
  `update_time` datetime DEFAULT NULL COMMENT '修改时间',
  `update_by` varchar(50) DEFAULT NULL COMMENT '修改用户编号',
  PRIMARY KEY (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='用户基本信息表';

-- ----------------------------
-- Records of sys_user
-- ----------------------------
INSERT INTO `sys_user` VALUES ('1addfa4c51ef4b12bf1061ce9d20523c', 'test1', 'zXE7E4Tqd5k=', 'tet1', '5', '0', '3', '1', '1', '0', '', '', '', '', '', '1', '', '', '1', '2018-09-28 14:14:50', null, '2018-09-28 14:16:43', null);
INSERT INTO `sys_user` VALUES ('21948e6d7cdf404fb0a5a320a86c3144', 'system', 'WkjHmUoP3mE=', '超级管理员', '5', '0', '3', '1', '1', '96086bf7ae1c4de4835673873b682242', '13802907704', '240823329', '', '240823329@qq.com', '450981198407303917', '1', '广州市', '百分23322', '0', '2018-09-28 13:24:10', null, '2018-10-07 22:27:31', '21948e6d7cdf404fb0a5a320a86c3144');
INSERT INTO `sys_user` VALUES ('cb33c25f5c664058a111a9b876152317', 'super', '0d+ywCe6ffI=', '超级用户', '10', '0', '1', '1', '2', '0', '13802907704', '240823329', '', '240823329@qq.com', '', '1', '', '超级用户，拥有最高的权限', '0', '2017-01-15 09:47:46', null, '2018-10-07 22:12:32', 'cb33c25f5c664058a111a9b876152317');
INSERT INTO `sys_user` VALUES ('edbf22ceaab743cebad380ab89d12551', 'test2', 'zXE7E4Tqd5k=', 'test2', '5', '0', '3', '1', '1', '0', '', '', '', '', '', '1', '', '', '1', '2018-09-28 14:15:12', null, '2018-09-28 14:16:43', null);
