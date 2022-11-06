import React, { memo, useEffect, useRef, useState } from 'react';
import styles from './index.less';
import classnames from 'classnames';
import { setLocaleData } from 'monaco-editor-nls';
const monaco = require('monaco-editor/esm/vs/editor/editor.api');
import zh_CN from 'monaco-editor-nls/locale/zh-hans.json';
setLocaleData(zh_CN);
import { language } from 'monaco-editor/esm/vs/basic-languages/sql/sql';
const { keywords } = language;
import { useTheme } from '@/utils/hooks';


interface IProps {
  id: string;
  className?: string;
  height?: number;
  getEditor: any;
  defaultValue: string | undefined;
}

// export const hintData: any = {
//   // adbs: ['dim_realtime_recharge_paycfg_range', 'dim_realtime_recharge_range'],
//   dimi: ['ads_adid', 'ads_spec_adid_category'],
// }

export default memo(function MonacoEditor(props: IProps) {
  const { defaultValue, className, getEditor, id = 0 } = props;
  const [editor, setEditor] = useState<any>();
  const themeColor = useTheme();


  useEffect(() => {
    const editor = monaco.editor.create(document.getElementById(`monaco-editor-${id}`)!, {
      value: '',
      language: 'sql',
      roundedSelection: false,
      scrollBeyondLastLine: false,
      readOnly: false,
      minimap: {
        enabled: false // 是否启用预览图
      }, // 预览图设置
      theme: localStorage.getItem('theme') == 'dark' ? 'vs-dark' : 'default'
    });
    window.onresize = function () {
      editor.layout()
    };
    getEditor(editor)
    setEditor(editor)
    setValue(editor, defaultValue || '')
    monaco.editor.defineTheme('BlackTheme', {
      base: 'vs-dark',
      inherit: true,
      rules: [{ background: '#15161a' }],
      colors: {
        // 相关颜色属性配置
        // 'editor.foreground': '#000000',
        'editor.background': '#15161a',     //背景色
        // 'editorCursor.foreground': '#8B0000',
        // 'editor.lineHighlightBackground': '#0000FF20',
        // 'editorLineNumber.foreground': '#008800',
        // 'editor.selectionBackground': '#88000030',
        // 'editor.inactiveSelectionBackground': '#88000015'
      }
    });
    monaco.editor.defineTheme('Default1', {
      base: 'vs',
      inherit: true,
      rules: [{ background: '#15161a' }],
      colors: {
        // 相关颜色属性配置
        // 'editor.foreground': '#000000',
        'editor.background': '#f8f8fa',     //背景色
        // 'editorCursor.foreground': '#8B0000',
        // 'editor.lineHighlightBackground': '#0000FF20',
        // 'editorLineNumber.foreground': '#008800',
        // 'editor.selectionBackground': '#88000030',
        // 'editor.inactiveSelectionBackground': '#88000015'
      }
    });
  }, [])

  useEffect(() => {
    monaco.editor.setTheme(themeColor == 'dark' ? 'BlackTheme' : 'Default');
  }, [themeColor])

  // 获取 SQL 语法提示
  const getSQLSuggest = () => {
    return keywords.map((key: any) => ({
      label: key,
      kind: monaco.languages.CompletionItemKind.Enum,
      insertText: key,
    }))
  }

  // 获取选中区域的值
  const getSelectionVal = () => {
    const selection = editor.getSelection() // 获取光标选中的值
    const { startLineNumber, endLineNumber, startColumn, endColumn } = selection
    const model = editor.getModel(editor)
    const value = model.getValueInRange({
      startLineNumber,
      startColumn,
      endLineNumber,
      endColumn,
    })
    return value
  }

  // 设置编辑器的值
  const setValue = (editor: any, value: string) => {
    const model = editor.getModel(editor)
    model.setValue(value)
  }

  // 获取编辑器的值
  const getValue = () => {
    if (editor?.getModel) {
      const model = editor.getModel(editor)
      const value = model.getValue()
      console.log(value)
      return value
    }
  }

  return <div className={classnames(className, styles.box)}>
    <div id={`monaco-editor-${id}`} className={styles.editorContainer} />
  </div>
});
