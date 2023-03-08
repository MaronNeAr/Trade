import { get, post, deletes, getBaseURL } from './request'

const HttpManager = {
  //登录
  login: (params) => post(`login`, params),
  //注册
  register: (params) => post(`register`, params),
  //更新用户信息
  updateUserInfo: (params) => post(`update`, params),
  //更新用户电话
  updateUserPhone: (params) => post(`update/phone`, params),
  //发送验证码
  sendAuthCode: (params) => post(`send/auth-code`, params),
  //获取账户余额
  getAccount: (params) => post(`account`, params),
  //充值
  recharge: (params) => post(`recharge`, params),
  //提现
  cash: (params) => post(`cash`, params),
  //交易-买入
  buy: (params) => post(`buy`, params),
  //交易-卖出
  sell: (params) => post(`sell`, params),
  //获取持仓情况
  getPosition: (params) => post(`position`, params),
  //获取交易明细
  getTradeDetail: (params) => post(`detail`, params),
  //获取评论
  getComment: (tid) => get(`comment/${tid}`),
  //获取所有推文信息
  getAllTweets: () => get(`tweets`),
  //获取交易信息
  getCurrency: () => get(`currency`),
  //获取币种价格
  getTickers: () => get(`tickers`),
  //获取交易对日K图
  getKChart: (sc) => get(`k-chart/${sc}`),
  //获取市场深度
  getDepth: (sc) => get(`m-depth/${sc}`),
  //获取聚合行情
  getTicker: (sc) => get(`ticker/${sc}`),
  // 获取图片信息
  attachImageUrl: (url) => `${getBaseURL()}/${url}`,
  //提交推文
  postTweet: (params) => post(`publish`, params),
  //添加评论
  postComment: (params) => post(`comment`, params),
  //提交点赞信息
  postFollow: (params) => post(`tweet/follow`, params),
  //保存交易信息
  postCurrency: (params) => post(`currency`, params),
  //保存币种价格
  postTickers: (params) => post(`tickers`, params), 
  //保存交易对日K图
  postKChart: (sc, params) => post(`k-chart/${sc}`, params),
  //保存市场深度
  postDepth: (sc, params) => post(`m-depth/${sc}`, params),
  //保存聚合行情
  postTicker: (sc, params) => post(`ticker/${sc}`, params)
}

export { HttpManager }
