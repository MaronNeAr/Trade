<template>
<div>
    <div class="infiniteUl" id="refreshScroll">
        <nut-collapse v-model:active="activeNames" icon="down-arrow" class="product-details">
            <nut-infiniteloading pull-icon="refresh" container-id="refreshScroll" :use-window="false" :is-open-refresh="true" :has-more="refreshHasMore" @load-more="refreshLoadMore" @refresh="refresh">
                <nut-collapse-item v-for="item, idx in currencies" :name="idx" :key="idx">
                    <template v-slot:mTitle>
                        <span class="code blue">{{item.qcdn}}</span>
                        &emsp;
                        <span class="name">{{item.bcdn}}</span>
                        &emsp;
                        <span class="number green" v-if="item.state == 'online'">{{ item.price }}</span>
                        <span class="number red" v-else>----</span>
                    </template>
                    <template v-slot:sTitle>
                        <div class="green" v-if="item.state == 'online'">{{ item.state }}</div>
                        <div class="red" v-else>{{ item.state }}</div>
                    </template>
                    <k-canvas :tradePair="item.sc" v-if="item.state == 'online'"></k-canvas>
                    <div v-else align="center">{{ getToast(item.toa) }}</div>
                </nut-collapse-item>
            </nut-infiniteloading>
        </nut-collapse>
    </div>
</div>
</template>

<script lang="ts">
import {
    onMounted,
    reactive,
    ref,
    watch
} from 'vue';
import axios from 'axios';
import KCanvas from '@/components/KCanvas.vue'
import {
    Toast
} from '@nutui/nutui';
import {
    writeFile
} from 'node:fs';
import {
    HttpManager
} from '@/api';
export default {
    props: {
        type: String
    },
    components: {
        KCanvas
    },
    setup(props) {
        let baseURL = 'https://api-aws.huobi.pro';
        const activeNames = ref([]);
        const currencies = ref([]);
        const symbols = ref([]);
        const currentSymblols = ref([]);
        const refreshHasMore = ref(true);

        watch(() => props.type, (value) => {
            activeNames.value = [];
            currencies.value = [];
            refreshHasMore.value = true;
            currentSymblols.value = symbols.value.filter((item) => item.qc == value);
            for (let i = 0; i < 20; i++) {
                currencies.value.push(currentSymblols.value.shift());
            }
        });

        const refreshLoadMore = done => {
            setTimeout(() => {
                for (let i = 0; i < 20; i++) {
                    if (currentSymblols.value.length == 0) break;
                    currencies.value.push(currentSymblols.value.shift());
                }
                if (currentSymblols.value.length == 0) refreshHasMore.value = false;
                done()
            }, 500);
        };

        const refresh = (done) => {
            setTimeout(() => {
                Toast.success('刷新成功');
                asyncRefresh();
                done();
            }, 1000)
        };

        async function asyncRefresh() {
            currencies.value = [];
            await axios.get(baseURL + "/v2/settings/common/symbols").then((response) => {
                symbols.value = response.data.data;
                let params = new URLSearchParams();
                params.append("content", JSON.stringify(response.data.data))
                HttpManager.postCurrency(params);
            }).catch(async (error) => {
                console.log(error);
                const result = (await HttpManager.getCurrency() as ResponseBody);
                if (result.data) symbols.value = JSON.parse(result.data);
            });
            // symbols.value = ((await axios.get(baseURL + "/v2/settings/common/symbols")) as ResponseBody).data.data;
            currentSymblols.value = symbols.value.filter((item) => item.qc == props.type);
            for (let i = 0; i < 20; i++) {
                if (currentSymblols.value.length == 0) break;
                currencies.value.push(currentSymblols.value.shift());
            }
            Toast.hide();
        }

        async function getData() {
            const result = (await HttpManager.getCurrency() as ResponseBody);
            if (result.data) {
                symbols.value = JSON.parse(result.data);
                getPrice();
            }
        }

        async function getPrice() {
            const result = await HttpManager.getTickers() as ResponseBody;
            if (result.data) {
                // console.log(result.data);
                const resultData = JSON.parse(result.data);
                symbols.value = symbols.value.map(item => {
                    const matchedItem = resultData.find(obj => obj.symbol == item.sc);
                    if (matchedItem) {
                        return {
                            ...item,
                            price: matchedItem.close
                        };
                    } else {
                        return item;
                    }
                });
            }
            currentSymblols.value = symbols.value.filter((item) => item.qc == props.type);
            for (let i = 0; i < 20; i++) {
                if (currentSymblols.value.length == 0) break;
                currencies.value.push(currentSymblols.value.shift());
            }
            Toast.hide();
        }

        const getToast = (time) => {
            let date = new Date(time);
            return "于北京时间" + date.getFullYear() + "年" + (date.getMonth() + 1) + "月" + date.getDay() + "日" +
                date.getHours() + "时" + date.getMinutes() + "分开始交易";
        }

        Toast.loading("加载数据中...");
        getData();

        return {
            activeNames,
            currencies,
            getToast,
            refresh,
            refreshHasMore,
            refreshLoadMore
        }
    }
}
</script>

<style scoped>
.infiniteUl {
    position: absolute;
    height: 84%;
    width: 100%;
    padding: 0;
    margin: 0;
    overflow-y: auto;
    overflow-x: hidden;
    background: #eee
}

.infiniteLi {
    margin-top: 10px;
    font-size: 14px;
    color: rgba(100, 100, 100, 1);
    text-align: center;
}

.code {
    width: 50px;
    text-align: center;
    display: inline-block;
}

.name {
    color: black;
    width: 50px;
    white-space: nowrap;
    text-overflow: ellipsis;
    display: inline-block;
}

.number {
    font-size: 18px;
    width: 60px;
    text-align: left;
    display: inline-block;
}

.red {
    color: red;
}

.blue {
    color: blue;
}

.green {
    color: green;
}

.rate {
    float: right;
}
</style>
