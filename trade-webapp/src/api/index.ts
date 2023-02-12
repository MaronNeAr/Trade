import { get, post, deletes, getBaseURL } from './request'

const HttpManager = {

  //登录
  login: (params) => post(`login`, params),
  //注册
  register: (params) => post(`register`, params),
  //交易-买入
  buy: (params) => post(`buy`, params),
  //交易-卖出
  sell: (params) => post(`sell`, params),
  //获取评论
  getComment: (tid) => get(`comment/${tid}`),
  //获取所有推文信息
  getAllTweets: () => get(`tweets`),
  //获取交易信息
  getCurrency: () => get(`currency`),
  //获取交易对日K图
  getKChart: (sc) => get(`k-chart/${sc}`),
  //获取市场深度
  getDepth: (sc) => get(`m-depth/${sc}`),
  //获取聚合行情
  getTicker: (sc) => get(`ticker/${sc}`),
  // 获取图片信息
  attachImageUrl: (url) => `${getBaseURL()}/${url}`,
  //添加评论
  postComment: (params) => post(`comment`, params),
  //提交点赞信息
  postFollow: (params) => post(`tweet/follow`, params),
  //保存交易信息
  postCurrency: (params) => post(`currency`, params),
  //保存交易对日K图
  postKChart: (sc, params) => post(`k-chart/${sc}`, params),
  //保存市场深度
  postDepth: (sc, params) => post(`m-depth/${sc}`, params),
  //保存聚合行情
  postTicker: (sc, params) => post(`ticker/${sc}`, params)
}

export { HttpManager }
