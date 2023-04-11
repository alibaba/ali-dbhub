import React, { useState, createContext } from 'react';
import { IOperationData } from '@/components/OperationTableModal';
import { ITreeNode } from '@/types';
import Database from '@/pages/database';

export type ICreateConsoleDialog =
  | false
  | {
    dataSourceId: number;
    databaseName: string;
    schemaName: string;
  };

export type IOperationDataDialog = false | IOperationData;

export interface IModel {
  createConsoleDialog: ICreateConsoleDialog;
  operationData: IOperationDataDialog;
  needRefreshNodeTree: any;
  dblclickNodeData: ITreeNode | null;
  aiImportSql: string;
  showSearchResult: boolean;
}

export interface IContext {
  model: IModel;
  setCreateConsoleDialog: (value: ICreateConsoleDialog) => void;
  setOperationDataDialog: (value: IOperationDataDialog) => void;
  setNeedRefreshNodeTree: (value: any) => void;
  setDblclickNodeData: (value: ITreeNode | null) => void;
  setAiImportSql: (value: string) => void;
  setShowSearchResult: (value: boolean) => void;
}

const initDatabaseValue: IModel = {
  createConsoleDialog: false,
  operationData: false,
  needRefreshNodeTree: {},
  dblclickNodeData: null,
  aiImportSql: '',
  showSearchResult: localStorage.getItem('showSearchResultBox') === 'true',
};

export const DatabaseContext = createContext<IContext>({} as any);

export default function DatabaseContextProvider({
  children,
}: {
  children: React.ReactNode;
}) {
  const [model, setStateModel] = useState<IModel>(initDatabaseValue);

  const setCreateConsoleDialog = (
    createConsoleDialog: ICreateConsoleDialog,
  ) => {
    setStateModel({
      ...model,
      createConsoleDialog,
    });
  };
  const setOperationDataDialog = (operationData: IOperationDataDialog) => {
    setStateModel({
      ...model,
      operationData,
    });
  };
  const setDblclickNodeData = (dblclickNodeData: ITreeNode | null) => {
    setStateModel({
      ...model,
      dblclickNodeData,
    });
  };

  const setNeedRefreshNodeTree = (needRefreshNodeTree: any) => {
    setStateModel({
      ...model,
      needRefreshNodeTree,
    });
  };

  const setAiImportSql = (aiImportSql: any) => {
    setStateModel({
      ...model,
      aiImportSql,
    });
  };

  const setShowSearchResult = (showSearchResult: boolean) => {
    setStateModel({
      ...model,
      showSearchResult,
    });
    localStorage.setItem('showSearchResultBox', showSearchResult.toString())
  };

  return (
    <DatabaseContext.Provider
      value={{
        model,
        setCreateConsoleDialog,
        setOperationDataDialog,
        setNeedRefreshNodeTree,
        setDblclickNodeData,
        setAiImportSql,
        setShowSearchResult
      }}
    >
      {children}
    </DatabaseContext.Provider>
  );
}
