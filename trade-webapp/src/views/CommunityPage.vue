<template>
<div>
    <nut-navbar title="虚拟社区" class="header" v-if="navShow">
        <template #left>
            <nut-icon name="refresh2"></nut-icon>
        </template>
    </nut-navbar>
    <div class="comm-head" ref="head">
        <nut-icon class="refresh" name="horizontal" color="#fff" @click="leftDialogShow= !leftDialogShow"></nut-icon>
        &ensp;
        <nut-icon class="refresh" name="refresh2" color="#fff"></nut-icon>
    </div>
    <div class="name">{{user.username}}</div>
    <img class="head-icon" :src="attachImageUrl(user.icon)" />
    <br />
    <br />
    <br />
    <tweet v-for="tweet in tweets" :key="tweet.id" :tweet="tweet"></tweet>
    <nut-drag direction="y" :style="{right:'0px',bottom:'240px'}">
        <nut-fixednav un-active-text="NEW" active-text="BACK" v-model:visible="visible" :nav-list="navList" />
    </nut-drag>
    <nut-overlay v-model:visible="leftDialogShow">
        <nut-cell-group class="left-dialog">
            <nut-cell class="hover-change" to="/test">个人中心</nut-cell>
            <nut-cell class="hover-change">我的草稿</nut-cell>
            <nut-cell class="hover-change">创作中心</nut-cell>
            <nut-cell class="hover-change">浏览记录</nut-cell>
            <nut-cell class="hover-change">发现好友</nut-cell>
        </nut-cell-group>
    </nut-overlay>
</div>
</template>

<script lang="ts">
import {
    computed,
    getCurrentInstance,
    onBeforeUnmount,
    onMounted,
    reactive,
    ref
} from 'vue';
import Tweet from '@/components/Tweet.vue';
import {
    watch
} from 'original-fs';
import {
    fa
} from 'element-plus/lib/locale';
import { get } from '@/api/request';
import { HttpManager } from '@/api';

export default {
    components: {
        Tweet
    },
    setup() {
        const cookie = getCurrentInstance().appContext.config.globalProperties.$cookies;
        const tweets = ref([]);
        const visible = ref(false);
        const navShow = ref(false);
        const leftDialogShow = ref(false);
        const overlayShow = ref(false);
        const head = ref(null);
        const user = reactive({
            username:cookie.get("username") ? cookie.get("username") : "尚未登录",
            account:cookie.get("account") ? cookie.get("account") : "",
            icon:cookie.get("icon") ? cookie.get("icon") : "img/icon/default.png"
        });
        const navList = reactive([{
                id: 1,
                text: '文字',
                icon: 'https://img11.360buyimg.com/imagetools/jfs/t1/117646/2/11112/1297/5ef83e95E81d77f05/daf8e3b1c81e3c98.png'
            },
            {
                id: 2,
                text: '图文',
                icon: 'https://img12.360buyimg.com/imagetools/jfs/t1/147573/29/1603/1721/5ef83e94E1393a678/5ddf1695ec989373.png'
            }
        ]);
        const methods = {
            handleScroll() {
                if (head.value == null) return;
                else if (head.value.getBoundingClientRect().top < -240) navShow.value = true;
                else navShow.value = false;
            }
        }
        
        async function getData() {
            const result = (await HttpManager.getAllTweets()) as ResponseBody;
            tweets.value = result.data;
        }

        onMounted(() => {
            window.addEventListener('scroll', methods.handleScroll, true);
            getData();
        })
        onBeforeUnmount(() => {
            window.removeEventListener('scroll', methods.handleScroll)
        })
        return {
            tweets,
            visible,
            navShow,
            leftDialogShow,
            overlayShow,
            head,
            user,
            navList,
            attachImageUrl: HttpManager.attachImageUrl,
            ...methods
        }
    }
}
</script>

<style scoped>
.header {
    position: fixed;
    top: 0%;
    width: 100%;
    height: 6%;
}

.comm-head {
    background: url('../assets/images/comm_head.jpg');
    background-size: auto 100%;
    overflow: hidden;
    height: 30%;
}

.refresh {
    left: 3%;
    top: 4%;
}

.name {
    position: absolute;
    right: 25%;
    top: 27%;
    color: white;
    font-family: '宋体';
}

.head-icon {
    position: absolute;
    top: 25%;
    right: 5%;
    width: 16%;
    border-radius: 5px;
}

.left-dialog {
    position: fixed;
    top: 4%;
    left: 0%;
}

.nut-cell:hover {
    background-color: #f5f5f5;
}
</style>
