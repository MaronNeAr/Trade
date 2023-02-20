<template>
  <div>
    <iframe class="i-frame" src="http://view.xdocin.com/xdoc?_xdoc=http://124.220.209.244:8005/img/test/test.docx"></iframe>
    <div align="center">
        <nut-button type="primary" @click="documentUrl = 'img/test/test.docx'">word文档预览</nut-button>
        <nut-button type="info" @click="documentUrl = 'img/test/test.pdf'">pdf文档预览</nut-button>
        <nut-button type="success" @click="documentUrl = 'img/test/test.md'">markdown文档预览</nut-button>
    </div>
  </div>
</template>

<script>
import { ref } from 'vue';
import {
    HttpManager
} from '@/api';

export default {
    setup() {
        const documentUrl = ref('img/test/test.pdf');
        return {
            documentUrl,
            attachImageUrl: HttpManager.attachImageUrl
        }
    }
}
</script>

<style scoped>
.i-frame {
    width: 100%;
    height:800px;
}
</style>