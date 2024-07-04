import Vuex from 'vuex'
import basic from "@/store/Basic";
import user from "@/store/User";
import admin from "@/store/Admin";
import worker from "@/store/Worker";

const store = new Vuex.Store({
    modules: {
        basic: basic,
        user: user,
        admin: admin,
        worker: worker,
    },
    state: {

    },
    getters: {

    },
    mutations: {

    }
})

export default store
