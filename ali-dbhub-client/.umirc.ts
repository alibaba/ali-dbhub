import { defineConfig } from 'umi';
const path = require('path');
const MonacoWebpackPlugin = require('monaco-editor-webpack-plugin');
const BundleAnalyzerPlugin = require('webpack-bundle-analyzer').BundleAnalyzerPlugin;
// const MonacoWebpackPlugin = require('monaco-editor-esm-webpack-plugin');
const autoprefixer = require('autoprefixer');

const chainWebpack = (config: any, args:any) => {
  config.plugin('monaco-editor').use(MonacoWebpackPlugin, [
    {
      languages: ['mysql', 'pgsql', 'sql'],
    },
  ]);

  // config.module.rules.forEach((rule:any) => {
  //   if (rule.test.toString() == /\.svg$/.toString()) {
  //     rule.exclude = /\.file\..+$/;
  //     rule.loader = 'svg-url-loader';
  //     rule.options = { noquotes: true };
  //   }
  //   if (rule.test.toString() == /\.css$/.toString() || rule.test.toString() == /\.less$/.toString()) {
  //     rule.oneOf.forEach((one:any) => {
  //       one.use[2].options.plugins = () => [autoprefixer({
  //         overrideBrowserslist: args.options.browsers
  //       })];
  //     });
  //     // console.log(rule.oneOf[1].use);
  //     rule.oneOf[0].exclude = rule.oneOf[1].include = /(node_modules|\.global\.)/;
  //     rule.oneOf[0].use[1].options.localIdentName = '[local]_[hash:base64:5]';
  //     if (rule.oneOf[0].use[3] === 'less-loader') {
  //       rule.oneOf[0].use.push({
  //         loader: path.resolve(__dirname, './tools/CSSVariableExtractPlugin/loader.js'),
  //         options: {
  //           themes: ['default', 'dark'],
  //           rootSelector: 'body, :global(.theme-scope)',
  //           useGlobal: true
  //         }
  //       });
  //     }
  //   }
  // });
  // console.log(config.module.rules)
  


  // TODO: Monaco汉化
  // config.module.rules()
  // .test(/\.js/)
  // .use(MonacoWebpackPlugin.loader)
  // .loader(MonacoWebpackPlugin.loader)
  // .options({name: /node_modules[\\\/]monaco-editor[\\\/]esm/ ,esModule: false});
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
  lessLoader: {
    loader: path.resolve(__dirname, './tools/CSSVariableExtractPlugin/loader.js'),
    options: {
      themes: ['default', 'dark'],
      rootSelector: 'body, :global(.theme-scope)',
      useGlobal: true
    },
  },
});
