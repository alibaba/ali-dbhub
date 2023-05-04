import { defineConfig } from 'umi';
const MonacoWebpackPlugin = require('monaco-editor-webpack-plugin');
const assetDir = "https://oss-chat2db.alibaba.com/static/front/";

const chainWebpack = (config: any, { webpack }: any) => {
  config.plugin('monaco-editor').use(MonacoWebpackPlugin, [
    {
      languages: ['mysql', 'pgsql', 'sql'],
    },
  ]);
  config.output.publicPath(assetDir);

  // config.output
  // .filename('[name].js')
  // .chunkFilename('[name].chunk.js')
  // .publicPath(assetDir)
  // // 修改css输出目录
  // config.plugin("extract-css").tap(() => [
  //   {
  //     filename: '[name].css',
  //     chunkFilename: `[name].chunk.css`,
  //     ignoreOrder: true,
  //     publicPath: assetDir
  //   },
  // ]);
};

export default defineConfig({
  publicPath: assetDir,
  chainWebpack
});
