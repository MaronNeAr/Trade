<template>
<nut-cell title="请选择城市" :desc="desc" @click="()=>{show=true}"></nut-cell>
<nut-picker v-model:visible="show" v-model="selectedCascader" :columns="cascaderColumns" title="城市选择" @confirm="confirm" @change="change"></nut-picker>
</template>

<script>
import {
    ref
} from 'vue';
export default {
    setup(props) {
        const show = ref(false);
        const desc = ref('');
        const selectedCascader = ref(['FuJian', 'FuZhou', 'TaiJiang']);
        const cascaderColumns = ref([{
                text: '北京市',
                value: '110000',
                children: [{
                        text: '东城区',
                        value: '110101'
                    },
                    {
                        text: '西城区',
                        value: '110102'
                    },
                    {
                        text: '朝阳区',
                        value: '110105'
                    },
                    {
                        text: '丰台区',
                        value: '110106'
                    },
                    {
                        text: '石景山区',
                        value: '110107'
                    },
                    {
                        text: '海淀区',
                        value: '110108'
                    },
                    {
                        text: '门头沟区',
                        value: '110109'
                    },
                    {
                        text: '房山区',
                        value: '110111'
                    },
                    {
                        text: '通州区',
                        value: '110112'
                    },
                    {
                        text: '顺义区',
                        value: '110113'
                    },
                    {
                        text: '昌平区',
                        value: '110114'
                    },
                    {
                        text: '大兴区',
                        value: '110115'
                    },
                    {
                        text: '怀柔区',
                        value: '110116'
                    },
                    {
                        text: '平谷区',
                        value: '110117'
                    },
                    {
                        text: '密云区',
                        value: '110118'
                    },
                    {
                        text: '延庆区',
                        value: '110119'
                    }
                ]
            },
            {
                text: '天津',
                value: 'TianJin',
                children: [{
                        text: '和平区',
                        value: '120101',
                    },
                    {
                        text: '河东区',
                        value: '120102',
                    },
                    {
                        text: '河西区',
                        value: '120103',
                    },
                    {
                        text: '南开区',
                        value: '120104',
                    },
                    {
                        text: '河北区',
                        value: '120105',
                    },
                    {
                        text: '红桥区',
                        value: '120106',
                    },
                    {
                        text: '东丽区',
                        value: '120110',
                    },
                    {
                        text: '西青区',
                        value: '120111',
                    },
                    {
                        text: '津南区',
                        value: '120112',
                    },
                    {
                        text: '北辰区',
                        value: '120113',
                    },
                    {
                        text: '武清区',
                        value: '120114',
                    },
                    {
                        text: '宝坻区',
                        value: '120115',
                    },
                ],
            },
            {
                text: '上海市',
                value: '310000',
                children: [{
                        text: '黄浦区',
                        value: '310101'
                    },
                    {
                        text: '徐汇区',
                        value: '310104'
                    },
                    {
                        text: '长宁区',
                        value: '310105'
                    },
                    {
                        text: '静安区',
                        value: '310106'
                    },
                    {
                        text: '普陀区',
                        value: '310107'
                    },
                    {
                        text: '虹口区',
                        value: '310109'
                    },
                    {
                        text: '杨浦区',
                        value: '310110'
                    },
                    {
                        text: '闵行区',
                        value: '310112'
                    },
                    {
                        text: '宝山区',
                        value: '310113'
                    },
                    {
                        text: '嘉定区',
                        value: '310114'
                    },
                    {
                        text: '浦东新区',
                        value: '310115'
                    },
                    {
                        text: '金山区',
                        value: '310116'
                    },
                    {
                        text: '松江区',
                        value: '310117'
                    },
                    {
                        text: '青浦区',
                        value: '310118'
                    },
                    {
                        text: '奉贤区',
                        value: '310120'
                    },
                ],
            },
        ]);

        const confirm = ({
            selectedValue,
            selectedOptions
        }) => {
            desc.value = selectedValue.join(',');
        }
        const change = ({
            selectedValue,
            selectedOptions
        }) => {
            console.log(selectedValue);
        };

        return {
            show,
            desc,
            selectedCascader,
            cascaderColumns,
            change,
            confirm
        };
    }
};
</script>
