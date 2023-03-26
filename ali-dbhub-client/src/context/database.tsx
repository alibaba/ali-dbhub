import React, { memo, useEffect, useState, useRef, createContext } from 'react';
import { IOperationData } from '@/components/OperationTableModal';

export type ICreateConsoleDialog = false | {
  dataSourceId: number;
  databaseName: string;
}

export type IOperationDataDialog = false | IOperationData

export interface IModel {
  createConsoleDialog: ICreateConsoleDialog;
  operationData: IOperationDataDialog;
}

export interface IContext {
  model: IModel;
  setcreateConsoleDialog: (value: ICreateConsoleDialog) => void;
  setOperationDataDialog: (value: IOperationDataDialog) => void;
}

const initDatabaseValue: IModel = {
  createConsoleDialog: false,
  operationData: false,
}

export const DatabaseContext = createContext<IContext>({} as any);

export default function DatabaseContextProvider({ children }: { children: React.ReactNode }) {
  const [model, setStateModel] = useState<IModel>(initDatabaseValue);

  const setcreateConsoleDialog = (createConsoleDialog: ICreateConsoleDialog) => {
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

  return <DatabaseContext.Provider value={{
    model,
    setcreateConsoleDialog,
    setOperationDataDialog
  }}>
    {children}
  </DatabaseContext.Provider>
}