import { defineConfig } from 'umi';
const MonacoWebpackPlugin = require('monaco-editor-webpack-plugin');
const BundleAnalyzerPlugin = require('webpack-bundle-analyzer').BundleAnalyzerPlugin;
// const MonacoWebpackPlugin = require('monaco-editor-esm-webpack-plugin');
const UMI_ENV = process.env.UMI_ENV || 'local'; 
const assetDir = "static";

const chainWebpack = (config: any, { webpack }: any) => {
  config.plugin('monaco-editor').use(MonacoWebpackPlugin, [
    {
      languages: ['mysql', 'pgsql', 'sql'],
    },
  ]);
};

export default defineConfig({
  title: 'Chat2DB',
  history: {
    type: 'hash',
  },
  base: '/',
  publicPath: '/',
  hash: false,
  routes: [
    {
      path: '/',
      component: '@/components/AppContainer',
      routes: [
        { path: '/login', exact: true, component: '@/pages/login' },
        // { path: '/verify', exact: true, component: '@/pages/verify' },
        { path: '/error', component: '@/pages/error' },
        { path: '/demo', exact: true, component: '@/pages/demo' },
        {
          path: '/',
          component: '@/layouts/BaseLayout',
          routes: [
            {
              exact: true,
              path: '/',
              component: '@/pages/database',
            },
            {
              exact: true,
              path: '/database',
              component: '@/pages/database',
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
              path: '/chat',
              exact: true,
              component: '@/pages/chat-ai'
            },
            {
              redirect: '/error',
            }
          ]
        }
      ],
    },
  ],
  mfsu: {},
  fastRefresh: {},
  dynamicImport: {
    loading: '@/components/Loading/LazyLoading'
  },
  nodeModulesTransform: {
    type: 'none',
  },
  chainWebpack,
  devServer: {
    port: 8001,
    host: '127.0.0.1',
  },
  define: {
    'process.env.UMI_ENV': UMI_ENV,
  }
});
