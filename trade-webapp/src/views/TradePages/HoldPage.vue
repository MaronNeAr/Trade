<template>
<div class="main-box">
    <nut-table :columns="columns" :data="tableData" :striped="striped" :bordered="false"></nut-table>
</div>
</template>

<script lang="ts">
import {
    getCurrentInstance,
    reactive,
    toRefs
} from 'vue';
import {
    HttpManager
} from '@/api';
import axios from 'axios';
export default {
    props: {},
    setup() {
        let baseURL = 'https://api-aws.huobi.pro';
        const cookie = getCurrentInstance().appContext.config.globalProperties.$cookies;
        const state = reactive({
            striped: true,
            columns: [{
                    title: '虚拟币',
                    key: 'displayName',
                    align: 'center'
                },
                {
                    title: '持有量',
                    key: 'inventory',
                    align: 'center'
                },
                {
                    title: '当前价格',
                    key: 'price',
                    align: 'center'
                },
                {
                    title: '市值',
                    key: 'total',
                    align: 'center'
                }
            ],

            tableData: []
        });
        const methods = {
            getData: async () => {
                if (!cookie.get("account")) return;
                let params = new URLSearchParams();
                params.append("account", cookie.get("account"));
                const result = await HttpManager.getPosition(params) as ResponseBody;
                if (result.success) state.tableData = result.data;
                else console.log(result.message);
            },
            getPrice: async () => {
                await axios.get(baseURL + "/market/tickers").then((response) => {
                    if (response.data && response.data.data) {
                        const result = response.data.data;
                        state.tableData = state.tableData.map(item => {
                            const matchedItem = result.find(obj => obj.symbol.indexOf(item.displayName.toLowerCase()) != -1);
                            if (matchedItem) {
                                return {
                                    ...item,
                                    price: matchedItem.close,
                                    total: (matchedItem.close * item.inventory).toFixed(2)
                                };
                            } else {
                                return item;
                            }
                        });
                        let params = new URLSearchParams();
                        params.append("content", JSON.stringify(result));
                        HttpManager.postTickers(params) ;
                    }
                }).catch(async (error) => {
                    const result = await HttpManager.getTickers() as ResponseBody;
                    if (result.success && result.data) {
                        // console.log(result.data);
                        const resultData = JSON.parse(result.data);
                        state.tableData = state.tableData.map(item => {
                            const matchedItem = resultData.find(obj => obj.symbol.indexOf(item.displayName.toLowerCase()) != -1);
                            if (matchedItem) {
                                return {
                                    ...item,
                                    price: matchedItem.close,
                                    total: (matchedItem.close * item.inventory).toFixed(2)
                                };
                            } else {
                                return item;
                            }
                        });
                    } else console.log("暂无价格数据，请连接vp");
                });
            }
        }
        methods.getData();
        methods.getPrice();
        return {
            ...toRefs(state),
            ...methods
        };
    }
};
</script>

<style scoped>
.main-box {
    border-radius: 10px;
    overflow: hidden;
}
</style>
