import {createApp} from 'vue'
import App from './App.vue'
import router from './router/index'
import store from './store/index'
import axios from './Axios'
import ElementPlus from 'element-plus'
import 'element-plus/dist/index.css'
import * as ElementPlusIconsVue from '@element-plus/icons-vue'
import '@/assets/icon/iconfont/iconfont.css'
import zhCn from "element-plus/es/locale/lang/zh-cn.mjs";
import * as echarts from "echarts";

const app = createApp(App)

for (const [key, component] of Object.entries(ElementPlusIconsVue)) {
    app.component(key, component)
}

window.addEventListener('error', function (e) {
    console.log(e)
})

app.config.globalProperties.$echarts = echarts

app
    .use(router)
    .use(store)
    .use(axios)
    .use(ElementPlus, {
        locale: zhCn
    })
    .mount('#app')
