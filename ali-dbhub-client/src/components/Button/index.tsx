import React, { PureComponent } from 'react';
import classnames from 'classnames';
import styles from './index.less';

export type Theme = 'default' | 'primary';

export default class Button extends PureComponent<{
  theme?: Theme;
  disabled?: boolean;
} & React.DetailedHTMLProps<React.HTMLAttributes<HTMLButtonElement>, HTMLButtonElement>> {
  render() {
    const { className, children, theme, ...rest } = this.props;
    const _theme = theme ? (typeof theme === 'string' ? [theme] : theme) : [];
    const _className = classnames(className, styles.button, _theme.map(t => styles[t]), {
      [styles.disabled]: this.props.disabled
    });
    return <button className={_className} {...rest}>{children}</button>
  }
}
