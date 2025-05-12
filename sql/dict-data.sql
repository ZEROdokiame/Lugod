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
