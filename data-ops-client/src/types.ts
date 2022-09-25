import { DatabaseTypeCode } from '@/utils/constants'
export interface INavItem {
  code: string;
  title: string;
  icon: string;
  path: string;
};

export interface IPageResponse<T> {
  data: T[];
  pageNo: number;
  pageSize: number;
  total: number;
}
export interface IConnectionBase{
  id?:number;
  alias: string;
  url: string;
  user: string;
  password: string;
  type: DatabaseTypeCode;
  envType: string;
}



