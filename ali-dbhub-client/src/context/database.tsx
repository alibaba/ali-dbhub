import React, { memo, useEffect, useState, useRef, createContext } from 'react';
import { IOperationData } from '@/components/OperationTableModal';
import { ITreeNode } from '@/types';

export type ICreateConsoleDialog = false | {
  dataSourceId: number;
  databaseName: string;
}

export type IOperationDataDialog = false | IOperationData

export interface IModel {
  createConsoleDialog: ICreateConsoleDialog;
  operationData: IOperationDataDialog;
  needRefreshNodeTree: any;
  dblclickNodeData: ITreeNode | null;
  aiImportSql: string;
}

export interface IContext {
  model: IModel;
  setCreateConsoleDialog: (value: ICreateConsoleDialog) => void;
  setOperationDataDialog: (value: IOperationDataDialog) => void;
  setNeedRefreshNodeTree: (value: any) => void;
  setDblclickNodeData: (value: ITreeNode | null) => void;
  setAiImportSql: (value: string) => void;
}

const initDatabaseValue: IModel = {
  createConsoleDialog: false,
  operationData: false,
  needRefreshNodeTree: {},
  dblclickNodeData: null,
  aiImportSql: '',
}

export const DatabaseContext = createContext<IContext>({} as any);

export default function DatabaseContextProvider({ children }: { children: React.ReactNode }) {
  const [model, setStateModel] = useState<IModel>(initDatabaseValue);

  const setCreateConsoleDialog = (createConsoleDialog: ICreateConsoleDialog) => {
    setStateModel({
      ...model,
      createConsoleDialog
    })
  }
  const setOperationDataDialog = (operationData: IOperationDataDialog) => {
    setStateModel({
      ...model,
      operationData
    })
  }
  const setDblclickNodeData = (dblclickNodeData: ITreeNode | null) => {
    setStateModel({
      ...model,
      dblclickNodeData
    })
  }

  const setNeedRefreshNodeTree = (needRefreshNodeTree: any) => {
    setStateModel({
      ...model,
      needRefreshNodeTree
    })
  }

  const setAiImportSql = (aiImportSql: any) => {
    setStateModel({
      ...model,
      aiImportSql
    })
  }

  return <DatabaseContext.Provider value={{
    model,
    setCreateConsoleDialog,
    setOperationDataDialog,
    setNeedRefreshNodeTree,
    setDblclickNodeData,
    setAiImportSql
  }}>
    {children}
  </DatabaseContext.Provider>
}