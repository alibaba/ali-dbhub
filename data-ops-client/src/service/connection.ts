import createRequest from "./base";
import { IPageResponse, IConnectionBase,IDB } from '@/types'

export interface IGetConnectionParams {
  searchKey?: string;
  pageNo: number;
  pageSize: number;
}

const getList = createRequest<IGetConnectionParams, IPageResponse<IConnectionBase>>('/api/connection/manage/list',{});

const getDetaile = createRequest<{id:string}, IConnectionBase>('/api/connection/manage/:id',{});

const save = createRequest<IConnectionBase, void>('/api/connection/manage/create',{method:'post'});


// TODO: 没有找到测试连接接口
const test = createRequest<IConnectionBase, void>('/api/connection/test',{});

const update = createRequest<IConnectionBase, void>('/api/connection/manage/update',{method:'put'});

const remove = createRequest<{id:number},void>('/api/connection/manage/:id',{method:'delete'});

const clone = createRequest<{id:number},void>('/api/connection/manage/clone',{method:'post'});


const getDBList = createRequest<{id:string}, IDB[]>('/api/connection/attach',{method:'get'});

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