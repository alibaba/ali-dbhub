import React, { memo, useEffect, useState } from 'react';
import globalStyle from '@/global.less';
import styles from './index.less';
import classnames from 'classnames';
import Iconfont from '../Iconfont';
import { Dropdown } from 'antd';
import { ITreeNode } from '@/types';
import { TreeNodeType } from '@/utils/constants'
import Menu, { IMenu } from '@/components/Menu'
import StateIndicator from '@/components/StateIndicator'
import LoadingContent from '../Loading/LoadingContent';
import request from 'umi-request';

interface IProps {
  className?: any;
  treeData: ITreeNode[] | undefined;
  loadData?: Function;
  nodeClick?: Function;
}
interface TreeNodeIProps {
  data: ITreeNode;
  level: number;
  show: boolean;
  loadData?: Function;
  nodeClick?: Function;
}



export function TreeNode(props: TreeNodeIProps) {
  const { data, level, show = false, loadData, nodeClick } = props
  const [showChildren, setShowChildren] = useState(false);
  const [isLoading, setIsLoading] = useState(false);

  const indentArr = new Array(level);
  for (let i = 0; i < level; i++) {
    indentArr[i] = 'indent'
  }

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
      setShowChildren(!showChildren);
    }
  };

  // const renderMenu = () => {
  //   const tableMenu: IMenu[] = [
  //     {
  //       title: '打开SQL窗口',
  //       type: 'new-tab',
  //       icon: '\ue603'
  //     },
  //     {
  //       title: '删除表',
  //       type: 'delete',
  //       icon: '\ue604'
  //     }
  //   ]
  //   return <div className={styles.menuBox}>
  //     <Menu data={tableMenu}></Menu>
  //   </div>
  // }

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
      case TreeNodeType.SAVE:
        return '\ue936';
      case TreeNodeType.INDEXES:
        return '\ue648';
      default:
        return '\ue936';
    }
  }

  return <>
    {/* <Dropdown overlay={renderMenu()} trigger={['contextMenu']}>
    </Dropdown> */}
    <div className={classnames(styles.treeNode, { [styles.hiddenTreeNode]: !show })} >
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
          <div className={styles.arrows} onClick={handleClick.bind(null, data)}>
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
        <div className={styles.contentText} onClick={() => { nodeClick && nodeClick(data); console.log(data) }}>
          <div className={styles.name} dangerouslySetInnerHTML={{ __html: data.name }}></div>
          <div className={styles.type}>{data.dataType}</div>
        </div>
      </div>
    </div>
    {
      !!data.children?.length &&
      data.children.map((item: any, i: number) => {
        return (
          <TreeNode loadData={loadData} key={i} show={(showChildren && show)} level={level + 1} data={item}></TreeNode>
        );
      })
    }
  </>
}

export default function Tree(props: IProps) {
  const { className, treeData, loadData, nodeClick } = props;
  const treeDataEmpty = () => {
    return ''
  }
  return (
    <div className={classnames(className, styles.box)}>
      <LoadingContent data={treeData} handleEmpty empty={treeDataEmpty()}>
        {
          treeData?.map((item) => {
            return <TreeNode nodeClick={nodeClick} loadData={loadData} key={item.name} show={true} level={0} data={item}></TreeNode>
          })
        }
      </LoadingContent>
    </div>
  );
};
