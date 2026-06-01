import request from '@/utils/request'

export function listBorrow(query) {
  return request({
    url: '/bookmanager/borrow/list',
    method: 'get',
    params: query
  })
}

export function getBorrow(borrowId) {
  return request({
    url: '/bookmanager/borrow/' + borrowId,
    method: 'get'
  })
}

export function borrowBook(data) {
  return request({
    url: '/bookmanager/borrow/borrowBook',
    method: 'post',
    data: data
  })
}

export function returnBook(borrowId) {
  return request({
    url: '/bookmanager/borrow/returnBook/' + borrowId,
    method: 'put'
  })
}

export function renewBook(borrowId) {
  return request({
    url: '/bookmanager/borrow/renewBook/' + borrowId,
    method: 'put'
  })
}
