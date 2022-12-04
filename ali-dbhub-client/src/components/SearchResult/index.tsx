import React, { memo, useEffect, useState } from 'react';
import styles from './index.less';
import classnames from 'classnames';
import Tabs from '@/components/Tabs';
import type { ColumnsType } from 'antd/es/table';
import Iconfont from '@/components/Iconfont';
import StateIndicator from '@/components/StateIndicator';
import LoadingContent from '@/components/Loading/LoadingContent';
import { Button, DatePicker, Input, Table, Modal } from 'antd';
import { StatusType, TableDataType, TableDataTypeCorresValue } from '@/utils/constants';
import { formatDate } from '@/utils';
import { IManageResultData, ITableHeaderItem, ITableCellItem } from '@/types';


interface IProps {
  className?: string;
  manageResultDataList: IManageResultData[];
}

interface DataType {
  [key: string]: any;
}

export default memo<IProps>(function SearchResult({ className, manageResultDataList = [] }) {
  const [isUnfold, setIsUnfold] = useState(true);
  const [currentTab, setCurrentTab] = useState('0');

  useEffect(() => {
    setCurrentTab('0')
  }, [manageResultDataList])

  const renderStatus = (text: string) => {
    return <div className={styles.tableStatus}>
      <i className={classnames(styles.dot, { [styles.successDot]: text == StatusType.SUCCESS })}></i>
      {text == StatusType.SUCCESS ? '成功' : '失败'}
    </div>
  }

  function onChange(index: string) {
    setCurrentTab(index)
  }

  const makerResultHeaderList = () => {
    const list: any = []
    manageResultDataList?.map((item, index) => {
      list.push({
        label: <div key={index}>
          <Iconfont className={classnames(
            styles[item.success ? 'successIcon' : 'failIcon'],
            styles.statusIcon
          )}
            code={item.success ? '\ue605' : '\ue87c'} />
          执行结果{index + 1}
        </div>,
        key: index
      })
    })
    return list
  }

  const moveLeftAside = () => {
    const databaseLeftAside = document.getElementById('database-left-aside');
    if (databaseLeftAside) {
      if (databaseLeftAside.offsetWidth === 0) {
        databaseLeftAside.style.width = '250px'
        setIsUnfold(true)
      } else {
        databaseLeftAside.style.width = '0px'
        setIsUnfold(false)
      }
    }
  }

  return <div className={classnames(className, styles.box)}>
    <div className={styles.resultHeader}>
      <Tabs
        onChange={onChange}
        tabs={makerResultHeaderList()}
      />
    </div>
    <div className={styles.resultContent}>
      <LoadingContent data={manageResultDataList} handleEmpty>
        {
          manageResultDataList.map((item, index) => {
            return <TableBox key={index} className={classnames({ [styles.cursorTableBox]: (index + '') == currentTab })} data={item} headerList={item.headerList} dataList={item.dataList}></TableBox>
          })
        }
      </LoadingContent>
    </div>
    <div className={styles.footer}>
      <div className={classnames({ [styles.reversalIconBox]: !isUnfold }, styles.iconBox)} onClick={moveLeftAside}>
        <Iconfont code='&#xeb93;'></Iconfont>
      </div>
      <div>
      </div>
    </div>
  </div>
})

interface ITableProps {
  headerList: ITableHeaderItem[];
  dataList: ITableCellItem[][];
  className?: string;
  data: any;
}

export function TableBox({ headerList, dataList, className, data }: ITableProps) {
  const [columns, setColumns] = useState<any>();
  const [tableData, setTableData] = useState<any>();
  useEffect(() => {
    const columns = headerList?.map((item, index) => {
      return {
        title: item.stringValue,
        dataIndex: dataList.length && TableDataTypeCorresValue[dataList[0][index].type],
        key: dataList.length && TableDataTypeCorresValue[dataList[0][index].type],
      }
    })
    setColumns(columns)
  }, [headerList])

  useEffect(() => {
    const tableData = dataList?.map((item: ITableCellItem[]) => {
      const rowData: any = {}
      item.map((i: ITableCellItem) => {
        rowData[TableDataTypeCorresValue[i.type]] = i[TableDataTypeCorresValue[i.type]]
      })
      return rowData
    })
    setTableData(tableData)
  }, [dataList])

  return <div className={classnames(className, styles.tableBox)}>
    <Table bordered pagination={false} columns={columns} dataSource={tableData} size="small" />
  </div>
}
