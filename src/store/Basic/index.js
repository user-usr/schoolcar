const index = {
    namespaced: true,

    state: () => ({
        // HTTP_URL: 'http://localhost:8888',
        HTTP_URL: 'http://110.41.80.141:8888',
        activeName: 'NewsPage',
        loginState: false,
        token: '',
        id: '',
        workspaceMenuData: [
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
        navMsg: [
            {
                name: 'NewsPage',
                value: '主页',
                path: '/news-page'
            },
        ],
        loginMsg: [
            {
                name: 'LoginPage',
                value: '登陆',
                path: '/login-page'
            },
            {
                name: 'LogonPage',
                value: '注册',
                path: '/logon-page'
            }
        ],
        headerDropdownMsg: [],
        collegeOptions: [
            {
                value: '信息工程学院',
            },
            {
                value: '环境与资源学院',
            },
            {
                value: '材料与化学学院',
            },
            {
                value: '制造科学与工程学院',
            },
            {
                value: '土木工程与建筑学院',
            },
            {
                value: '生命科学与工程学院',
            },
            {
                value: '计算机科学与技术学院',
            },
            {
                value: '国防科技学院',
            },
            {
                value: '数理学院',
            },
            {
                value: '经济管理学院',
            },
            {
                value: '法学院',
            },
            {
                value: '文学与艺术学院',
            },
            {
                value: '外国语学院',
            },
            {
                value: '马克思主义学院',
            },
            {
                value: '体育与健康学院',
            },
            {
                value: '应用技术学院',
            },
        ]
    }),
    getters: {
        HTTP_URL: state => state.HTTP_URL,
        loginState: state => state.loginState,
        loginMsg: state => state.loginMsg,
        navMsg: state => state.navMsg,
        headerDropdownMsg: state => state.headerDropdownMsg,
        activeName: state => state.activeName,
        collegeOptions: state => state.collegeOptions,
        token: state => state.token,
        id: state => state.id,
        workspaceMenuData: state => state.workspaceMenuData,
    },
    mutations: {
        basicInitial(state) {
            state.activeName = 'NewsPage';
            state.loginState = false;
            state.navMsg = [
                {
                    name: 'NewsPage',
                    value: '主页',
                    path: '/news-page',
                },
            ];
            state.headerDropdownMsg = [];
            state.token = '';
            state.id = '';
            state.workspaceMenuData = [
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
            ];
        },
        setLoginState(state, loginState) {
            state.loginState = loginState
        },
        setNavMsg(state, navMsg) {
            state.navMsg = navMsg
        },
        setHeaderDropdownMsg(state, headerDropdownMsg) {
            state.headerDropdownMsg = headerDropdownMsg
        },
        setActiveName(state, activeName) {
            state.activeName = activeName
        },
        setToken(state, token) {
            state.token = token;
        },
        setID(state, id) {
            state.id = id
        },
        setWorkspaceMenuData(state, workspaceMenuData) {
            state.workspaceMenuData = workspaceMenuData
        },
        handleRefresh(state, sessionStorage)  {
            state.loginState = JSON.parse(sessionStorage.getItem('loginState'));
            state.navMsg = JSON.parse(sessionStorage.getItem('navMsg'));
            state.headerDropdownMsg = JSON.parse(sessionStorage.getItem('headerDropdownMsg'));
            state.activeName = sessionStorage.getItem('activeName');
            state.token = sessionStorage.getItem('token');
            state.id = sessionStorage.getItem('id');
            state.workspaceMenuData = JSON.parse(sessionStorage.getItem('workspaceMenuData'));
        }

    },
}

export default index;
