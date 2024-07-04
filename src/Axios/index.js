import axios from "axios";

const Axios = axios.create({
    baseURL: '/api',
    headers: {
        "Content-Type": "application/json",
    },
    timeout: 10000,
})


export default Axios;
