-- ----------------------------
-- 1、患者信息表
-- ----------------------------
CREATE TABLE IF NOT EXISTS `hospital_patient` (
  `patient_id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '患者ID',
  `patient_name` varchar(30) NOT NULL COMMENT '患者姓名',
  `gender` char(1) DEFAULT '0' COMMENT '患者性别（0男 1女 2未知）',
  `age` int(3) DEFAULT NULL COMMENT '年龄',
  `id_card` varchar(30) DEFAULT NULL COMMENT '身份证号',
  `phone` varchar(11) DEFAULT NULL COMMENT '联系电话',
  `address` varchar(200) DEFAULT NULL COMMENT '地址',
  `emergency_contact` varchar(30) DEFAULT NULL COMMENT '紧急联系人',
  `emergency_phone` varchar(11) DEFAULT NULL COMMENT '紧急联系人电话',
  `status` char(1) DEFAULT '0' COMMENT '就诊状态（0未就诊 1已分诊 2就诊中 3已就诊 4已取消）',
  `queue_number` varchar(20) DEFAULT NULL COMMENT '排队号码',
  `call_time` datetime DEFAULT NULL COMMENT '叫号时间',
  `dept_id` bigint(20) DEFAULT NULL COMMENT '科室ID',
  `doctor_id` bigint(20) DEFAULT NULL COMMENT '医生ID',
  `create_by` varchar(64) DEFAULT '' COMMENT '创建者',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(64) DEFAULT '' COMMENT '更新者',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `remark` varchar(500) DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`patient_id`)
) ENGINE=InnoDB AUTO_INCREMENT=100 DEFAULT CHARSET=utf8mb4 COMMENT='患者信息表';

-- ----------------------------
-- 2、初始化-患者字典数据
-- ----------------------------
-- 医院患者状态字典类型
INSERT INTO sys_dict_type(dict_name, dict_type, status, create_by, create_time, remark) 
VALUES ('患者状态', 'hospital_patient_status', '0', 'admin', NOW(), '医院患者就诊状态');

-- 医院患者状态字典数据
INSERT INTO sys_dict_data(dict_sort, dict_label, dict_value, dict_type, css_class, list_class, is_default, status, create_by, create_time, remark) 
VALUES (1, '未就诊', '0', 'hospital_patient_status', '', 'info', 'Y', '0', 'admin', NOW(), '未就诊状态');

INSERT INTO sys_dict_data(dict_sort, dict_label, dict_value, dict_type, css_class, list_class, is_default, status, create_by, create_time, remark) 
VALUES (2, '已分诊', '1', 'hospital_patient_status', '', 'primary', 'N', '0', 'admin', NOW(), '已分诊状态');

INSERT INTO sys_dict_data(dict_sort, dict_label, dict_value, dict_type, css_class, list_class, is_default, status, create_by, create_time, remark) 
VALUES (3, '就诊中', '2', 'hospital_patient_status', '', 'warning', 'N', '0', 'admin', NOW(), '就诊中状态');

INSERT INTO sys_dict_data(dict_sort, dict_label, dict_value, dict_type, css_class, list_class, is_default, status, create_by, create_time, remark) 
VALUES (4, '已就诊', '3', 'hospital_patient_status', '', 'success', 'N', '0', 'admin', NOW(), '已就诊状态');

INSERT INTO sys_dict_data(dict_sort, dict_label, dict_value, dict_type, css_class, list_class, is_default, status, create_by, create_time, remark) 
VALUES (5, '已取消', '4', 'hospital_patient_status', '', 'danger', 'N', '0', 'admin', NOW(), '已取消状态');
