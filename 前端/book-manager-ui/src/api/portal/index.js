import request from '@/utils/request'

export function myBorrow(query) {
  return request({
    url: '/portal/myBorrow',
    method: 'get',
    params: query
  })
}

export function myFines(query) {
  return request({
    url: '/portal/myFines',
    method: 'get',
    params: query
  })
}

export function searchBooks(query) {
  return request({
    url: '/portal/searchBooks',
    method: 'get',
    params: query
  })
}

export function portalRenew(borrowId) {
  return request({
    url: '/portal/renewBook/' + borrowId,
    method: 'put'
  })
}

export function portalPay(data) {
  return request({
    url: '/bookmanager/fine/pay',
    method: 'post',
    data: data
  })
}
