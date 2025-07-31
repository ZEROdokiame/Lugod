import request from '@/utils/request'

// 查询医院科室列表
export function listHospitalDept(query) {
  return request({
    url: '/hospital/dept/list',
    method: 'get',
    params: query
  })
}

// 查询所有可用科室（用于下拉选择）
export function listAllHospitalDept() {
  return request({
    url: '/hospital/dept/listAll',
    method: 'get'
  })
}

// 查询科室详细信息
export function getHospitalDept(deptId) {
  return request({
    url: '/hospital/dept/' + deptId,
    method: 'get'
  })
}

// 新增科室
export function addHospitalDept(data) {
  return request({
    url: '/hospital/dept',
    method: 'post',
    data: data
  })
}

// 修改科室
export function updateHospitalDept(data) {
  return request({
    url: '/hospital/dept',
    method: 'put',
    data: data
  })
}

// 删除科室
export function delHospitalDept(deptId) {
  return request({
    url: '/hospital/dept/' + deptId,
    method: 'delete'
  })
}
