import request from '@/utils/request'

// 查询队列列表
export function listQueue(query) {
  return request({
    url: '/hospital/queue/list',
    method: 'get',
    params: query
  })
}

// 查询队列详细
export function getQueue(queueId) {
  return request({
    url: '/hospital/queue/' + queueId,
    method: 'get'
  })
}

// 新增队列
export function addQueue(data) {
  return request({
    url: '/hospital/queue',
    method: 'post',
    data: data
  })
}

// 修改队列
export function updateQueue(data) {
  return request({
    url: '/hospital/queue',
    method: 'put',
    data: data
  })
}

// 删除队列
export function delQueue(queueId) {
  return request({
    url: '/hospital/queue/' + queueId,
    method: 'delete'
  })
}

// 修改队列状态
export function changeQueueStatus(queueId, status) {
  const data = {
    queueId,
    status
  }
  return request({
    url: '/hospital/queue/changeStatus',
    method: 'put',
    data: data
  })
}
