import request from '@/utils/request'

// 查询药材库存管理列表
export function listStorage(query) {
  return request({
    url: '/hospital/storage/list',
    method: 'get',
    params: query
  })
}

// 查询药材库存管理详细
export function getStorage(id) {
  return request({
    url: '/hospital/storage/' + id,
    method: 'get'
  })
}

// 新增药材库存管理
export function addStorage(data) {
  return request({
    url: '/hospital/storage',
    method: 'post',
    data: data
  })
}

// 修改药材库存管理
export function updateStorage(data) {
  return request({
    url: '/hospital/storage',
    method: 'put',
    data: data
  })
}

// 删除药材库存管理
export function delStorage(id) {
  return request({
    url: '/hospital/storage/' + id,
    method: 'delete'
  })
}
