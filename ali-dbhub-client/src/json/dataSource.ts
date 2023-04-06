import { DatabaseTypeCode } from '@/utils/constants';

export enum InputType {
  INPUT = 'input',
  PASSWORD = 'password',
  SELECT = 'select'
}

export type ISelect = {
  selected: boolean;
  value: string;
  items?: IFormItem[];
}

export interface IFormItem  {
  defaultValue: string;
  inputType: InputType;
  labelNameCN: string;
  labelNameEN: string;
  name: string;
  required: boolean;
  width: number;
  pattern?: string;
  template?: string;
  selects?: ISelect[];
}

// 配置链接数据源表单 Json
export type IDataSourceForm = {
  type: DatabaseTypeCode;
  items: IFormItem[]
}

export const dataSourceFormConfigs:IDataSourceForm[] = [
  {
    "items": [
      {
        "defaultValue": "@loclhost",
        "inputType": InputType.INPUT,
        "labelNameCN": "名称",
        "labelNameEN": "Name",
        "name": "alias",
        "required": true,
        "width": 100
      },
      {
        "defaultValue": "loclhost",
        "inputType": InputType.INPUT,
        "labelNameCN": "主机",
        "labelNameEN": "Host",
        "name": "host",
        "required": true,
        "width": 70
      },
      {
        "defaultValue": "3306",
        "inputType": InputType.INPUT,
        "labelNameCN": "端口",
        "labelNameEN": "Port",
        "name": "port",
        "required": true,
        "width": 30
      },
      {
        "defaultValue": "User&Password",
        "inputType": InputType.SELECT,
        "labelNameCN": "身份验证",
        "labelNameEN": "Authentication",
        "name": "auth",
        "required": true,
        "selects": [
          {
            "items": [
              {
                "defaultValue": "root",
                "inputType": InputType.INPUT,
                "labelNameCN": "用户名",
                "labelNameEN": "User",
                "name": "userName",
                "required": true,
                "width": 100
              },
              {
                "defaultValue": "",
                "inputType": InputType.PASSWORD,
                "labelNameCN": "密码",
                "labelNameEN": "Password",
                "name": "password",
                "required": true,
                "width": 100
              }
            ],
            "selected": true,
            "value": "User&Password"
          },
          {
            "selected": false,
            "value": "NONE"
          }
        ],
        "width": 50
      },
      {
        "defaultValue": "",
        "inputType": InputType.INPUT,
        "labelNameCN": "数据库",
        "labelNameEN": "Database",
        "name": "database",
        "required": false,
        "width": 100
      },
      {
        "defaultValue": "jdbc:mysql://localhost:3306",
        "inputType": InputType.INPUT,
        "labelNameCN": "URL",
        "labelNameEN": "URL",
        "name": "url",
        "required": true,
        "width": 100,
        "pattern": "jdbc:mysql://(.*):(.*)/(.*)",
        "template": "jdbc:mysql://${host}:${port}/${database}"
      }
    ],
    "type": DatabaseTypeCode.MYSQL
  },
  {
    "items": [
      {
        "defaultValue": "@loclhost",
        "inputType": InputType.INPUT,
        "labelNameCN": "名称",
        "labelNameEN": "Name",
        "name": "alias",
        "required": true,
        "width": 100
      },
      {
        "defaultValue": "loclhost",
        "inputType": InputType.INPUT,
        "labelNameCN": "主机",
        "labelNameEN": "Host",
        "name": "host",
        "required": true,
        "width": 70
      },
      {
        "defaultValue": "5432",
        "inputType": InputType.INPUT,
        "labelNameCN": "端口",
        "labelNameEN": "Port",
        "name": "port",
        "required": true,
        "width": 30
      },
      {
        "defaultValue": "User&Password",
        "inputType": InputType.SELECT,
        "labelNameCN": "身份验证",
        "labelNameEN": "Authentication",
        "name": "auth",
        "required": true,
        "selects": [
          {
            "items": [
              {
                "defaultValue": "root",
                "inputType": InputType.INPUT,
                "labelNameCN": "用户名",
                "labelNameEN": "User",
                "name": "userName",
                "required": true,
                "width": 100
              },
              {
                "defaultValue": "",
                "inputType": InputType.PASSWORD,
                "labelNameCN": "密码",
                "labelNameEN": "Password",
                "name": "password",
                "required": true,
                "width": 100
              }
            ],
            "selected": true,
            "value": "User&Password"
          },
          {
            "selected": false,
            "value": "NONE"
          }
        ],
        "width": 50
      },
      {
        "defaultValue": "",
        "inputType": InputType.INPUT,
        "labelNameCN": "数据库",
        "labelNameEN": "Database",
        "name": "database",
        "required": false,
        "width": 100
      },
      {
        "defaultValue": "jdbc:postgresql://localhost:5432",
        "inputType": InputType.INPUT,
        "labelNameCN": "URL",
        "labelNameEN": "URL",
        "name": "url",
        "required": true,
        "width": 100,
        "pattern": "jdbc:mysql://(.*):(.*)/(.*)",
        "template": "jdbc:mysql://${host}:${port}/${database}"
      }
    ],
    "type": DatabaseTypeCode.POSTGRESQL
  },
  {
    "items": [
      {
        "defaultValue": "@loclhost",
        "inputType": InputType.INPUT,
        "labelNameCN": "名称",
        "labelNameEN": "Name",
        "name": "alias",
        "required": true,
        "width": 100
      },
      {
        "defaultValue": "loclhost",
        "inputType": InputType.INPUT,
        "labelNameCN": "主机",
        "labelNameEN": "Host",
        "name": "host",
        "required": true,
        "width": 70
      },
      {
        "defaultValue": "1521",
        "inputType": InputType.INPUT,
        "labelNameCN": "端口",
        "labelNameEN": "Port",
        "name": "port",
        "required": true,
        "width": 30
      },
      {
        "defaultValue": "XE",
        "inputType": InputType.INPUT,
        "labelNameCN": "SID",
        "labelNameEN": "SID",
        "name": "sid",
        "required": true,
        "width": 70
      },
      {
        "defaultValue": "thin",
        "inputType": InputType.SELECT,
        "labelNameCN": "驱动",
        "labelNameEN": "Driver",
        "name": "driver",
        "required": true,
        "selects": [
          {
            "selected": true,
            "value": "thin"
          },
          {
            "selected": false,
            "value": "oci"
          },
          {
            "selected": false,
            "value": "oci8"
          }
        ],
        "width": 30
      },
      {
        "defaultValue": "User&Password",
        "inputType": InputType.SELECT,
        "labelNameCN": "身份验证",
        "labelNameEN": "Authentication",
        "name": "auth",
        "required": true,
        "selects": [
          {
            "items": [
              {
                "defaultValue": "root",
                "inputType": InputType.INPUT,
                "labelNameCN": "用户名",
                "labelNameEN": "User",
                "name": "userName",
                "required": true,
                "width": 100
              },
              {
                "defaultValue": "",
                "inputType": InputType.PASSWORD,
                "labelNameCN": "密码",
                "labelNameEN": "Password",
                "name": "password",
                "required": true,
                "width": 100
              }
            ],
            "selected": true,
            "value": "User&Password"
          },
          {
            "selected": false,
            "value": "NONE"
          }
        ],
        "width": 50
      },
      {
        "defaultValue": "jdbc:oracle:thin://localhost:3306:XE",
        "inputType": InputType.INPUT,
        "labelNameCN": "URL",
        "labelNameEN": "URL",
        "name": "url",
        "required": true,
        "width": 100,
        "pattern": "jdbc:mysql://(.*):(.*)/(.*)",
        "template": "jdbc:mysql://${host}:${port}/${database}"
      }
    ],
    "type": DatabaseTypeCode.ORACLE
  },
  {
    "items": [
      {
        "defaultValue": "@loclhost",
        "inputType": InputType.INPUT,
        "labelNameCN": "名称",
        "labelNameEN": "Name",
        "name": "alias",
        "required": true,
        "width": 100
      },
      {
        "defaultValue": "loclhost",
        "inputType": InputType.INPUT,
        "labelNameCN": "主机",
        "labelNameEN": "Host",
        "name": "host",
        "required": true,
        "width": 70
      },
      {
        "defaultValue": "1433",
        "inputType": InputType.INPUT,
        "labelNameCN": "端口",
        "labelNameEN": "Port",
        "name": "port",
        "required": true,
        "width": 30
      },
      {
        "defaultValue": "",
        "inputType": InputType.INPUT,
        "labelNameCN": "Instance",
        "labelNameEN": "Instance",
        "name": "instance",
        "required": false,
        "width": 100
      },
      {
        "defaultValue": "User&Password",
        "inputType": InputType.SELECT,
        "labelNameCN": "身份验证",
        "labelNameEN": "Authentication",
        "name": "auth",
        "required": true,
        "selects": [
          {
            "items": [
              {
                "defaultValue": "root",
                "inputType": InputType.INPUT,
                "labelNameCN": "用户名",
                "labelNameEN": "User",
                "name": "userName",
                "required": true,
                "width": 100
              },
              {
                "defaultValue": "",
                "inputType": InputType.PASSWORD,
                "labelNameCN": "密码",
                "labelNameEN": "Password",
                "name": "password",
                "required": true,
                "width": 100
              }
            ],
            "selected": true,
            "value": "User&Password"
          },
          {
            "selected": false,
            "value": "NONE"
          }
        ],
        "width": 50
      },
      {
        "defaultValue": "",
        "inputType": InputType.INPUT,
        "labelNameCN": "数据库",
        "labelNameEN": "Database",
        "name": "database",
        "required": false,
        "width": 100
      },
      {
        "defaultValue": "jdbc:sqlserver://localhost:1433",
        "inputType": InputType.INPUT,
        "labelNameCN": "URL",
        "labelNameEN": "URL",
        "name": "url",
        "required": true,
        "width": 100,
        "pattern": "jdbc:mysql://(.*):(.*)/(.*)",
        "template": "jdbc:mysql://${host}:${port}/${database}"
      }
    ],
    "type": DatabaseTypeCode.SQLSERVER
  },
  {
    "items": [
      {
        "defaultValue": "@loclhost",
        "inputType": InputType.INPUT,
        "labelNameCN": "名称",
        "labelNameEN": "Name",
        "name": "alias",
        "required": true,
        "width": 100
      },
      {
        "defaultValue": "loclhost",
        "inputType": InputType.INPUT,
        "labelNameCN": "主机",
        "labelNameEN": "Host",
        "name": "host",
        "required": true,
        "width": 70
      },
      {
        "defaultValue": "9092",
        "inputType": InputType.INPUT,
        "labelNameCN": "端口",
        "labelNameEN": "Port",
        "name": "port",
        "required": true,
        "width": 30
      },
      {
        "defaultValue": "User&Password",
        "inputType": InputType.SELECT,
        "labelNameCN": "身份验证",
        "labelNameEN": "Authentication",
        "name": "auth",
        "required": true,
        "selects": [
          {
            "items": [
              {
                "defaultValue": "root",
                "inputType": InputType.INPUT,
                "labelNameCN": "用户名",
                "labelNameEN": "User",
                "name": "userName",
                "required": true,
                "width": 100
              },
              {
                "defaultValue": "",
                "inputType": InputType.PASSWORD,
                "labelNameCN": "密码",
                "labelNameEN": "Password",
                "name": "password",
                "required": true,
                "width": 100
              }
            ],
            "selected": true,
            "value": "User&Password"
          },
          {
            "selected": false,
            "value": "NONE"
          }
        ],
        "width": 50
      },
      {
        "defaultValue": "",
        "inputType": InputType.INPUT,
        "labelNameCN": "数据库",
        "labelNameEN": "Database",
        "name": "database",
        "required": false,
        "width": 100
      },
      {
        "defaultValue": "jdbc:h2:tcp://localhost:9092",
        "inputType": InputType.INPUT,
        "labelNameCN": "URL",
        "labelNameEN": "URL",
        "name": "url",
        "required": true,
        "width": 100,
        "pattern": "jdbc:mysql://(.*):(.*)/(.*)",
        "template": "jdbc:mysql://${host}:${port}/${database}"
      }
    ],
    "type": DatabaseTypeCode.H2
  },
  {
    "items": [
      {
        "defaultValue": "@loclhost",
        "inputType": InputType.INPUT,
        "labelNameCN": "名称",
        "labelNameEN": "Name",
        "name": "alias",
        "required": true,
        "width": 100
      },
      {
        "defaultValue": "identifier.sqlite",
        "inputType": InputType.INPUT,
        "labelNameCN": "File",
        "labelNameEN": "File",
        "name": "file",
        "required": true,
        "width": 100
      },
      {
        "defaultValue": "jdbc:sqlite:identifier.sqlite",
        "inputType": InputType.INPUT,
        "labelNameCN": "URL",
        "labelNameEN": "URL",
        "name": "url",
        "required": true,
        "width": 100,
        "pattern": "jdbc:mysql://(.*):(.*)/(.*)",
        "template": "jdbc:mysql://${host}:${port}/${database}"
      }
    ],
    "type": DatabaseTypeCode.SQLITE
  }
]
