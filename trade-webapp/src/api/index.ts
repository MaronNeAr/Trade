import { get, post, deletes, getBaseURL } from './request'

const HttpManager = {

  //登录注册
  login: (params) => post(`login`, params),
  register: (params) => post(`register`, params),
  //获取所有推文信息
  getAllTweets: () => get(`tweets`),
  //获取交易信息
  getCurrency: () => get(`currency`),
  //获取交易对日K图
  getKChart: (sc) => get(`k-chart/${sc}`),
  // 获取图片信息
  attachImageUrl: (url) => `${getBaseURL()}/${url}`,
  //提交点赞信息
  postFollow: (params) => post(`tweet/follow`, params),
  //保存交易信息
  postCurrency: (params) => post(`currency`, params),
  //保存交易对日K图
  postKChart: (sc, params) => post(`k-chart/${sc}`, params),
}

export { HttpManager }
