import request from '@/utils/request'

export function listBook(query) {
  return request({
    url: '/bookmanager/book/list',
    method: 'get',
    params: query
  })
}

export function getBook(bookId) {
  return request({
    url: '/bookmanager/book/' + bookId,
    method: 'get'
  })
}

export function addBook(data) {
  return request({
    url: '/bookmanager/book',
    method: 'post',
    data: data
  })
}

export function updateBook(data) {
  return request({
    url: '/bookmanager/book',
    method: 'put',
    data: data
  })
}

export function delBook(bookId) {
  return request({
    url: '/bookmanager/book/' + bookId,
    method: 'delete'
  })
}

export function changeBookStatus(data) {
  return request({
    url: '/bookmanager/book/changeStatus',
    method: 'put',
    data: data
  })
}
