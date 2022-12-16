import createRequest from "./base";
import { IPageResponse, IConnectionBase,IPageParams,IHistoryRecord, IWindowTab } from '@/types';
import { DatabaseTypeCode } from '@/utils/constants'

export interface IGetHistoryListParams extends IPageParams  {
  dataSourceId?: string;
  databaseName?: string;
}

const saveWindowTab = createRequest<IWindowTab, string>('/api/operation/saved/create',{method: 'post'});

const updateWindowTab = createRequest<IWindowTab, string>('/api/operation/saved/update',{method: 'put'});

const getSaveList = createRequest<IGetHistoryListParams, IPageResponse<IWindowTab>>('/api/operation/saved/list',{});

const deleteWindowTab = createRequest<{id:string}, string>('/api/operation/saved/:id',{method: 'delete'});

const createHistory = createRequest<IWindowTab, void>('/api/operation/log/create',{method: 'post'});

const getHistoryList = createRequest<IGetHistoryListParams, IPageResponse<IHistoryRecord>>('/api/operation/log/list',{});

export default {
  getSaveList,
  updateWindowTab,
  getHistoryList,
  saveWindowTab,
  deleteWindowTab,
  createHistory
}