<template>
<div>
    <nut-row>
        <nut-col :span="16">
            <nut-menu>
                <nut-menu-item v-model="state.qcType" @change="handleQcChange" @open="qcMenuShow = false" @close="qcMenuShow = true" :options="state.qcOptions"></nut-menu-item>
                <nut-menu-item v-model="state.scType" @change="handleBcChange" @open="bcMenuShow = false" @close="bcMenuShow = true" :options="state.bcOptions"></nut-menu-item>
            </nut-menu>
        </nut-col>
        <nut-col :span="8">
            <nut-input class="searchBar" right-icon="search" v-model="state.searchText" placeholder="搜索"></nut-input>
        </nut-col>
    </nut-row>
    <br />
    <nut-row v-if="qcMenuShow && bcMenuShow">
        <nut-col :span="13">
            <k-canvas-base :tradePair="tradePair" class="k-canvas test-border"></k-canvas-base>
            <br />
            <el-input-number class="line-style" size="large" v-model="state.price" :step="Math.max(state.minPrice / 100, 0.000001)" :precision="6" :min="state.minPrice"></el-input-number>
            <br />
            <br />
            <el-input-number class="line-style" size="large" v-model="state.number" :step="Math.max(state.maxNumber / 100, 0.01)" :precision="2" :min="0" :max="state.maxNumber"></el-input-number>
            <br />
            <br />
            <nut-button class="line-style" type="primary" @click="buy">买入</nut-button>
        </nut-col>
        <nut-col :span="1">&nbsp;</nut-col>
        <nut-col :span="10" class="market-depth">
            <div class="buy-depth" v-for="item, idx in state.buyDepth" :key="idx">
                <div class="type">卖{{5 - idx}}</div>
                <div class="price green">{{item[0]}}</div>
                <div class="count">{{item[1]}}</div>
            </div>
            <nut-divider></nut-divider>
            <div class="sell-depth" v-for="item, idx in state.sellDepth" :key="idx">
                <div class="type">买{{idx + 1}}</div>
                <div class="price red">{{item[0]}}</div>
                <div class="count">{{item[1]}}</div>
            </div>
        </nut-col>
    </nut-row>
    <div v-if="qcMenuShow && bcMenuShow">
        <nut-divider></nut-divider>
        <hold-page></hold-page>
    </div>
</div>
</template>

<script lang="ts">
import KCanvasBase from '@/components/KCanvasBase.vue'
import HoldPage from './HoldPage.vue';
import {
    computed,
    getCurrentInstance,
    reactive,
    ref,
    watch
} from 'vue'
import {
    HttpManager
} from '@/api';
import axios from 'axios';
import {
    Toast
} from '@nutui/nutui';
export default {
    components: {
        KCanvasBase,
        HoldPage
    },
    setup() {
        let baseURL = 'https://api-aws.huobi.pro';
        const cookie = getCurrentInstance().appContext.config.globalProperties.$cookies;
        const qcMenuShow = ref(true);
        const bcMenuShow = ref(true);
        const searchPanelShow = computed(() => {
            return true;
        });
        const tradePair = ref("none");
        const user = reactive({
            username: cookie.get("username") ? cookie.get("username") : "尚未登录",
            account: cookie.get("account") ? cookie.get("account") : "",
            icon: cookie.get("icon") ? cookie.get("icon") : "img/icon/default.png"
        });
        const state = reactive({
            qcOptions: [{
                    text: 'USDT',
                    value: 'usdt'
                },
                {
                    text: 'BTC',
                    value: 'btc'
                },
                {
                    text: 'ETH',
                    value: 'eth'
                },
                {
                    text: 'HT',
                    value: 'ht'
                },
                {
                    text: 'HUSD',
                    value: 'husd'
                },
                {
                    text: 'USDD',
                    value: 'usdd'
                }
            ],
            bcList: [{
                text: '请选择',
                value: 'none'
            }],
            bcOptions: computed(() => {
                return state.bcList.filter(item => item.text.indexOf(state.searchText) != -1 || item.value.indexOf(state.searchText) != -1 || item.value == 'none');
            }),
            qcType: 'usdt',
            scType: 'none',
            bcType: computed(() => state.scType.replace(state.qcType, '')),
            searchText: '',
            buyDepth: [
                ['--', '--'],
                ['--', '--'],
                ['--', '--'],
                ['--', '--'],
                ['--', '--']
            ],
            sellDepth: [
                ['--', '--'],
                ['--', '--'],
                ['--', '--'],
                ['--', '--'],
                ['--', '--']
            ],
            price: 0,
            minPrice: 0,
            number: 0,
            maxNumber: 0,
            status: false
        });
        const methods = {
            handleQcChange: val => {
                state.scType = 'none';
                state.searchText = '';
                getCurrency();
            },
            handleBcChange: val => {
                tradePair.value = val;
                getDepth();
                getTickers();
            },
            buy: async () => {
                Toast.loading("Waiting...", {
                    duration: 0
                });
                await axios.get(baseURL + '/v2/market-status').then((response) => {
                    if (response.data) state.status = true;
                    else state.status = false;
                }).catch(error => {
                    state.status = false;
                });
                Toast.hide();
                if (user.account == "") {
                    Toast.warn("若要开始交易，请先登录");
                    console.log("若要开始交易，请先登录");
                    return;
                } else if (!state.status) {
                    Toast.warn("若要开始交易，请启用虚拟服务专用网络（VPN）");
                    console.log("若要开始交易，请启用虚拟服务专用网络（VPN）");
                    return;
                }
                let params = new URLSearchParams();
                params.append("bc", state.bcType);
                params.append("qc", state.qcType);
                params.append("price", state.price);
                params.append("size", state.number);
                params.append("account", user.account);
                const response = await HttpManager.buy(params) as ResponseBody;
                if (response.success) Toast.success(response.message);
                else Toast.warn(response.message);
            }
        };
        watch(() => state.searchText, () => {
            state.scType = 'none';
        });
        const getCurrency = async () => {
            state.bcList = [{
                text: '请选择',
                value: 'none'
            }];
            const result = await HttpManager.getCurrency() as ResponseBody;
            let currencies = [];
            if (result.data) currencies = JSON.parse(result.data);
            currencies.sort((o1, o2) => o1.toa - o2.toa);
            currencies.forEach(item => {
                if (item.qc == state.qcType && item.state == 'online') {
                    state.bcList.push({
                        text: item.bcdn,
                        value: item.sc
                    });
                }
            });
            // state.bcList.sort((o1, o2) => o1.text > o2.text ? 1 : -1);
        };
        const getDepth = async () => {
            await axios.get(baseURL + '/market/depth?symbol=' + state.scType + '&depth=5&type=step0').then(response => {
                state.buyDepth = response.data.tick.bids;
                state.sellDepth = response.data.tick.asks;
                let params = new URLSearchParams();
                params.append("content", JSON.stringify(response.data.tick));
                HttpManager.postDepth(state.scType, params);
            }).catch(async error => {
                // console.log(error);
                const result = (await HttpManager.getDepth(state.scType) as ResponseBody);
                if (result.data) {
                    state.buyDepth = JSON.parse(result.data).bids;
                    state.sellDepth = JSON.parse(result.data).asks;
                } else {
                    state.buyDepth = [
                        ['--', '--'],
                        ['--', '--'],
                        ['--', '--'],
                        ['--', '--'],
                        ['--', '--']
                    ];
                    state.sellDepth = [
                        ['--', '--'],
                        ['--', '--'],
                        ['--', '--'],
                        ['--', '--'],
                        ['--', '--']
                    ];
                    console.log("暂无市场深度数据，请连接vp");
                }
            });
        };
        const getTickers = async () => {
            await axios.get(baseURL + '/market/detail/merged?symbol=' + state.scType).then(response => {
                let tick = response.data.tick;
                state.price = tick.close;
                state.minPrice = tick.ask[0];
                state.number = tick.ask[1];
                state.maxNumber = tick.ask[1];
                state.status = true;
                let params = new URLSearchParams();
                params.append("content", JSON.stringify(response.data.tick));
                HttpManager.postTicker(state.scType, params);
            }).catch(async error => {
                const result = (await HttpManager.getTicker(state.scType) as ResponseBody);
                if (result.data) {
                    let tick = JSON.parse(result.data);
                    state.price = tick.close;
                    state.minPrice = tick.ask[0];
                    state.number = tick.ask[1];
                    state.maxNumber = tick.ask[1];
                } else console.log("暂无聚合行情数据，请连接vp");
            });
        }
        getCurrency();
        getDepth();
        getTickers();
        return {
            qcMenuShow,
            bcMenuShow,
            searchPanelShow,
            tradePair,
            state,
            ...methods
        }
    }
}
</script>

<style scoped>
.searchBar {
    height: 50px;
    line-height: 30px;
    border-radius: 0 5px 5px 0;
    box-shadow: 4px 0 3px 0px #dbdbdb;
}

.k-canvas {
    border-radius: 10px;
    background-color: #2c537c;
    height: 150px;
    border: 1px rgb(149, 197, 240);
    box-shadow: 3px 2px #cfcfcf;
    animation-fill-mode: none;
}

.line-style {
    width: 100%;
    border-radius: 5px;
    box-shadow: 3px 2px #cfcfcf;
}

.right-box {
    border-radius: 10px;
    height: 100%;
}

.market-depth {
    padding: 10px;
    background-color: aliceblue;
    border-radius: 10px;
    box-shadow: 3px 2px #cfcfcf;
}

.buy-depth,
.sell-depth {
    padding: 0 8px;
    line-height: 21.3px;
    font-size: 12px;
}

.type {
    width: 20%;
    text-align: left;
    overflow: hidden;
    text-overflow: ellipsis;
    white-space: nowrap;
    display: inline-block;
}

.price {
    width: 40%;
    display: inline-block;
    overflow: hidden;
    text-overflow: ellipsis;
    white-space: nowrap;
    text-align: left;
}

.count {
    width: 40%;
    display: inline-block;
    overflow: hidden;
    text-overflow: ellipsis;
    white-space: nowrap;
    text-align: left;
}

.red {
    color: red;
}

.green {
    color: green;
}
</style>
