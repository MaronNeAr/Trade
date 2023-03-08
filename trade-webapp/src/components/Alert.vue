<template>
    <div class="CAlert-box">
            <div class="CAlert" v-show="isShow">
                <div class="alert-box">
                    <div class="box-content">
                        <div class="message">
                            <p>委托类型:{{ alertMes.type }}</p><br>
                            <p>加密货币名称:{{ alertMes.name }}</p><br>
                            <p>委托价格:{{ alertMes.price }}</p><br>
                            <p>委托数量:{{ alertMes.amount }}</p><br>
                        </div>
                        <div class="box-btn">
                            <button @click="Cancel" class="ok">取消</button>
                            <button @click="Commit" class="no">确定买入</button>
                        </div>
                    </div>
                </div>
            </div>
    </div>
</template>
<script>
import {
    reactive,
    ref
} from 'vue';
export default {
    props: ['alertMes','title', 'content', 'isShow', 'commit',],
    methods: {
        // 关闭 -- 弹窗盒子
        Cancel() {
            this.$emit('Cancel')
        },
        // 提交 -- 弹窗盒子
        Commit() {
            this.$emit('Commit')
        },
    }
}
</script>
<style lang="scss">
.CAlert-box {
    .CAlert-enter {
        opacity: 0;
        height: 0;
    }

    .CAlert-enter-to {
        opacity: 1;
        height: 100%;
    }

    .CAlert-enter-active {
        transition: all 0.3s;
    }

    .CAlert-leave {
        opacity: 1;
        height: 100%;
    }

    .CAlert-leave-active {
        transition: all 0.3s;
    }

    .CAlert {
        position: fixed;
        top: 0;
        left: 0;
        bottom: 0;
        right: 0;

        .alert-box {
            min-width: 300px;
            min-height: 300px;
            border-radius: 10px;
            position: absolute;
            top: 50%;
            left: 50%;
            z-index: 999;
            transform: translate(-50%, -50%);
            overflow: hidden;
            background: linear-gradient(#ffeaea,#fff);
            .message {
                    position: absolute;
                    top:10%;
                    left:15%;
                    width:80%;
                    height:60%;
                    font-size: larger;
                }
            .box-content {
                .box-btn {
                    position: absolute;
                    bottom: 0px;
                    left: 50%;
                    transform: translateX(-50%);
                    width: 100%;
                    display: flex;
    
                    button {
                        width: 50%;
                        height: 8vh;
                        line-height: 34px;
                        font-size: large;
                        border: 1px solid rgb(236, 234, 234);
                        box-sizing: border-box;
                        outline: 0;
                        cursor: pointer;
                        
                    }

                    .ok {
                        background: linear-gradient(#ffffff,#febcba,);
                        color: black;
                    }

                    .no {
                        background: linear-gradient(#ffffff,#febcba,);
                        color:orangered;
                    }
                }
            }
        }
    }
}
</style>

