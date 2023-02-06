<template>
  <div class="main-block">
    <yin-header class="header"></yin-header>
    <div class="content-box" :class="{ 'content-collapse': collapse }">
      <router-view class="main" :style="{width: width}"></router-view>
    </div>
    <yin-footer class="footer"></yin-footer>
    <el-card v-if="codeShow" class="code-box">
      <el-image :src="require('../assets/images/QRcode.png')"></el-image>
    </el-card>
    <el-card class="qr-code" @click="codeShow =! codeShow">
      <el-icon><Position/></el-icon>
    </el-card>
  </div>
</template>

<script lang="ts" setup>
import { ref, watch } from "vue";
import YinHeader from "@/components/layouts/YinHeader.vue";
import YinAudio from "@/components/layouts/YinAudio.vue";
import YinAside from "@/components/layouts/YinAside.vue";
import YinFooter from "@/components/layouts/YinFooter.vue";
import emitter from "@/utils/emitter";

const collapse = ref(false);
emitter.on("collapse", (msg) => {
  collapse.value = msg as boolean;
});

const width =  ref("80%");
const codeShow = ref(false);

function flexible() {
  if (document.body.clientWidth < 500) {
    width.value = "100%";
  }
}

flexible();
</script>

<style scoped>
* {
  transition: all 0.2s;
  animation: fadeInAnimation ease-in 0.5s;
  animation-iteration-count: 1;
  animation-fill-mode: forwards;
}
.main-block {
  width: 100%;
}
.content-box {
  width:100%;
  transform: translateX(-50%);
  position: fixed;
  left: 50%;
  right: 0;
  top: 5%;
  bottom: 5%;
  overflow-y: scroll;
  transition: left 0.3s ease-in-out;
  padding: 20px;
  background-image: url(../assets/images/bg.png);
}

.main {
  position: relative;
  left:50%;
  transform: translateX(-50%);
}

.content-collapse {
  left: 65px;
}
.header {
  position:fixed;
  top:0;
  height:8%;
  width:100%;
  margin: 0%;
}
.code-box {
  position:fixed;
  bottom:9%;
  right:0;
  width:200px;
  height:200px;
}
.qr-code {
  position: fixed;
  height:60px;
  width:60px;
  bottom:4%;
  right:0;
  border: 1px solid;
  border-radius:50%;
  transform: translate(50%);
}
.qr-code:hover {
  background-color:rgb(191, 193, 196);
}
.footer {
  position:fixed;
  bottom:0;
  height:8%;
  width:100%;
  margin: 0%;
}
</style>
