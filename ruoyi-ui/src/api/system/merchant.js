import request from "@/utils/request";

// 查询商户列表
export function listMerchant(query) {
  return request({
    url: "/system/merchant/list",
    method: "get",
    params: query,
  });
}

// 查询商户详细
export function getMerchant(id) {
  return request({
    url: "/system/merchant/" + id,
    method: "get",
  });
}

// 新增商户
export function addMerchant(data) {
  return request({
    url: "/system/merchant/add",
    method: "post",
    data: data,
  });
}

// 修改商户
export function updateMerchant(data) {
  return request({
    url: "/system/merchant/edit",
    method: "post",
    data: data,
  });
}

// 删除商户
export function delMerchant(id) {
  return request({
    url: "/system/merchant/" + id,
    method: "delete",
  });
}

// 获取商户(机构)的列表
export function getAllMerchantList() {
  return request({
    url: "/system/customer/getAllMerchantList",
    method: "get",
  });
}
