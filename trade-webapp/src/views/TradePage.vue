<template>
<div>
    <div class="top-box">普通交易</div>
    <nut-navbar class="header">
        <template #content>
            <nut-tabs v-model="state.tab" @click="changeTabList" auto-height>
                <nut-tabpane pane-key="buy" title="买入"></nut-tabpane>
                <nut-tabpane pane-key="sale" title="卖出"></nut-tabpane>
                <!-- <nut-tabpane pane-key="rem" title="撤单"></nut-tabpane> -->
                <nut-tabpane pane-key="hold" title="持仓"></nut-tabpane>
                <nut-tabpane pane-key="query" title="查询"></nut-tabpane>
            </nut-tabs>
        </template>
    </nut-navbar>
    <router-view class="trade-view"></router-view>
</div>
</template>

<script lang="ts">
import {
    ref,
    reactive,
    onMounted,
    toRefs
} from 'vue';
import {
    Toast
} from '@nutui/nutui';
import { useRouter } from 'vue-router';
export default {
    setup(props) {
        const router = useRouter();
        const state = reactive({
            tab: 'buy'
        });
        const methods = {
            changeTabList(tab: any) {
                state.tab = tab.paneKey as string;
                router.push(state.tab);
            }
        }
        return {
            state,
            ...methods
        };
    }
}
</script>

<style scoped>
.trade-view {
    margin: 0 10px;
}
.top-box {
    background-color: #bd3124;
    color: #fff;
    height: 6%;
    font-size: 20px;
    display: grid;
    text-align: center;
    align-items: center;
    box-shadow: 3px 2px #cfcfcf;
}

.content {
    height: 500px;
}
</style>
