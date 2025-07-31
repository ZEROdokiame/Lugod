import request from '@/utils/request'

// 获取叫号显示信息
export function getDisplayInfo() {
  return request({
    url: '/hospital/display/status',
    method: 'get'
  })
}

// 获取科室叫号显示信息
export function getDeptDisplay(deptId) {
  return request({
    url: '/hospital/display/dept/' + deptId,
    method: 'get'
  })
}
