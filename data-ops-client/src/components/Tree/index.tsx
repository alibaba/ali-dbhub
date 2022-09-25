import React, { memo, useEffect, useState } from 'react';
import styles from './index.less';
import classnames from 'classnames';
import Iconfont from '../Iconfont';
import { Dropdown } from 'antd';

interface IProps {
  className?: any;
}

const treeList = [
  {
    key: '0-1',
    name: 'database',
    type: 'database',
    children: [
      {
        key: '0-1-1',
        name: '表',
        type: 'table',
        children: [
          {
            key: '1',
            name: 'activity_audio_emp_recode',
            type: 'table',
            children: [
              {
                key: '1',
                name: '列(10)',
                type: 'line',
                children: [
                  {
                    key: '0-1',
                    name: 'role_id',
                    type: 'line',
                  },
                  {
                    key: '0-1',
                    name: 'remark',
                    type: 'line',
                  },
                  {
                    key: '0-1',
                    name: 'deleted',
                    type: 'line',
                  },
                  {
                    key: '0-1',
                    name: 'role_id',
                    type: 'line',
                  },
                  {
                    key: '0-1',
                    name: 'remark',
                    type: 'line',
                  },
                  {
                    key: '0-1',
                    name: 'deleted',
                    type: 'line',
                  },
                  {
                    key: '0-1',
                    name: 'role_id',
                    type: 'line',
                  },
                  {
                    key: '0-1',
                    name: 'remark',
                    type: 'line',
                  },
                  {
                    key: '0-1',
                    name: 'deleted',
                    type: 'line',
                  },
                ]
              },
              {
                key: '1',
                name: '索引(10)',
                type: 'indexes',
                children: [
                  {
                    key: '1',
                    name: 'idx_follower_id',
                    type: 'indexes',
                  },
                  {
                    key: '1',
                    name: 'idx_follower_id',
                    type: 'indexes',
                  },
                  {
                    key: '1',
                    name: 'idx_follower_id',
                    type: 'indexes',
                  },
                  {
                    key: '1',
                    name: 'idx_follower_id',
                    type: 'indexes',
                  },
                  {
                    key: '1',
                    name: 'idx_follower_id',
                    type: 'indexes',
                  },
                  {
                    key: '1',
                    name: 'idx_follower_id',
                    type: 'indexes',
                  },
                  {
                    key: '1',
                    name: 'idx_follower_id',
                    type: 'indexes',
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
        type: 'search',
        children: [
          {
            key: '1',
            name: 'SQL1',
            type: 'save',
          },
          {
            key: '1',
            name: 'SQL2',
            type: 'save',
          },
          {
            key: '1',
            name: 'SQL3',
            type: 'save',
          },
        ]
      }
    ]
  },
  {
    key: '0-1',
    name: 'database1',
    type: 'database',
    children: [
      {
        key: '1',
        name: 'activity_audio_emp_recode',
        type: 'table',
        children: [
          {
            key: '0-1',
            name: 'role_id',
            type: 'line',
          },
          {
            key: '0-1',
            name: 'remark',
            type: 'line',
          },
          {
            key: '0-1',
            name: 'deleted',
            type: 'line',
          },
          {
            key: '0-1',
            name: 'role_id',
            type: 'line',
          },
          {
            key: '0-1',
            name: 'remark',
            type: 'line',
          },
          {
            key: '0-1',
            name: 'deleted',
            type: 'line',
          },
          {
            key: '0-1',
            name: 'role_id',
            type: 'line',
          },
          {
            key: '0-1',
            name: 'remark',
            type: 'line',
          },
          {
            key: '0-1',
            name: 'deleted',
            type: 'line',
          },
        ],
      },
      {
        key: '1',
        name: 'activity_audio_emp_recode',
        type: 'table',
        children: [
          {
            key: '0-1',
            name: 'role_id',
            type: 'line',
          },
          {
            key: '0-1',
            name: 'remark',
            type: 'line',
          },
          {
            key: '0-1',
            name: 'deleted',
            type: 'line',
          },
          {
            key: '0-1',
            name: 'role_id',
            type: 'line',
          },
          {
            key: '0-1',
            name: 'remark',
            type: 'line',
          },
          {
            key: '0-1',
            name: 'deleted',
            type: 'line',
          },
          {
            key: '0-1',
            name: 'role_id',
            type: 'line',
          },
          {
            key: '0-1',
            name: 'remark',
            type: 'line',
          },
          {
            key: '0-1',
            name: 'deleted',
            type: 'line',
          },
        ],
      },
      {
        key: '1',
        name: 'activity_audio_emp_recode',
        type: 'table',
        children: [
          {
            key: '0-1',
            name: 'role_id',
            type: 'line',
          },
          {
            key: '0-1',
            name: 'remark',
            type: 'line',
          },
          {
            key: '0-1',
            name: 'deleted',
            type: 'line',
          },
          {
            key: '0-1',
            name: 'role_id',
            type: 'line',
          },
          {
            key: '0-1',
            name: 'remark',
            type: 'line',
          },
          {
            key: '0-1',
            name: 'deleted',
            type: 'line',
          },
          {
            key: '0-1',
            name: 'role_id',
            type: 'line',
          },
          {
            key: '0-1',
            name: 'remark',
            type: 'line',
          },
          {
            key: '0-1',
            name: 'deleted',
            type: 'line',
          },
        ],
      },
      {
        key: '1',
        name: 'activity_audio_emp_recode',
        type: 'table',
        children: [
          {
            key: '0-1',
            name: 'role_id',
            type: 'line',
          },
          {
            key: '0-1',
            name: 'remark',
            type: 'line',
          },
          {
            key: '0-1',
            name: 'deleted',
            type: 'line',
          },
          {
            key: '0-1',
            name: 'role_id',
            type: 'line',
          },
          {
            key: '0-1',
            name: 'remark',
            type: 'line',
          },
          {
            key: '0-1',
            name: 'deleted',
            type: 'line',
          },
          {
            key: '0-1',
            name: 'role_id',
            type: 'line',
          },
          {
            key: '0-1',
            name: 'remark',
            type: 'line',
          },
          {
            key: '0-1',
            name: 'deleted',
            type: 'line',
          },
        ],
      },
      {
        key: '1',
        name: 'activity_audio_emp_recode',
        type: 'table',
        children: [
          {
            key: '0-1',
            name: 'role_id',
            type: 'line',
          },
          {
            key: '0-1',
            name: 'remark',
            type: 'line',
          },
          {
            key: '0-1',
            name: 'deleted',
            type: 'line',
          },
          {
            key: '0-1',
            name: 'role_id',
            type: 'line',
          },
          {
            key: '0-1',
            name: 'remark',
            type: 'line',
          },
          {
            key: '0-1',
            name: 'deleted',
            type: 'line',
          },
          {
            key: '0-1',
            name: 'role_id',
            type: 'line',
          },
          {
            key: '0-1',
            name: 'remark',
            type: 'line',
          },
          {
            key: '0-1',
            name: 'deleted',
            type: 'line',
          },
        ],
      },
      {
        key: '1',
        name: 'activity_audio_emp_recode',
        type: 'table',
        children: [
          {
            key: '0-1',
            name: 'role_id',
            type: 'line',
          },
          {
            key: '0-1',
            name: 'remark',
            type: 'line',
          },
          {
            key: '0-1',
            name: 'deleted',
            type: 'line',
          },
          {
            key: '0-1',
            name: 'role_id',
            type: 'line',
          },
          {
            key: '0-1',
            name: 'remark',
            type: 'line',
          },
          {
            key: '0-1',
            name: 'deleted',
            type: 'line',
          },
          {
            key: '0-1',
            name: 'role_id',
            type: 'line',
          },
          {
            key: '0-1',
            name: 'remark',
            type: 'line',
          },
          {
            key: '0-1',
            name: 'deleted',
            type: 'line',
          },
        ],
      },
    ]
  }
];

export function TreeNode({ data, level, show = false }) {
  const [showChildren, setShowChildren] = useState(false);

  const indentArr = new Array(level);
  for (let i = 0; i < level; i++) {
    indentArr[i] = 'indent'
  }

  const handleClick = () => {
    setShowChildren(!showChildren);
  };

  const renderMenu = () => {
    return <div className={styles.menu}>
      下拉菜单
    </div>
  }

  const recognizeIcon = (type) => {
    switch (type) {
      case 'table':
        return '\ue63e';
      case 'database':
        return '\ue62c';
      case 'search':
        return '\uec4c';
      case 'line':
        return '\ue611';
      case 'save':
        return '\ue936';
      case 'indexes':
        return '\ue648';
      default:
        return '\ue936';
    }
  }

  return (
    <>
      <Dropdown overlay={renderMenu} trigger={['contextMenu']}>
        <div className={classnames(styles.treeNode, { [styles.hiddenTreeNode]: !show })} onClick={handleClick}>
          <div className={styles.left}>
            {
              indentArr.map((item, i) => {
                return <div key={i} className={styles.indent}></div>
              })
            }
          </div>
          <div className={styles.right}>
            {
              data.children?.length &&
              <div className={styles.arrows} >
                <Iconfont code={showChildren ? "\ue61e" : "\ue65f"} />
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
            <TreeNode key={i} show={(showChildren && show)} level={level + 1} data={item}></TreeNode>
          );
        })
      }
    </>
  );
}

export default memo<IProps>(function Tree(props) {
  const { className } = props;
  return (
    <div className={classnames(className, styles.box)}>
      {treeList.map((item) => {
        return <TreeNode key={item.name} show={true} level={0} data={item}></TreeNode>
      })}
    </div>
  );
});
