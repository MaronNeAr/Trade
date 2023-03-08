<template>
<div class="content">
    <nut-form class="form">
        <nut-form-item label="用户名">
            <nut-input v-model="basicData.name" class="nut-input-text" placeholder="请输入用户名" type="text" @keyup.enter="login" />
        </nut-form-item>
        <nut-form-item label="密码">
            <nut-input v-model="basicData.password" class="nut-input-text" placeholder="请输入密码" type="password" @keyup.enter="login" />
        </nut-form-item>
        <br />
        <div class="button">
            <nut-button type="primary" class="login" @click="login">登录</nut-button>
            <nut-button type="primary" class="register" @click="register">注册</nut-button>
        </div>
    </nut-form>
</div>
</template>

<script lang="ts">
import {
    reactive,
    getCurrentInstance
} from 'vue';
import { HttpManager } from '@/api';
import { Notify } from '@nutui/nutui';
import { useRouter } from 'vue-router';
export default {
    components: {},
    setup() {
        const router = useRouter();
        const cookie = getCurrentInstance().appContext.config.globalProperties.$cookies;
        const basicData = reactive({
            name: '',
            password: '',
        });

        const asyncLogin = async() => {
            let params = new URLSearchParams();
            params.append("account", basicData.name);
            params.append("password", basicData.password);
            const response = (await HttpManager.login(params)) as ResponseBody;
            if (response.success) {
                Notify.success(response.message);
                const result = response.data;
                console.log(result);
                cookie.set("account", result.account);
                cookie.set("username", result.username);
                cookie.set("phone", result.phone);
                cookie.set("email", result.email);
                cookie.set("icon", result.icon);
                router.push('/account');
            } else Notify.danger(response.message);
        }

        const asyncRegister = async() => {
            let params = new URLSearchParams();
            params.append("account", basicData.name);
            params.append("password", basicData.password);
            const response = (await HttpManager.register(params)) as ResponseBody;
            if (response.success) Notify.success(response.message);
            else Notify.danger(response.message);
        }


        const methods = {
            login() {
                asyncLogin();
            },
            register() {
                asyncRegister();
            }
        }
        return {
            basicData,
            ...methods
        };
    }
}
</script>

<style scoped>
div>a {
    text-decoration: none;
    color: grey;
}

.button {
    width: 90%;
    height: 7vh;
}

.login {
    position: absolute;
    left: 8.5vw;
    width: 30vw;
    border-radius: 4vh;
    height: 6vh;
}

.register {
    position: absolute;
    right: 8.5vw;
    width: 30vw;
    border-radius: 4vh;
    height: 6vh;
}

.form {
    position: relative;
    width: 90%;
    left: 50%;
    top: 30%;
    transform: translate(-50%, -50%);
    animation: down-to-up ease 0.5s;
    animation-iteration-count: 1;
    /*设置动画播放次数*/
    animation-fill-mode: forwards;
}

@keyframes down-to-up {
    0% {
        top: 100%;
    }

    100% {
    }
}

.content {
    background: url("@/assets/images/login_bg.png");
    width: 100%;
    height: 100%;
    position: fixed;
    background-size: 100% 100%;
}
</style>
