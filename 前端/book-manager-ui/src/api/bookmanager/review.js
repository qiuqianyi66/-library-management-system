import request from '@/utils/request'

export function listReview(query) {
  return request({
    url: '/bookmanager/review/list',
    method: 'get',
    params: query
  })
}

export function getReview(id) {
  return request({
    url: '/bookmanager/review/' + id,
    method: 'get'
  })
}

export function addReview(data) {
  return request({
    url: '/bookmanager/review',
    method: 'post',
    data: data
  })
}

export function updateReview(data) {
  return request({
    url: '/bookmanager/review',
    method: 'put',
    data: data
  })
}

export function delReview(ids) {
  return request({
    url: '/bookmanager/review/' + ids,
    method: 'delete'
  })
}
