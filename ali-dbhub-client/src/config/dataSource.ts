import { DatabaseTypeCode } from '@/utils/constants';
import { IDataSourceForm} from './types';
import {InputType,AuthenticationType, OperationColumn } from './enum';

export const dataSourceFormConfigs: IDataSourceForm[] = [
  // MYSQL
  {
    baseInfo: {
      items: [
        {
          defaultValue: '@localhost',
          inputType: InputType.INPUT,
          labelNameCN: '名称',
          labelNameEN: 'Name',
          name: 'alias',
          required: true,
          width: 100,
        },
        {
          defaultValue: 'localhost',
          inputType: InputType.INPUT,
          labelNameCN: '主机',
          labelNameEN: 'Host',
          name: 'host',
          required: true,
          width: 70,
        },
        {
          defaultValue: '3306',
          inputType: InputType.INPUT,
          labelNameCN: '端口',
          labelNameEN: 'Port',
          name: 'port',
          labelTextAlign: 'right',
          required: true,
          width: 30,
        },
        {
          defaultValue: AuthenticationType.USERANDPASSWORD,
          inputType: InputType.SELECT,
          labelNameCN: '身份验证',
          labelNameEN: 'Authentication',
          name: 'authentication',
          required: true,
          selects: [
            {
              items: [
                {
                  defaultValue: 'root',
                  inputType: InputType.INPUT,
                  labelNameCN: '用户名',
                  labelNameEN: 'User',
                  name: 'user',
                  required: true,
                  width: 100,
                },
                {
                  defaultValue: '',
                  inputType: InputType.PASSWORD,
                  labelNameCN: '密码',
                  labelNameEN: 'Password',
                  name: 'password',
                  required: true,
                  width: 100,
                },
              ],
              label: 'User&Password',
              value: AuthenticationType.USERANDPASSWORD,
            },
            {
              label: 'NONE',
              value: AuthenticationType.NONE,
            },
          ],
          width: 50,
        },
        {
          defaultValue: '',
          inputType: InputType.INPUT,
          labelNameCN: '数据库',
          labelNameEN: 'Database',
          name: 'database',
          required: false,
          width: 100,
        },
        {
          defaultValue: 'jdbc:mysql://localhost:3306',
          inputType: InputType.INPUT,
          labelNameCN: 'URL',
          labelNameEN: 'URL',
          name: 'url',
          required: true,
          width: 100,
        },
      ],
      type: DatabaseTypeCode.MYSQL,
      pattern: /jdbc:mysql:\/\/(.*):(\d+)(\/(\w+))?/,
      template: 'jdbc:mysql://{host}:{port}/{database}',
    },
    ssh: {
      items: [
        {
          defaultValue: false,
          inputType: InputType.INPUT,
          labelNameCN: 'ssh',
          labelNameEN: 'ssh',
          name: 'ssh',
          required: false,
          width: 100,
        },
        {
          defaultValue: false,
          inputType: InputType.INPUT,
          labelNameCN: 'ssh2',
          labelNameEN: 'ssh2',
          name: 'ssh2',
          required: false,
          width: 100,
        },
      ]
    },
    extendInfo: {
      items: [
        {
          defaultValue: false,
          inputType: InputType.INPUT,
          labelNameCN: 'xxValue',
          labelNameEN: 'xxxxxValue',
          name: 'xxxxxValue',
          required: false,
          width: 100,
        },
        {
          defaultValue: false,
          inputType: InputType.INPUT,
          labelNameCN: 'xxValue1',
          labelNameEN: 'xxValue1',
          name: 'xxxxxValue1',
          required: false,
          width: 100,
        },
      ]
    }
  },
  // POSTGRESQL
  {
    baseInfo: {
      items: [
        {
          defaultValue: '@localhost',
          inputType: InputType.INPUT,
          labelNameCN: '名称',
          labelNameEN: 'Name',
          name: 'alias',
          required: true,
          width: 100,
        },
        {
          defaultValue: 'localhost',
          inputType: InputType.INPUT,
          labelNameCN: '主机',
          labelNameEN: 'Host',
          name: 'host',
          required: true,
          width: 70,
        },
        {
          defaultValue: '5432',
          inputType: InputType.INPUT,
          labelNameCN: '端口',
          labelNameEN: 'Port',
          name: 'port',
          labelTextAlign: 'right',
          required: true,
          width: 30,
        },
        {
          defaultValue: AuthenticationType.USERANDPASSWORD,
          inputType: InputType.SELECT,
          labelNameCN: '身份验证',
          labelNameEN: 'Authentication',
          name: 'authentication',
          required: true,
          selects: [
            {
              items: [
                {
                  defaultValue: 'root',
                  inputType: InputType.INPUT,
                  labelNameCN: '用户名',
                  labelNameEN: 'User',
                  name: 'user',
                  required: true,
                  width: 100,
                },
                {
                  defaultValue: '',
                  inputType: InputType.PASSWORD,
                  labelNameCN: '密码',
                  labelNameEN: 'Password',
                  name: 'password',
                  required: true,
                  width: 100,
                },
              ],
              label: 'User&Password',
              value: AuthenticationType.USERANDPASSWORD,
            },
            {
  
              label: 'NONE',
              value: AuthenticationType.NONE,
            },
          ],
          width: 50,
        },
        {
          defaultValue: '',
          inputType: InputType.INPUT,
          labelNameCN: '数据库',
          labelNameEN: 'Database',
          name: 'database',
          required: false,
          width: 100,
        },
        {
          defaultValue: 'jdbc:postgresql://localhost:5432',
          inputType: InputType.INPUT,
          labelNameCN: 'URL',
          labelNameEN: 'URL',
          name: 'url',
          required: true,
          width: 100,
        },
      ],
      type: DatabaseTypeCode.POSTGRESQL,
      pattern: /jdbc:postgresql:\/\/(.*):(\d+)(\/(\w+))?/,
      template: 'jdbc:postgresql://{host}:{port}/{database}',
    }
  },
  // ORACLE
  {
    baseInfo: {
      items: [
        {
          defaultValue: '@localhost',
          inputType: InputType.INPUT,
          labelNameCN: '名称',
          labelNameEN: 'Name',
          name: 'alias',
          required: true,
          width: 100,
        },
        {
          defaultValue: 'localhost',
          inputType: InputType.INPUT,
          labelNameCN: '主机',
          labelNameEN: 'Host',
          name: 'host',
          required: true,
          width: 70,
        },
        {
          defaultValue: '1521',
          inputType: InputType.INPUT,
          labelNameCN: '端口',
          labelNameEN: 'Port',
          name: 'port',
          labelTextAlign: 'right',
          required: true,
          width: 30,
        },
        {
          defaultValue: 'XE',
          inputType: InputType.INPUT,
          labelNameCN: 'SID',
          labelNameEN: 'SID',
          name: 'sid',
          required: true,
          width: 70,
        },
        {
          defaultValue: 'thin',
          inputType: InputType.SELECT,
          labelNameCN: '驱动',
          labelNameEN: 'Driver',
          name: 'driver',
          required: true,
          labelTextAlign: 'right',
          selects: [
            {
              value: 'thin',
            },
            {
  
              value: 'oci',
            },
            {
  
              value: 'oci8',
            },
          ],
          width: 30,
        },
        {
          defaultValue: AuthenticationType.USERANDPASSWORD,
          inputType: InputType.SELECT,
          labelNameCN: '身份验证',
          labelNameEN: 'Authentication',
          name: 'authentication',
          required: true,
          selects: [
            {
              items: [
                {
                  defaultValue: 'root',
                  inputType: InputType.INPUT,
                  labelNameCN: '用户名',
                  labelNameEN: 'User',
                  name: 'user',
                  required: true,
                  width: 100,
                },
                {
                  defaultValue: '',
                  inputType: InputType.PASSWORD,
                  labelNameCN: '密码',
                  labelNameEN: 'Password',
                  name: 'password',
                  required: true,
                  width: 100,
                },
              ],
              label: 'User&Password',
              value: AuthenticationType.USERANDPASSWORD,
            },
            {
  
              label: 'NONE',
              value: AuthenticationType.NONE,
            },
          ],
          width: 50,
        },
        {
          defaultValue: 'jdbc:oracle:thin:@localhost:1521:XE',
          inputType: InputType.INPUT,
          labelNameCN: 'URL',
          labelNameEN: 'URL',
          name: 'url',
          required: true,
          width: 100,
        },
      ],
      type: DatabaseTypeCode.ORACLE,
      pattern: /jdbc:oracle:(.*):@(.*):(\d+):(.*)/,
      template: 'jdbc:oracle:{driver}:@{host}:{port}:{sid}',
    }
  },
  // H2
  {
    baseInfo: {
      items: [
        {
          defaultValue: '@localhost',
          inputType: InputType.INPUT,
          labelNameCN: '名称',
          labelNameEN: 'Name',
          name: 'alias',
          required: true,
          width: 100,
        },
        {
          defaultValue: 'localhost',
          inputType: InputType.INPUT,
          labelNameCN: '主机',
          labelNameEN: 'Host',
          name: 'host',
          required: true,
          width: 70,
        },
        {
          defaultValue: '9092',
          inputType: InputType.INPUT,
          labelNameCN: '端口',
          labelNameEN: 'Port',
          name: 'port',
          labelTextAlign: 'right',
          required: true,
          width: 30,
        },
        {
          defaultValue: AuthenticationType.USERANDPASSWORD,
          inputType: InputType.SELECT,
          labelNameCN: '身份验证',
          labelNameEN: 'Authentication',
          name: 'authentication',
          required: true,
          selects: [
            {
              items: [
                {
                  defaultValue: 'root',
                  inputType: InputType.INPUT,
                  labelNameCN: '用户名',
                  labelNameEN: 'User',
                  name: 'user',
                  required: true,
                  width: 100,
                },
                {
                  defaultValue: '',
                  inputType: InputType.PASSWORD,
                  labelNameCN: '密码',
                  labelNameEN: 'Password',
                  name: 'password',
                  required: true,
                  width: 100,
                },
              ],
  
              label: 'User&Password',
              value: AuthenticationType.USERANDPASSWORD,
            },
            {
  
              label: 'NONE',
              value: AuthenticationType.NONE,
            },
          ],
          width: 50,
        },
        {
          defaultValue: '',
          inputType: InputType.INPUT,
          labelNameCN: '数据库',
          labelNameEN: 'Database',
          name: 'database',
          required: false,
          width: 100,
        },
        {
          defaultValue: 'jdbc:h2:tcp://localhost:9092',
          inputType: InputType.INPUT,
          labelNameCN: 'URL',
          labelNameEN: 'URL',
          name: 'url',
          required: true,
          width: 100,
        },
      ],
      type: DatabaseTypeCode.H2,
      pattern: /jdbc:h2:tcp:\/\/(.*):(\d+)(\/(\w+))?/,
      template: 'jdbc:h2:tcp://{host}:{port}/{database}',
    }
  },
  // SQLSERVER
  {
    baseInfo: {
      items: [
        {
          defaultValue: '@localhost',
          inputType: InputType.INPUT,
          labelNameCN: '名称',
          labelNameEN: 'Name',
          name: 'alias',
          required: true,
          width: 100,
        },
        {
          defaultValue: 'localhost',
          inputType: InputType.INPUT,
          labelNameCN: '主机',
          labelNameEN: 'Host',
          name: 'host',
          required: true,
          width: 70,
        },
        {
          defaultValue: '1433',
          inputType: InputType.INPUT,
          labelNameCN: '端口',
          labelNameEN: 'Port',
          name: 'port',
          labelTextAlign: 'right',
          required: true,
          width: 30,
        },
        {
          defaultValue: '',
          inputType: InputType.INPUT,
          labelNameCN: 'Instance',
          labelNameEN: 'Instance',
          name: 'instance',
          required: false,
          width: 100,
        },
        {
          defaultValue: AuthenticationType.USERANDPASSWORD,
          inputType: InputType.SELECT,
          labelNameCN: '身份验证',
          labelNameEN: 'Authentication',
          name: 'authentication',
          required: true,
          selects: [
            {
              items: [
                {
                  defaultValue: 'root',
                  inputType: InputType.INPUT,
                  labelNameCN: '用户名',
                  labelNameEN: 'User',
                  name: 'user',
                  required: true,
                  width: 100,
                },
                {
                  defaultValue: '',
                  inputType: InputType.PASSWORD,
                  labelNameCN: '密码',
                  labelNameEN: 'Password',
                  name: 'password',
                  required: true,
                  width: 100,
                },
              ],
  
              label: 'User&Password',
              value: AuthenticationType.USERANDPASSWORD,
            },
            {
  
              label: 'NONE',
              value: AuthenticationType.NONE,
            },
          ],
          width: 50,
        },
        {
          defaultValue: '',
          inputType: InputType.INPUT,
          labelNameCN: '数据库',
          labelNameEN: 'Database',
          name: 'database',
          required: false,
          width: 100,
        },
        {
          defaultValue: 'jdbc:sqlserver://localhost:1433',
          inputType: InputType.INPUT,
          labelNameCN: 'URL',
          labelNameEN: 'URL',
          name: 'url',
          required: true,
          width: 100,
        },
      ],
      type: DatabaseTypeCode.SQLSERVER,
      pattern: /jdbc:sqlserver:\/\/(.*):(\d+)(\/(\w+))?/,
      template: 'jdbc:sqlserver://{host}:{port}/{database}',
    }
  },
  // SQLITE
  {
    baseInfo: {
      items: [
        {
          defaultValue: '@localhost',
          inputType: InputType.INPUT,
          labelNameCN: '名称',
          labelNameEN: 'Name',
          name: 'alias',
          required: true,
          width: 100,
        },
        {
          defaultValue: 'identifier.sqlite',
          inputType: InputType.INPUT,
          labelNameCN: 'File',
          labelNameEN: 'File',
          name: 'file',
          required: true,
          width: 100,
        },
        {
          defaultValue: 'jdbc:sqlite:identifier.sqlite',
          inputType: InputType.INPUT,
          labelNameCN: 'URL',
          labelNameEN: 'URL',
          name: 'url',
          required: true,
          width: 100,
        },
      ],
      type: DatabaseTypeCode.SQLITE,
      pattern: /jdbc:sqlite/,
      template: 'jdbc:sqlite://{host}:{port}/{database}',
    }
  },
  // MARIADB
  {
    baseInfo: {
      items: [
        {
          defaultValue: '@localhost',
          inputType: InputType.INPUT,
          labelNameCN: '名称',
          labelNameEN: 'Name',
          name: 'alias',
          required: true,
          width: 100,
        },
        {
          defaultValue: 'localhost',
          inputType: InputType.INPUT,
          labelNameCN: '主机',
          labelNameEN: 'Host',
          name: 'host',
          required: true,
          width: 70,
        },
        {
          defaultValue: '3306',
          inputType: InputType.INPUT,
          labelNameCN: '端口',
          labelNameEN: 'Port',
          name: 'port',
          labelTextAlign: 'right',
          required: true,
          width: 30,
        },
        {
          defaultValue: AuthenticationType.USERANDPASSWORD,
          inputType: InputType.SELECT,
          labelNameCN: '身份验证',
          labelNameEN: 'Authentication',
          name: 'authentication',
          required: true,
          selects: [
            {
              items: [
                {
                  defaultValue: 'root',
                  inputType: InputType.INPUT,
                  labelNameCN: '用户名',
                  labelNameEN: 'User',
                  name: 'user',
                  required: true,
                  width: 100,
                },
                {
                  defaultValue: '',
                  inputType: InputType.PASSWORD,
                  labelNameCN: '密码',
                  labelNameEN: 'Password',
                  name: 'password',
                  required: true,
                  width: 100,
                },
              ],
  
              label: 'User&Password',
              value: AuthenticationType.USERANDPASSWORD,
            },
            {
  
              label: 'NONE',
              value: AuthenticationType.NONE,
            },
          ],
          width: 50,
        },
        {
          defaultValue: '',
          inputType: InputType.INPUT,
          labelNameCN: '数据库',
          labelNameEN: 'Database',
          name: 'database',
          required: false,
          width: 100,
        },
        {
          defaultValue: 'jdbc:mariadb://localhost:3306',
          inputType: InputType.INPUT,
          labelNameCN: 'URL',
          labelNameEN: 'URL',
          name: 'url',
          required: true,
          width: 100,
        },
      ],
      type: DatabaseTypeCode.MARIADB,
      pattern: /jdbc:mariadb:\/\/(.*):(\d+)(\/(\w+))?/,
      template: 'jdbc:mariadb://{host}:{port}/{database}',
    }
  },
  // CLICKHOUSE
  {
    baseInfo: {
      items: [
        {
          defaultValue: '@localhost',
          inputType: InputType.INPUT,
          labelNameCN: '名称',
          labelNameEN: 'Name',
          name: 'alias',
          required: true,
          width: 100,
        },
        {
          defaultValue: 'localhost',
          inputType: InputType.INPUT,
          labelNameCN: '主机',
          labelNameEN: 'Host',
          name: 'host',
          required: true,
          width: 70,
        },
        {
          defaultValue: '8123',
          inputType: InputType.INPUT,
          labelNameCN: '端口',
          labelNameEN: 'Port',
          name: 'port',
          labelTextAlign: 'right',
          required: true,
          width: 30,
        },
        {
          defaultValue: AuthenticationType.USERANDPASSWORD,
          inputType: InputType.SELECT,
          labelNameCN: '身份验证',
          labelNameEN: 'Authentication',
          name: 'authentication',
          required: true,
          selects: [
            {
              items: [
                {
                  defaultValue: 'root',
                  inputType: InputType.INPUT,
                  labelNameCN: '用户名',
                  labelNameEN: 'User',
                  name: 'user',
                  required: true,
                  width: 100,
                },
                {
                  defaultValue: '',
                  inputType: InputType.PASSWORD,
                  labelNameCN: '密码',
                  labelNameEN: 'Password',
                  name: 'password',
                  required: true,
                  width: 100,
                },
              ],
  
              label: 'User&Password',
              value: AuthenticationType.USERANDPASSWORD,
            },
            {
  
              label: 'NONE',
              value: AuthenticationType.NONE,
            },
          ],
          width: 50,
        },
        {
          defaultValue: '',
          inputType: InputType.INPUT,
          labelNameCN: '数据库',
          labelNameEN: 'Database',
          name: 'database',
          required: false,
          width: 100,
        },
        {
          defaultValue: 'jdbc:clickhouse://localhost:8123',
          inputType: InputType.INPUT,
          labelNameCN: 'URL',
          labelNameEN: 'URL',
          name: 'url',
          required: true,
          width: 100,
        },
      ],
      type: DatabaseTypeCode.CLICKHOUSE,
      pattern: /jdbc:clickhouse:\/\/(.*):(\d+)(\/(\w+))?/,
      template: 'jdbc:clickhouse://{host}:{port}/{database}',
      excludes: [OperationColumn.ExportDDL,OperationColumn.CreateTable] //排除掉导出ddl 和 创建表功能 支持的功能见 ./enum.ts => OperationColumn
    }
  },
];
