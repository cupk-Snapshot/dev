import request from "/@/utils/request";
import {Session} from "/@/utils/storage";

export function orderApi() {
    let token = Session.get('token');
    token =  "Bearer " + token
    return {
        all: (current: number, size: number) => {
            return request({
                url: '/admin/order/all',
                method: 'GET',
                headers: {'Authorization': token},
                params: {
                    current: current,
                    size: size
                }
            });
        },
        delete: (ids: object) => {
            return request({
                url: '/admin/order/delete',
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
