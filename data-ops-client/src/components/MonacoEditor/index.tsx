import React, { memo, useEffect, useState } from 'react';
import styles from './index.less';
import classnames from 'classnames';
import { setLocaleData } from 'monaco-editor-nls';
import zh_CN from 'monaco-editor-nls/locale/zh-hans.json';
setLocaleData(zh_CN);
const monaco = require('monaco-editor/esm/vs/editor/editor.api');
import { language } from 'monaco-editor/esm/vs/basic-languages/sql/sql';
const { keywords } = language

interface IProps {
  id: string;
  className?: string;
  height?: number;
  getEditor: any;
  defaultValue: string | undefined;
  getValue?: (value: string) => {}
}

export const hintData = {
  adbs: ['dim_realtime_recharge_paycfg_range', 'dim_realtime_recharge_range'],
  dimi: ['ads_adid', 'ads_spec_adid_category'],
}

export default memo(function MonacoEditor(props: IProps) {
  const { defaultValue, className, getEditor, id = 0, getValue: getMonacovalue } = props
  const [editor, setEditor] = useState<any>()

  useEffect(() => {
    console.log(id)
    registerCompletion()
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
  }, [id])

  const registerCompletion = () => {
    monaco.languages.registerCompletionItemProvider('sql', {
      triggerCharacters: ['.', ...keywords],
      provideCompletionItems: (model, position) => {
        let suggestions = []

        const { lineNumber, column } = position

        const textBeforePointer = model.getValueInRange({
          startLineNumber: lineNumber,
          startColumn: 0,
          endLineNumber: lineNumber,
          endColumn: column,
        })

        const tokens = textBeforePointer.trim().split(/\s+/)
        const lastToken = tokens[tokens.length - 1] // 获取最后一段非空字符串

        if (lastToken.endsWith('.')) {
          const tokenNoDot = lastToken.slice(0, lastToken.length - 1)
          if (Object.keys(hintData).includes(tokenNoDot)) {
            suggestions = [...getTableSuggest(tokenNoDot)]
          }
        } else if (lastToken === '.') {
          suggestions = []
        } else {
          suggestions = [...getDBSuggest(), ...getSQLSuggest()]
        }
        return {
          suggestions,
        }
      },
    })
  }
  // 获取DB数据
  const getDBSuggest = () => {
    return Object.keys(hintData).map((key) => ({
      label: key,
      kind: monaco.languages.CompletionItemKind.Constant,
      insertText: key,
    }))
  }

  // 获取Table数据
  const getTableSuggest = (dbName) => {
    const tableNames = hintData[dbName]
    if (!tableNames) {
      return []
    }
    return tableNames.map((name) => ({
      label: name,
      kind: monaco.languages.CompletionItemKind.Constant,
      insertText: name,
    }))
  }

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

  //设置主题
  const setTheme = () => {
    monaco.editor.setTheme('vs-dark')
  }

  // 设置编辑器的值
  const setValue = (editor, value: string) => {
    const model = editor.getModel(editor)
    model.setValue(value)
  }

  getMonacovalue

  // 获取编辑器的值
  const getValue = () => {
    const model = editor.getModel(editor)
    const value = model.getValue()
    return value
  }

  return <div className={classnames(className, styles.box)}>
    <div id={`monaco-editor-${id}`} className={styles.editorContainer} />
  </div>
});
