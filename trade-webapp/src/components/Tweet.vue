<template>
<div>
    <nut-row>
        <nut-col :span="4">
            <img class="icon" :src="attachImageUrl(tweet.icon)" />
        </nut-col>
        <nut-col :span="18">
            <div class="name">{{tweet.username}}</div>
            <div class="text">{{tweet.content}}</div>
            <div class="h5px"></div>
            <nut-image class="image" :src="attachImageUrl(tweet.picture)" />
            <div class="h5px"></div>
            <div class="option">
                <nut-icon name="heart-fill1" v-if="followed" @click="unfollow" color="red"></nut-icon>
                <nut-icon name="follow" v-else @click="follow"></nut-icon>
                &ensp;&ensp;&ensp;&ensp;
                <nut-icon name="comment" @click="commentInputShow = !commentInputShow"></nut-icon>
                &ensp;&ensp;&ensp;&ensp;
                <nut-icon name="share"></nut-icon>
                &ensp;&ensp;
            </div>
            <div class="h5px"></div>
            <div class="comment">
                <div v-if="followShow">
                    <nut-icon name="follow" size="9" class="name"></nut-icon>
                    <span class="name">{{ " " + followers }}</span>
                    <div class="h5px"></div>
                </div>
                <div class="comment-content" v-for="comment in comments" :key="comment.id">
                    <span class="name">{{comment.observer}}：</span>
                    <span>{{comment.comment}}</span>
                </div>
                <div class="h5px"></div>
                <div>
                    <nut-input class="comment-input" placeholder="评论" v-model="comment.text" v-if="commentInputShow">
                        <template #button>
                            <nut-button size="small" type="success" @click="send">发送</nut-button>
                        </template>
                    </nut-input>
                </div>
            </div>
        </nut-col>
    </nut-row>
    <nut-divider></nut-divider>
</div>
</template>

<script lang="ts">
import {
  getCurrentInstance,
    reactive,
    ref
} from 'vue';
import {
    HttpManager
} 
from "@/api";import { Notify } from '@nutui/nutui';

export default {
    props: ['tweet'],
    setup(props) {
        const cookie = getCurrentInstance().appContext.config.globalProperties.$cookies;
        const localname = ref(cookie.get("username") ? cookie.get("username") : "游客");
        const followShow = ref(props.tweet.followers != "");
        const followed = ref(props.tweet.followers.indexOf(localname.value) != -1);
        const followers = ref(props.tweet.followers);
        const commentInputShow = ref(false);
        const comment = reactive({
            text: null
        });
        const comments = ref([]);
        const methods = {
            follow() {
                followed.value = true;
                if (followers.value == "") {
                    followers.value += localname.value;
                    followShow.value = true;
                } else followers.value += "、" + localname.value;
                postFollow();
            },
            unfollow() {
                followed.value = false;
                if (followers.value == localname.value) {
                    followers.value = "";
                    followShow.value = false;
                } else if (followers.value.indexOf(localname.value) == 0) followers.value = followers.value.replace(localname.value + "、", "")
                else followers.value = followers.value.replace("、" + localname.value, "");
                postFollow();
            },
            send() {
                postComment();
            }
        };
        async function postFollow() {
            let params = new URLSearchParams();
            params.append("tid", props.tweet.id);
            params.append("followers", followers.value);
            const response = (await HttpManager.postFollow(params)) as ResponseBody;
            if (!response.success) Notify.danger(response.message);
        }
        const getComment = async() => {
            const response = await HttpManager.getComment(props.tweet.id) as ResponseBody;
            if (!response.success) Notify.danger(response.message);
            comments.value = response.data;
        }
        const postComment = async() => {
            let params = new URLSearchParams();
            params.append("tid", props.tweet.id);
            params.append("observer", localname.value); 
            params.append("comment", comment.text);
            const response = await HttpManager.postComment(params) as ResponseBody;
            if (!response.success) Notify.danger(response.message);
            else {
                comments.value.push({
                    id: null,
                    observer: localname.value,
                    comment: comment.text,
                    tid: props.tweet.tid
                });
                comment.text = null;
            }
        }
        getComment();
        return {
            localname,
            followShow,
            followed,
            followers,
            commentInputShow,
            comment,
            comments,
            attachImageUrl: HttpManager.attachImageUrl,
            ...methods
        };
    }
}
</script>

<style scoped>
* {
    transition: height 0.5s ease;
}

.icon {
    position: relative;
    left: 50%;
    transform: translate(-50%);
    width: 60%;
    border-radius: 5px;
}

.name {
    color: #576b95;
}

.image {
    max-width: 80%;
    max-height: 50%;
}

.option {
    text-align: right;
}

.comment {
    padding: 5px 10px;
    border-radius: 10px;
    background-color: #f7f7f7;
    font-size: 14px;
}

.comment-input {
    border-radius: 8px;
}

.h5px {
    height: 5px;
}
</style>
