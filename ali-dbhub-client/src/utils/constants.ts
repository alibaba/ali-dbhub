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
  MARIADB = 'MARIADB',
  CLICKHOUSE = 'CLICKHOUSE',
}

export const databaseType: {
  [keys: string]: IDatabase;
} = {
  [DatabaseTypeCode.MYSQL]: {
    name: 'MySQL',
    img: mysqlLogo,
    code: DatabaseTypeCode.MYSQL,
    port: 3306,
    icon: '\uec6d',
  },
  [DatabaseTypeCode.H2]: {
    name: 'H2',
    img: h2Logo,
    code: DatabaseTypeCode.H2,
    port: 9092,
    icon: '\ue61c',
  },
  [DatabaseTypeCode.ORACLE]: {
    name: 'Oracle',
    img: moreDBLogo,
    code: DatabaseTypeCode.ORACLE,
    port: 1521,
    icon: '\uec48',
  },
  [DatabaseTypeCode.POSTGRESQL]: {
    name: 'PostgreSql',
    img: moreDBLogo,
    code: DatabaseTypeCode.POSTGRESQL,
    port: 5432,
    icon: '\uec5d',
  },
  [DatabaseTypeCode.SQLSERVER]: {
    name: 'SQLServer',
    img: moreDBLogo,
    code: DatabaseTypeCode.SQLSERVER,
    port: 1521,
    icon: '\ue664',
  },
  [DatabaseTypeCode.SQLITE]: {
    name: 'SQLite',
    img: moreDBLogo,
    code: DatabaseTypeCode.SQLITE,
    port: 5432,
    icon: '\ue65a',
  },
  [DatabaseTypeCode.MARIADB]: {
    name: 'mariadb',
    img: moreDBLogo,
    code: DatabaseTypeCode.MARIADB,
    port: 3306,
    icon: '\ue6f5',
  },
  [DatabaseTypeCode.CLICKHOUSE]: {
    name: 'clickHouse',
    img: moreDBLogo,
    code: DatabaseTypeCode.CLICKHOUSE,
    port: 8123,
    icon: '\ue8f4',
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
  INDEX = 'index',
  // SEARCH = 'search',
  // LINE = 'line',
  // LINETOTAL = 'lineTotal',
  // SAVE = 'save',
  // INDEXESTOTAL = 'indexesTotal',
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

export enum TabOpened {
  IS_OPEN = 'y',
  NOT_OPEN = 'n',
}

/** console顶部注释 */
export const consoleTopComment = `--Chat2DB进阶功能使用说明
--********************************************************************--
--自然语言转SQL
##例如: 查询列出在过去三个月内雇用了超过10名员工的部门名称，选中执行则会返回相应的SQL
--带参数自然语言转SQL
##例如: 查询列出在过去三个月内雇用了超过10名员工的部门名称，选中执行并传入查询相关的表结构信息，则会返回相应的SQL
--SQL解释
##例如: SELECT department_name FROM departments WHERE department_id IN (SELECT department_id FROM employees GROUP BY department_id HAVING COUNT(*) > 10)，选中执行则会返回此条SQL执行的内容和目标等
--带参数SQL解释
##例如: SELECT department_name FROM departments WHERE department_id IN (SELECT department_id FROM employees GROUP BY department_id HAVING COUNT(*) > 10)，选中执行并传入附加信息，则会返回此条SQL附加信息相关的解释
--SQL优化
##例如: SELECT * FROM department ORDER BY gmt_create DESC，选中执行则会返回此条SQL相关的多种类型优化建议
--带参数SQL优化
##例如: SELECT * FROM department ORDER BY gmt_create DESC，选中执行并传入优化附加信息，则会返回此条SQL附加信息相关的优化建议
--SQL转换
##例如: SELECT IFNULL(NULL, "W3Schools.com")，选中执行则会转换为当前数据库的查询语言
--带参数SQL转换
##例如: SELECT IFNULL(NULL, "W3Schools.com")，选中执行并传入目标SQL类型，则会目标SQL类型相应的SQL语言
--********************************************************************--\n\n\n`;
