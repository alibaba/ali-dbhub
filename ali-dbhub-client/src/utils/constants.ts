import mysqlLogo from '@/assets/mysql-logo.png';
import redisLogo from '@/assets/redis-logo.png';
import h2Logo from '@/assets/h2-logo.png';
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
  H2 = 'H2'
}

export const databaseType:{
  [keys:string]:IDatabase;
} = {
  [DatabaseTypeCode.MYSQL]:{
    name: 'MySQL',
    img: mysqlLogo,
    code: DatabaseTypeCode.MYSQL
  },
  // [DatabaseTypeCode.REDIS]:{
  //   name: 'Redis',
  //   img: redisLogo,
  //   code: DatabaseTypeCode.REDIS
  // },
  [DatabaseTypeCode.H2]:{
    name: 'H2',
    img: h2Logo,
    code: DatabaseTypeCode.H2
  },
}

export const databaseTypeList = Object.keys(databaseType).map(keys=>{
  return databaseType[keys]
})

export enum TreeNodeType {
  TABLE = 'table',
  DATABASE = 'database',
  SEARCH = 'search',
  LINE = 'line',
  SAVE = 'save',
  INDEXES = 'indexes'
}

export enum WindowTabStatus {
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

