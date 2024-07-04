const worker = {
    namespaced: true,
    state: () => ({
        activeName: 'NewsPage',
        navMsg: [
            {
                name: 'NewsPage',
                value: '主页',
                path: '/news-page'
            },
            {
                name: 'WorkerInfPage',
                value: '个人中心',
                path: '/worker-page'
            },
            {
                name: 'WorkerCarEnterOutPage',
                value: '电动车中心',
                path: '/worker-car-page'
            },
            {
                name: 'WorkerWorkPage',
                value: '工作中心',
                path: '/worker-workspace-page'
            },
        ],
        headerDropdownMsg: [
            {
                name: 'WorkerInfPage',
                value: '个人中心',
                path: '/worker-inf-page'
            },
            {
                name: 'Logout',
                value: '退出登录',
                path: '/logout'
            }
        ],
        workerPageMenuMsg: [
            {
                index: 1,
                value: '个人信息',
                name: 'WorkerInfPage',
                icon: 'iconfont icon-user-information-icon',
            },
            {
                index: 2,
                value: '修改密码',
                name: 'WorkerChangePasswordPage',
                icon: 'iconfont icon-user-change-password-icon',
            },
        ],
        workerCarPageMenuMsg: [
            {
                index: 1,
                value: '出入记录',
                name: 'WorkerCarEnterOutPage',
                icon: 'iconfont icon-car-enter-out-icon',
            },
            {
                index: 2,
                value: '违规记录',
                name: 'WorkerCarViolationPage',
                icon: 'iconfont icon-car-violation-icon',
            }
        ],
        workerWorkspacePageMenuMsg: [
            {
                index: 1,
                value: '工作台',
                name: 'WorkerWorkPage',
                icon: 'iconfont icon-user-workspace-icon',
            },
        ],
        workerWorkPageMenuData: [
            {
                label: '全部',
                name: 'all',
            },
            {
                label: '待审核',
                name: 'doing',
            },
            {
                label: '已完成',
                name: 'done',
            }
        ],
    }),
    getters: {
        activeName: state => state.activeName,
        navMsg: state => state.navMsg,
        headerDropdownMsg: state => state.headerDropdownMsg,
        workerPageMenuMsg: state => state.workerPageMenuMsg,
        workerCarPageMenuMsg: state => state.workerCarPageMenuMsg,
        workerWorkspacePageMenuMsg: state => state.workerWorkspacePageMenuMsg,
        workerWorkPageMenuData: state => state.workerWorkPageMenuData,
    },
    mutations: {

    },
}

export default worker
