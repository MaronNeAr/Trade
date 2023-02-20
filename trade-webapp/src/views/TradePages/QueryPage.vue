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
export default {
    props: {},
    setup() {
        const cookie = getCurrentInstance().appContext.config.globalProperties.$cookies;
        const state = reactive({
            striped: true,
            columns: [
                {
                    title: '交易对',
                    key: 'cc',
                    align: 'center'
                },
                {
                    title: '交易价格',
                    key: 'price',
                    align: 'right'
                },
                {
                    title: '数量',
                    key: 'size',
                    align: 'left'
                },
                {
                    title: '交易时间',
                    key: 'time',
                    align: 'center'
                },

            ],
            tableData: []
        });
        const methods = {
            getData: async () => {
                if (!cookie.get("account")) return;
                let params = new URLSearchParams();
                params.append("account", cookie.get("account"));
                const result = await HttpManager.getTradeDetail(params) as ResponseBody;
                // console.log(result);
                if (result.success) {
                    state.tableData = result.data;
                    state.tableData.filter((item) => {
                        const date = new Date(item.time);
                        item.cc = item.baseCurrency.toUpperCase() + '/' + item.quoteCurrency.toUpperCase() + '/' + (item.type == 'buy' ? 'B' : 'S');
                        item.time = date.getFullYear() + '/' + (date.getMonth() + 1) + '/' + date.getDate() + ' ' + date.getHours() + ':' + date.getMinutes();
                        // console.log(item.time);
                    });
                } else console.log(result.message);
            }
        }
        methods.getData();
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
