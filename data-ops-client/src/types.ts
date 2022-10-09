import { DatabaseTypeCode, TreeNodeType} from '@/utils/constants'

export interface IDatabase {
  name: string;
  code:DatabaseTypeCode;
  img: string;
}
export interface IPageResponse<T> {
  data: T[];
  pageNo: number;
  pageSize: number;
  total: number;
  hasNextPage?: boolean;

}
export interface IPageParams {
  searchKey?:string;
  pageNo: number;
  pageSize: number;
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
export interface IHistoryRecord{
  id?: number;
  name: string;
  dataSourceId: string;
  databaseName: string;
  type: DatabaseTypeCode;
}
export interface ITableColumn{
  name: string;
  type: string;
  description: string;
}
export interface ITableIndex{
  name: string;
  type: string;
  columns: string;
}
export interface ITable{
  name: string;
  description: string;
  columnList: ITableColumn[];
  indexList: ITableIndex[];
}
export interface ITreeNode{
  key: string;
  name: string;
  type: TreeNodeType;
  isLeaf?: boolean;
  children?: ITreeNode[];
}



