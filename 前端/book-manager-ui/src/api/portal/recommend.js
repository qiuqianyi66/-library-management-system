import request from '@/utils/request'

export function recommendForMe() {
  return request({
    url: '/portal/recommend/forMe',
    method: 'get'
  })
}

export function recommendByBook(bookId) {
  return request({
    url: '/portal/recommend/byBook/' + bookId,
    method: 'get'
  })
}

export function recommendHot() {
  return request({
    url: '/portal/recommend/hot',
    method: 'get'
  })
}
