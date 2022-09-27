import mysqlLogo from '@/assets/mysqlLogo.png';
import redisLogo from '@/assets/redisLogo.png';
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
    img: mysqlLogo,
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




