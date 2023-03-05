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

interface IProps {
  className?: any;
  // treeData: ITreeNode[] | undefined;
  loadData?: Function;
  nodeDoubleClick?: Function;
  openOperationTableModal?: Function;
}
interface TreeNodeIProps {
  data: ITreeNode;
  level: number;
  show: boolean;
  showAllChildrenPenetrate?: boolean;
  loadData?: Function;
  nodeDoubleClick?: Function;
  openOperationTableModal?: Function;
}

export function TreeNode(props: TreeNodeIProps) {
  const { data, level, show = false, loadData, nodeDoubleClick, openOperationTableModal, showAllChildrenPenetrate = false } = props
  const [showChildren, setShowChildren] = useState(false);
  const [showAllChildren, setShowAllChildren] = useState(false);
  const [isLoading, setIsLoading] = useState(false);
  const indentArr = new Array(level);
  for (let i = 0; i < level; i++) {
    indentArr[i] = 'indent'
  }

  useEffect(() => {
    setShowChildren(showAllChildrenPenetrate)
  }, [showAllChildrenPenetrate])

  //展开-收起
  const handleClick = (data: ITreeNode) => {
    if (!showChildren && !data.children && loadData) {
      setIsLoading(true)
    }

    if (loadData) {
      loadData(data).then((res: ITreeNode[]) => {
        if (res?.length) {
          data.children = res
        }
        setIsLoading(false)
        setTimeout(() => {
          setShowChildren(!showChildren);
        }, 0);
      })
    } else {
      if (level === 0) {
        setShowAllChildren(!showAllChildren)
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

  const recognizeIcon = (nodeType: TreeNodeType) => {
    switch (nodeType) {
      case TreeNodeType.TABLE:
        return '\ue63e';
      case TreeNodeType.DATABASE:
        return '\ue62c';
      case TreeNodeType.SEARCH:
        return '\uec4c';
      case TreeNodeType.LINE:
        return '\ue611';
      case TreeNodeType.LINETOTAL:
        return '\ue611';
      case TreeNodeType.SAVE:
        return '\ue936';
      case TreeNodeType.INDEXES:
        return '\ue648';
      case TreeNodeType.INDEXESTOTAL:
        return '\ue648';
      default:
        return '\ue936';
    }
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
              console.log(e)
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
                    <Iconfont code={showChildren ? "\ue61e" : "\ue65f"} />
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
          <TreeNode openOperationTableModal={openOperationTableModal} nodeDoubleClick={nodeDoubleClick} loadData={loadData} key={i} showAllChildrenPenetrate={showAllChildrenPenetrate || showAllChildren} show={(showChildren && show)} level={level + 1} data={item}></TreeNode>
        );
      })
    }
  </>
}

export default function Tree(props: IProps) {
  const { className, loadData, nodeDoubleClick, openOperationTableModal } = props;
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
          key: t.alias,
          nodeType: TreeNodeType.DATABASE,
        }
      })
      setTreeData(treeData)
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
                loadData={loadData}
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
