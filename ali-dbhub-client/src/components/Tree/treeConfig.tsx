import { ITreeNode } from '@/types';
import { TreeNodeType } from '@/utils/constants';
import connectionService from '@/service/connection';
import mysqlServer from '@/service/mysql';

export type ITreeConfigItem = {
  getNodeData: (data: ITreeNode) => Promise<ITreeNode[]>;
}

export type ITreeConfig = Partial<{ [key in TreeNodeType]: ITreeConfigItem }>;

export const treeConfig: ITreeConfig = {

  [TreeNodeType.DATASOURCE]: {
    getNodeData: (parentData: ITreeNode) => {
      return new Promise((r: (value: ITreeNode[]) => void, j) => {
        let p = {
          id: parentData.key
        }
        connectionService.getDBList(p).then(res => {
          const data: ITreeNode[] = res.map(t => {
            return {
              key: t.name,
              name: t.name,
              nodeType: TreeNodeType.DATABASE,
              children: [
                {
                  key: t.name + 'tables',
                  name: 'tables',
                  nodeType: TreeNodeType.TABLES,
                  dataSourceId: parentData.dataSourceId,
                  dataBaseName: t.name,
                }
              ]
            }
          })
          r(data);
        })
      })
    },
  },

  [TreeNodeType.TABLES]: {
    getNodeData: (parentData: ITreeNode) => {
      return new Promise((r: (value: ITreeNode[]) => void, j) => {
        let p = {
          dataSourceId: parentData.dataSourceId!,
          databaseName: parentData.dataBaseName!,
          pageNo: 1,
          pageSize: 100,
        }

        mysqlServer.getList(p).then(res => {
          const tableList: ITreeNode[] = res.data?.map(item => {
            return {
              name: item.name,
              nodeType: TreeNodeType.TABLE,
              key: item.name,
              dataSourceId: parentData.dataSourceId!,
              databaseName: parentData.dataBaseName!,
              children: [
                {
                  name: 'columns',
                  nodeType: TreeNodeType.COLUMNS,
                  key: 'columns',
                  dataSourceId: parentData.dataSourceId!,
                  databaseName: parentData.dataBaseName!,
                },
                {
                  name: 'keys',
                  nodeType: TreeNodeType.KEYS,
                  key: 'keys',
                  dataSourceId: parentData.dataSourceId!,
                  databaseName: parentData.dataBaseName!,
                },
                {
                  name: 'indexs',
                  nodeType: TreeNodeType.INDEXES,
                  key: 'indexs',
                  dataSourceId: parentData.dataSourceId!,
                  databaseName: parentData.dataBaseName!,
                },
              ]
            }
          })
          r(tableList);
        })
      })
    }
  },

  [TreeNodeType.COLUMNS]: {
    getNodeData: (parentData: ITreeNode) => {
      return new Promise((r: (value: ITreeNode[]) => void, j) => {
        let p = {
          dataSourceId: parentData.dataSourceId!,
          databaseName: parentData.dataBaseName!,
          tableName: parentData.name,
        }

        mysqlServer.getColumnList(p).then(res => {
          const tableList: ITreeNode[] = res?.map(item => {
            return {
              name: item.name,
              nodeType: TreeNodeType.COLUMN,
              key: item.name,
              isLeaf: true,
            }
          })
          r(tableList);
        })
      })
    }
  },

  [TreeNodeType.INDEXES]: {
    getNodeData: (parentData: ITreeNode) => {
      return new Promise((r: (value: ITreeNode[]) => void, j) => {
        let p = {
          dataSourceId: parentData.dataSourceId!,
          databaseName: parentData.dataBaseName!,
          tableName: parentData.name,
        }

        mysqlServer.getIndexList(p).then(res => {
          const tableList: ITreeNode[] = res?.map(item => {
            return {
              name: item.name,
              nodeType: TreeNodeType.INDEXE,
              key: item.name,
              isLeaf: true,
            }
          })
          r(tableList);
        })
      })
    }
  },

  [TreeNodeType.KEYS]: {
    getNodeData: (parentData: ITreeNode) => {
      return new Promise((r: (value: ITreeNode[]) => void, j) => {
        let p = {
          dataSourceId: parentData.dataSourceId!,
          databaseName: parentData.dataBaseName!,
          tableName: parentData.name,
        }

        mysqlServer.getKeyList(p).then(res => {
          const tableList: ITreeNode[] = res?.map(item => {
            return {
              name: item.name,
              nodeType: TreeNodeType.KEY,
              key: item.name,
              isLeaf: true,
            }
          })
          r(tableList);
        })
      })
    }
  }
}