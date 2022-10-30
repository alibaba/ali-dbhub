import mysqlLogo from '@/assets/mysql-logo.png';
import redisLogo from '@/assets/redis-logo.png';
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
  [DatabaseTypeCode.REDIS]:{
    name: 'Redis',
    img: redisLogo,
    code: DatabaseTypeCode.REDIS
  },
  [DatabaseTypeCode.H2]:{
    name: 'H2',
    img: redisLogo,
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
