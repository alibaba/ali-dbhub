import createRequest from "./base";
import { IPageResponse, IConnectionBase } from '@/types'

export interface IGetConnectionParams {
  searchKey?: string;
  pageNo: number;
  pageSize: number;
}

const getList = createRequest<IGetConnectionParams, IPageResponse<IConnectionBase>>('/api/connection/manage/list',{});

const getDetaile = createRequest<{id:string}, IConnectionBase>('/api/connection/manage/:id',{});

const save = createRequest<IConnectionBase, void>('/api/connection/manage/create',{method:'post'});


export default {
  getList,
  getDetaile,
  save
}