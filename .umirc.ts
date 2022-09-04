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
        {
          path: '/',
          component: '@/pages/home',
          routes: [
            {
              exact: true,
              path: '/database/:databaseId',
              component: '@/components/Database',
            },
            { path: '/login', exact: true, component: '@/pages/login' },
            { path: '/demo', exact: true, component: '@/pages/demo' },
            { component: '@/pages/error' },
          ],
        },
        
      ]
    },
    
  ],
  fastRefresh: {},
  mfsu: {},
});
