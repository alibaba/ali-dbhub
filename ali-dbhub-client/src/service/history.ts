import createRequest from "./base";
import { IPageResponse, IConnectionBase,IPageParams,IHistoryRecord, IWindowTab } from '@/types';
import { DatabaseTypeCode } from '@/utils/constants'

export interface IGetHistoryListParams extends IPageParams  {
  dataSourceId?: string;
  databaseName?: string;
}



const saveWindowTab = createRequest<IWindowTab, string>('/api/ddl/create',{method: 'post'});

const updateWindowTab = createRequest<IWindowTab, string>('/api/ddl/update',{method: 'put'});

const getSaveList = createRequest<IGetHistoryListParams, IPageResponse<IWindowTab>>('/api/ddl/list',{});

const deleteWindowTab = createRequest<{id:string}, string>('/api/ddl/:id',{method: 'delete'});

const createHistory = createRequest<IWindowTab, void>('/api/history/create',{method: 'post'});

const getHistoryList = createRequest<IGetHistoryListParams, IPageResponse<IHistoryRecord>>('/api/history/list',{});

export default {
  getSaveList,
  updateWindowTab,
  getHistoryList,
  saveWindowTab,
  deleteWindowTab,
  createHistory
}