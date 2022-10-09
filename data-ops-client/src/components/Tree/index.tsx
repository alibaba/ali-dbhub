import React, { memo, useEffect, useState } from 'react';
import globalStyle from '@/global.less';
import styles from './index.less';
import classnames from 'classnames';
import Iconfont from '../Iconfont';
import { Dropdown } from 'antd';
import { ITreeNode } from '@/types';
import { TreeNodeType } from '@/utils/constants'

interface IProps {
  className?: any;
  treeData: ITreeNode[] | null;
  loadData: Function;
}

const treeList: ITreeNode[] = [
  {
    key: '0-1',
    name: 'database',
    type: TreeNodeType.DATABASE,
    children: [
      {
        key: '0-1-1',
        name: '表',
        type: TreeNodeType.TABLE,
        children: [
          {
            key: '1',
            name: 'activity_audio_emp_recode',
            type: TreeNodeType.TABLE,
            children: [
              {
                key: '1',
                name: '列(10)',
                type: TreeNodeType.LINE,
                children: [
                  {
                    key: '0-1',
                    name: 'role_id',
                    type: TreeNodeType.LINE,
                  },
                  {
                    key: '0-1',
                    name: 'remark',
                    type: TreeNodeType.LINE,
                  },
                  {
                    key: '0-1',
                    name: 'deleted',
                    type: TreeNodeType.LINE,
                  },
                  {
                    key: '0-1',
                    name: 'role_id',
                    type: TreeNodeType.LINE,
                  },
                  {
                    key: '0-1',
                    name: 'remark',
                    type: TreeNodeType.LINE,
                  },
                  {
                    key: '0-1',
                    name: 'deleted',
                    type: TreeNodeType.LINE,
                  },
                  {
                    key: '0-1',
                    name: 'role_id',
                    type: TreeNodeType.LINE,
                  },
                  {
                    key: '0-1',
                    name: 'remark',
                    type: TreeNodeType.LINE,
                  },
                  {
                    key: '0-1',
                    name: 'deleted',
                    type: TreeNodeType.LINE,
                  },
                ]
              },
              {
                key: '1',
                name: '索引(10)',
                type: TreeNodeType.INDEXES,
                children: [
                  {
                    key: '1',
                    name: 'idx_follower_id',
                    type: TreeNodeType.INDEXES,
                  },
                  {
                    key: '1',
                    name: 'idx_follower_id',
                    type: TreeNodeType.INDEXES,
                  },
                  {
                    key: '1',
                    name: 'idx_follower_id',
                    type: TreeNodeType.INDEXES,
                  },
                  {
                    key: '1',
                    name: 'idx_follower_id',
                    type: TreeNodeType.INDEXES,
                  },
                  {
                    key: '1',
                    name: 'idx_follower_id',
                    type: TreeNodeType.INDEXES,
                  },
                  {
                    key: '1',
                    name: 'idx_follower_id',
                    type: TreeNodeType.INDEXES,
                  },
                  {
                    key: '1',
                    name: 'idx_follower_id',
                    type: TreeNodeType.INDEXES,
                  },
                ]
              }
            ],
          },
          {
            key: '1',
            name: 'activity_audio_emp_recode',
            type: TreeNodeType.TABLE,
            children: [
              {
                key: '1',
                name: '列(10)',
                type: TreeNodeType.LINE,
                children: [
                  {
                    key: '0-1',
                    name: 'role_id',
                    type: TreeNodeType.LINE,
                  },
                  {
                    key: '0-1',
                    name: 'remark',
                    type: TreeNodeType.LINE,
                  },
                  {
                    key: '0-1',
                    name: 'deleted',
                    type: TreeNodeType.LINE,
                  },
                  {
                    key: '0-1',
                    name: 'role_id',
                    type: TreeNodeType.LINE,
                  },
                  {
                    key: '0-1',
                    name: 'remark',
                    type: TreeNodeType.LINE,
                  },
                  {
                    key: '0-1',
                    name: 'deleted',
                    type: TreeNodeType.LINE,
                  },
                  {
                    key: '0-1',
                    name: 'role_id',
                    type: TreeNodeType.LINE,
                  },
                  {
                    key: '0-1',
                    name: 'remark',
                    type: TreeNodeType.LINE,
                  },
                  {
                    key: '0-1',
                    name: 'deleted',
                    type: TreeNodeType.LINE,
                  },
                ]
              },
              {
                key: '1',
                name: '索引(10)',
                type: TreeNodeType.INDEXES,
                children: [
                  {
                    key: '1',
                    name: 'idx_follower_id',
                    type: TreeNodeType.INDEXES,
                  },
                  {
                    key: '1',
                    name: 'idx_follower_id',
                    type: TreeNodeType.INDEXES,
                  },
                  {
                    key: '1',
                    name: 'idx_follower_id',
                    type: TreeNodeType.INDEXES,
                  },
                  {
                    key: '1',
                    name: 'idx_follower_id',
                    type: TreeNodeType.INDEXES,
                  },
                  {
                    key: '1',
                    name: 'idx_follower_id',
                    type: TreeNodeType.INDEXES,
                  },
                  {
                    key: '1',
                    name: 'idx_follower_id',
                    type: TreeNodeType.INDEXES,
                  },
                  {
                    key: '1',
                    name: 'idx_follower_id',
                    type: TreeNodeType.INDEXES,
                  },
                ]
              }
            ],
          },
          {
            key: '1',
            name: 'activity_audio_emp_recode',
            type: TreeNodeType.TABLE,
            children: [
              {
                key: '1',
                name: '列(10)',
                type: TreeNodeType.LINE,
                children: [
                  {
                    key: '0-1',
                    name: 'role_id',
                    type: TreeNodeType.LINE,
                  },
                  {
                    key: '0-1',
                    name: 'remark',
                    type: TreeNodeType.LINE,
                  },
                  {
                    key: '0-1',
                    name: 'deleted',
                    type: TreeNodeType.LINE,
                  },
                  {
                    key: '0-1',
                    name: 'role_id',
                    type: TreeNodeType.LINE,
                  },
                  {
                    key: '0-1',
                    name: 'remark',
                    type: TreeNodeType.LINE,
                  },
                  {
                    key: '0-1',
                    name: 'deleted',
                    type: TreeNodeType.LINE,
                  },
                  {
                    key: '0-1',
                    name: 'role_id',
                    type: TreeNodeType.LINE,
                  },
                  {
                    key: '0-1',
                    name: 'remark',
                    type: TreeNodeType.LINE,
                  },
                  {
                    key: '0-1',
                    name: 'deleted',
                    type: TreeNodeType.LINE,
                  },
                ]
              },
              {
                key: '1',
                name: '索引(10)',
                type: TreeNodeType.INDEXES,
                children: [
                  {
                    key: '1',
                    name: 'idx_follower_id',
                    type: TreeNodeType.INDEXES,
                  },
                  {
                    key: '1',
                    name: 'idx_follower_id',
                    type: TreeNodeType.INDEXES,
                  },
                  {
                    key: '1',
                    name: 'idx_follower_id',
                    type: TreeNodeType.INDEXES,
                  },
                  {
                    key: '1',
                    name: 'idx_follower_id',
                    type: TreeNodeType.INDEXES,
                  },
                  {
                    key: '1',
                    name: 'idx_follower_id',
                    type: TreeNodeType.INDEXES,
                  },
                  {
                    key: '1',
                    name: 'idx_follower_id',
                    type: TreeNodeType.INDEXES,
                  },
                  {
                    key: '1',
                    name: 'idx_follower_id',
                    type: TreeNodeType.INDEXES,
                  },
                ]
              }
            ],
          },
          {
            key: '1',
            name: 'activity_audio_emp_recode',
            type: TreeNodeType.TABLE,
            children: [
              {
                key: '1',
                name: '列(10)',
                type: TreeNodeType.LINE,
                children: [
                  {
                    key: '0-1',
                    name: 'role_id',
                    type: TreeNodeType.LINE,
                  },
                  {
                    key: '0-1',
                    name: 'remark',
                    type: TreeNodeType.LINE,
                  },
                  {
                    key: '0-1',
                    name: 'deleted',
                    type: TreeNodeType.LINE,
                  },
                  {
                    key: '0-1',
                    name: 'role_id',
                    type: TreeNodeType.LINE,
                  },
                  {
                    key: '0-1',
                    name: 'remark',
                    type: TreeNodeType.LINE,
                  },
                  {
                    key: '0-1',
                    name: 'deleted',
                    type: TreeNodeType.LINE,
                  },
                  {
                    key: '0-1',
                    name: 'role_id',
                    type: TreeNodeType.LINE,
                  },
                  {
                    key: '0-1',
                    name: 'remark',
                    type: TreeNodeType.LINE,
                  },
                  {
                    key: '0-1',
                    name: 'deleted',
                    type: TreeNodeType.LINE,
                  },
                ]
              },
              {
                key: '1',
                name: '索引(10)',
                type: TreeNodeType.INDEXES,
                children: [
                  {
                    key: '1',
                    name: 'idx_follower_id',
                    type: TreeNodeType.INDEXES,
                  },
                  {
                    key: '1',
                    name: 'idx_follower_id',
                    type: TreeNodeType.INDEXES,
                  },
                  {
                    key: '1',
                    name: 'idx_follower_id',
                    type: TreeNodeType.INDEXES,
                  },
                  {
                    key: '1',
                    name: 'idx_follower_id',
                    type: TreeNodeType.INDEXES,
                  },
                  {
                    key: '1',
                    name: 'idx_follower_id',
                    type: TreeNodeType.INDEXES,
                  },
                  {
                    key: '1',
                    name: 'idx_follower_id',
                    type: TreeNodeType.INDEXES,
                  },
                  {
                    key: '1',
                    name: 'idx_follower_id',
                    type: TreeNodeType.INDEXES,
                  },
                ]
              }
            ],
          },
        ]
      },
      {
        key: '0-1-1',
        name: '查询',
        type: TreeNodeType.SEARCH,
        children: [
          {
            key: '1',
            name: 'SQL1',
            type: TreeNodeType.SAVE,
          },
          {
            key: '1',
            name: 'SQL2',
            type: TreeNodeType.SAVE,
          },
          {
            key: '1',
            name: 'SQL3',
            type: TreeNodeType.SAVE,
          },
        ]
      }
    ]
  },
];

interface TreeNodeIProps {
  data: ITreeNode;
  level: number;
  show: boolean;
  loadData: Function;
}

export function TreeNode(props: TreeNodeIProps) {
  const { data, level, show = false, loadData } = props
  const [showChildren, setShowChildren] = useState(false);
  const [isLoading, setIsLoading] = useState(false);

  const indentArr = new Array(level);
  for (let i = 0; i < level; i++) {
    indentArr[i] = 'indent'
  }

  //展开-收起
  const handleClick = (data: ITreeNode) => {
    if (!showChildren && !data.children) {
      setIsLoading(true)
    }
    loadData(data).then((res: ITreeNode[]) => {
      if (res?.length) {
        data.children = res
      }
      setIsLoading(false)
      setTimeout(() => {
        setShowChildren(!showChildren);
      }, 0);
    })
  };

  const renderMenu = () => {
    interface IMenu {
      title: string;
      type: string;
      icon: string;
    }
    const tableMenu: IMenu[] = [
      {
        title: '打开SQL窗口',
        type: 'new-tab',
        icon: '\ue603'
      },
      {
        title: '删除表',
        type: 'delete',
        icon: '\ue604'
      }
    ]

    const renderMenuList = (menuList: IMenu[]) => {
      return <ul className={globalStyle.menuList}>
        {
          menuList.map(item => {
            return <li className={globalStyle.menuItem}>
              <Iconfont code={item.icon} />
              {item.title}
            </li>
          })
        }
      </ul>
    }

    return <div className={styles.menuBox}>
      {renderMenuList(tableMenu)}
    </div>
  }

  const recognizeIcon = (type: TreeNodeType) => {
    switch (type) {
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
    <Dropdown overlay={renderMenu} trigger={['contextMenu']}>
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
            <Iconfont code={recognizeIcon(data.type)}></Iconfont>
          </div>
          <div className={styles.name}>{data.name}</div>
        </div>
      </div>
    </Dropdown>
    {
      data.children?.length &&
      data.children.map((item: any, i: number) => {
        return (
          <TreeNode loadData={loadData} key={i} show={(showChildren && show)} level={level + 1} data={item}></TreeNode>
        );
      })
    }
  </>
}

export default function Tree(props: IProps) {
  const { className, treeData, loadData } = props;
  return (
    <div className={classnames(className, styles.box)}>
      {treeData?.length && treeData.map((item) => {
        return <TreeNode loadData={loadData} key={item.name} show={true} level={0} data={item}></TreeNode>
      })}
    </div>
  );
};
