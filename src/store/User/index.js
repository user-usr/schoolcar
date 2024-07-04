const user = {
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
                name: 'UserInformationPage',
                value: '个人中心',
                path: '/user-page'
            },
            {
                name: 'UserCarInformationPage',
                value: '个人电动车',
                path: '/car-page'
            }
        ],
        headerDropdownMsg: [
            {
                name: 'UserInformationPage',
                value: '个人中心',
                path: '/user-page'
            },
            {
                name: 'Logout',
                value: '退出登录',
                path: '/logout'
            }
        ],
        userPageMenuMsg: [
            {
                index: 1,
                value: '个人信息',
                name: 'UserInformationPage',
                icon: 'iconfont icon-user-information-icon',
            },
            {
                index: 2,
                value: '修改密码',
                name: 'UserChangePasswordPage',
                icon: 'iconfont icon-user-change-password-icon',
            },
            {
                index: 3,
                value: '工作台',
                name: 'UserWorkPage',
                icon: 'iconfont icon-user-workspace-icon'
            }
        ],
        carPageMenuMsg: [
            {
                index: 1,
                value: '车辆信息',
                name: 'UserCarInformationPage',
                icon: 'iconfont icon-car-icon',
            },
            {
                index: 2,
                value: '出入记录',
                name: 'UserCarEnterOutPage',
                icon: 'iconfont icon-car-enter-out-icon',
            },
            {
                index: 3,
                value: '违规记录',
                name: 'UserCarViolationPage',
                icon: 'iconfont icon-car-violation-icon',
            }
        ],
        userInformationRules: {
            u_Name: [
                {required: true, message: '请输入用户名', trigger: 'blur'}
            ],
            u_RealName: [
                {required: true, message: '请输入姓名', trigger: 'blur'}
            ],
            u_StudentID: [
                {required: true, message: '请输入学号', trigger: 'blur'}
            ],
            u_College: [
                {required: true, message: '请输入学院', trigger: 'blur'}
            ],
            u_Major: [
                {required: true, message: '请输入专业', trigger: 'blur'}
            ],
            u_Grade: [
                {required: true, message: '请输入年级', trigger: 'blur'}
            ],
            u_Class: [
                {required: true, message: '请输入班级', trigger: 'blur'}
            ],
            u_Tel: [
                {required: true, message: '请输入电话', trigger: 'blur'},
                {pattern: /^1[3|4|5|6|7|8|9]\d{9}$/, message: '请输入正确的电话号码', trigger: 'blur'}
            ],
            u_Email: [
                {required: true, message: '请输入邮箱', trigger: 'blur'},
                {type: 'email', message: '请输入正确的邮箱地址', trigger: ['blur', 'change']}
            ],
            u_ID: [
                {required: true, message: '请输入身份证', trigger: 'blur'},
                {
                    pattern: /(^\d{15}$)|(^\d{18}$)|(^\d{17}(\d|X|x)$)/,
                    message: '请输入正确的身份证号码',
                    trigger: 'blur'
                }
            ]
        },
        carRules: {
            c_PlateNumber: [
                {required: true, message: '请输入车牌号', trigger: 'blur'}
            ],
            u_StudentID: [
                {required: true, message: '请选择车主学号', trigger: 'blur'}
            ],
            c_Brand: [
                {required: true, message: '请输入品牌', trigger: 'blur'}
            ],
            c_Model: [
                {required: true, message: '请输入型号', trigger: 'blur'}
            ],
            c_Type: [
                {required: true, message: '请输入类型', trigger: 'blur'}
            ],
            c_Color: [
                {required: true, message: '请输入颜色', trigger: 'blur'}
            ],
            c_Evidence: [
                {required: true, message: '请上传凭证', trigger: 'blur'}
            ]
        },
        userWorkPageMenuData: [
            {
                label: '全部',
                name: 'all',
            },
            {
                label: '审核中',
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
        userPageMenuMsg: state => state.userPageMenuMsg,
        carPageMenuMsg: state => state.carPageMenuMsg,
        headerDropdownMsg: state => state.headerDropdownMsg,
        navMsg: state => state.navMsg,
        userInformationRules: state => state.userInformationRules,
        carRules: state => state.carRules,
        userWorkPageMenuData: state => state.userWorkPageMenuData
    },
    mutations: {

    },
}
export default user
