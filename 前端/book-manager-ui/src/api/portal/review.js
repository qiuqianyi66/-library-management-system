import request from '@/utils/request'

export function getBookReviews(bookId) {
  return request({
    url: '/portal/review/book/' + bookId,
    method: 'get'
  })
}

export function submitReview(data) {
  return request({
    url: '/portal/review/submit',
    method: 'post',
    data: data
  })
}
