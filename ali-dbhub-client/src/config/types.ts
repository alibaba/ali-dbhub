import { InputType, AuthenticationType } from './enum';
import { DatabaseTypeCode } from '@/utils/constants';
import { OperationColumn } from '@/components/Tree/treeConfig';

export type ISelect = {
  value?: AuthenticationType | string;
  label?: string;
  items?: IFormItem[];
};

export interface IFormItem {
  defaultValue: any;
  inputType: InputType;
  labelNameCN: string;
  labelNameEN: string;
  name: string;
  required: boolean;
  width: number;
  selected?: any;
  selects?: ISelect[];
  labelTextAlign?: 'right';
}

// 配置链接数据源表单 Json
export type IDataSourceForm = {
  type: DatabaseTypeCode;
  items: IFormItem[];
  pattern: RegExp;
  template: string;
  excludes?: OperationColumn[];
};