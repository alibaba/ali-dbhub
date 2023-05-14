const { app, shell } = require('electron');
const path = require('path');
const os = require('os');

module.exports = [
  {
    label: '文件',
    submenu: [
      {
        label: '退出',
        click() {
          // 退出程序
          app.quit();
        },
      },
    ],
  },
  {
    label: '帮助',
    submenu: [
      {
        label: '打开日志',
        click() {
          const fileName = '.chat2db/logs/application.log';
          const url = path.join(os.homedir(), fileName);
          shell.openPath(url).then((str) => console.log('err:', str));
        },
      },
      {
        label: '打开控制台',
        click() {
          mainWindow && mainWindow.toggleDevTools();
        },
      },
      {
        label: '访问官网',
        click() {
          const url = 'https://chat2db.opensource.alibaba.com/';
          shell.openExternal(url);
        },
      },
      // {
      //   label: '关于',
      //   role: 'about', // about （关于），此值只针对 Mac  OS X 系统
      //   // 点击事件 role 属性能识别时 点击事件无效
      //   click: () => {
      //     var aboutWin = new BrowserWindow({
      //       width: 300,
      //       height: 200,
      //       parent: win,
      //       modal: true,
      //     });
      //     aboutWin.loadFile('about.html');
      //   },
      // },
    ],
  },
];
