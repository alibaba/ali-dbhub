// 引入electron并创建一个Browserwindow
const { app, BrowserWindow, shell, net } = require('electron');
const path = require('path');
const url = require('url');
// const isDev = require('electron-is-dev');
// const { autoUpdater } = require('electron-updater');
const isPro = process.env.NODE_ENV !== 'development';
// 修改main.js实时更新
// reloader(module);

// 保持window对象的全局引用,避免JavaScript对象被垃圾回收时,窗口被自动关闭.
let mainWindow;

function createWindow() {
  //创建浏览器窗口,宽高自定义具体大小你开心就好
  let options = {};
  if (process.platform === 'win32') { // 如果平台是win32，也即windows
    options.show = true; // 当window创建的时候打开
    options.backgroundColor = '#3f3c37';
  }

  mainWindow = new BrowserWindow({
    icon: './logo/logo.png',
    width: 1200,
    minWidth: 800,
    height: 800,
    center: true,
    title: 'Chat2DB',
    backgroundColor: '#1b1c21',
    ...options,
    webPreferences: {
      webSercurity: false,
      nodeIntegration: true,
      contextIsolation: true,
      preload: path.join(__dirname, 'preload.js'),
    },
  });
  
  // 加载应用-----  electron-quick-start中默认的加载入口
  mainWindow.loadFile(`${__dirname}/dist/index.html`);

  // 关闭window时触发下列事件.
  mainWindow.on('closed', function (event) {
    event.preventDefault();
    mainWindow = null;
  });

  // 监听打开新窗口事件 用默认浏览器打开
  mainWindow.webContents.on('new-window', function (event, url) {
    event.preventDefault();
    shell.openExternal(url);
  });
}

// 让electron 不会弹窗报错
process.on('uncaughtException', (error) => {
  console.error(error);
  logStream.write(`${error.stack}
`);
});

// 当 Electron 完成初始化并准备创建浏览器窗口时调用此方法
app.on('ready', createWindow);


app.on('before-quit', (event) => {
  const request = net.request({
    headers: {
      'Content-Type': 'application/json',
    },
    method: 'POST',
    url: 'http://127.0.0.1:10824/api/system/stop',
  });
  request.write(JSON.stringify({}));
  request.on('response', (response) => {
    response.on('data', (res) => {
      let data = JSON.parse(res.toString());
    });
    response.on('end', () => {});
  });
  request.end();
});

// 所有窗口关闭时退出应用.
app.on('window-all-closed', function (event) {
  // macOS中除非用户按下 `Cmd + Q` 显式退出,否则应用与菜单栏始终处于活动状态.
  event.preventDefault();
  app.hide()
  // if (process.platform !== 'darwin') {
  //   app.quit();
  // }
});

app.on('activate', function () {
  // macOS中点击Dock图标时没有已打开的其余应用窗口时,则通常在应用中重建一个窗口
  if (mainWindow === null) {
    createWindow();
  }
});

// 你可以在这个脚本中续写或者使用require引入独立的js文件.
