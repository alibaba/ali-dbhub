import React, { memo, useEffect, useState, useRef } from 'react';
import styles from './index.less';
import classnames from 'classnames';
import { history, useParams } from 'umi';
import { Button, DatePicker, Input } from 'antd';
import i18n from '../../i18n';
import AppHeader from '@/components/AppHeader';
import Iconfont from '@/components/Iconfont';
import Tree from '@/components/Tree';
import PageLoading from '@/components/PageLoading';
import MonacoEditor from '@/components/MonacoEditor';
import DraggableDivider from '@/components/DraggableDivider';
import databaseManageServer from '@/service/databaseManage';
import { IConnectionItem } from '@/types'


interface IProps {
  className?: any;
}

export default memo<IProps>(function Database({ className }) {
  const params: { id: string } = useParams()
  const letfRef = useRef<HTMLDivElement | null>(null);
  const [databaseDetaile, setDatabaseDetaile] = useState<IConnectionItem>()
  const monacoEditorBox = useRef<HTMLDivElement | null>(null);
  let editor: any = ''

  useEffect(() => {
    if (params.id) {
      getDetaile()
    }
  }, [])

  const getDetaile = () => {
    let p = {
      id: params.id
    }
    databaseManageServer.getConnectionDetaile(p).then(res => {
      setDatabaseDetaile(res)
    })
  }

  const callback = () => {
    editor && editor.layout()
  }

  function getEditor(e: any) {
    editor = e
  }

  return (
    <div className={classnames(className, styles.box)}>
      <div ref={letfRef} className={styles.aside}>
        <div className={styles.header}>
          <div className={styles.currentNameBox}>
            <div className={styles.databaseName}>
              {databaseDetaile?.alias}
            </div>
            <Iconfont code="&#xe7b1;"></Iconfont>
          </div>
          <div className={styles.searchBox}>
            <div className={styles.inputBox}>
              <Iconfont code="&#xe600;"></Iconfont>
              <Input type="text" placeholder={i18n('database.input.search')} />
            </div>
            <div className={classnames(styles.refresh, styles.button)}>
              <Iconfont code="&#xec08;"></Iconfont>
            </div>
            <div className={classnames(styles.create, styles.button)}>
              <Iconfont code="&#xe631;"></Iconfont>
            </div>
          </div>
        </div>
        <div className={styles.overview}>
          <Iconfont code="&#xe63d;"></Iconfont>
          <span>{i18n('database.button.overview')}</span>
        </div>
        <Tree className={styles.tree}></Tree>
      </div>
      <DraggableDivider callback={callback} volatileRef={letfRef} />
      <div className={styles.main}>
        <AppHeader></AppHeader>
        <div className={styles.databaseQuery}>
          <div ref={monacoEditorBox} className={styles.monacoEditor}>
            <MonacoEditor getEditor={getEditor}></MonacoEditor>
          </div>
          <DraggableDivider callback={callback} direction='row' min={200} volatileRef={monacoEditorBox} />
          <div className={styles.searchResult}>
            结果区域
            <PageLoading></PageLoading>
          </div>
        </div>
      </div>
    </div>
  );
});
