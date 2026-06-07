import request from '@/utils/request'

export function mySummary() {
  return request({
    url: '/portal/stats/mySummary',
    method: 'get'
  })
}

export function myMonthlyTrend() {
  return request({
    url: '/portal/stats/myMonthlyTrend',
    method: 'get'
  })
}

export function myTypePreference() {
  return request({
    url: '/portal/stats/myTypePreference',
    method: 'get'
  })
}
