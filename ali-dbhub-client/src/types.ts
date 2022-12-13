import { DatabaseTypeCode, TreeNodeType, WindowTabStatus,TableDataType} from '@/utils/constants'

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
  tabOpened: 'y' | 'n';
  envType: string;
}
export interface IHistoryRecord{
  id?: string | number;
  name: string;
  dataSourceId: string | number;
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
  nodeType: TreeNodeType;
  dataType?: string;
  isLeaf?: boolean;
  children?: ITreeNode[];
  parent?: ITreeNode;
}
export interface IDB {
  name: string;
  description: string;
  count: number;
  databaseType?: DatabaseTypeCode;
}
export interface IWindowTab {
  id?:string;
  name: string;
  type: DatabaseTypeCode;
  dataSourceId: string|number;
  databaseName: string;
  consoleId?: string;
  status?: WindowTabStatus;
  ddl?: string;
  sql?:string;
}

export interface ITableHeaderItem {
  type: TableDataType;
  stringValue: string;
}

export interface ITableCellItem {
  type: TableDataType;
  stringValue: string;
  dateValue: number;
  byteValue: number[];
  emptyValue: any;
  bigDecimalValue: string;
}

export interface IManageResultData {
  dataList: ITableCellItem[][];
  headerList: ITableHeaderItem[];
  description: string;
  message: string;
  sql: string;
  success: boolean;

}
