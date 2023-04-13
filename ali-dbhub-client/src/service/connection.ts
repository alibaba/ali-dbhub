import createRequest from './base';
import { IPageResponse, IConnectionBase, IDB } from '@/types';

export interface IGetConnectionParams {
  searchKey?: string;
  pageNo: number;
  pageSize: number;
}

const getList = createRequest<
  IGetConnectionParams,
  IPageResponse<IConnectionBase>
>('/api/connection/datasource/list', {});

const getDetails = createRequest<{ id: string }, IConnectionBase>(
  '/api/connection/datasource/:id',
  {},
);

const save = createRequest<IConnectionBase, string>(
  '/api/connection/datasource/create',
  { method: 'post' },
);

const close = createRequest<IConnectionBase, void>(
  '/api/connection/datasource/close',
  { method: 'post' },
);

const test = createRequest<IConnectionBase, boolean>(
  '/api/connection/datasource/pre_connect',
  {},
);

const update = createRequest<IConnectionBase, void>(
  '/api/connection/datasource/update',
  { method: 'put' },
);

const remove = createRequest<{ id: number }, void>(
  '/api/connection/datasource/:id',
  { method: 'delete' },
);

const clone = createRequest<{ id: number }, void>(
  '/api/connection/datasource/clone',
  { method: 'post' },
);

const getDBList = createRequest<{ id: number }, IDB[]>(
  '/api/connection/datasource/connect',
  { method: 'get' },
);

export default {
  getList,
  getDetails,
  save,
  test,
  update,
  remove,
  clone,
  getDBList,
  close,
};
