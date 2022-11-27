import createRequest from "./base";
import { IPageResponse, ITable,IPageParams } from '@/types';

export interface IGetListParams extends IPageParams  {
  dataSourceId: string;
  databaseName: string;
}

export interface IExecuteSqlParams {
  sql:string,
  dataSourceId:string,
  databaseName:string,
  consoleId: string,
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
  consoleId: string,	
  dataSourceId: string,
  databaseName: string,
}

const getList = createRequest<IGetListParams, IPageResponse<ITable>>('/api/rdb/table/list',{});

const executeSql = createRequest<IExecuteSqlParams, IExecuteSqlResponse>('/api/rdb/data/manage',{method: 'put'});

const connectConsole = createRequest<IConnectConsoleParams, void>('/api/connection/console/connect',{method: 'get'});

export default {
  getList,
  executeSql,
  connectConsole
}