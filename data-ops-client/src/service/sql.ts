import createRequest from "./base";
import { IPageResponse, IConnectionBase,IPageParams,IHistoryRecord } from '@/types';

export interface IGetSaveListParams extends IPageParams  {
  dataSourceId?: number;
  databaseName?: string;
} 

const getSaveList = createRequest<IGetSaveListParams, IPageResponse<IHistoryRecord>>('/api/ddl/list',{});

const getHistoryList = createRequest<IPageParams, IPageResponse<IHistoryRecord>>('/api/history/list',{});

export default {
  getSaveList,
  getHistoryList
}