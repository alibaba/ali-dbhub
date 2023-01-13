import createRequest from "./base";
import { IPageResponse, ITable,IPageParams } from '@/types';
import { DatabaseTypeCode } from '@/utils/constants';

export interface IGetListParams extends IPageParams  {
  dataSourceId: string;
  databaseName: string;
}

export interface IExecuteSqlParams {
  sql: string,
  dataSourceId: number,
  databaseName: string,
  consoleId: number,
}

export interface IExecuteSqlResponse {
  sql: string;
  description: string;
  message: string;
  success: boolean;
  headerList:any[];
  dataList: any[];
}
export interface IConnectConsoleParams {
  consoleId: number,	
  dataSourceId: number,
  databaseName: string,
}

const getList = createRequest<IGetListParams, IPageResponse<ITable>>('/api/rdb/ddl/list',{});

const executeSql = createRequest<IExecuteSqlParams, IExecuteSqlResponse>('/api/rdb/dml/execute',{method: 'put'});

const connectConsole = createRequest<IConnectConsoleParams, void>('/api/connection/console/connect',{method: 'get'});

//表操作
export interface IDeleteTableParams {
  tableName:string;
  dataSourceId:number;	
  databaseName:string;
}

export interface IExecuteTableParams {
  sql: string;
  consoleId: number;	
  dataSourceId: number;
  databaseName: string;
}

const deleteTable = createRequest<IDeleteTableParams, void>('/api/rdb/ddl/delete',{method: 'post'});
const createTableExample = createRequest<{dbType:DatabaseTypeCode}, string>('/api/rdb/ddl/create/example',{method: 'get'});
const updateTableExample = createRequest<{dbType:DatabaseTypeCode}, string>('/api/rdb/ddl/update/example',{method: 'get'});
const exportCreateTableSql = createRequest<IDeleteTableParams, string>('/api/rdb/ddl/export',{method: 'get'});
const executeTable = createRequest<IExecuteTableParams, string>('/api/rdb/ddl/execute',{method: 'put'});


export default {
  getList,
  executeSql,
  connectConsole,
  deleteTable,
  createTableExample,
  updateTableExample,
  exportCreateTableSql,
  executeTable
}