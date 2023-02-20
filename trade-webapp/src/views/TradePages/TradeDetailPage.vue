<template>
<div>
    <nut-navbar class="top-box">
        <template #left>
            <router-link to="/account">返回</router-link>
        </template>
        <template #content>
            <div>交易明细</div>
        </template>
    </nut-navbar>
    <query-page class="main-content"></query-page>
</div>
</template>

    
<script lang="ts">
import QueryPage from './QueryPage.vue';
export default {
  components: { QueryPage }
}
</script>

    
<style scoped>
.top-box {
    background-color: #bd3124;
    color: #fff;
    height: 6%;
    font-size: 20px;
    line-height: 50px;
    box-shadow: 3px 2px #cfcfcf;
}

.main-content {
    position: fixed;
    height: 94%;
    width: 100%;
    bottom: 0%;
    overflow-y: scroll;
}
</style>