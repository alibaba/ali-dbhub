import { DatabaseTypeCode } from '@/utils/constants'

export interface IDatabase {
  name: string;
  code:DatabaseTypeCode;
  img: string;
}
export interface IPageResponse<T> {
  data: T[];
  pageNo: number;
  pageSize: number;
  total: number;
}
export interface IPageParams {
  searchKey?:string;
  pageNo: number;
  pageSize: number;
}
export interface IConnectionBase{
  id?:number;
  alias: string;
  url: string;
  user: string;
  password: string;
  type: DatabaseTypeCode;
  envType: string;
}
export interface IHistoryRecord{
  id?:number;
  name: string;
  dataSourceId: string;
  databaseName: string;
  type: DatabaseTypeCode;
}



