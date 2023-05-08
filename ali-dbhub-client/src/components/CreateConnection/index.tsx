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

const { Option } = Select;

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

function VisiblyCreateConnection(props: IProps) {
  const { className, submitCallback } = props;
  const { model, setEditDataSourceData, setRefreshTreeNum, setModel } = useContext(DatabaseContext);
  const editDataSourceData: IEditDataSourceData = model.editDataSourceData as IEditDataSourceData
  const dataSourceId = editDataSourceData.id;
  const dataSourceType = editDataSourceData.dataType;
  const [form] = Form.useForm();
  let aliasChanged = false;

  const dataSourceFormConfigMemo = useMemo<IDataSourceForm>(() => {
    return deepClone(dataSourceFormConfigs).find((t: IDataSourceForm) => {
      return t.type === dataSourceType
    })
  }, [])

  const initialValuesMemo = useMemo(() => {
    return initialFormData(dataSourceFormConfigMemo.items)
  }, [])

  const [dataSourceFormConfig, setDataSourceFormConfig] = useState<IDataSourceForm>(dataSourceFormConfigMemo);

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

  function extractObj(url: any) {
    const { template, pattern } = dataSourceFormConfig
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
    const { template, pattern } = dataSourceFormConfig
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

  function selectChange(t: { name: string, value: any }) {
    dataSourceFormConfig.items.map((j, i) => {
      if (j.name === t.name) {
        j.defaultValue = t.value
      }
    })
    setDataSourceFormConfig({ ...dataSourceFormConfig })
  }

  useEffect(() => {
    console.log(dataSourceFormConfig)
  }, [dataSourceFormConfig])

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

  const submitConnection = (type: submitType) => {
    form.validateFields().then(res => {
      saveConnection(res, type);
    }).catch(error => {
      message.error('请确认必填信息')
    })
  }

  // 测试、保存、修改连接
  function saveConnection(values: IConnectionBase, type: submitType) {
    let p = {
      ...values,
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

  function onCancel() {
    setEditDataSourceData(false)
  }

  return <div className={classnames(styles.box, className)}>
    <Modal
      title={dataSourceId ? "修改数据源" : "连接数据源"}
      open={!!editDataSourceData}
      onCancel={onCancel}
      footer={false}
      width={560}
    >
      <Form
        form={form}
        initialValues={initialValues}
        autoComplete="off"
        className={styles.form}
        onFieldsChange={onFieldsChange}
      >
        {dataSourceFormConfig.items.map((t => renderFormItem(t)))}
        <div className={styles.formFooter}>
          <div className={styles.test}>
            {
              // !dataSourceId &&
              <Button
                onClick={submitConnection.bind(null, submitType.TEST)}
                className={styles.test}>
                测试连接
              </Button>
            }
          </div>
          <div className={styles.rightButton}>
            <Button onClick={onCancel} className={styles.cancel}>
              取消
            </Button>
            <Button className={styles.save} theme="primary" onClick={submitConnection.bind(null, dataSourceId ? submitType.UPDATE : submitType.SAVE)}>
              {
                dataSourceId ? '修改' : '连接'
              }
            </Button>
          </div>
        </div>
      </Form>
    </Modal>
  </div >
}

export default function CreateConnection() {
  const { model } = useContext(DatabaseContext);
  const editDataSourceData = model.editDataSourceData
  return editDataSourceData ? <VisiblyCreateConnection /> : <></>
}


