<template>
    <div class="canvas" ref="canvas"></div>
</template>

<script lang="ts">
import * as echarts from 'echarts'
import axios from 'axios';
import { ref } from 'vue';
import { HttpManager } from '@/api';

export default {
    props: {
        tradePair : String
    },
    setup(props) {
        let baseURL = 'https://api-aws.huobi.pro';
        const tradeDate = ref([]);
        const canvas = ref(null);

        async function getData() {
            await await axios.get(baseURL + "/market/history/kline?symbol=" + props.tradePair + "&period=1day").then((response) => {
                tradeDate.value = response.data.data;
                let params = new URLSearchParams();
                params.append("content", JSON.stringify(response.data.data))
                HttpManager.postKChart(props.tradePair, params);
                renderChart();
            }).catch(async (error) => {
                const result = (await HttpManager.getKChart(props.tradePair) as ResponseBody);
                if (result.data && result.data != undefined) {
                    tradeDate.value = JSON.parse(result.data);
                    if (Array.isArray(tradeDate.value)) renderChart();
                } else console.log("暂无在线数据，请连接vp");
            });
            // tradeDate.value = ((await axios.get(baseURL + "/market/history/kline?symbol=" + props.tradePair + "&period=1day")) as ResponseBody).data.data;
        }

        getData();

        const renderChart = async() => {
            if (!canvas.value) return;
            const myChart = echarts.init(canvas.value);
            const upColor = '#ec0000';
            const upBorderColor = '#8A0000';
            const downColor = '#00da3c';
            const downBorderColor = '#008F28';
            // Each item: open，close，lowest，highest
            let tempDate = [];
            tradeDate.value.forEach(item => {
                let date = new Date(item.id * 1000);
                tempDate.push([date.getFullYear() + "/" + (date.getMonth() + 1) + "/" + date.getDay(), item.open, item.close, item.low, item.high])
            });

            const data0 = splitData(tempDate);

            function splitData(rawData) {
                const categoryData = [];
                const values = [];
                for (var i = 0; i < rawData.length; i++) {
                    categoryData.push(rawData[i].splice(0, 1)[0]);
                    values.push(rawData[i]);
                }
                return {
                    categoryData: categoryData,
                    values: values
                };
            }

            function calculateMA(dayCount) {
                var result = [];
                for (var i = 0, len = data0.values.length; i < len; i++) {
                    if (i < dayCount) {
                        result.push('-');
                        continue;
                    }
                    var sum = 0;
                    for (var j = 0; j < dayCount; j++) {
                        sum += +data0.values[i - j][1];
                    }
                    result.push(sum / dayCount);
                }
                return result;
            }
            var option = {
                title: {
                    left: 0
                },
                tooltip: {
                    trigger: 'axis',
                    axisPointer: {
                        type: 'cross'
                    }
                },
                legend: {
                    data: ['日K', 'MA5', 'MA10', 'MA20', 'MA30']
                },
                grid: {
                    left: '10%',
                    right: '10%',
                    bottom: '15%'
                },
                xAxis: {
                    type: 'category',
                    data: data0.categoryData,
                    boundaryGap: false,
                    axisLine: {
                        onZero: false
                    },
                    splitLine: {
                        show: false
                    },
                    min: 'dataMin',
                    max: 'dataMax'
                },
                yAxis: {
                    scale: true,
                    splitArea: {
                        show: true
                    }
                },
                dataZoom: [{
                        type: 'inside',
                        start: 50,
                        end: 100
                    },
                    {
                        show: true,
                        type: 'slider',
                        top: '90%',
                        start: 50,
                        end: 100
                    }
                ],
                series: [{
                        name: '日K',
                        type: 'candlestick',
                        data: data0.values,
                        itemStyle: {
                            color: upColor,
                            color0: downColor,
                            borderColor: upBorderColor,
                            borderColor0: downBorderColor
                        },
                        markPoint: {
                            label: {
                                formatter: function (param) {
                                    return param != null ? Math.round(param.value) + '' : '';
                                }
                            },
                            data: [{
                                    name: 'Mark',
                                    coord: ['2013/5/31', 2300],
                                    value: 2300,
                                    itemStyle: {
                                        color: 'rgb(41,60,85)'
                                    }
                                },
                                {
                                    name: 'highest value',
                                    type: 'max',
                                    valueDim: 'highest'
                                },
                                {
                                    name: 'lowest value',
                                    type: 'min',
                                    valueDim: 'lowest'
                                },
                                {
                                    name: 'average value on close',
                                    type: 'average',
                                    valueDim: 'close'
                                }
                            ],
                            tooltip: {
                                formatter: function (param) {
                                    return param.name + '<br>' + (param.data.coord || '');
                                }
                            }
                        },
                        markLine: {
                            symbol: ['none', 'none'],
                            data: [
                                [{
                                        name: 'from lowest to highest',
                                        type: 'min',
                                        valueDim: 'lowest',
                                        symbol: 'circle',
                                        symbolSize: 10,
                                        label: {
                                            show: false
                                        },
                                        emphasis: {
                                            label: {
                                                show: false
                                            }
                                        }
                                    },
                                    {
                                        type: 'max',
                                        valueDim: 'highest',
                                        symbol: 'circle',
                                        symbolSize: 10,
                                        label: {
                                            show: false
                                        },
                                        emphasis: {
                                            label: {
                                                show: false
                                            }
                                        }
                                    }
                                ],
                                {
                                    name: 'min line on close',
                                    type: 'min',
                                    valueDim: 'close'
                                },
                                {
                                    name: 'max line on close',
                                    type: 'max',
                                    valueDim: 'close'
                                }
                            ]
                        }
                    },
                    {
                        name: 'MA5',
                        type: 'line',
                        data: calculateMA(5),
                        smooth: true,
                        lineStyle: {
                            opacity: 0.5
                        }
                    },
                    {
                        name: 'MA10',
                        type: 'line',
                        data: calculateMA(10),
                        smooth: true,
                        lineStyle: {
                            opacity: 0.5
                        }
                    },
                    {
                        name: 'MA20',
                        type: 'line',
                        data: calculateMA(20),
                        smooth: true,
                        lineStyle: {
                            opacity: 0.5
                        }
                    },
                    {
                        name: 'MA30',
                        type: 'line',
                        data: calculateMA(30),
                        smooth: true,
                        lineStyle: {
                            opacity: 0.5
                        }
                    }
                ]
            };
            option && myChart.setOption(option);
        }

        return {
            props,
            canvas
        }
    }
}
</script>

<style scoped>
.canvas {
    width: 100%;
    height: 300px;
    animation-fill-mode: none;
}
</style>