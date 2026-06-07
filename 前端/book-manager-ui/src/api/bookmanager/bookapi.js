import request from '@/utils/request'

export function fetchBookByIsbn(isbn) {
  return request({
    url: '/bookmanager/bookapi/fetchByIsbn/' + isbn,
    method: 'get'
  })
}
