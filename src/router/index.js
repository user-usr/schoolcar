import {createRouter, createWebHistory} from 'vue-router';
import NewsPage from "@/views/NewsPage.vue";
import UserPage from "@/views/User/UserPage.vue";
import CarPage from "@/views/User/CarPage.vue";
import LoginPage from "@/views/LoginPage.vue";
import LogonPage from "@/views/LogonPage.vue";
import UserInformationPage from "@/views/User/UserPage/UserInformationPage.vue";
import UserChangePasswordPage from "@/views/User/UserPage/UserChangePasswordPage.vue";
import CarInformationPage from "@/views/User/CarPage/CarInformationPage.vue";
import CarEnterOutPage from "@/views/CarEnterOutPage.vue";
import CarViolationPage from "@/views/CarViolationPage.vue";
import UserWorkPage from "@/views/User/UserPage/UserWorkPage.vue";
import WorkerPage from "@/views/Woker/WorkerPage.vue";
import WorkerInfPage from "@/views/Woker/WorkerPage/WorkerInfPage.vue";
import WorkerChangePasswordPage from "@/views/Woker/WorkerPage/WorkerChangePasswordPage.vue";
import WorkerCarPage from "@/views/Woker/WorkerCarPage.vue";
import WorkerWorkspacePage from "@/views/Woker/WorkerWorkspacePage.vue";
import WorkerWorkPage from "@/views/Woker/WorkerWorkspacePage/WorkerWorkPage.vue";
import AdminWorkspacePage from "@/views/Admin/AdminWorkspacePage.vue";
import AdminPersonnelCenterPage from "@/views/Admin/AdminPersonnelCenterPage.vue";
import AdminCarCenterPage from "@/views/Admin/AdminCarCenterPage.vue";
import AdminGeneralPage from "@/views/Admin/AdminWorkspacePage/AdminGeneralPage.vue";
import AdminRegularUsersPage from "@/views/Admin/AdminPersonnelCenterPage/AdminRegularUsersPage.vue";
import AdminWorkersPage from "@/views/Admin/AdminPersonnelCenterPage/AdminWorkersPage.vue";
import AdminCarInfPage from "@/views/Admin/AdminCarCenterPage/AdminCarInfPage.vue";
import AdminNewsCenterPage from "@/views/Admin/AdminWorkspacePage/AdminNewsCenterPage.vue";

const router = createRouter({
    history: createWebHistory(),
    routes: [
        {
            path: '/',
            redirect: '/news-page'
        },
        {
            path: '/news-page',
            name: 'NewsPage',
            component: NewsPage
        },
        {
            path: '/user-page',
            name: 'UserPage',
            component: UserPage,
            children: [
                {
                    path: '',
                    name: 'UserInformationPage',
                    component: UserInformationPage

                },
                {
                    path: 'user-change-password-page',
                    name: 'UserChangePasswordPage',
                    component: UserChangePasswordPage
                },
                {
                    path: 'user-work-page',
                    name: 'UserWorkPage',
                    component: UserWorkPage
                }
            ]
        },
        {
            path: '/user-car-page',
            name: 'UserCarPage',
            component: CarPage,
            children: [
                {
                    path: '',
                    name: 'UserCarInformationPage',
                    component: CarInformationPage
                },
                {
                    path: 'user-car-enter-out-page',
                    name: 'UserCarEnterOutPage',
                    component: CarEnterOutPage
                },
                {
                    path: 'user-car-violation-page',
                    name: 'UserCarViolationPage',
                    component: CarViolationPage
                }
            ]
        },
        {
            path: '/worker-page',
            name: 'WorkerPage',
            component: WorkerPage,
            children: [
                {
                    path: '',
                    name: 'WorkerInfPage',
                    component: WorkerInfPage
                },
                {
                    path: 'worker-change-password-page',
                    name: 'WorkerChangePasswordPage',
                    component: WorkerChangePasswordPage
                }
            ]
        },
        {
            path: '/worker-car-page',
            name: 'WorkerCarPage',
            component: WorkerCarPage,
            children: [
                {
                    path: '',
                    name: 'WorkerCarEnterOutPage',
                    component: CarEnterOutPage,
                },
                {
                    path: 'worker-car-violation-page',
                    name: 'WorkerCarViolationPage',
                    component: CarViolationPage
                }
            ]
        },
        {
            path: '/worker-workspace-page',
            name: 'WorkerWorkspacePage',
            component: WorkerWorkspacePage,
            children: [
                {
                    path: '',
                    name: 'WorkerWorkPage',
                    component: WorkerWorkPage,
                },
            ]
        },
        {
            path: '/admin-workspace-page',
            name: 'AdminWorkspacePage',
            component: AdminWorkspacePage,
            children: [
                {
                    path: '',
                    name: 'AdminGeneralPage',
                    component: AdminGeneralPage,
                },
                {
                    path: 'admin-news-center-page',
                    name: 'AdminNewsCenterPage',
                    component: AdminNewsCenterPage
                }
            ],
        },
        {
            path: '/admin-personnel-center-page',
            name: 'AdminPersonnelCenterPage',
            component: AdminPersonnelCenterPage,
            children: [
                {
                    path: '',
                    name: 'AdminRegularUsersPage',
                    component: AdminRegularUsersPage
                },
                {
                    path: 'admin-workers-page',
                    name: 'AdminWorkersPage',
                    component: AdminWorkersPage
                }
            ],
        },
        {
            path: '/admin-car-center-page',
            name: 'AdminCarCenterPage',
            component: AdminCarCenterPage,
            children: [
                {
                    path: '',
                    name: 'AdminCarInfPage',
                    component: AdminCarInfPage
                },
                {
                    path: 'admin-car-in-out-page',
                    name: 'AdminCarInOutPage',
                    component: CarEnterOutPage
                },
                {
                    path: 'admin-car-violation-page',
                    name: 'AdminCarViolationPage',
                    component: CarViolationPage
                }
            ],
        },
        {
            path: '/login-page',
            name: 'LoginPage',
            component: LoginPage
        },
        {
            path: '/logon-page',
            name: 'LogonPage',
            component: LogonPage
        },
    ]
})

export default router
