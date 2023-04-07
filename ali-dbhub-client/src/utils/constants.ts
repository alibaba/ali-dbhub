import mysqlLogo from '@/assets/mysql-logo.png';
import redisLogo from '@/assets/redis-logo.png';
import h2Logo from '@/assets/h2-logo.png';
import moreDBLogo from '@/assets/moreDB-logo.png';
import { IDatabase } from '@/types';

export enum ThemeType {
  default = 'default',
  dark = 'dark',
}

export enum StatusType {
  SUCCESS = 'success',
  FAIL = 'fail',
}

export enum DatabaseTypeCode {
  MYSQL = 'MYSQL',
  ORACLE = 'ORACLE',
  DB2 = 'DB2',
  MONGODB = 'MONGODB',
  REDIS = 'REDIS',
  H2 = 'H2',
  POSTGRESQL = 'POSTGRESQL',
  SQLSERVER = 'SQLSERVER',
  SQLITE = 'SQLITE',
}

export const databaseType: {
  [keys: string]: IDatabase;
} = {
  [DatabaseTypeCode.MYSQL]: {
    name: 'MySQL',
    img: mysqlLogo,
    code: DatabaseTypeCode.MYSQL,
    port: 3306,
    icon: '\uec6d'
  },
  // [DatabaseTypeCode.REDIS]:{
  //   name: 'Redis',
  //   img: redisLogo,
  //   code: DatabaseTypeCode.REDIS
  // },
  [DatabaseTypeCode.H2]: {
    name: 'H2',
    img: h2Logo,
    code: DatabaseTypeCode.H2,
    port: 9092,
    icon: '\ue61c'
  },
  [DatabaseTypeCode.ORACLE]: {
    name: 'Oracle',
    img: moreDBLogo,
    code: DatabaseTypeCode.ORACLE,
    port: 1521,
    icon: '\uec48'
  },
  [DatabaseTypeCode.POSTGRESQL]: {
    name: 'PostgreSql',
    img: moreDBLogo,
    code: DatabaseTypeCode.POSTGRESQL,
    port: 5432,
    icon: '\uec5d'
  },
  [DatabaseTypeCode.SQLSERVER]: {
    name: 'SQLServer',
    img: moreDBLogo,
    code: DatabaseTypeCode.SQLSERVER,
    port: 1521,
    icon: '\ue664'
  },
  [DatabaseTypeCode.SQLITE]: {
    name: 'SQLite',
    img: moreDBLogo,
    code: DatabaseTypeCode.SQLITE,
    port: 5432,
    icon: '\ue65a'
  },
};

export const databaseTypeList = Object.keys(databaseType).map((keys) => {
  return databaseType[keys];
});

export enum TreeNodeType {
  DATASOURCES = 'dataSources',
  DATASOURCE = 'dataSource',
  DATABASE = 'database',
  SCHEMAS = 'schemas',
  TABLES = 'tables',
  TABLE = 'table',
  COLUMNS = 'columns',
  COLUMN = 'column',
  KEYS = 'keys',
  KEY = 'key',
  INDEXES = 'indexes',
  INDEXE = 'indexe',
  SEARCH = 'search',
  LINE = 'line',
  LINETOTAL = 'lineTotal',
  SAVE = 'save',
  INDEXESTOTAL = 'indexesTotal',
}

export enum WindowTabStatus {
  DRAFT = 'DRAFT',
  RELEASE = 'RELEASE',
}

export enum ConsoleStatus {
  DRAFT = 'DRAFT',
  RELEASE = 'RELEASE',
}

export enum TableDataType {
  STRING = 'STRING',
  DATA = 'DATA',
  BYTE = 'BYTE',
  EMPTY = 'EMPTY',
  BIG_DECIMAL = 'BIG_DECIMAL',
}

export enum TableDataTypeCorresValue {
  STRING = 'stringValue',
  DATA = 'dateValue',
  BYTE = 'byteValue',
  EMPTY = 'emptyValue',
  BIG_DECIMAL = 'bigDecimalValue',
}

export enum EnvType {
  DAILY = 'DAILY',
  PRODUCT = 'PRODUCT',
}

export enum OSType {
  WIN = 'Win',
  MAC = 'Mac',
  RESTS = 'rests',
}
export enum ConsoleType {
  SQLQ = 'SQLQ',
  EDITTABLE = 'editTable',
}

export enum  TabOpened {
  IS_OPEN = 'y',
  NOT_OPEN = 'n'
}


