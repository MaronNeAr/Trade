<template>
<div>
    <nut-searchbar background="linear-gradient(to right, #fff, red)" v-model="searchValue" placeholder="请输入产品代码/简拼" class="search">
        <template v-slot:leftout>
            <nut-icon @click="clickLeft" size="20" name="horizontal"></nut-icon>
        </template>
        <template v-slot:leftin>
            <nut-icon size="14" name="search2"></nut-icon>
        </template>
        <template v-slot:rightin>
            <nut-icon size="20" name="uploader"></nut-icon>
        </template>
    </nut-searchbar>
    <nut-navbar class="header">
        <template #content>
            <nut-tabs v-model="productType" @click="changeTabList" auto-height>
                <nut-tabpane pane-key="usdt" title="USDT">
                </nut-tabpane>
                <nut-tabpane pane-key="btc" title="BTC">
                </nut-tabpane>
                <nut-tabpane pane-key="eth" title="ETH">
                </nut-tabpane>
                <nut-tabpane pane-key="ht" title="HT">
                </nut-tabpane>
                <nut-tabpane pane-key="husd" title="HUSD">
                </nut-tabpane>
                <nut-tabpane pane-key="usdd" title="USDD">
                </nut-tabpane>
            </nut-tabs>
        </template>
    </nut-navbar>
    <router-view :type="productType"></router-view>
</div>
</template>

<script lang="ts">
import {
    ref
} from 'vue';
import {
    useRouter
} from 'vue-router';
export default {
    components: {},
    setup() {
        const router = useRouter();
        const productType = ref('usdt');
        const searchValue = ref('');
        const methods = {
            back() {
                alert('Click Back');
            },
            changeTabList(tab: any) {
                productType.value = tab.paneKey as string;
                // router.push(productType.value)
            },
            clickLeft() {
                alert("back");
            }
        };
        return {
            searchValue,
            productType,
            ...methods
        };
    }
}
</script>

<style scoped>
.search {
    position: relative;
    width: 100%;
    height: 6%;
}

.header {
    position: sticky;
    top: -1px;
    width: 100%;
}

.content {
    height: 10%;
}
</style>
