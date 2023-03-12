import React, { memo, useEffect, useRef, useState } from 'react';
import globalStyle from '@/global.less';
import styles from './index.less';
import classnames from 'classnames';
import Iconfont from '../Iconfont';
import { Dropdown, Modal, Tooltip } from 'antd';
import { ITreeNode } from '@/types';
import { callVar } from '@/utils';
import { TreeNodeType } from '@/utils/constants'
import Menu, { IMenu, MenuItem } from '@/components/Menu'
import StateIndicator from '@/components/StateIndicator'
import LoadingContent from '../Loading/LoadingContent';
import { useCanDoubleClick } from '@/utils/hooks';
import { IOperationData } from '@/components/OperationTableModal';
import connectionService from '@/service/connection';
import mysqlServer from '@/service/mysql';

interface IProps {
  className?: any;
  // treeData: ITreeNode[] | undefined;
  nodeDoubleClick?: Function;
  openOperationTableModal?: Function;
}

interface TreeNodeIProps {
  data: ITreeNode;
  level: number;
  show: boolean;
  showAllChildrenPenetrate?: boolean;
  nodeDoubleClick?: Function;
  openOperationTableModal?: Function;
}

export function TreeNode(props: TreeNodeIProps) {
  const { data, level, show = false, nodeDoubleClick, openOperationTableModal, showAllChildrenPenetrate = false } = props;
  const [showChildren, setShowChildren] = useState(false);
  const [showAllChildren, setShowAllChildren] = useState(false);
  const [isLoading, setIsLoading] = useState(false);
  const indentArr = new Array(level);
  for (let i = 0; i < level; i++) {
    indentArr[i] = 'indent';
  }

  useEffect(() => {
    setShowChildren(showAllChildrenPenetrate);
  }, [showAllChildrenPenetrate])

  //展开-收起
  const handleClick = (data: ITreeNode) => {
    if (!showChildren && !data.children) {
      setIsLoading(true);
    }

    if (loadDataObj[data.nodeType] && !data.children) {
      loadData(data)?.then((res: ITreeNode[]) => {
        if (res?.length) {
          data.children = res;
        }
        setIsLoading(false);
        setTimeout(() => {
          setShowChildren(!showChildren);
        }, 0);
      })
    } else {
      if (level === 0) {
        setShowAllChildren(!showAllChildren);
      }
      setShowChildren(!showChildren);
    }
  };

  const renderMenu = () => {
    const tableMenu: IMenu<string>[] = [
      {
        title: '设计表结构',
        key: 'edit',
      },
      {
        title: '导出建表语句',
        key: 'export',
      },
      {
        title: '删除表',
        key: 'delete',
      }
    ]

    function tableClick(item: IMenu<string>) {
      const operationData: IOperationData = {
        type: item.key,
        nodeData: data
      }
      openOperationTableModal?.(operationData)
      // TODO: 关闭下拉弹窗 有木有更好的方法
      const customDropdown: any = document.getElementsByClassName('custom-dropdown');
      for (let i = 0; i < customDropdown.length; i++) {
        customDropdown[i].classList.add('custom-dropdown-hidden')
      }
    }

    if (data.nodeType == TreeNodeType.TABLE) {
      return <div className={styles.menuBox}>
        <Menu>
          {
            tableMenu.map(item => {
              return <MenuItem key={item.key} onClick={tableClick.bind(null, item)}>{item.title}</MenuItem>
            })
          }
        </Menu>
      </div>
    } else {
      return <span></span>
    }
  }

  const switchIcon: { [key in TreeNodeType]: { icon: string } } = {
    [TreeNodeType.DATASOURCE]: {
      icon: '\ue62c'
    },
    [TreeNodeType.DATABASE]: {
      icon: '\ue62c'
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
      icon: '\ue611'
    },
    [TreeNodeType.INDEXES]: {
      icon: '\ueac5'
    },
    [TreeNodeType.INDEXE]: {
      icon: '\ue611'
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

  const recognizeIcon = (nodeType: TreeNodeType) => {
    return switchIcon[nodeType].icon
  }

  const treeNodeClick = useCanDoubleClick();

  function renderTitle(data: ITreeNode) {
    return <>
      <span>{data.name}</span>
      {
        data.dataType &&
        <span style={{ color: callVar('--custom-primary-color') }}>（{data.dataType}）</span>
      }
    </>
  }

  return <>
    <Dropdown overlay={renderMenu()} trigger={['contextMenu']}>
      <Tooltip placement="right" title={renderTitle(data)}>
        <div
          onClick={
            (e) => {
              treeNodeClick({
                onClick: handleClick.bind(null, data),
                onDoubleClick: () => { nodeDoubleClick && nodeDoubleClick(data) }
              })
            }
          }
          className={classnames(styles.treeNode, { [styles.hiddenTreeNode]: !show })} >
          <div className={styles.left}>
            {
              indentArr.map((item, i) => {
                return <div key={i} className={styles.indent}></div>
              })
            }
          </div>
          <div className={styles.right}>
            {
              !data.isLeaf &&
              <div className={styles.arrows}>
                {
                  isLoading
                    ?
                    <div className={styles.loadingIcon}>
                      <Iconfont code='&#xe6cd;' />
                    </div>
                    :
                    <Iconfont className={classnames(styles.arrowsIcon, { [styles.rotateArrowsIcon]: showChildren })} code='&#xe608;' />
                }
              </div>
            }
            <div className={styles.typeIcon}>
              <Iconfont code={recognizeIcon(data.nodeType)}></Iconfont>
            </div>
            <div className={styles.contentText} >
              <div className={styles.name} dangerouslySetInnerHTML={{ __html: data.name }}></div>
              <div className={styles.type}>{data.dataType}</div>
            </div>
          </div>
        </div>
      </Tooltip>
    </Dropdown>
    {
      !!data.children?.length &&
      data.children.map((item: any, i: number) => {
        return (
          <TreeNode openOperationTableModal={openOperationTableModal} nodeDoubleClick={nodeDoubleClick} key={i} showAllChildrenPenetrate={showAllChildrenPenetrate || showAllChildren} show={(showChildren && show)} level={level + 1} data={item}></TreeNode>
        );
      })
    }
  </>
}

export default function Tree(props: IProps) {
  const { className, nodeDoubleClick, openOperationTableModal } = props;
  const [treeData, setTreeData] = useState<ITreeNode[] | undefined>();

  useEffect(() => {
    let p = {
      pageNo: 1,
      pageSize: 100
    }

    connectionService.getList(p).then(res => {
      const treeData = res.data.map(t => {
        return {
          name: t.alias,
          key: t.id!.toString(),
          nodeType: TreeNodeType.DATASOURCE,
          dataSourceId: t.id,
        }
      })
      setTreeData(treeData);
    })
  }, [])

  const treeDataEmpty = () => {
    return ''
  }

  return (
    <>
      <div className={classnames(className, styles.box)}>
        <LoadingContent data={treeData} handleEmpty empty={treeDataEmpty()}>
          {
            treeData?.map((item) => {
              return <TreeNode
                // openOperationTableModal={openOperationTableModal}
                nodeDoubleClick={nodeDoubleClick}
                key={item.name}
                show={true}
                level={0}
                data={item}
              ></TreeNode>
            })
          }
        </LoadingContent>
      </div>
    </>
  );
};

interface ILoadDataObjItem {
  getNodeData: (data: ITreeNode) => Promise<ITreeNode[]>;
}

const loadDataObj: Partial<{ [key in TreeNodeType]: ILoadDataObjItem }> = {
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
    }
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
  },
}

function loadData(data: ITreeNode) {
  return loadDataObj[data.nodeType]?.getNodeData(data);
}
