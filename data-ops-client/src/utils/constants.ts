import mysqlLogo from '@/assets/mysql-logo.png';
import redisLogo from '@/assets/redis-logo.png';
import {IDatabase} from '@/types'

export enum DatabaseTypeCode {
  MYSQL = 'MYSQL',
  ORACLE = 'ORACLE',
  DB2 = 'DB2',
  MONGODB = 'MONGODB',
  REDIS = 'REDIS'
}

export const databaseType:{
  [keys:string]:IDatabase;
} = {
  [DatabaseTypeCode.MYSQL]:{
    name: 'MySQL',
    img: redisLogo,
    code: DatabaseTypeCode.MYSQL
  },
  [DatabaseTypeCode.REDIS]:{
    name: 'redis',
    img: redisLogo,
    code: DatabaseTypeCode.REDIS
  }
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
