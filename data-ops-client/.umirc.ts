import { defineConfig } from 'umi';

export default defineConfig({
  title: 'dataOps',
  nodeModulesTransform: {
    type: 'none',
  },
  routes: [
    {
      path: '/',
      component: '@/components/AppContainer',
      routes: [
        { path: '/login', exact: true, component: '@/pages/login' },
        { path: '/demo', exact: true, component: '@/pages/demo' },
        { path: '/error', component: '@/pages/error' },
        {
          path: '/database',
          component: '@/pages/databaseManage',
          routes: [
            {
              exact: true,
              path: '/database/:id',
              component: '@/components/Database',
            },
          ],
        },
        { 
          path: '/', 
          component: '@/pages/home',
          routes:[
            {
              path: '/',
              exact: true,
              redirect: '/databaseList',
            },
            {
              path: '/databaseList',
              exact: true,
              component: '@/components/DatabaseList',
            },
            {
              path: '/SQLHistory',
              exact: true,
              component: '@/components/SQLHistory',
            },
            {
              redirect: '/error',
            },
          ]
        }
      ],
    },
  ],
  fastRefresh: {},
  mfsu: {},
  dynamicImport: {
    loading: '@/components/PageLoading/index',
  },
  history: {
    type: 'hash'
  },
  base: './',
  publicPath: './',
  hash:true,
});
