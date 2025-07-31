import request from '@/utils/request'

// 根据科室获取医生列表
export function listDoctorsByDept(deptId) {
  return request({
    url: '/hospital/doctor/dept/' + deptId,
    method: 'get'
  })
}

// 查询医生详细信息
export function getDoctor(doctorId) {
  return request({
    url: '/hospital/doctor/' + doctorId,
    method: 'get'
  })
}

// 获取等待队列的医生信息
export function getDoctorQueue() {
  return request({
    url: '/hospital/doctor/waiting',
    method: 'get'
  })
}

// 叫号患者
export function callPatient(patientId) {
  return request({
    url: '/hospital/doctor/call/' + patientId,
    method: 'put'
  })
}

// 完成就诊
export function completePatient(patientId) {
  return request({
    url: '/hospital/doctor/complete/' + patientId,
    method: 'put'
  })
}

// 取消就诊
export function cancelPatient(patientId) {
  return request({
    url: '/hospital/doctor/cancel/' + patientId,
    method: 'put'
  })
}

// 获取当前就诊患者
export function getCurrentPatient() {
  return request({
    url: '/hospital/doctor/current',
    method: 'get'
  })
}
