import React, { memo, useEffect, useState, useRef, createContext } from 'react';


export type ICreateConsoleDialog = false | {
  dataSourceId: number;
  databaseName: string;
}

export interface IModel {
  createConsoleDialog: ICreateConsoleDialog
}

export interface IContext {
  model: IModel;
  setcreateConsoleDialog: (value: ICreateConsoleDialog) => void;
}

const initDatabaseValue: IModel = {
  createConsoleDialog: false,
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

  return <DatabaseContext.Provider value={{
    model,
    setcreateConsoleDialog
  }}>
    {children}
  </DatabaseContext.Provider>
}