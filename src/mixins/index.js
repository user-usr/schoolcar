import {ElNotification} from "element-plus";
import axios from '@/Axios'

export const mixin = {
    methods: {
        getImageUrl(url) {
            return this.$store.getters['basic/HTTP_URL'] + '/' + url
        },
        getImage(url) {
            return this.getImageUrl(url)
        },
        getAllImage(urls) {
            const results = []
            for (const urlKey in urls) {
                results.push(this.getImage(urls.at(urlKey)))
            }
            return results
        },
        changeRouter(name) {
            this.$store.commit('basic/setActiveName', name)
            this.$router.push({
                name: name
            })
        },
        showElNotification(title = '通知', message = '你好', type = 'success') {
            ElNotification({
                title: title,
                message: message,
                type: type,
                duration: 1500
            })
        },
        toLogin(loginData) {
            return axios({
                url: '/login',
                method: 'get',
                params: loginData,
            }).then(res => {
                return res.data
            })
        },
        getCarInformationData(token, id, uid) {
            return axios({
                url: '/car/car-information-data',
                method: 'get',
                headers: {
                    token: token,
                    id: id,
                },
                params: {
                    uid: uid,
                }
            }).then(res => {
                return res.data
            })
        },
        updateUserInformation(token, id, userNewData) {
            return axios({
                url: `/user/update/${userNewData.uid}`,
                method: 'put',
                data: userNewData,
                headers: {
                    token: token,
                    id: id,
                }
            }).then(res => {
                return res.data
            })
        },
        updateUserPassword(token, id, oldPassword, newPassword) {
            return axios({
                url: `/user/updatePassword/${id}`,
                method: 'put',
                headers: {
                    token: token,
                    id: id,
                },
                data: {
                    oldPassword: oldPassword,
                    newPassword: newPassword,
                },
            }).then(res => {
                return res.data
            })
        },
        uploadFile(headers, formData) {
            return axios({
                url: '/file/upload',
                method: 'post',
                data: formData,
                headers: headers
            }).then(res => {
                return res.data
            })
        },
        toLogon(logonData) {
            return axios({
                url: '/logon',
                method: 'post',
                data: logonData,
            }).then(res => {
                return res.data
            })
        },
        toLogout() {
            this.$store.commit('basic/basicInitial')
            this.showElNotification('退出登录', '', 'success')
            this.changeRouter('NewsPage')
        },
        updateWorkerPassword(token, id, oldPassword, newPassword) {
            return axios({
                url: `/worker/updatePassword/${id}`,
                method: 'put',
                headers: {
                    token: token,
                    id: id,
                },
                data: {
                    oldPassword: oldPassword,
                    newPassword: newPassword,
                },
            }).then(res => {
                return res.data
            })
        },
    }
}
