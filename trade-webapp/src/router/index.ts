import { createRouter, createWebHistory, createWebHashHistory, RouteRecordRaw } from 'vue-router'

const routes: Array<RouteRecordRaw> = [
  {
    path: '/',
    component: () => import('@/views/Main.vue'),
    meta: { title: '自述文件' },
    children: [
      {
        path:'/',
        component: () => import('@/views/ProductPage.vue'),
        children: [
          {
            path:'',
            component: () => import('@/views/Products/CurrencyPage.vue'),
          },
          {
            path:'/cu',
            component: () => import('@/views/Products/CurrencyPage.vue'),
          },
          {
            path:'/cn',
            component: () => import('@/views/Products/AStockPage.vue'),
          },
          {
            path:'/hk',
            component: () => import('@/views/Products/HKStockPage.vue'),
          },
          {
            path:'/us',
            component: () => import('@/views/Products/USStockPage.vue'),
          },
          {
            path:'/fg',
            component: () => import('@/views/Products/FutureGoodsPage.vue'),
          },
        ]
      },
      {
        path:'/community',
        name:'community',
        component: () => import('@/views/CommunityPage.vue'),
      },
      {
        path: '/chat',
        name: 'chat',
        component: () => import('@/views/ChatPage.vue')
      },
      {
        path:'/trade',
        name:'trade',
        component: () => import('@/views/TradePage.vue'),
        children: [
          {
            path:'',
            component: () => import('@/views/TradePages/BuyPage.vue')
          },
          {
            path:'/buy',
            component: () => import('@/views/TradePages/BuyPage.vue')
          },
          {
            path:'/sale',
            component: () => import('@/views/TradePages/SalePage.vue')
          },
          {
            path:'/rem',
            component: () => import('@/views/TradePages/RemPage.vue')
          },
          {
            path:'/hold',
            component: () => import('@/views/TradePages/HoldPage.vue')
          },
          {
            path:'/query',
            component: () => import('@/views/TradePages/QueryPage.vue')
          }
        ]
      },
      {
        path:'/account',
        name:'account',
        component: () => import('@/views/AccountPage.vue'),
        children: [
          {
            path:'',
            component: () => import('@/views/TradePages/HoldPage.vue'),
          },
          {
            path:'/cc',
            component: () => import('@/views/TradePages/HoldPage.vue'),
          },
          {
            path:'/ls',
            component: () => import('@/views/TradePages/QueryPage.vue'),
          }
        ]
      },
      {
        path: '/info',
        component: () => import('@/views/InfoPage.vue')
      },
      {
        path: '/detail',
        component: () => import('@/views/TradePages/TradeDetailPage.vue')
      },
      {
        path: '/wallet',
        component: () => import('@/views/WalletPage.vue')
      },
      {
        path: '/deposit',
        component: () => import('@/views/TradePages/DepositPage.vue')
      },
    ]
  },
  {
    path: '/login',
    component: () => import('@/views/LoginPage.vue')
  },
  {
    path: '/test',
    component: () => import('@/components/AddressSelect.vue')
  }
]

const router = createRouter({
  // history: createWebHistory(process.env.BASE_URL),
  history: createWebHashHistory(process.env.BASE_URL),
  routes
})

export default router
