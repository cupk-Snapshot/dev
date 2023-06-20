import request from "/@/utils/request";
import {Session} from "/@/utils/storage";

export function acceptApi() {
    let token = Session.get('token');
    token =  "Bearer " + token
    return {
        all: (current: number, size: number) => {
            return request({
                url: '/admin/accept/all',
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
                url: '/admin/accept/update',
                method: 'POST',
                headers: {
                    'Authorization': token,
                    'Content-Type': 'application/x-www-form-urlencoded'
                },
                data: data
            });
        },
        examine: (data: object) => {
            return request({
                url: '/admin/accept/examine',
                method: 'POST',
                headers: {
                    'Authorization': token,
                    'Content-Type': 'application/x-www-form-urlencoded'
                },
                data: data
            });
        },
        add: (data: object) => {
            return request({
                url: '/admin/accept/add',
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
                url: '/admin/accept/delete',
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
