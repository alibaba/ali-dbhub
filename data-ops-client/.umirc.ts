import { defineConfig } from 'umi';
const MonacoWebpackPlugin = require('monaco-editor-webpack-plugin');
// const MonacoWebpackPlugin = require('monaco-editor-esm-webpack-plugin');


const chainWebpack = (config:any, { webpack }:any) => {
  config.plugin('monaco-editor').use(MonacoWebpackPlugin, [
    {
      languages: [ 'mysql', 'pgsql', 'sql']
    }
  ])

  // TODO: Monaco汉化
  // config.module.rules
  // .test(/\.js/)
  // .use(MonacoWebpackPlugin.loader)
  // .loader(MonacoWebpackPlugin.loader)
  // .options({name: /node_modules[\\\/]monaco-editor[\\\/]esm/ ,esModule: false});
};




export default defineConfig({
  title: 'dataOps',
  history: {
    type: 'hash'
  },
  base: '/',
  publicPath: './',
  hash:true,
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
          component: '@/pages/database-manage',
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
          component: '@/layout/HomeLayout',
          routes:[
            {
              path: '/',
              exact: true,
              redirect: '/connection',
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
              redirect: '/error',
            },
          ]
        }
      ],
    },
  ],
  mfsu: {},
  fastRefresh: {},
  dynamicImport: {
    loading: '@/components/Loading/index',
  },
  nodeModulesTransform: {
    type: 'none',
  },
  chainWebpack,
});
