<template>
<div>
    <router-view class="main-block"></router-view>
    <nut-tabbar @tab-switch="tabSwitch" class="footer">
        <nut-tabbar-item to="/" tab-title="行情" icon="category"></nut-tabbar-item>
        <nut-tabbar-item to="community" tab-title="社区" icon="find"></nut-tabbar-item>
        <nut-tabbar-item to="chat" tab-title="CHAT" icon="message"></nut-tabbar-item>
        <nut-tabbar-item to="trade" tab-title="交易" icon="dshop"></nut-tabbar-item>
        <nut-tabbar-item to="account" tab-title="账户" icon="my"></nut-tabbar-item>
    </nut-tabbar>
</div>
</template>

<script>
export default {

}
</script>

<style scoped>
.main-block {
    position: fixed;
    top: 0%;
    bottom: 6%;
    width: 100%;
    overflow-y: scroll;
}

.footer {
    position: fixed;
    height: 6%;
    bottom: 0%;
}
</style>
