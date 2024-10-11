import request from "@/utils/request";

// 查询渠道配置列表
export function listChannel(query) {
  return request({
    url: "/system/channel/list",
    method: "get",
    params: query,
  });
}

// 查询渠道配置详细
export function getChannel(id) {
  return request({
    url: "/system/channel/" + id,
    method: "get",
  });
}

// 新增渠道配置
export function addChannel(data) {
  return request({
    url: "/system/channel/add",
    method: "post",
    data: data,
  });
}

// 修改渠道配置
export function updateChannel(data) {
  return request({
    url: "/system/channel/edit",
    method: "post",
    data: data,
  });
}

// 删除渠道配置
export function delChannel(id) {
  return request({
    url: "/system/channel/" + id,
    method: "delete",
  });
}
