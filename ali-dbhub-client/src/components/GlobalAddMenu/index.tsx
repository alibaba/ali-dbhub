import React, { memo, useState } from 'react';
import styles from './index.less';
import classnames from 'classnames';
import Iconfont from '../IconFont';
import type { MenuProps } from 'antd';
import { Menu } from 'antd';
import { IDatabase, ITreeNode } from '@/types'
import { databaseType, DatabaseTypeCode } from '@/utils/constants';
import ConnectionDialog from '@/components/ConnectionDialog';
import CreateConnection from '@/components/CreateConnection';

interface IProps {
  className?: string;
  getAddTreeNode: (data: ITreeNode) => void;
}

type MenuItem = {
  label: React.ReactNode,
  key: React.Key | null,
  icon?: React.ReactNode,
  children?: MenuItem[],
}

function getItem(
  label: React.ReactNode,
  key: React.Key | null,
  icon?: React.ReactNode,
  children?: MenuItem[],
): MenuItem {
  return {
    label,
    key,
    icon,
    children,
  } as MenuItem;
}

const newDataSourceChildren = Object.keys(databaseType).map(t => {
  const source: IDatabase = databaseType[t];
  return getItem(source.name, source.code, <Iconfont className={styles.databaseTypeIcon} code={source.icon} />)
})

type IGlobalAddMenuItem = {

} & MenuItem


const globalAddMenuList: IGlobalAddMenuItem[] = [
  // {
  //   label: '新建控制台',
  //   key: 'newConsole',
  //   icon: <Iconfont code='&#xe619;' />
  // },
  {
    label: '新建数据源',
    key: 'newDataSource',
    icon: <Iconfont code='&#xe631;' />,
    children: newDataSourceChildren
  },
]

const items: MenuItem[] = newDataSourceChildren

export default memo<IProps>(function GlobalAddMenu(props) {
  const { className, getAddTreeNode } = props;
  const [isModalVisible, setIsModalVisible] = useState(false);
  const [dataSourceType, setDataSourceType] = useState<DatabaseTypeCode>();

  const onClickMenuNode: MenuProps['onClick'] = (e) => {
    setDataSourceType(e.keyPath[0] as DatabaseTypeCode);
    setIsModalVisible(true);
  };

  function submitCallback(data: ITreeNode) {
    getAddTreeNode(data);
    setIsModalVisible(false);
  }

  return <div className={classnames(styles.box, className)}>
    <Menu onClick={onClickMenuNode} mode="vertical" items={items as any} />
    {!!dataSourceType && isModalVisible && <CreateConnection
      submitCallback={submitCallback}
      dataSourceType={dataSourceType}
      onCancel={() => { setIsModalVisible(false) }}
    />}
    {/* <ConnectionDialog
      submitCallback={submitCallback}
      dataSourceType={dataSourceType}
      onCancel={() => { setIsModalVisible(false) }}
      openModal={isModalVisible}
    /> */}
  </div>
})
