<template>
<div>
    <nut-navbar class="top-box">
        <template #left>
            <router-link to="/account">返回</router-link>
        </template>
        <template #content>
            <div>个人信息</div>
        </template>
    </nut-navbar>
    <br />
    <br />
    <br />
    <br />
    <img :src="imgUrl" class="prev-img" />
    <nut-uploader class="upload-img" accept="image/png, image/jpeg" @change="chooseImg" :auto-upload="false"></nut-uploader> 
    <br />
    <br />
    <br />
    <br />
    <br />
    <br />
    <br />
    <br />
    <nut-input v-model="nickname" label="昵称" :placeholder="user.username" clearable center>
        <template #button>
            <nut-button size="small" type="primary" @click="uploadUserInfo">好</nut-button>
        </template>
    </nut-input>
    <br />
    <br />
    <br />
    <br />
    <br />
    <div align="center">
        <nut-button plain type="primary" @click="logOut">退出登录</nut-button>
    </div>
</div>
</template>

<script lang="ts">
import {
    HttpManager
} from '@/api';
import {
  computed,
    getCurrentInstance,
    reactive,
    toRefs,
} from 'vue';
import { Toast } from '@nutui/nutui';
import { useRouter } from 'vue-router';

export default {
    setup() {
        const router = useRouter();
        const cookie = getCurrentInstance().appContext.config.globalProperties.$cookies;
        const user = reactive({
            username: cookie.get("username") ? cookie.get("username") : "点击登录",
            account: cookie.get("account") ? "账号：" + cookie.get("account") : "",
            icon: cookie.get("icon") ? cookie.get("icon") : "img/icon/default.png"
        });
        const state = reactive({
            imgUrl: computed(() => {
                if (state.fileList.length > 0) return state.fileList[0].url;
                else return HttpManager.attachImageUrl(user.icon);
            }),
            nickname: user.username,
            fileList: []
        });
        const methods= {
            chooseImg: params => {
                state.fileList = params.fileList;
            },
            uploadUserInfo: async() => {
                let params = new URLSearchParams();
                params.append("username", state.nickname);
                params.append("icon", state.imgUrl);
                params.append("account", cookie.get("account"));
                const result = await HttpManager.updateUserInfo(params) as ResponseBody;
                if (result.success) {
                    cookie.set("username", state.nickname);
                    // console.log(result.data.icon);
                    cookie.set("icon", result.data.icon);
                    Toast.success(result.message);
                } else Toast.fail(result.message);
            },
            logOut: () => {
                cookie.remove("username");
                cookie.remove("account");
                cookie.remove("icon");
                router.push('/account');
            }
        }
        return {
            user,
            attachImageUrl: HttpManager.attachImageUrl,
            ...toRefs(state),
            ...methods
        }
    }
}
</script>

<style scoped>
.top-box {
    background-color: #bd3124;
    color: #fff;
    height: 6%;
    font-size: 20px;
    line-height: 50px;
    box-shadow: 3px 2px #cfcfcf;
}

.upload-img {
    position: absolute;
    left: 50%;
    transform: translate(-50%);
    display: inline-block;
    border-radius: 50%;
    overflow: hidden;
    opacity: 0%;
    border: 5px rgb(250, 114, 114) solid;
}

.upload-img>* {
    opacity: 100%;
}

.prev-img {
    position: absolute;
    left: 50%;
    transform: translate(-50%);
    width: 100px;
    height: 100px;
    border-radius: 50%;
    overflow: hidden;
    border: 5px rgb(250, 114, 114) solid;
}
</style>
