import Vue from 'vue'
import { createApp } from "vue";
import ElementPlus from "element-plus";
import App from "./App.vue";
import router from "./router";
import store from "./store";
import VueCookies from 'vue-cookies';
import NutUI from "@nutui/nutui";
import "@nutui/nutui/dist/style.css";
import * as echarts from 'echarts'
import VueEcharts from 'vue-echarts'
import "./assets/css/main.css";
import * as ElementPlusIconsVue from '@element-plus/icons-vue'

import { Store } from "vuex";
declare module "@vue/runtime-core" {
  interface State {
    count: number;
  }

  interface ComponentCustomProperties {
    $store: Store<State>;
  }
}

const app = createApp(App);
for (const [key, component] of Object.entries(ElementPlusIconsVue)) {
  app.component(key, component);
}
app.component('v-chart', VueEcharts);
app.config.globalProperties.$echarts = echarts;
app.config.globalProperties.$cookies = VueCookies;
app.use(store).use(router).use(ElementPlus).use(NutUI).mount("#app");
