<template>
<div>
    <nut-row>
        <nut-col :span="16">
            <nut-menu>
                <nut-menu-item v-model="state.qcType" @change="handleQcChange" @open="canvasShow = false" @close="canvasShow = true" :options="state.qcOptions"></nut-menu-item>
                <nut-menu-item v-model="state.bcType" @change="handleBcChange" @open="canvasShow = false" @close="canvasShow = true" :options="state.bcOptions"></nut-menu-item>
            </nut-menu>
        </nut-col>
        <nut-col :span="8">
            <nut-input class="searchBar" right-icon="search" v-model="state.searchText" placeholder="搜索"></nut-input>
        </nut-col>
    </nut-row>
    <br />
    <nut-row v-if="canvasShow">
        <nut-col :span="13">
            <k-canvas-base :tradePair="tradePair" class="k-canvas test-border"></k-canvas-base>
            <br />
            <el-input-number class="line-style" size="large" v-model="state.price"  :step="Math.max(state.minPrice / 100, 0.000001)" :precision="6" :min="state.minPrice"></el-input-number>
            <br />
            <br />
            <el-input-number class="line-style" size="large" v-model="state.number" :step="Math.max(state.maxNumber / 100, 0.01)" :precision="2" :min="0" :max="state.maxNumber"></el-input-number>
            <br />
            <br />
            <nut-button class="line-style" type="primary">买入</nut-button>
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
</div>
</template>

<script lang="ts">
import KCanvasBase from '@/components/KCanvasBase.vue'
import {
    computed,
    onUpdated,
    reactive,
    ref,
    watch
} from 'vue'
import {
    HttpManager
} from '@/api';
import {
    SearchBar
} from '@nutui/nutui';
import axios from 'axios';
export default {
    components: {
        KCanvasBase
    },
    setup() {
        let baseURL = 'https://api-aws.huobi.pro';
        const canvasShow = ref(true);
        const searchPanelShow = computed(() => {
            return true;
        });
        const tradePair = ref("none");
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
            bcType: 'none',
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
            maxPrice:10000
        });
        const handleQcChange = val => {
            state.bcType = 'none';
            state.searchText = '';
            getCurrency();
        };
        const handleBcChange = val => {
            tradePair.value = val;
            getDepth();
            getTickers();
        };
        watch(() => state.searchText, () => {
            state.bcType = 'none';
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
            await axios.get(baseURL + '/market/depth?symbol=' + state.bcType + '&depth=5&type=step0').then(response => {
                state.buyDepth = response.data.tick.bids;
                state.sellDepth = response.data.tick.asks;
                let params = new URLSearchParams();
                params.append("content", JSON.stringify(response.data.tick));
                HttpManager.postDepth(state.bcType, params);
            }).catch(async error => {
                // console.log(error);
                const result = (await HttpManager.getDepth(state.bcType) as ResponseBody);
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
        const getTickers = async() => {
            await axios.get(baseURL + '/market/detail/merged?symbol=' + state.bcType).then(response => {
                let tick = response.data.tick;
                state.price = tick.close;
                state.minPrice = tick.ask[0];
                state.number = tick.ask[1];
                state.maxNumber = tick.ask[1];
                let params = new URLSearchParams();
                params.append("content", JSON.stringify(response.data.tick));
                HttpManager.postTicker(state.bcType, params);
            }).catch(async error => {
                const result = (await HttpManager.getTicker(state.bcType) as ResponseBody);
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
            canvasShow,
            searchPanelShow,
            tradePair,
            state,
            handleQcChange,
            handleBcChange
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
