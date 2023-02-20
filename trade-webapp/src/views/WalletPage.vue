<template>
<div>
    <nut-navbar class="top-box" @on-click-back="back" @on-click-title="title">
        <template #left>
            <router-link to="/account">返回</router-link>
        </template>
        <template #content>
            <div>钱包</div>
        </template>
    </nut-navbar>
    <img class="icon" src="@/assets/icons/trade.png" />
    <div class="balance">${{ format(accountData.balance) }}</div>
    <div class="quote-currency">
        <nut-grid :column-num="3">
            <nut-grid-item :text="format(accountData.usdt)">
                <template #icon>USDT</template>
            </nut-grid-item>
            <nut-grid-item :text="format(accountData.btc)">
                <template #icon>BTC</template>
            </nut-grid-item>
            <nut-grid-item :text="format(accountData.eth)">
                <template #icon>ETH</template>
            </nut-grid-item>
            <nut-grid-item :text="format(accountData.ht)">
                <template #icon>HT</template>
            </nut-grid-item>
            <nut-grid-item :text="format(accountData.husd)">
                <template #icon>HUSD</template>
            </nut-grid-item>
            <nut-grid-item :text="format(accountData.usdd)">
                <template #icon>USDD</template>
            </nut-grid-item>
        </nut-grid>
    </div>
    <nut-button class="recharge" type="primary" @click="open(0)">充值</nut-button>
    <nut-button class="cashout" type="primary" plain @click="open(1)">提现</nut-button>
    <div class="feedback">常见问题</div>
    <nut-popup position="bottom" closeable round :style="{ height: '320px' }" v-model:visible="showRound">
        <div class="input-number">{{amount}}</div>
        <nut-numberkeyboard type="rightColumn" v-model:visible="visible" :custom-key="customKey" :confirm-text="confirmText" @input="input" @delete="deleteNumber" @close="close" @confirm="confirm">
        </nut-numberkeyboard>
    </nut-popup>
</div>
</template>

<script lang="ts">
import {
    reactive,
    toRefs,
    computed,
    getCurrentInstance
} from 'vue';
import {
    Toast
} from '@nutui/nutui';
import {
    HttpManager
} from '@/api';
export default {
    props: {},
    setup() {
        const cookie = getCurrentInstance().appContext.config.globalProperties.$cookies;
        const state = reactive({
            showRound: false,
            visible: false,
            customKey: reactive(['.']),
            confirmText: computed(() => state.status == 0 ? '充值' : '提现'),
            accountData: {},
            amount: '',
            status: 0,
            content: ''
        });

        const methods = {
            showKeyBoard() {
                state.visible = true;
            },
            deleteNumber() {
                if (state.amount.length > 0) state.amount = state.amount.slice(0, state.amount.length - 1);
            },
            input(number) {
                if (state.amount.indexOf('.') != -1 && number == '.') return;
                else if (state.amount.indexOf('.') != -1 && state.amount.length - state.amount.indexOf('.') > 2) return;
                state.amount += number;
            },
            open(status) {
                state.showRound = true;
                state.visible = true;
                state.status = status;
            },
            close() {
                state.visible = false;
                state.showRound = false;
                if (state.status == 0) asyncRecharge();
                else asyncCash();
            },
            format: (amount) => amount ? amount.toFixed(2) : '0.00'
        }

        const asyncRecharge = async () => {
            let params = new URLSearchParams();
            params.append("account", cookie.get("account"));
            params.append("recharge", state.amount);
            const response = await HttpManager.recharge(params) as ResponseBody;
            if (response.success) Toast.success(response.message);
            else Toast.fail(response.message);
            getAccount();
            state.amount = '';
        }

        const asyncCash = async () => {
            let params = new URLSearchParams();
            params.append("account", cookie.get("account"));
            params.append("cash", state.amount);
            const response = await HttpManager.cash(params) as ResponseBody;
            if (response.success) Toast.success(response.message);
            else Toast.fail(response.message);
            getAccount();
            state.amount = '';
        }

        const getAccount = async () => {
            let params = new URLSearchParams();
            params.append("account", cookie.get("account"));
            const result = await HttpManager.getAccount(params) as ResponseBody;
            state.accountData = result.data;
        }

        getAccount();

        return {
            ...methods,
            ...toRefs(state)
        };
    }
};
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

.icon {
    position: absolute;
    top: 20%;
    left: 50%;
    transform: translate(-50%);
    width: 20%;
}

.balance {
    position: absolute;
    top: 30%;
    left: 50%;
    transform: translate(-50%);
    font-family: '黑体';
    font-size: 40px;
    font-weight: bolder;
}

.quote-currency {
    position: absolute;
    width: 100%;
    top: 45%;
    color: red;
}

.recharge {
    position: absolute;
    top: 76%;
    left: 50%;
    width: 40%;
    transform: translate(-50%);
}

.cashout {
    position: absolute;
    top: 84%;
    left: 50%;
    width: 40%;
    transform: translate(-50%);
}

.feedback {
    position: absolute;
    bottom: 5%;
    width: 100%;
    color: rgb(24, 24, 97);
    font-size: 12px;
    text-align: center;
}

.input-number {
    position: relative;
    top: 5%;
    left: 5%;
    font-family: '黑体';
    font-size: 40px;
    font-weight: bolder;
}
</style>
