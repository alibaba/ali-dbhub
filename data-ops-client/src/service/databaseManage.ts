import createRequest from "./base";
import { IPageResponse, IConnectionItem } from '@/types'

export interface IGetConnectionManageParams {
  searchKey?: string;
  pageNo: number;
  pageSize: number;
}

const getConnectionDatabaseList = createRequest<IGetConnectionManageParams, IPageResponse<IConnectionItem>>('/api/connection/manage/list',{method:'get'});

const getConnectionDetaile = createRequest<{id:string}, IConnectionItem>('/api/connection/manage/:id',{method:'get'});

export default {
  getConnectionDatabaseList,
  getConnectionDetaile
}