import createRequest from "./base";
import { IPageResponse, IConnectionBase,IPageParams,IHistoryRecord, IWindowTab } from '@/types';
import { DatabaseTypeCode } from '@/utils/constants'

export interface IGetSaveListParams extends IPageParams  {
  dataSourceId?: string;
  databaseName?: string;
} 

const getSaveList = createRequest<IGetSaveListParams, IPageResponse<IWindowTab>>('/api/ddl/list',{});

const saveWindowTab = createRequest<IWindowTab, string>('/api/ddl/create',{method: 'post'});

const deleteWindowTab = createRequest<{id:string}, string>('/api/ddl/:id',{method: 'delete'});

const getHistoryList = createRequest<IPageParams, IPageResponse<IHistoryRecord>>('/api/history/list',{});

export default {
  getSaveList,
  getHistoryList,
  saveWindowTab,
  deleteWindowTab
}