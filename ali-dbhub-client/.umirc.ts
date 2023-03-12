import { defineConfig } from 'umi';
const MonacoWebpackPlugin = require('monaco-editor-webpack-plugin');
// const MonacoWebpackPlugin = require('monaco-editor-esm-webpack-plugin');

const chainWebpack = (config: any, { webpack }: any) => {
  config.plugin('monaco-editor').use(MonacoWebpackPlugin, [
    {
      languages: ['mysql', 'pgsql', 'sql'],
    },
  ]);

  // TODO: Monaco汉化
  // config.module.rules()
  // .test(/\.js/)
  // .use(MonacoWebpackPlugin.loader)
  // .loader(MonacoWebpackPlugin.loader)
  // .options({name: /node_modules[\\\/]monaco-editor[\\\/]esm/ ,esModule: false});
};

export default defineConfig({
  title: 'dbHub',
  history: {
    type: 'hash',
  },
  base: '/',
  publicPath: './',
  hash: true,
  routes: [
    {
      path: '/',
      component: '@/components/AppContainer',
      routes: [
        { path: '/login', exact: true, component: '@/pages/login' },
        { path: '/error', component: '@/pages/error' },
        { path: '/demo', exact: true, component: '@/pages/demo' },
        {
          path: '/database',
          component: '@/layouts/BaseLayout',
          routes: [
            {
              exact: true,
              path: '/database/:type/:id',
              component: '@/pages/database',
            },
          ],
        },
        {
          path: '/',
          component: '@/layouts/HomeLayout',
          routes: [
            {
              path: '/',
              exact: true,
              component: '@/pages/connection',
            },
            {
              path: '/connection',
              exact: true,
              component: '@/pages/connection',
            },
            {
              path: '/sql-history',
              exact: true,
              component: '@/pages/sql-history',
            },
            {
              path: '/manage',
              exact: true,
              component: '@/pages/manage',
            },
            {
              redirect: '/error',
            },
          ],
        },
      ],
    },
  ],
  mfsu: {},
  fastRefresh: {},
  // 桌面端不需要懒加载
  // dynamicImportSyntax:{},
  // dynamicImport: {
  //   loading: '@/components/Loading/LazyLoading',
  // },
  nodeModulesTransform: {
    type: 'none',
  },
  chainWebpack,
});
