
# 花旗杯交易软件——CitiEX

Trade.app<br>

模拟虚拟货币交易所<br>
支持游戏产品实时交易与行情反馈<br>
用户可开设个人游戏交易账户，交由花旗银行托管<br>
设有论坛，可供用户之间进行交流互动<br>


## 目录

- [项目描述](#项目描述)
	- [技术栈](#技术栈)
	- [主要依赖](#主要依赖)
	- [实现功能](#实现功能)
		- [首页](#首页)
		- [行情页](#行情页)
		- [社区页](#社区页)
		- [交易页](#交易页)
		- [账户页](#账户页)
		- [登录页](#登录页)
- [安装方法](#安装方法)


### 项目描述

#### 技术栈

前端：vue3 + vuex + vue-router + nutui + axios + element-ui + ts<br>
后端：Ssm框架 + Springboot + Mybatis + MySQL + Redis + RabbitMQ + druid<br>

#### 主要依赖

* 使用vue-cli搭建项目框架（主要运用electron）<br>
* 使用vue-router进行页面路由切换<br>
* vuex进行状态管理<br>
* nutui实现多种组件快速应用<br>
* axios向后台发出请求<br>
* core-js以便使用新语法API<br>
* echarts制作图表<br>
* 使用stylus编写样式<br>
* 使用vue-cookies和vue-cookie处理浏览器中的cookie操作<br>

#### 实现功能

##### 首页

> * tabbar点击切换页面： 行情、社区、交易、账户 <br>
> * 默认首页为行情 <br>

##### 行情页

> * 查看交易产品实时行情 <br>
> * 可选择不同虚拟货币 <br>
> * 搜索栏可搜索交易产品（待完善）<br>

##### 社区页

> * 滑动查看用户推文<br>
> * 推文详情展示：文字/图片<br>
> * 支持点赞，评论，转发（待完善）<br>
> * 右侧侧边栏点击后可发布推文（文字/图文）<br>
> * 左上角点击可跳转至个人相关界面（待完善）<br>
> * 左上角可点击刷新按钮<br>

##### 交易页

> * 支持用户进行交易 <br>
> * 需选择虚拟货币与产品 <br>
> * 可进行买入、卖出、查询<br>
> * 可查看持仓 <br>

##### 账户页

> * 点击登录进行个人账户登录，将跳转至登录页<br>
> * 登录后可修改个人头像与昵称<br>
> * 登录后可查看合计资产、交易明细、钱包（待完善）<br>

##### 登录页

> * 可以进行登录/注册 <br>


### 安装方法

``` bash
# install dependencies
npm install

# serve with hot reload at localhost:8080
npm run serve

```

For detailed explanation on how things work, checkout the [guide](http://vuejs-templates.github.io/webpack/) and [docs for vue-loader](http://vuejs.github.io/vue-loader).

