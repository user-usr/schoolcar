const admin = {
    namespaced: true,
    state: () => ({
        activeName: 'NewsPage',
        navMsg: [
            {
                name: 'NewsPage',
                value: '主页',
                path: '/news-page',
            },
            {
                name: 'AdminGeneralPage',
                value: '系统中心',
                path: '/admin-work-space-page',
            },
            {
                name: 'AdminRegularUsersPage',
                value: '人员中心',
                path: '/admin-personnel-center-page',
            },
            {
                name: 'AdminCarInfPage',
                value: '电动车中心',
                path: '/admin-car-center-page',
            }
        ],
        adminWorkspacePageMenuMsg: [
            {
                index: 1,
                value: '总览',
                name: 'AdminGeneralPage',
                icon: 'iconfont icon-user-information-icon',
            },
            {
                index: 2,
                value: '新闻中心',
                name: 'AdminNewsCenterPage',
                icon: 'iconfont icon-user-information-icon',
            },
        ],
        adminPersonnelCenterPageMenuMsg: [
            {
                index: 1,
                value: '普通用户',
                name: 'AdminRegularUsersPage',
                icon: 'iconfont icon-user-information-icon',
            },
            {
                index: 2,
                value: '工作人员',
                name: 'AdminWorkersPage',
                icon: 'iconfont icon-user-information-icon',
            },
        ],
        adminCarCenterPageMenuMsg: [
            {
                index: 1,
                value: '车辆信息',
                name: 'AdminCarInfPage',
                icon: 'iconfont icon-user-information-icon',
            },
            {
                index: 2,
                value: '进出记录',
                name: 'AdminCarInOutPage',
                icon: 'iconfont icon-user-information-icon',
            },
            {
                index: 3,
                value: '违规记录',
                name: 'AdminCarViolationPage',
                icon: 'iconfont icon-user-information-icon',
            },
        ],
        headerDropdownMsg: [
            {
                name: 'Logout',
                value: '退出登录',
                path: '/logout'
            }
        ],
    }),
    getters: {
        activeName: state => state.activeName,
        navMsg: state => state.navMsg,
        headerDropdownMsg: state => state.headerDropdownMsg,
        adminWorkspacePageMenuMsg: state => state.adminWorkspacePageMenuMsg,
        adminPersonnelCenterPageMenuMsg: state => state.adminPersonnelCenterPageMenuMsg,
        adminCarCenterPageMenuMsg: state => state.adminCarCenterPageMenuMsg,
    },
    mutations: {},
}

export default admin
