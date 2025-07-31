import axios from 'axios'
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

// 创建不使用baseURL的axios实例
const hospitalRequest = axios.create({
  timeout: 10000
})

// 请求拦截器
hospitalRequest.interceptors.request.use(config => {
  // 设置Content-Type
  config.headers['Content-Type'] = 'application/json;charset=utf-8'
  
  // 设置token
  const token = localStorage.getItem('token') // 或其他获取token的方式
  if (token) {
    config.headers['Authorization'] = 'Bearer ' + token
  }
  
  // 构造完整URL
  if (!config.url.startsWith('http')) {
    config.url = '/hospital' + (config.url.startsWith('/') ? config.url : '/' + config.url)
  }
  
  return config
}, error => {
  console.log(error)
  Promise.reject(error)
})

// 响应拦截器
hospitalRequest.interceptors.response.use(res => {
  return res.data
}, error => {
  console.log(error)
  Promise.reject(error)
})
