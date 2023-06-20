import request from '/@/utils/request';
import {Session} from "/@/utils/storage";


export function chartsApi() {
    let token = Session.get('token');
    token =  "Bearer " + token
    return {
        totalUser: () => {
            return request({
                url: '/admin/charts/total/user',
                method: 'GET',
                headers: {'Authorization': token},
            });
        },
        totalReport: () => {
            return request({
                url: '/admin/charts/total/report',
                method: 'GET',
                headers: {'Authorization': token},
            });
        },
        totalAccept: () => {
            return request({
                url: '/admin/charts/total/accept',
                method: 'GET',
                headers: {'Authorization': token},
            });
        },
        totalPoints: () => {
            return request({
                url: '/admin/charts/total/points',
                method: 'GET',
                headers: {'Authorization': token},
            });
        },
        typePercent: () => {
            return request({
                url: '/admin/charts/type/percent',
                method: 'GET',
                headers: {'Authorization': token},
            });
        },
        reportNum: () => {
            return request({
                url: '/admin/charts/report/num',
                method: 'GET',
                headers: {'Authorization': token},
            });
        }
    };
}