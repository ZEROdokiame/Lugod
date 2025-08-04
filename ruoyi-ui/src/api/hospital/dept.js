import request from '@/utils/request'

// 查询医院科室列表
export function listDepts() {
  return request({
    url: '/hospital/dept/list',
    method: 'get'
  })
}

// 查询科室列表（用于叫号队列）- 查询hospital的dept表
export function listDeptExcludeChild() {
  return request({
    url: '/hospital/dept/listAll',
    method: 'get'
  })
}

// 根据科室ID获取医生列表
export function listDoctorsByDept(deptId) {
  return request({
    url: '/hospital/doctor/list/' + deptId,
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

// 新增医生
export function addDoctor(data) {
  return request({
    url: '/hospital/doctor',
    method: 'post',
    data: data
  })
}

// 修改医生信息
export function updateDoctor(data) {
  return request({
    url: '/hospital/doctor',
    method: 'put',
    data: data
  })
}

// 删除医生
export function delDoctor(doctorId) {
  return request({
    url: '/hospital/doctor/' + doctorId,
    method: 'delete'
  })
}
