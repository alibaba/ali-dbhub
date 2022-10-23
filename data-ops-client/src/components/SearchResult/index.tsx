import React, { memo, useState } from 'react';
import styles from './index.less';
import classnames from 'classnames';
import Tabs from '@/components/Tabs';
import type { ColumnsType } from 'antd/es/table';
import Iconfont from '@/components/Iconfont';
import LoadingContent from '@/components/Loading/LoadingContent';
import { Button, DatePicker, Input, Table, Modal } from 'antd';
import { StatusType } from '@/utils/constants';
import { formatDate } from '@/utils';
import ResizeObserver from 'rc-resize-observer';
// import { VariableSizeGrid as Grid } from 'react-window';


interface IProps {
  className?: string;
}

interface DataType {
  [key: string]: any;
}

const historyListData: DataType[] = [
  {
    id: '1',
    startTime: 1665540690000,
    databaseName: 'ATA',
    sql: 'SELECT * FROM adbs',
    status: StatusType.SUCCESS,
  },
  {
    id: '2',
    startTime: 1665540690000,
    databaseName: 'ATA',
    sql: 'SELECT * FROM adbs',
    status: StatusType.FAIL,
  },
  {
    id: '3',
    startTime: 1665540690000,
    databaseName: 'ATA',
    sql: 'SELECT * FROM adbs',
    status: StatusType.SUCCESS,
  },
  {
    id: '4',
    startTime: 1665540690000,
    databaseName: 'ATA',
    sql: 'SELECT * FROM adbs',
    status: StatusType.FAIL,
  },
  {
    id: '1',
    startTime: 1665540690000,
    databaseName: 'ATA',
    sql: 'SELECT * FROM adbs',
    status: StatusType.SUCCESS,
  },
  {
    id: '2',
    startTime: 1665540690000,
    databaseName: 'ATA',
    sql: 'SELECT * FROM adbs',
    status: StatusType.FAIL,
  },
  {
    id: '3',
    startTime: 1665540690000,
    databaseName: 'ATA',
    sql: 'SELECT * FROM adbs',
    status: StatusType.SUCCESS,
  },
  {
    id: '4',
    startTime: 1665540690000,
    databaseName: 'ATA',
    sql: 'SELECT * FROM adbs',
    status: StatusType.FAIL,
  },
  {
    id: '1',
    startTime: 1665540690000,
    databaseName: 'ATA',
    sql: 'SELECT * FROM adbs',
    status: StatusType.SUCCESS,
  },
  {
    id: '2',
    startTime: 1665540690000,
    databaseName: 'ATA',
    sql: 'SELECT * FROM adbs',
    status: StatusType.FAIL,
  },
  {
    id: '3',
    startTime: 1665540690000,
    databaseName: 'ATA',
    sql: 'SELECT * FROM adbs',
    status: StatusType.SUCCESS,
  },
  {
    id: '4',
    startTime: 1665540690000,
    databaseName: 'ATA',
    sql: 'SELECT * FROM adbs',
    status: StatusType.FAIL,
  },
  {
    id: '1',
    startTime: 1665540690000,
    databaseName: 'ATA',
    sql: 'SELECT * FROM adbs',
    status: StatusType.SUCCESS,
  },
  {
    id: '2',
    startTime: 1665540690000,
    databaseName: 'ATA',
    sql: 'SELECT * FROM adbs',
    status: StatusType.FAIL,
  },
  {
    id: '3',
    startTime: 1665540690000,
    databaseName: 'ATA',
    sql: 'SELECT * FROM adbs',
    status: StatusType.SUCCESS,
  },
  {
    id: '4',
    startTime: 1665540690000,
    databaseName: 'ATA',
    sql: 'SELECT * FROM adbs',
    status: StatusType.FAIL,
  },
  {
    id: '1',
    startTime: 1665540690000,
    databaseName: 'ATA',
    sql: 'SELECT * FROM adbs',
    status: StatusType.SUCCESS,
  },
  {
    id: '2',
    startTime: 1665540690000,
    databaseName: 'ATA',
    sql: 'SELECT * FROM adbs',
    status: StatusType.FAIL,
  },
  {
    id: '3',
    startTime: 1665540690000,
    databaseName: 'ATA',
    sql: 'SELECT * FROM adbs',
    status: StatusType.SUCCESS,
  },
  {
    id: '4',
    startTime: 1665540690000,
    databaseName: 'ATA',
    sql: 'SELECT * FROM adbs',
    status: StatusType.FAIL,
  },
];


export default memo<IProps>(function SearchResult({ className }) {
  function size() {
    let a: any = []

    for (let i = 0; i < 200000; i++) {
      a.push({
        id: i,
        startTime: 1665540690000,
        databaseName: 'ATA',
        sql: 'SELECT * FROM adbs',
        status: StatusType.SUCCESS,
      })
    }
    return
  }

  const [tableData, setTableDate] = useState(historyListData);
  const renderStartTime = (text: string) => {
    return formatDate(text, 'yyyy-MM-dd hh:mm:ss')
  }
  const renderStatus = (text: string) => {
    return <div className={styles.tableStatus}>
      <i className={classnames(styles.dot, { [styles.successDot]: text == StatusType.SUCCESS })}></i>
      {text == StatusType.SUCCESS ? '成功' : '失败'}
    </div>
  }
  const columns: ColumnsType<DataType> = [
    {
      title: '开始时间',
      dataIndex: 'startTime',
      render: renderStartTime,
      width: 200,
    },
    {
      title: '数据库',
      dataIndex: 'databaseName',
    },
    {
      title: 'SQL',
      dataIndex: 'sql',
    },
    {
      title: '状态',
      dataIndex: 'status',
      render: renderStatus,
      width: 100,
    }
  ];

  function onChange() {
  }


  const makerResultHeaderList = () => {
    const list = [
      {
        label: <div>
          <Iconfont className={styles.recordIcon} code='&#xe8ad;'></Iconfont>
          执行记录
        </div>,
        key: '10',
      }
    ]
    const sqlRes = [
      {
        status: 'success',
        id: '0'
      },
      {
        status: 'fail',
        id: '1'
      },
      {
        status: 'fail',
        id: '2'
      },
      {
        status: 'success',
        id: '3'
      }, {
        status: 'success',
        id: '4'
      }, {
        status: 'success',
        id: '5'
      },
    ]

    sqlRes.map((item, index) => {
      list.push({
        label: <div>
          <Iconfont className={classnames(
            styles[item.status == 'success' ? 'successIcon' : 'failIcon'],
            styles.statusIcon
          )}
            code={item.status == 'success' ? '\ue605' : '\ue87c'} />
          执行结果{index}
        </div>,
        key: item.id
      })
    })
    return list
  }

  return <div className={classnames(className, styles.box)}>
    <div className={styles.resultHeader}>
      <Tabs
        onChange={onChange}
        tabs={makerResultHeaderList()}
      />
    </div>
    <div className={styles.resultContent}>
      <LoadingContent data={tableData} handleEmpty>
        <div className={styles.tableBox}>
          <Table pagination={false} columns={columns} dataSource={tableData} size="small" />
        </div>
      </LoadingContent>
    </div>
    <div className={styles.footer}>
      <div className={styles.iconBox}>
        <Iconfont code='&#xeb93;'></Iconfont>
      </div>
      <div>

      </div>
    </div>
  </div>
})
