import mysqlLogo from '@/assets/mysql-logo.png';
import redisLogo from '@/assets/redis-logo.png';
import h2Logo from '@/assets/h2-logo.png';
import moreDBLogo from '@/assets/moreDB-logo.png';
import {IDatabase} from '@/types'

export enum StatusType {
  SUCCESS= 'success',
  FAIL = 'fail'
}

export enum DatabaseTypeCode {
  MYSQL = 'MYSQL',
  ORACLE = 'ORACLE',
  DB2 = 'DB2',
  MONGODB = 'MONGODB',
  REDIS = 'REDIS',
  H2 = 'H2',
  POSTGRESQL = 'POSTGRESQL'
}

export const databaseType:{
  [keys:string]:IDatabase;
} = {
  [DatabaseTypeCode.MYSQL]:{
    name: 'MySQL',
    img: mysqlLogo,
    code: DatabaseTypeCode.MYSQL,
    port: 3306
  },
  // [DatabaseTypeCode.REDIS]:{
  //   name: 'Redis',
  //   img: redisLogo,
  //   code: DatabaseTypeCode.REDIS
  // },
  [DatabaseTypeCode.H2]:{
    name: 'H2',
    img: h2Logo,
    code: DatabaseTypeCode.H2,
    port: 9092
  },
  [DatabaseTypeCode.ORACLE]:{
    name: 'Oracle',
    img: moreDBLogo,
    code: DatabaseTypeCode.ORACLE,
    port: 1521
  },
  [DatabaseTypeCode.POSTGRESQL]:{
    name: 'PostgreSql',
    img: moreDBLogo,
    code: DatabaseTypeCode.POSTGRESQL,
    port: 5432
  },
}

export const databaseTypeList = Object.keys(databaseType).map(keys=>{
  return databaseType[keys]
})

export enum TreeNodeType {
  DATASOURCE = 'dataSource',
  DATABASE = 'database',
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
  BIG_DECIMAL = 'BIG_DECIMAL'
}

export enum TableDataTypeCorresValue {
  STRING = 'stringValue',
  DATA = 'dateValue',
  BYTE = 'byteValue',
  EMPTY = 'emptyValue',
  BIG_DECIMAL = 'bigDecimalValue'
}

export enum EnvType {
  DAILY = 'DAILY',
  PRODUCT = 'PRODUCT'
}

export enum OSType {
  WIN = 'Win',
  MAC = 'Mac',
  RESTS = 'rests',
}
export enum ConsoleType {
  SQLQ = 'SQLQ',
  EDITTABLE = 'editTable'
}

export enum  TabOpened {
  IS_OPEN = 'Y',
  NOT_OPEN = 'N'
}


