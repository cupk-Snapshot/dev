import request from '@/utils/request'

// 登录方法
export function login(username, password) {
  const data = {
    username,
    password,
	grant_type: 'password',
	client_id:"snapshot",
	client_secret:"snapshot-secret"
  }
  return request({
    'url': '/oauth/token',
    headers: {
      isToken: false,
	   'Content-Type': 'application/x-www-form-urlencoded'
    },
    'method': 'GET',
    'data': data
  })
}

export function loginOn(phone_num, sms_code) {
  const data = {
    phone_num,
    sms_code,
	grant_type: 'sms_code',
	client_id:"snapshot",
	client_secret:"snapshot-secret"
  }
  return request({
    'url': '/oauth/token',
    headers: {
      isToken: false,
	   'Content-Type': 'application/x-www-form-urlencoded'
    },
    'method': 'GET',
    'data': data
  })
}

// 获取用户详细信息
export function getInfo() {
  return request({
    'url': '/user/info/2',
    'method': 'get'
  })
}

// 退出方法
export function logout() {
  return request({
    'url': '/logout',
    'method': 'post'
  })
}

// 获取验证码
// export function getCodeImg() {
//   return request({
//     'url': '/captchaImage',
//     headers: {
//       isToken: false
//     },
//     method: 'get',
//     timeout: 20000
//   })
// }
