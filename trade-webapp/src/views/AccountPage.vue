<template>
<div class="main-page">
    <div class="account-panel">
        <router-link :to="infoUrl">
            <nut-row>
                <nut-col :span="8" class="panel-left">
                    <img class="head-icon" :src="attachImageUrl(user.icon)" />
                </nut-col>
                <nut-col :span="16" class="panel-right">
                    <div>
                        <div class="name">{{user.username}}</div>
                        <div class="account-number">{{user.account}}</div>
                    </div>
                </nut-col>
            </nut-row>
        </router-link>
    </div>
    <div class="account-currency">
        <nut-row>
            <router-link to="detail">
                <nut-col :span="12" class="currency-left">
                    <div>
                        <div>
                            <nut-icon name="people" size="12"></nut-icon>
                            <span> 合计资产</span>
                        </div>
                        <div>
                            <nut-icon name="retweet" size="12"></nut-icon>
                            <span> 交易明细</span>
                        </div>
                    </div>
                </nut-col>
            </router-link>
            <router-link :to="routerUrl">
                <nut-col :span="12" class="currency-right">
                    <nut-icon name="checked"></nut-icon>
                    <div>钱包</div>
                </nut-col>
            </router-link>
        </nut-row>
    </div>
    <div class="trade-details">
        <nut-navbar class="sticky">
            <template #content>
                <nut-tabs v-model="detailsType" @click="changeTabList" auto-height>
                    <nut-tabpane pane-key="cc" title="持仓">
                    </nut-tabpane>
                    <nut-tabpane pane-key="ls" title="历史">
                    </nut-tabpane>
                </nut-tabs>
            </template>
        </nut-navbar>
        <router-view></router-view>
    </div>
</div>
</template>

<script lang="ts">
import {
    getCurrentInstance,
    reactive,
    toRefs
} from 'vue';
import {
    useRouter
} from 'vue-router';
import {
    HttpManager
} from '@/api';
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
            detailsType: "cc",
            routerUrl: cookie.get("account") ? "/wallet" : "/login",
            infoUrl: cookie.get("account") ? "/info" : "/login"
        });
        const methods = {
            changeTabList(tab: any) {
                state.detailsType = tab.paneKey as string;
                router.push(state.detailsType)
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
.main-page {
    background: linear-gradient(#810c07, #fff);
}

.account-panel {
    height: 30%;
    background: url('../assets/images/account_head.png');
    background-size: auto 100%;
}

.panel-left {
    height: 100%;
}

.panel-right {
    height: 100%;
    align-items: center;
    display: flex;
}

.head-icon {
    position: relative;
    top: 50%;
    left: 50%;
    transform: translate(-50%, -50%);
    width: 50%;
    border-radius: 50%;
    border: 1px solid #fff;
}

.account-currency {
    text-align: center;
    margin: 10px;
    height: 12%;
    border-radius: 10px;
    color: #fff;
    background-color: rgb(218, 164, 16);
}

.account-currency *:hover {
    background-color: rgba(196, 156, 98, 1);
}

.currency-left {
    height: 100%;
    align-items: center;
    justify-content: center;
    line-height: 30px;
    border-radius: 10px;
    display: flex;
}

.currency-right {
    height: 100%;
    align-items: center;
    justify-content: center;
    border-radius: 10px;
    display: flex;
}

.trade-details {
    margin: 10px;
    border-radius: 10px;
}

.sticky {
    position: sticky;
    top: 8px;
}

.trade-details * {
    border-radius: 10px 0;
}
</style>
