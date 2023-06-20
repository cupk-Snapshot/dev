import request from "/@/utils/request";
import {Session} from "/@/utils/storage";

export function userApi() {
    let token = Session.get('token');
    token =  "Bearer " + token
    return {
        all: (current: number, size: number) => {
            return request({
                url: '/admin/user/all',
                method: 'GET',
                headers: {'Authorization': token},
                params: {
                    current: current,
                    size: size
                }
            });
        },
        update: (data: object) => {
            return request({
                url: '/admin/user/update',
                method: 'POST',
                headers: {
                    'Authorization': token,
                    'Content-Type': 'application/x-www-form-urlencoded'
                },
                data: {
                    userId: data.userId,
                    nickName: data.nickName,
                    username: data.username,
                    phoneNum: data.phoneNum,
                    name: data.name,
                    isEnabled: data.isEnabled == false ? 0 : 1

                }
            });
        },
        add: (data: object) => {
            return request({
                url: '/admin/user/add',
                method: 'POST',
                headers: {
                    'Authorization': token,
                    'Content-Type': 'application/x-www-form-urlencoded'
                },
                data: data
            });
        },
        delete: (ids: object) => {
            return request({
                url: '/admin/user/delete',
                method: 'POST',
                headers: {
                    'Authorization': token,
                    'Content-Type': 'application/json'
                },
                data: ids
            });
        },
    };
}
