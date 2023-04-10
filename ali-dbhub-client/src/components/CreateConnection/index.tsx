import React, { memo, useEffect, useMemo, useState, Fragment } from 'react';
import styles from './index.less';
import classnames from 'classnames';
import Button from '@/components/Button';
import { DatabaseTypeCode, databaseTypeList, TreeNodeType, EnvType } from '@/utils/constants';
import { ITreeNode, IConnectionBase } from '@/types';
import connectionServer from '@/service/connection'
import { dataSourceFormConfigs } from '@/config/dataSource';
import { IDataSourceForm, IFormItem, ISelect } from '@/config/types';
import { InputType } from '@/config/enum';
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

interface IProps {
  className?: string;
  rowData?: any;
  dataSourceType?: DatabaseTypeCode;
  submitCallback?: (data: ITreeNode) => void;
  onCancel?: () => void;
}

function initialFormData(dataSourceFormConfig: IFormItem[] | undefined) {
  let initValue: any = {}
  dataSourceFormConfig?.map(t => {
    initValue[t.name] = t.defaultValue
    if (t.selects?.length) {
      t.selects?.map(t => {
        if (t.selected) {
          initValue = {
            ...initValue,
            ...initialFormData(t.items)
          }
        }
      })
    }
  })
  return initValue
}

export default memo<IProps>(function CreateConnection(props) {
  const { className, rowData, onCancel, submitCallback, dataSourceType } = props;
  const [form] = Form.useForm();
  let aliasChanged = false;

  const [dataSourceFormConfig, setDataSourceFormConfig] = useState<IDataSourceForm>(
    dataSourceFormConfigs.find((t: IDataSourceForm) => {
      return t.type === dataSourceType
    })!
  );

  const [initialValues] = useState(initialFormData(dataSourceFormConfig.items));


  function renderFormItem(t: IFormItem): React.ReactNode {

    const FormItemTypes: { [key in InputType]: () => React.ReactNode } = {
      [InputType.INPUT]: () => <Form.Item
        label={t.labelNameCN}
        name={t.name}
      // rules={[
      //   {
      //     required: t.required,
      //   }
      // ]}
      >
        <Input />
      </Form.Item>,

      [InputType.SELECT]: () => <Form.Item
        label={t.labelNameCN}
        name={t.name}
      // rules={[
      //   {
      //     required: t.required,
      //   }
      // ]}
      >
        <Select value={currentSelectValue(t.selects)} onChange={selectChange}>
          {t.selects?.map((t: ISelect) => <Option key={t.value} value={t.value}>{t.label}</Option>)}
        </Select>
      </Form.Item>,

      [InputType.PASSWORD]: () => <Form.Item
        label={t.labelNameCN}
        name={t.name}
      // rules={[
      //   {
      //     required: t.required,
      //   }
      // ]}
      >
        <Input.Password />
      </Form.Item>
    }

    function selectChange() {
      const newForm: any = form;
      const formData = newForm.getFieldValue();
      dataSourceFormConfig.items.map((j, i) => {
        if (j.name === t.name) {
          j.selects?.map(item => {
            item.selected = item.value === formData[t.name] ? true : false;
          })
        }
      })
      setDataSourceFormConfig({ ...dataSourceFormConfig })
    }

    function currentSelectValue(selects: ISelect[] | undefined) {
      let value: any = ''
      selects?.forEach(t => {
        if (t.selected) {
          value = t.value
        }
      })
      return value
    }

    return <Fragment key={t.name}>
      <div key={t.name} className={classnames({ [styles.labelTextAlign]: t.labelTextAlign })} style={{ width: `${t.width}%` }}>
        {FormItemTypes[t.inputType]()}
      </div>
      {
        t.selects?.map(t => {
          if (t.selected) {
            return t.items?.map(t => renderFormItem(t))
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
      p.id = rowData?.id;
    }

    connectionServer[type](p).then(res => {
      if (type === submitType.TEST) {
        message.success(res === false ? '测试连接失败' : '测试连接成功');
      } else {
        const dataSource: ITreeNode = {
          name: p.alias,
          key: (res || p.id) as string,
          nodeType: TreeNodeType.DATASOURCE,
          dataSourceId: (res || p.id) as number,
          dataType: dataSourceType,
        }
        submitCallback?.(dataSource);
      }
    })
  }

  function extractObj(data: any) {
    const { template, pattern } = dataSourceFormConfig
    // 提取关键词对应的内容 value
    const matches = data[0].value.match(pattern);
    // 提取花括号内的关键词 key
    const reg = /{(.*?)}/g;
    let match;
    const arr = [];
    while ((match = reg.exec(template)) !== null) {
      arr.push(match[1]);
    }
    // key与value一一对应
    const extractObj: any = {}
    arr.map((t, i) => {
      extractObj[t] = t === 'database' ? matches[i + 2] : matches[i + 1]
    })
    return extractObj
  }

  function onFieldsChange(data: any, datas: any) {
    const { template } = dataSourceFormConfig
    const formData = form.getFieldsValue();
    let newData: any = {}

    // 改变url上边动
    const keyName = data[0].name[0];
    const keyValue = data[0].value;
    if (keyName === 'url') {
      newData = extractObj(data)
    } else if (keyName === 'alias') {
      aliasChanged = true
    } else {
      // 改变上边url动
      let url = template;
      datas.map((t: any) => {
        url = url.replace(`{${t.name[0]}}`, t.value)
      })
      newData = {
        url
      }
    }
    if (keyName === 'host' && !aliasChanged) {
      newData.alias = '@' + keyValue
    }

    form.setFieldsValue({
      ...formData,
      ...newData,
    });
  }

  return <div className={classnames(styles.box, className)}>
    <Modal
      title="连接数据库"
      open={true}
      onCancel={onCancel}
      footer={false}
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
              !rowData &&
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
            <Button className={styles.save} theme="primary" onClick={submitConnection.bind(null, rowData ? submitType.UPDATE : submitType.SAVE)}>
              {
                rowData ? '修改' : '连接'
              }
            </Button>
          </div>
        </div>
      </Form>
    </Modal>
  </div>
})
