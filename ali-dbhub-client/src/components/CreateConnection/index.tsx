import React, { memo, useEffect, useMemo, useState, Fragment, useContext, useCallback, useLayoutEffect } from 'react';
import styles from './index.less';
import classnames from 'classnames';
import Button from '@/components/Button';
import { DatabaseTypeCode, databaseTypeList, TreeNodeType, EnvType } from '@/utils/constants';
import { ITreeNode, IConnectionBase } from '@/types';
import connectionServer from '@/service/connection'
import { dataSourceFormConfigs } from '@/config/dataSource';
import { IDataSourceForm, IFormItem, ISelect } from '@/config/types';
import { DatabaseContext } from '@/context/database';
import { InputType } from '@/config/enum';
import { deepClone } from '@/utils'
import {
  Select,
  Modal,
  Form,
  Input,
  message,
  Radio,
  // Menu,
} from 'antd';
import Tabs, { ITab } from '@/components/Tabs';

const { Option } = Select;

type ITabsType = 'ssh' | 'extendInfo' | 'baseInfo'

export enum submitType {
  UPDATE = 'update',
  SAVE = 'save',
  TEST = 'test'
}

export interface IEditDataSourceData {
  dataType: DatabaseTypeCode,
  id?: number
}

interface IProps {
  className?: string;
  submitCallback?: (data: ITreeNode) => void;
}

const tabsConfig = [
  {
    label: '基础连接',
    key: 'baseInfo'
  },
  {
    label: 'SSH',
    key: 'ssh'
  },
  {
    label: '啥啥啥',
    key: 'extendInfo'
  },
]

function VisiblyCreateConnection(props: IProps) {
  const { className, submitCallback } = props;
  const { model, setEditDataSourceData, setRefreshTreeNum, setModel } = useContext(DatabaseContext);
  const editDataSourceData: IEditDataSourceData = model.editDataSourceData as IEditDataSourceData
  const dataSourceId = editDataSourceData.id;
  const dataSourceType = editDataSourceData.dataType;
  const [baseInfoForm] = Form.useForm();
  const [sshForm] = Form.useForm();
  const [extendInfoForm] = Form.useForm();
  const [currentTab, setCurrentTab] = useState<ITab>(tabsConfig[0]);

  const dataSourceFormConfigMemo = useMemo<IDataSourceForm>(() => {
    return deepClone(dataSourceFormConfigs).find((t: IDataSourceForm) => {
      return t.baseInfo.type === dataSourceType
    })
  }, [])

  const [dataSourceFormConfig, setDataSourceFormConfig] = useState<IDataSourceForm>(dataSourceFormConfigMemo);

  useEffect(() => {
    console.log(dataSourceFormConfig)
  }, [dataSourceFormConfig])

  // 测试、保存、修改连接
  function saveConnection(type: submitType) {
    const ssh = sshForm.getFieldsValue();
    const baseInfo = baseInfoForm.getFieldsValue();
    const extendInfo = extendInfoForm.getFieldsValue();
    let p: any = {
      ssh,
      baseInfo,
      extendInfo,
      // ...values,
      EnvType: EnvType.DAILY,
      type: dataSourceType!
    };

    if (type === submitType.UPDATE) {
      p.id = dataSourceId;
    }

    const api: any = connectionServer[type](p)
    api.then((res: any) => {
      if (type === submitType.TEST) {
        message.success(res === false ? '测试连接失败' : '测试连接成功');
      } else {
        setModel({
          ...model,
          editDataSourceData: false,
          refreshTreeNum: new Date().getTime(),
        })
      }
    })
  }

  function onCancel() {
    setEditDataSourceData(false)
  }

  function changeTabs(key: string, index: number) {
    console.log(tabsConfig[index])
    setCurrentTab(tabsConfig[index])
  }

  return <div className={classnames(styles.box, className)}>
    <Modal
      title={dataSourceId ? "修改数据源" : "连接数据源"}
      open={!!editDataSourceData}
      onCancel={onCancel}
      footer={false}
      width={560}
    >
      <Tabs className={styles.tabsBox} tabs={tabsConfig} onChange={changeTabs}></Tabs>
      <div className={classnames(styles.baseInfoBox, { [styles.showFormBox]: currentTab.key === 'baseInfo' })}>
        <RenderForm form={baseInfoForm} tab='baseInfo' dataSourceType={dataSourceType} dataSourceId={dataSourceId} ></RenderForm>
      </div>
      <div className={classnames(styles.sshBox, { [styles.showFormBox]: currentTab.key === 'ssh' })}>
        <RenderForm form={sshForm} tab='ssh' dataSourceType={dataSourceType} dataSourceId={dataSourceId} ></RenderForm>
      </div>
      <div className={classnames(styles.extendInfoBox, { [styles.showFormBox]: currentTab.key === 'extendInfo' })}>
        <RenderForm form={extendInfoForm} tab='extendInfo' dataSourceType={dataSourceType} dataSourceId={dataSourceId} ></RenderForm>
      </div>
      <div className={styles.formFooter}>
        <div className={styles.test}>
          {
            // !dataSourceId &&
            <Button
              onClick={saveConnection.bind(null, submitType.TEST)}
              className={styles.test}>
              测试连接
            </Button>
          }
        </div>
        <div className={styles.rightButton}>
          <Button onClick={onCancel} className={styles.cancel}>
            取消
          </Button>
          <Button className={styles.save} theme="primary" onClick={saveConnection.bind(null, dataSourceId ? submitType.UPDATE : submitType.SAVE)}>
            {
              dataSourceId ? '修改' : '连接'
            }
          </Button>
        </div>
      </div>
    </Modal>
  </div >
}

interface IRenderFormProps {
  dataSourceId: number | undefined,
  dataSourceType: string,
  tab: ITabsType;
  form: any;
}

function RenderForm(IRenderFormProps: IRenderFormProps) {
  const { dataSourceId, dataSourceType, tab, form } = IRenderFormProps
  let aliasChanged = false;
  const dataSourceFormConfigMemo = useMemo<IDataSourceForm>(() => {
    return deepClone(dataSourceFormConfigs).find((t: IDataSourceForm) => {
      return t.baseInfo.type === dataSourceType
    })
  }, [])
  const [dataSourceFormConfig, setDataSourceFormConfig] = useState<IDataSourceForm>(dataSourceFormConfigMemo);

  const initialValuesMemo = useMemo(() => {
    return initialFormData(dataSourceFormConfigMemo.baseInfo.items)
  }, [])

  const [initialValues] = useState(initialValuesMemo);

  function initialFormData(dataSourceFormConfig: IFormItem[] | undefined) {
    let initValue: any = {}
    if (dataSourceId) {
      connectionServer.getDetails({ id: dataSourceId + '' }).then((res: any) => {
        //TODO: 这里只处理了authentication，应该是需要处理所有的selete的
        if (res.user) {
          res.authentication = 1
        } else {
          res.authentication = 2
        }
        selectChange({ name: 'authentication', value: res.user ? 1 : 2 });

        regEXFormatting({ url: res.url }, res)
      })
    } else {
      dataSourceFormConfig?.map(t => {
        initValue[t.name] = t.defaultValue
        if (t.selects?.length) {
          t.selects?.map(item => {
            if (item.value === t.defaultValue) {
              initValue = {
                ...initValue,
                ...initialFormData(item.items)
              }
            }
          })
        }
      })
    }
    return initValue
  }

  function selectChange(t: { name: string, value: any }) {
    dataSourceFormConfig.baseInfo.items.map((j, i) => {
      if (j.name === t.name) {
        j.defaultValue = t.value
      }
    })
    setDataSourceFormConfig({ ...dataSourceFormConfig })
  }

  function onFieldsChange(data: any, datas: any) {
    // 将antd的格式转换为正常的对象格式
    if (!data.length) {
      return
    }
    const keyName = data[0].name[0];
    const keyValue = data[0].value;
    const variableData = {
      [keyName]: keyValue
    }
    const dataObj: any = {}
    datas.map((t: any) => {
      dataObj[t.name[0]] = t.value
    })
    // 正则拆分url/组建url
    regEXFormatting(variableData, dataObj);
  }

  function extractObj(url: any) {
    const { template, pattern } = dataSourceFormConfig.baseInfo
    // 提取关键词对应的内容 value
    const matches = url.match(pattern)!;
    console.log(matches)
    // 提取花括号内的关键词 key
    const reg = /{(.*?)}/g;
    let match;
    const arr = [];
    while ((match = reg.exec(template)) !== null) {
      arr.push(match[1]);
    }
    // key与value一一对应
    const newExtract: any = {}
    arr.map((t, i) => {
      newExtract[t] = t === 'database' ? (matches[i + 2] || '') : matches[i + 1]
    })
    return newExtract
  }

  function regEXFormatting(variableData: { [key: string]: any }, dataObj: { [key: string]: any }) {
    const { template, pattern } = dataSourceFormConfig.baseInfo
    const keyName = Object.keys(variableData)[0]
    const keyValue = variableData[Object.keys(variableData)[0]]
    let newData: any = {}
    if (keyName === 'url') {
      //先判断url是否符合规定的正则
      if (pattern.test(keyValue)) {
        newData = extractObj(keyValue);
      }
    } else if (keyName === 'alias') {
      aliasChanged = true
    } else {
      // 改变上边url动
      let url = template;
      Object.keys(dataObj).map(t => {
        url = url.replace(`{${t}}`, dataObj[t])
      })
      newData = {
        url
      }
    }
    if (keyName === 'host' && !aliasChanged) {
      newData.alias = '@' + keyValue
    }
    form.setFieldsValue({
      ...dataObj,
      ...newData,
    });
  }

  function renderFormItem(t: IFormItem): React.ReactNode {
    const FormItemTypes: { [key in InputType]: () => React.ReactNode } = {
      [InputType.INPUT]: () => <Form.Item
        label={t.labelNameCN}
        name={t.name}
      >
        <Input />
      </Form.Item>,

      [InputType.SELECT]: () => <Form.Item
        label={t.labelNameCN}
        name={t.name}
      >
        <Select value={t.defaultValue} onChange={(e) => { selectChange({ name: t.name, value: e }) }}>
          {t.selects?.map((t: ISelect) => <Option key={t.value} value={t.value}>{t.label}</Option>)}
        </Select>
      </Form.Item>,

      [InputType.PASSWORD]: () => <Form.Item
        label={t.labelNameCN}
        name={t.name}
      >
        <Input.Password />
      </Form.Item>
    }

    return <Fragment key={t.name}>
      <div key={t.name} className={classnames({ [styles.labelTextAlign]: t.labelTextAlign })} style={{ width: `${t.width}%` }}>
        {FormItemTypes[t.inputType]()}
      </div>
      {
        t.selects?.map(item => {
          if (t.defaultValue === item.value) {
            return item.items?.map(t => renderFormItem(t))
          }
        })
      }
    </Fragment>
  }

  return <Form
    form={form}
    initialValues={initialValues}
    autoComplete="off"
    className={styles.form}
    onFieldsChange={onFieldsChange}
  >
    {dataSourceFormConfig[tab]!.items.map((t => renderFormItem(t)))}
  </Form>
}

export default function CreateConnection() {
  const { model } = useContext(DatabaseContext);
  const editDataSourceData = model.editDataSourceData
  return editDataSourceData ? <VisiblyCreateConnection /> : <></>
}


