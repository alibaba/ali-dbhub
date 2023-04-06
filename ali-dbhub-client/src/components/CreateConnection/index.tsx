import React, { memo, useEffect, useMemo, useState } from 'react';
import styles from './index.less';
import classnames from 'classnames';
import { DatabaseTypeCode, databaseTypeList } from '@/utils/constants';
import { ITreeNode } from '@/types';
import { dataSourceFormConfigs, IDataSourceForm, InputType, IFormItem, ISelect } from '@/json/dataSource';
import {
  Select,
  Button,
  Modal,
  Form,
  Input,
  message,
  Radio,
  // Menu,
} from 'antd';

const { Option } = Select;

interface IProps {
  className?: string;
  rowData?: any;
  openModal: boolean;
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
  const { className, rowData, onCancel, submitCallback, openModal, dataSourceType } = props;
  const [form] = Form.useForm();

  const [dataSourceFormConfig, setDataSourceFormConfig] = useState<IDataSourceForm>(
    dataSourceFormConfigs.find((t: IDataSourceForm) => {
      return t.type === dataSourceType
    })!
  );

  const [initialValues, setInitialValues] = useState(initialFormData(dataSourceFormConfig.items));

  function renderFormItem(t: IFormItem): React.ReactNode {
    function selectChange() {
      const newForm: any = form;
      const formData = newForm.getFieldValue();
      dataSourceFormConfig.items.map(i => {
        if (i.name === t.name) {
        }
      })
    }

    function currentSelectValue(selects: ISelect[] | undefined) {
      let value = ''
      selects?.forEach(t => {
        if (t.selected) {
          value = t.value
        }
      })
      return value
    }

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
        <Select value={currentSelectValue(t.selects) || 'User&Password'} onChange={selectChange}>
          {t.selects?.map((t: ISelect) => <Option key={t.value} value={t.value}>{t.value}</Option>)}
        </Select>
      </Form.Item>,

      [InputType.PASSWORD]: () => <Form.Item
        label={t.labelNameCN}
        name={t.name}
      >
        <Input.Password />
      </Form.Item>
    }

    return <>
      <div key={t.name} style={{ width: `${t.width}%` }}>
        {FormItemTypes[t.inputType]()}
      </div>
      {
        t.selects?.map(t => {
          if (t.selected) {
            console.log(t);
            return t.items?.map(t => renderFormItem(t))
          }
        })
      }
    </>
  }

  return <div className={classnames(styles.box, className)}>
    <Modal
      title="连接数据库"
      open={openModal}
      onCancel={onCancel}
      footer={false}
    >
      <Form
        form={form}
        initialValues={initialValues}
        autoComplete="off"
        className={styles.form}
      >
        {dataSourceFormConfig.items.map((t => renderFormItem(t)))}
      </Form>
    </Modal>
  </div>
})
