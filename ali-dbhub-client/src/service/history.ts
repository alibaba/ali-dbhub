import createRequest from "./base";
import { IPageResponse, IConnectionBase,IPageParams,IHistoryRecord, IWindowTab } from '@/types';
import { DatabaseTypeCode,ConsoleStatus } from '@/utils/constants'

export interface IGetHistoryListParams extends IPageParams  {
  dataSourceId?: string;
  databaseName?: string;
}

export interface ISavedConsole {
  dataSourceId: number;
  databaseName: string;
  ddl: string;
  id: number;
  name: string;
  type: DatabaseTypeCode;
}

export interface ISaveBasicInfo {
  name: string;
  type: DatabaseTypeCode;
  ddl: string;
  dataSourceId: number;
  databaseName: string;
}
export interface ISaveConsole extends ISaveBasicInfo {
  status: ConsoleStatus;
  tabOpened: 'y' | 'n';
}

export interface IUpdateWindowParams {
  id: number;
  name: string;
  ddl: string;
  dataSourceId: number;
  databaseName: string;
}

const saveWindowTab = createRequest<ISaveConsole, number>('/api/operation/saved/create',{method: 'post'});

const updateWindowTab = createRequest<IUpdateWindowParams, number>('/api/operation/saved/update',{method: 'put'});

const getSaveList = createRequest<IGetHistoryListParams, IPageResponse<ISavedConsole>>('/api/operation/saved/list',{});

const deleteWindowTab = createRequest<{id:string}, string>('/api/operation/saved/:id',{method: 'delete'});

const createHistory = createRequest<ISaveBasicInfo, void>('/api/operation/log/create',{method: 'post'});

const getHistoryList = createRequest<IGetHistoryListParams, IPageResponse<IHistoryRecord>>('/api/operation/log/list',{});

export default {
  getSaveList,
  updateWindowTab,
  getHistoryList,
  saveWindowTab,
  deleteWindowTab,
  createHistory
}