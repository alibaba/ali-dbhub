import React, { memo, useEffect, useRef, useState, forwardRef, useImperativeHandle } from 'react';
import globalStyle from '@/global.less';
import styles from './index.less';
import classnames from 'classnames';
import Iconfont from '../Iconfont';
import { Dropdown, Modal, Tooltip } from 'antd';
import { ITreeNode } from '@/types';
import { callVar } from '@/utils';
import { TreeNodeType } from '@/utils/constants';
import Menu, { IMenu, MenuItem } from '@/components/Menu';
import StateIndicator from '@/components/StateIndicator';
import LoadingContent from '../Loading/LoadingContent';
import { useCanDoubleClick } from '@/utils/hooks';
import { IOperationData } from '@/components/OperationTableModal';
import connectionService from '@/service/connection';
import mysqlServer from '@/service/mysql';
import TreeNodeRightClick from './TreeNodeRightClick';
import { treeConfig, switchIcon } from './treeConfig'
import { databaseType } from '@/utils/constants'

interface IProps {
  className?: string;
  nodeDoubleClick?: Function;
  openOperationTableModal?: Function;
  cRef: any;
  addTreeData?: ITreeNode[];
}

interface TreeNodeIProps {
  data: ITreeNode;
  level: number;
  show: boolean;
  setTreeData: Function;
  showAllChildrenPenetrate?: boolean;
  nodeDoubleClick?: Function;
  openOperationTableModal?: Function;
}

function TreeNode(props: TreeNodeIProps) {
  const { setTreeData, data, level, show = false, nodeDoubleClick, openOperationTableModal, showAllChildrenPenetrate = false } = props;
  const [showChildren, setShowChildren] = useState(false);
  const [showAllChildren, setShowAllChildren] = useState(false);
  const [isLoading, setIsLoading] = useState(false);
  const indentArr = new Array(level);

  const treeNodeClick = useCanDoubleClick();

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

    if (treeConfig[data.nodeType] && !data.children) {
      loadData(data)?.then((res: ITreeNode[]) => {
        setTimeout(() => {
          data.children = res;
          setIsLoading(false);
          setTimeout(() => {
            setShowChildren(!showChildren);
          }, 0);
        }, 300);
      })
    } else {
      if (level === 0) {
        setShowAllChildren(!showAllChildren);
      }
      setShowChildren(!showChildren);
    }
  };

  const renderMenu = () => {
    return <TreeNodeRightClick
      data={data}
      setTreeData={setTreeData}
      setIsLoading={setIsLoading}
      openOperationTableModal={openOperationTableModal}
      nodeConfig={treeConfig[data.nodeType]}
    />
  }

  const recognizeIcon = (nodeType: TreeNodeType) => {
    return switchIcon[nodeType].icon
  }

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
              {
                data.nodeType === TreeNodeType.DATASOURCE ?
                  <div style={{ backgroundImage: `url(${databaseType[data.dataType!]?.img})` }} className={styles.typeImg}></div>
                  :
                  <Iconfont code={recognizeIcon(data.nodeType)}></Iconfont>
              }
            </div>
            <div className={styles.contentText} >
              <div className={styles.name} dangerouslySetInnerHTML={{ __html: data.name }}></div>
              {/* <div className={styles.type}>{data.dataType}</div> */}
            </div>
          </div>
        </div>
      </Tooltip>
    </Dropdown>
    {
      !!data.children?.length &&
      data.children.map((item: any, i: number) => {
        return (
          <TreeNode setTreeData={setTreeData} openOperationTableModal={openOperationTableModal} nodeDoubleClick={nodeDoubleClick} key={i} showAllChildrenPenetrate={showAllChildrenPenetrate || showAllChildren} show={(showChildren && show)} level={level + 1} data={item}></TreeNode>
        );
      })
    }
  </>
}

function Tree(props: IProps) {
  const { className, nodeDoubleClick, cRef, addTreeData, openOperationTableModal } = props;
  const [treeData, setTreeData] = useState<ITreeNode[] | undefined>();

  useEffect(() => {
    setTreeData([...(treeData || []), ...(addTreeData || [])]);
  }, [addTreeData])



  useImperativeHandle(cRef, () => ({
    getDataSource
  }))

  useEffect(() => {
    getDataSource(setTreeData);
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
                openOperationTableModal={openOperationTableModal}
                nodeDoubleClick={nodeDoubleClick}
                setTreeData={setTreeData}
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

export function getDataSource(setTreeData: Function) {
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
        dataType: t.type
      }
    })
    setTreeData(treeData);
  })
}

function loadData(data: ITreeNode) {
  return treeConfig[data.nodeType]?.getNodeData(data);
}

export default forwardRef(Tree);
