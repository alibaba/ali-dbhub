import React, { memo, useState } from 'react';
import styles from './index.less';
import classnames from 'classnames';

interface IProps {
  className?: any;
  value: string | undefined;
  onChange: Function;
}

function Input(props: IProps) {
  const { className, value = '', onChange } = props;
  const [iptValue, setIptValue] = useState<string | undefined>(value);
  function changeValue(e: any) {
    setIptValue(e.target.value);
    onChange(e.target.value);
  }
  return (
    <input
      value={iptValue}
      onChange={(e) => {
        changeValue(e);
      }}
      className={classnames(className, styles.input)}
    />
  );
}

export default memo(Input);
