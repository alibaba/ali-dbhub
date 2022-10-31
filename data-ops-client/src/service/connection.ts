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


// TODO: 没有找到测试连接接口
const test = createRequest<IConnectionBase, void>('/api/connection/manage/create',{method:'post'});

const update = createRequest<IConnectionBase, void>('/api/connection/manage/update',{method:'put'});

const remove = createRequest<{id:number},void>('/api/connection/manage/:id',{method:'delete'});

const clone = createRequest<{id:number},void>('/api/connection/manage/clone',{method:'post'});


export interface IDBItem{
  name: string;
  description: string;
  count: string;
}

const getDBList = createRequest<{id:number}, IDBItem[]>('/api/connection/attach',{method:'get'});

export default {
  getList,
  getDetaile,
  save,
  test,
  update,
  remove,
  clone,
  getDBList
}