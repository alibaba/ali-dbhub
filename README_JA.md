
<h1 align="center">Chat2DB</h1>

<div align="center">

🔥🔥🔥 ChatGPT の機能を統合した、インテリジェントで汎用性の高いデータベース用 SQL クライアントおよびレポートツールです。

[![License](https://img.shields.io/github/license/alibaba/fastjson2?color=4D7A97&logo=apache)](https://www.apache.org/licenses/LICENSE-2.0.html)
[![GitHub release](https://img.shields.io/github/release/alibaba/ali-dbhub)](https://github.com/alibaba/ali-dbhub/releases)
[![GitHub Stars](https://img.shields.io/github/stars/alibaba/ali-dbhub)](https://github.com/alibaba/ali-dbhub/stargazers)
[![GitHub Forks](https://img.shields.io/github/forks/alibaba/ali-dbhub)](https://github.com/alibaba/ali-dbhub/fork)
[![GitHub Contributors](https://img.shields.io/github/contributors/alibaba/ali-dbhub)](https://github.com/alibaba/ali-dbhub/graphs/contributors)

</div>

<div align="center">
<p align="center"><b>Chat2DB リポジトリを共有する </b></p>
<p align="center">
<a href="https://twitter.com/intent/tweet?text=Chat2DB-An%20intelligent%20and%20versatile%20general-purpose%20SQL%20client%20and%20reporting%20tool%20for%20databases%20which%20integrates%20ChatGPT%20capabilities.&url=https://github.com/alibaba/Chat2DB&hashtags=ChatGPT,AGI,SQL%20Client,Reporting%20tool" target="blank" > <img src="https://img.shields.io/twitter/follow/_Chat2DB?label=Share Repo on Twitter&style=social" alt=""/> </a>
<a href="https://t.me/share/url?text=Chat2DB-An%20intelligent%20and%20versatile%20general-purpose%20SQL%20client%20and%20reporting%20tool%20for%20databases%20which%20integrates%20ChatGPT%20capabilities.&url=https://github.com/alibaba/Chat2DB" target="_blank"><img src="https://img.shields.io/twitter/url?label=Telegram&logo=Telegram&style=social&url=https://github.com/alibaba/Chat2DB" alt="Share on Telegram"/></a>
<a href="https://api.whatsapp.com/send?text=Chat2DB-An%20intelligent%20and%20versatile%20general-purpose%20SQL%20client%20and%20reporting%20tool%20for%20databases%20which%20integrates%20ChatGPT%20capabilities.%20https://github.com/alibaba/Chat2DB"><img src="https://img.shields.io/twitter/url?label=whatsapp&logo=whatsapp&style=social&url=https://github.com/alibaba/Chat2DB" /></a>
<a href="https://www.reddit.com/submit?url=https://github.com/alibaba/Chat2DB&title=Chat2DB-An%20intelligent%20and%20versatile%20general-purpose%20SQL%20client%20and%20reporting%20tool%20for%20databases%20which%20integrates%20ChatGPT%20capabilities." target="blank"><img src="https://img.shields.io/twitter/url?label=Reddit&logo=Reddit&style=social&url=https://github.com/alibaba/Chat2DB" alt="Share on Reddit"/></a>
<a href="mailto:?subject=Check%20this%20GitHub%20repository%20out.&body=Chat2DB-An%20intelligent%20and%20versatile%20general-purpose%20SQL%20client%20and%20reporting%20tool%20for%20databases%20which%20integrates%20ChatGPT%20capabilities.%3A%0Ahttps://github.com/alibaba/Chat2DB" target="_blank"><img src="https://img.shields.io/twitter/url?label=Gmail&logo=Gmail&style=social&url=https://github.com/alibaba/Chat2DB"/></a>
</p>

**ライセンス表記**: Chat2DB は、個人的かつ非商業的な使用のみを目的として構築・配布されています。商用利用を希望される場合は、対応する著者までご連絡ください。

Languages： 日本語 | [English](README.md) | [中文](README_CN.md)
</div>

## 📖 はじめに
&emsp; &emsp;Chat2DB は、Alibaba 社のオープンソースで無償提供されているマルチデータベースクライアントツールです。Windows と Mac のローカルインストールに加え、サーバーサイドへの展開や Web ページへのアクセスにも対応しています。Navicat や DBeaver といった従来のデータベースクライアントソフトと比較して、Chat2DB は AIGC の機能を統合しており、自然言語を SQL に変換することが可能です。また、SQL を自然言語に変換し、SQL の最適化提案を行うことで、開発者の作業効率を大幅に向上させることができます。AI 時代のデータベース開発者のためのツールであり、将来的には SQL を使用しない業務従事者でも、業務データの照会やレポートの作成が迅速に行えるようになります。
## ✨ 特徴
- 🌈 AI インテリジェントアシスタント、自然言語から SQL への変換、SQL から自然言語への変換、SQL 最適化の提案をサポートします
- 👭 チームコラボレーションをサポートし、開発者はオンラインデータベースパスワードを知る必要がなく、企業データベースアカウントセキュリティの問題を解決します。
- ⚙️ 強力なデータ管理機能、データテーブル、ビュー、ストアドプロシージャ、関数、トリガー、インデックス、シーケンス、ユーザー、ロール、権限などの管理をサポートしています
- 🔌 強力な拡張機能、現在 MySQL、PostgreSQL、Oracle、SQLServer、ClickHouse、OceanBase、H2、SQLite などをサポートし、将来的にはより多くのデータベースをサポートする予定です
- 🛡 Electron を使用したフロントエンド開発で Windows、Mac、Linux クライアント、Web バージョンを統合するソリューションを提供します
- 🎁 環境分離、オンライン、日次データ権限分離をサポートします


## ⏬ ダウンロードとインストール

| 概要                   | ダウンロード                                                                                                                                                   |
|-----------------------|------------------------------------------------------------------------------------------------------------------------------------------------------------|
| Windows      | [https://oss-chat2db.alibaba.com/release/1.0.11/Chat2DB%20Setup%201.0.11.exe](https://oss-chat2db.alibaba.com/release/1.0.11/Chat2DB%20Setup%201.0.11.exe) |
| MacOS ARM64 | [https://oss-chat2db.alibaba.com/release/1.0.11/Chat2DB-1.0.11-arm64.dmg](https://oss-chat2db.alibaba.com/release/1.0.11/Chat2DB-1.0.11-arm64.dmg)         |
| MacOS X64  | [https://oss-chat2db.alibaba.com/release/1.0.11/Chat2DB-1.0.11.dmg](https://oss-chat2db.alibaba.com/release/1.0.11/Chat2DB-1.0.11.dmg)                     |
| Jar包         | [https://oss-chat2db.alibaba.com/release/1.0.11/ali-dbhub-server-start.jar](https://oss-chat2db.alibaba.com/release/1.0.11/ali-dbhub-server-start.jar)     |

## 🚀 対応データベース
| データベース    | ステータス |
|---------------|-----------|
| Mysql         | ✅       |
| H2            | ✅       |
| Oracle        | ✅       |
| PostgreSQL    | ✅       |
| SQLServer     | ✅       |
| SQLLite       | ✅       |
| MariaDB       | ✅       |
| ClickHouse    | ✅       |
| DM            | ✅       |
| Presto        | ✅       |
| DB2           | ✅       |
| OceanBase     | ✅       |
| Redis         | ✅       |
| Hive          | ✅       |
| KingBase      | ✅       |
| MongoDB       | ✅       |
| Hbase         | Planning |
| Elasticsearch | Planning |
| openGauss     | Planning |
| TiDB          | Planning |
| InfluxDB      | Planning |

## 🌰 デモ
### データソースの作成
  <a><img src="https://gw.alicdn.com/imgextra/i3/O1CN01PlpLYy1hIq5aMugpg_!!6000000004255-0-tps-3446-1750.jpg" width="100%"/></a>
### データソースの管理
  <a><img src="https://gw.alicdn.com/imgextra/i2/O1CN01DpzZJL1T7w2Xv9VMl_!!6000000002336-0-tps-3410-1662.jpg" width="100%"/></a>
### SQL コンソール
  <a><img src="https://gw.alicdn.com/imgextra/i2/O1CN01aidnkx1Oo0LJ1Pdty_!!6000000001751-0-tps-3440-1736.jpg" width="100%"/></a>
### AI インテリジェントアシスタント
  <a><img src="https://gw.alicdn.com/imgextra/i4/O1CN01iaSXot1W6VeaDFbK2_!!6000000002739-0-tps-3430-1740.jpg" width="100%"/></a>

## 🔥 AI コンフィグ
### OPENAI を設定する

オプション 1（推奨）です： OPENAI の ChatSql 機能を利用するためには、以下の 2 つの条件を満たす必要があります:

- OPENAI_API_KEY が必要です。
- クライアントのネットワークから OPENAI のウェブサイトに接続することができ、中国のユーザーの場合は、VPN が必要です。注：ローカル VPN が完全に有効でない場合、クライアントでネットワークプロキシの HOST と PORT を設定することにより、ネットワークの接続性を確保することができます。

<a><img src="https://img.alicdn.com/imgextra/i2/O1CN01anrJMI1FEtSBbmTau_!!6000000000456-0-tps-1594-964.jpg" width="60%"/></a>

オプション 2（推奨）： 統合プロキシサービスを提供します。

- OPENAI_API_KEY は必要ありません。
- ネットワークに接続されていれば、プロキシや VPN は必要ありません。

ユーザーが AI 機能を素早く利用できるように、以下の QR コードをスキャンして、当社の WeChat 公開アカウントをフォローし、カスタム API_KEY を申請することができます。

<a><img src="https://oss-chat2db.alibaba.com/static/%E5%85%AC%E4%BC%97%E5%8F%B7.jpg" width="60%"/></a>

アプリケーション完成後、下図を参考に設定・利用を行う。Api Host を http://test.sqlgpt.cn/gateway/api/ として設定します。

<a><img src="https://img.alicdn.com/imgextra/i2/O1CN01xNobD21mo3B1ILrs2_!!6000000005000-0-tps-592-515.jpg" width="60%"/></a>

### カスタム AI を設定する
- カスタマイズ AI は、ChatGLM、ChatGPT、ERNIE Bot、Tongyi Qianwen など、導入した LLM であれば何でも OK です。ただし、カスタマイズされたインターフェースは、プロトコル定義に準拠する必要があります。そうでない場合は、二次開発が必要になる場合があります。コードには 2 つの DEMO が用意されており、以下のような構成になっています。具体的な使用方法としては、DEMO インターフェースを参照してカスタムインターフェースを作成するか、DEMO インターフェースで直接二次開発を行うことができます。
- ストリーム出力インターフェースをカスタマイズするための DEMO です。
  <a><img src="https://img.alicdn.com/imgextra/i1/O1CN01xMqnRH1DlkdSekvSF_!!6000000000257-0-tps-591-508.jpg" width="60%"/></a>
- ノンストリーム出力インターフェースをカスタマイズするための DEMO です。
  <a><img src="https://img.alicdn.com/imgextra/i1/O1CN01JqmbGo1fW0GAQhRu4_!!6000000004013-0-tps-587-489.jpg" width="60%"/></a>



## 📦 Docker インストール

```bash
docker pull chat2db/chat2db:latest
```

## 🎯 動作環境
注: ローカルデバッグが必要な場合
- Java ランタイムオープン JDK 17
- JRE のリファレンスパッケージングとデプロイメント方法です。
- Node ランタイム環境 Node16 Node.js 。

## 💻 ローカルデバッグ
- ローカルに git clone
```bash
$ git clone git@github.com:alibaba/Chat2DB.git
```
- フロントエンドインストール
```bash
$ cd Chat2DB/ali-dbhub-client
$ npm install # フロントエンドの依存関係をマウント
$ npm run build:prod # バックエンドのソースディレクトリに js をパッケージ化
```
- バックエンドデバッグ
```bash
$ cd ../ali-dbhub-server
$ mvn clean install # maven 3.8 以降のインストールが必要
$ cd ali-dbhub-server/ali-dbhub-server-start/target/
$ java -jar -Dchatgpt.apiKey=xxxxx ali-dbhub-server-start.jar  # チャットアプリケーションを起動するには、chatgpt.apiKey に ChatGPT キーを入力する必要があります。これを入力しないと AIGC 機能を使うことができません。
$ # http://127.0.0.1:10821 を開いてデバッグを開始する 注: フロントエンドのインストールが必要
```

- フロントエンドデバッグ
```bash
$ cd Chat2DB/ali-dbhub-client
$ npm install
$ npm run start
$ # http://127.0.0.1:10821 を開いてフロントエンドのデバッグを開始する
$ # 注 フロントエンドページは完全にサービスに依存するため、フロントエンドの学生はバックエンドプロジェクトのデバッグを行う必要がある
```
しかし、フロントデバッグにはリソースのマッピングが必要なので、[XSwitch](https://chrome.google.com/webstore/detail/idkjhjggpffolpidfkikidcokdkdaogg) をダウンロードし、次の設定ファイルを追加します
``` json
{
  "proxy": [
    [
      "http://127.0.0.1:10821/(.*).js$",
      "http://127.0.0.1:8001/$1.js",
    ],
    [
      "http://127.0.0.1:10821/(.*).css$",
      "http://127.0.0.1:8001/$1.css",
    ],
    [
      "http://127.0.0.1:10821/static/front/(.*)",
      "http://127.0.0.1:8001/$1",
    ],
    [
      "http://127.0.0.1:10821/static/(.*)$",
      "http://127.0.0.1:8001/static/$1",
    ],
  ],
}

```

## 📑 ドキュメント

* <a href="https://chat2db.opensource.alibaba.com">公式ウェブサイトドキュメント</a>
* <a href="https://github.com/alibaba/ali-dbhub/issues">Issue </a>

## Stargazers
[![Stargazers repo roster for @alibaba/Chat2DB](https://reporoster.com/stars/alibaba/Chat2DB)](https://github.com/alibaba/Chat2DB/stargazers)

## Forkers
[![Forkers repo roster for @alibaba/Chat2DB](https://reporoster.com/forks/alibaba/Chat2DB)](https://github.com/alibaba/Chat2DB/network/members)

## ☎️ お問い合わせ
グループに参加する前に、GitHubでスターとフォークをお願いします。
微信（由于个人微信加好友太多被限制了，如果无法添加成功，可以先邮箱联系 1558143046@qq.com）：

<a><img src="./document/qrcode/weixinqun1.png" width="30%"/></a>
<a><img src="./document/qrcode/weixinqun2.png" width="30%"/></a>
<a><img src="./document/qrcode/weixinqun3.png" width="30%"/></a>

Ding Talk：9135032392

qq

<a><img src="./document/qrcode/qqqun.png" width="30%"/></a>

## ❤️ 謝辞
Chat2DB~ にご協力いただいた学生の皆さん、ありがとうございました

<a href="https://github.com/alibaba/ali-dbhub/graphs/contributors">
  <img src="https://contrib.rocks/image?repo=alibaba/ali-dbhub" />
</a>


## Star History

<a href="https://star-history.com/#alibaba/chat2db&Date">
  <picture>
    <source media="(prefers-color-scheme: dark)" srcset="https://api.star-history.com/svg?repos=alibaba/chat2db&type=Date&theme=dark" />
    <source media="(prefers-color-scheme: light)" srcset="https://api.star-history.com/svg?repos=alibaba/chat2db&type=Date" />
    <img alt="Star History Chart" src="https://api.star-history.com/svg?repos=alibaba/chat2db&type=Date" />
  </picture>
</a>

