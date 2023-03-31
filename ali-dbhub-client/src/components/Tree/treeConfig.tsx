import { ITreeNode } from '@/types';
import { TreeNodeType } from '@/utils/constants';
import connectionService from '@/service/connection';
import mysqlServer from '@/service/mysql';

export type ITreeConfigItem = {
  getNodeData: (data: ITreeNode) => Promise<ITreeNode[]>;
}

export type ITreeConfig = Partial<{ [key in TreeNodeType]: ITreeConfigItem }>;

export const treeConfig: ITreeConfig = {

  [TreeNodeType.DATASOURCES]: {
    getNodeData: () => {
      return new Promise((r: (value: ITreeNode[]) => void, j) => {
        let p = {
          pageNo: 1,
          pageSize: 999
        }

        connectionService.getList(p).then(res => {
          const data: ITreeNode[] = res.data.map(t => {
            return {
              name: t.alias,
              key: t.id!.toString(),
              nodeType: TreeNodeType.DATASOURCE,
              dataSourceId: t.id,
              dataType: t.type
            }
          })
          r(data);
        })
      })
    },
  },

  [TreeNodeType.DATASOURCE]: {
    getNodeData: (parentData: ITreeNode) => {
      return new Promise((r: (value: ITreeNode[]) => void, j) => {
        let p = {
          id: parentData.dataSourceId!
        }
        connectionService.getDBList(p).then(res => {
          const data: ITreeNode[] = res.map(t => {
            return {
              key: t.name,
              name: t.name,
              nodeType: TreeNodeType.DATABASE,
              dataType: parentData.dataType,
              dataSourceId: +parentData.key,
              databaseName: t.name
            }
          })
          r(data);
        })
      })
    },
  },

  [TreeNodeType.DATABASE]: {
    getNodeData: (parentData: ITreeNode) => {
      return new Promise((r: (value: ITreeNode[]) => void, j) => {
        let p = {
          dataSourceId: parentData.dataSourceId!,
          databaseName: parentData.databaseName!
        }
        mysqlServer.getSchemaList(p).then(res => {
          if (res.length) {
            const data: ITreeNode[] = res.map(t => {
              return {
                key: t.name,
                name: t.name,
                nodeType: TreeNodeType.SCHEMAS,
                dataType: parentData.dataType,
                schemaName: t.name,
                dataSourceId: parentData.dataSourceId!,
                databaseName: parentData.databaseName!
              }
            })
            r(data);
          } else {
            let data = [
              {
                key: parentData.name + 'tables',
                name: 'tables',
                nodeType: TreeNodeType.TABLES,
                dataSourceId: parentData.dataSourceId,
                databaseName: parentData.databaseName,
                dataType: parentData.dataType,
              }
            ]
            r(data);
          }
        })
      })
    },
  },

  [TreeNodeType.SCHEMAS]: {
    getNodeData: (parentData: ITreeNode) => {
      return new Promise((r: (value: ITreeNode[]) => void, j) => {
        let data = [
          {
            key: parentData.name + 'tables',
            name: 'tables',
            nodeType: TreeNodeType.TABLES,
            dataSourceId: parentData.dataSourceId,
            databaseName: parentData.databaseName,
            dataType: parentData.dataType,
            schemaName: parentData.schemaName,
          }
        ]
        r(data);
      })
    },
  },

  [TreeNodeType.TABLES]: {
    getNodeData: (parentData: ITreeNode) => {
      console.log(parentData)

      return new Promise((r: (value: ITreeNode[]) => void, j) => {
        let p = {
          dataSourceId: parentData.dataSourceId!,
          databaseName: parentData.databaseName!,
          schemaName: parentData.schemaName,
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
              databaseName: parentData.databaseName!,
              dataType: parentData.dataType,
              tableName: item.name,
              schemaName: parentData.schemaName,
            }
          })
          r(tableList);
        })
      })
    }
  },

  [TreeNodeType.TABLE]: {
    getNodeData: (parentData: ITreeNode) => {
      console.log(parentData)

      return new Promise((r: (value: ITreeNode[]) => void, j) => {
        const tableList = [
          {
            name: 'columns',
            nodeType: TreeNodeType.COLUMNS,
            key: 'columns',
            tableName: parentData.tableName,
            dataSourceId: parentData.dataSourceId!,
            databaseName: parentData.databaseName!,
            dataType: parentData.dataType,
            schemaName: parentData.schemaName,
          },
          {
            name: 'keys',
            nodeType: TreeNodeType.KEYS,
            key: 'keys',
            tableName: parentData.tableName,
            dataSourceId: parentData.dataSourceId!,
            databaseName: parentData.databaseName!,
            dataType: parentData.dataType,
            schemaName: parentData.schemaName,
          },
          {
            name: 'indexs',
            nodeType: TreeNodeType.INDEXES,
            key: 'indexs',
            tableName: parentData.tableName,
            dataSourceId: parentData.dataSourceId!,
            databaseName: parentData.databaseName!,
            dataType: parentData.dataType,
            schemaName: parentData.schemaName,
          },
        ]

        r(tableList);
      })
    }
  },

  [TreeNodeType.COLUMNS]: {
    getNodeData: (parentData: ITreeNode) => {
      console.log('COLUMNS', parentData)

      return new Promise((r: (value: ITreeNode[]) => void, j) => {
        let p = {
          dataSourceId: parentData.dataSourceId!,
          databaseName: parentData.databaseName!,
          tableName: parentData.tableName!,
          dataType: parentData.dataType,
          schemaName: parentData.schemaName,
        }
        console.log(p)

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
          databaseName: parentData.databaseName!,
          tableName: parentData.tableName!,
          schemaName: parentData.schemaName,
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
          databaseName: parentData.databaseName!,
          tableName: parentData.tableName!,
          schemaName: parentData.schemaName,
        }

        mysqlServer.getKeyList(p).then(res => {
          console.log(res)
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

export const switchIcon: Partial<{ [key in TreeNodeType]: { icon: string } }> = {
  [TreeNodeType.DATABASE]: {
    icon: '\ue62c'
  },
  [TreeNodeType.SCHEMAS]: {
    icon: '\ue696'
  },
  [TreeNodeType.TABLE]: {
    icon: '\ue63e'
  },
  [TreeNodeType.TABLES]: {
    icon: '\ueac5'
  },
  [TreeNodeType.COLUMNS]: {
    icon: '\ueac5'
  },
  [TreeNodeType.COLUMN]: {
    icon: '\ue611'
  },
  [TreeNodeType.KEYS]: {
    icon: '\ueac5'
  },
  [TreeNodeType.KEY]: {
    icon: '\ue775'
  },
  [TreeNodeType.INDEXES]: {
    icon: '\ueac5'
  },
  [TreeNodeType.INDEXE]: {
    icon: '\ue65b'
  },
  [TreeNodeType.SEARCH]: {
    icon: '\uec4c'
  },
  [TreeNodeType.LINE]: {
    icon: '\ue611'
  },
  [TreeNodeType.LINETOTAL]: {
    icon: '\ue611'
  },
  [TreeNodeType.SAVE]: {
    icon: '\ue936'
  },
  [TreeNodeType.INDEXESTOTAL]: {
    icon: '\ue648'
  }
}