import request from '@/utils/request'

export function listFine(query) {
  return request({
    url: '/bookmanager/fine/list',
    method: 'get',
    params: query
  })
}

export function getFine(fineId) {
  return request({
    url: '/bookmanager/fine/' + fineId,
    method: 'get'
  })
}

export function payFine(data) {
  return request({
    url: '/bookmanager/fine/pay',
    method: 'post',
    data: data
  })
}

export function payCallback(data) {
  return request({
    url: '/bookmanager/fine/payCallback',
    method: 'post',
    data: data
  })
}
