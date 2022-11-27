import React, { PureComponent } from 'react';
import classnames from 'classnames';
import styles from './index.less';

export default class Iconfont extends PureComponent<{
  code: string;
} & React.DetailedHTMLProps<React.HTMLAttributes<HTMLElement>, HTMLElement>> {
  render() {
    return <i {...this.props} className={classnames(this.props.className, styles.iconfont)}>{this.props.code}</i>
  }
}
