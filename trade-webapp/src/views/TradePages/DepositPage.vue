<template>
    <div class="content">
        <nut-navbar class="top-box">
            <template #left>
                <router-link to="/trade">返回</router-link>
            </template>
            <template #content>
                <div>交易托管</div>
            </template>
        </nut-navbar>
        <nut-noticebar text="请输入买入限定和卖出限定的值，将会为您自动买入低于买入限定价格的虚拟币，自动卖出高于卖出限定价格的虚拟币！">
        </nut-noticebar>
        <div class="menu">
            <nut-menu active-color="#FA2C19">
                <nut-menu-item v-model="state.value1" :options="state.options1" />
                <nut-menu-item v-model="state.value2" @change="handleChange" :options="state.options2" />
            </nut-menu>
        </div>
        <nut-form class="form1">
            <nut-form-item label="买入限定" font-size="20px">
                <nut-input v-model="basicData.price1" class="nut-input-text" placeholder="请输入买入最高价格" type="text" />
            </nut-form-item>
            <nut-form-item label="卖出限定">
                <nut-input v-model="basicData.price2" class="nut-input-text" placeholder="请输入卖出最低价格" type="text" />
            </nut-form-item>
            <div style="margin: 16px" class="button">
                <nut-button size="large" type="primary" id="login">确定</nut-button>
            </div>
        </nut-form>
        <div class="table">
            <nut-table :columns="columns" :data="data2" :striped="striped" :bordered="false"></nut-table>
        </div>
    </div>
</template>
<script lang="ts">
import { ref, reactive, toRefs, createApp } from 'vue';
import { Navbar, Tabs, TabPane, ConfigProvider, Input, Button } from '@nutui/nutui';
export default {
    props: {},
    setup() {
        const basicData = reactive({
            price1: '',
            price2: ''
        });
        const state = reactive({
            options1: [
                { text: 'USDT', value: 0 },
                { text: 'BTC', value: 1 },
                { text: 'ETH', value: 2 },
                { text: 'HT', value: 3 },
                { text: 'HUSD', value: 4 }
            ],
            options2: [
                { text: '请选择', value: 'a' },
                { text: 'HT', value: 'ht' },
                { text: 'NEO', value: 'neo' },
                { text: 'XRP', value: 'xrp' },
                { text: 'IOST', value: 'iost' },
            ],
            value1: 0,
            value2: 'a'
        });

        const handleChange = val => {
            console.log('val', val);
        }
        const state1 = reactive({
            striped: true,
            columns: [
                {
                    title: '虚拟币',
                    key: 'displayName',
                    align: 'center'
                },
                {
                    title: '买入限定',
                    key: 'price1',
                    align: 'center'
                },
                {
                    title: '卖出限定',
                    key: 'price2',
                    align: 'center'
                },
            ],
            data2: [
                {
                    displayName: 'Tom',
                    price1: '男',
                    price2: '小学'
                },
                {
                    displayName: 'Lucy',
                    price1: '女',
                    price2: '本科'
                },
                {
                    displayName: 'Jack',
                    price1: '男',
                    price2: '高中'
                }
            ]
        });

        return {
            basicData,
            state,
            handleChange,
            ...toRefs(state1)
        };
    }

}
</script>
<style scoped>
* {
    animation-fill-mode: none;
}

.content {
    background: url("http://img.mp.itc.cn/upload/20161025/c688dfebd47a465a899c47a242600bbc_th.jpg");
    width: 100%;
    height: 100%;
    position: fixed;
    background-size: 100% 100%;
}

.top-box {
    background-color: #bd3124;
    color: #fff;
    height: 6%;
    font-size: 20px;
    line-height: 50px;
    box-shadow: 3px 2px #cfcfcf;
}

.menu {
    width: 90%;
    margin: 5vh auto;
    box-shadow: 2px 2px grey;
}

.trade-view {
    margin: 0 10px;
}

.form1 {
    width: 90%;
    margin: 5vh 5vw;
    font-size: 20px;
}

.top {
    background-color: #bd3124;
    color: #fff;
    height: 6%;
    font-size: 20px;
    display: grid;
    text-align: center;
    align-items: center;
}

.tip {
    margin: 3vh 3.5vw;
    background-color: #FBF8DC;
    width: 90vw;
    height: 25vh;
    border-radius: 3vh;
    border: 1vh double #CFCFCF;
    box-shadow: 2px 2px 5px #f14f40;
}

.table {
    width: 90%;
    margin: 5vh auto;
}
</style>