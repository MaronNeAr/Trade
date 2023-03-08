<template>
<div>
    <nut-navbar class="top-box">
        <template #content>
            <div>CHAT-DEMO</div>
        </template>
    </nut-navbar>

    <div class="message-box" ref="messageBox">
        <div class="message" :style="'bottom:' + bottom">
            <ul>
                <li v-for="message, idx in chatList" :key="idx" :class="message.self?'an-move-right':'an-move-left'">
                    <p class="time"><span>{{ getCurTime(new Date()) }}</span></p>
                    <div :class="'main' + (message.self?' self':'')">
                        <div class="text">{{message.content}}</div>
                    </div>
                </li>

            </ul>
        </div>
    </div>

    <nut-input class="bottom-input" v-model="prompt" placeholder="pls input prompt..." clearable center @keyup.enter="getChat">
        <template #button>
            <nut-button size="small" type="primary" @click="getChat">chat</nut-button>
        </template>
    </nut-input>
</div>
</template>

<script lang="ts">
import { bottom } from '@popperjs/core';
import {
    Configuration,
    OpenAIApi
} from 'openai'
import {
    getCurrentInstance,
    reactive,
    toRefs
} from 'vue';

export default {
    setup() {
        const cookie = getCurrentInstance().appContext.config.globalProperties.$cookies;
        const state = reactive({
            messageBox: null,
            prompt: '',
            chatList: []
        });
        const methods = {
            
            getCurTime: (date) => {
                if (typeof date === 'string') {
                    date = new Date(date);
                }
                if (date.getMinutes() < 10) {
                    return date.getHours() + ':0' + date.getMinutes();
                } else {
                    return date.getHours() + ':' + date.getMinutes();
                }
            },
            getChat: async () => {
                state.chatList.push({
                    self: true,
                    content: state.prompt
                });
                methods.scrollBottom();
                const configuration = new Configuration({
                    apiKey: process.env.OPENAI_API_KEY,
                });
                const openai = new OpenAIApi(configuration);
                const completion = await openai.createChatCompletion({
                    model: "gpt-3.5-turbo",
                    messages: [{
                        role: 'user',
                        content: state.prompt
                    }],
                });
                console.log(completion.data.choices[0].message);
                state.chatList.push({
                    self: false,
                    content: completion.data.choices[0].message.content
                });
                methods.scrollBottom();
                state.prompt = "";
            },
            scrollBottom: async() => {
                state.messageBox.scrollTop = state.messageBox.scrollHeight;
            }
        };
        return {
            ...toRefs(state),
            ...methods
        }
    }
}
</script>

<style scoped>
.bottom-input {
    position: fixed;
    bottom: 6%;
}

.top-box {
    background-color: #bd3124;
    color: #fff;
    height: 6%;
    font-size: 20px;
    line-height: 50px;
    box-shadow: 3px 2px #cfcfcf;
}

.message-box {
    /*height: 100%;*/
    margin: 0%;
    position: fixed;
    top: 8%;
    bottom:12%;
    overflow-y: scroll;
    width: 100%;
}

.message {
    position: absolute;
    padding: 0px 3%;
    width: 94%;
}

.message li {
    margin-bottom: 15px;
    left: 0;
    position: relative;
    display: block;
}

.message .time {
    margin: 10px 0;
    text-align: center;
}

.message .text {
    display: inline-block;
    position: relative;
    padding: 0 10px;
    max-width: calc(100% - 75px);
    min-height: 35px;
    line-height: 2.1;
    font-size: 15px;
    padding: 6px 10px;
    text-align: left;
    word-break: break-all;
    background-color: #fff;
    color: #000;
    border-radius: 4px;
    box-shadow: 0px 1px 7px -5px #000;
}

.message .avatar {
    float: left;
    margin: 0 10px 0 0;
    border-radius: 3px;
    background: #fff;
}

.message .time>span {
    display: inline-block;
    padding: 0 5px;
    font-size: 12px;
    color: #fff;
    border-radius: 2px;
    background-color: #DADADA;
}

.message .system>span {
    padding: 4px 9px;
    text-align: left;
}

.message .text:before {
    content: " ";
    position: absolute;
    top: 9px;
    right: 100%;
    border: 6px solid transparent;
    border-right-color: #fff;
}

.message .self {
    text-align: right;
}

.message .self .avatar {
    float: right;
    margin: 0 0 0 10px;
}

.message .self .text {
    background-color: #ff5b5b73;
}

.message .self .text:before {
    right: inherit;
    left: 100%;
    border-right-color: transparent;
    border-left-color: #ff5b5b73;
}

.message .image {
    max-width: 200px;
}

img.static-emotion-gif,
img.static-emotion {
    vertical-align: middle !important;
}

.an-move-left {
    left: 0;
    animation: moveLeft .7s ease;
    -webkit-animation: moveLeft .7s ease;
}

.an-move-right {
    left: 0;
    animation: moveRight .7s ease;
    -webkit-animation: moveRight .7s ease;
}

.bgnone {
    background: none;
}

@keyframes moveRight {
    0% {
        left: -20px;
        opacity: 0
    }

    ;

    100% {
        left: 0;
        opacity: 1
    }
}

@-webkit-keyframes moveRight {
    0% {
        left: -20px;
        opacity: 0
    }

    ;

    100% {
        left: 0px;
        opacity: 1
    }
}

@keyframes moveLeft {
    0% {
        left: 20px;
        opacity: 0
    }

    ;

    100% {
        left: 0px;
        opacity: 1
    }
}

@-webkit-keyframes moveLeft {
    0% {
        left: 20px;
        opacity: 0
    }

    ;

    100% {
        left: 0px;
        opacity: 1
    }
}

@media (max-width: 367px) {
    .fzDInfo {
        width: 82%;
    }
}
</style>
