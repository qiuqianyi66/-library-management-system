import request from '@/utils/request'

export function getStats() {
  return request({
    url: '/dashboard/stats',
    method: 'get'
  })
}

export function getBorrowTrend() {
  return request({
    url: '/dashboard/borrowTrend',
    method: 'get'
  })
}

export function getHotBooks() {
  return request({
    url: '/dashboard/hotBooks',
    method: 'get'
  })
}

export function getIncomeStats() {
  return request({
    url: '/dashboard/incomeStats',
    method: 'get'
  })
}
