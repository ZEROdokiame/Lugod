import request from '@/utils/request'

// 查询患者列表
export function listPatient(query) {
  return request({
    url: '/hospital/patient/list',
    method: 'get',
    params: query
  })
}

// 查询患者详细
export function getPatient(patientId) {
  return request({
    url: '/hospital/patient/' + patientId,
    method: 'get'
  })
}

// 新增患者
export function addPatient(data) {
  return request({
    url: '/hospital/patient',
    method: 'post',
    data: data
  })
}

// 修改患者
export function updatePatient(data) {
  return request({
    url: '/hospital/patient',
    method: 'put',
    data: data
  })
}

// 删除患者
export function delPatient(patientId) {
  return request({
    url: '/hospital/patient/' + patientId,
    method: 'delete'
  })
}

// 导出患者
export function exportPatient(query) {
  return request({
    url: '/hospital/patient/export',
    method: 'get',
    params: query
  })
}

// 获取患者状态统计
export function getPatientStatusStats() {
  return request({
    url: '/hospital/patient/statusStats',
    method: 'get'
  })
}
