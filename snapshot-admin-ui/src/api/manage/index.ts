import request from '/@/utils/request';

export function useManageApi() {
    return {
        signIn: (data: object) => {
            return request({
                url: '/manage/signIn',
                method: 'get',
                data,
            });
        },
        signOut: (data: object) => {
            return request({
                url: '/manage/signOut',
                method: 'post',
                data,
            });
        },
    };
}
