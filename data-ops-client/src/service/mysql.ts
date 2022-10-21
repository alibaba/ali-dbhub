import createRequest from "./base";
import { IPageResponse, ITable,IPageParams } from '@/types';

export interface IGetListParams extends IPageParams  {
  dataSourceId: string;
  databaseName: string;
} 

const getList = createRequest<IGetListParams, IPageResponse<ITable>>('/api/mysql/table/list',{});

export default {
  getList,
}