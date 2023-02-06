<template>
<div class="infiniteUl" id="refreshScroll">
    <nut-collapse v-model:active="activeNames" icon="down-arrow" class="product-details">
        <nut-infiniteloading pull-icon="JD" container-id="refreshScroll" :use-window="false" :is-open-refresh="true" :has-more="refreshHasMore" @load-more="refreshLoadMore" @refresh="refresh">
            <nut-collapse-item v-for="item, idx in refreshList" :name="idx" :key="idx">
                <template v-slot:mTitle>
                    <span class="code">{{item.at}}</span>
                    &emsp;
                    <span class="name">{{item.dn}}</span>
                    &emsp;
                    <span class="price-up" v-if="item.rate > 0">{{item.price}}</span>
                    <span class="price-down" v-else-if="item.rate < 0">{{item.price}}</span>
                    <span v-else>{{item.price}}</span>
                </template>
                <template v-slot:sTitle>
                    <nut-trendarrow :show-text-color="false" :rate="item.rate" class="rate" />
                </template>
                <div>股票详细信息 待开发...</div>
            </nut-collapse-item>
        </nut-infiniteloading>
    </nut-collapse>
</div>
</template>

<script>
import {
    ref,
    reactive,
    onMounted,
    toRefs
} from 'vue';
import {
    Toast
} from '@nutui/nutui';
export default {
    setup(props) {
        const activeNames = ref([]);
        const refreshHasMore = ref(true);
        const data = reactive({
            refreshList: []
        });
        const refreshLoadMore = done => {
            setTimeout(() => {
                const curLen = data.refreshList.length;
                for (let i = curLen; i < curLen + 30; i++) {
                    data.refreshList.push(
                        `${i}`
                    );
                }
                if (data.refreshList.length > 30) refreshHasMore.value = false;
                done()
            }, 500);
        };

        const refresh = (done) => {
            setTimeout(() => {
                Toast.success('刷新成功');
                done()
            }, 1000)
        }
        const init = () => {
            for (let i = 0; i < 30; i++) {
                data.refreshList.push(`${i}`);
            }
        }
        onMounted(() => {
            init()
        });
        return {
            activeNames,
            refreshLoadMore,
            refreshHasMore,
            refresh,
            ...toRefs(data)
        };
    }
}
</script>

<style>
.infiniteUl {
    height: 100%;
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
</style>
