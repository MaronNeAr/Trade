<template>
<div>
    <nut-searchbar v-model="searchValue" placeholder="请输入产品代码/简拼" class="search">
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
                <nut-tabpane pane-key="cu" title="USDT">
                </nut-tabpane>
                <nut-tabpane pane-key="cn" title="A股">
                </nut-tabpane>
                <nut-tabpane pane-key="hk" title="港股">
                </nut-tabpane>
                <nut-tabpane pane-key="us" title="美股">
                </nut-tabpane>
                <nut-tabpane pane-key="fg" title="期货">
                </nut-tabpane>
            </nut-tabs>
        </template>
    </nut-navbar>
    <router-view></router-view>
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
        const productType = ref('cu');
        const searchValue = ref('');
        const methods = {
            back() {
                alert('Click Back');
            },
            changeTabList(tab: any) {
                productType.value = tab.paneKey as string;
                router.push(productType.value)
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
    height: 5%;
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
