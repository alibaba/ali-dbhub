import React, { memo, useEffect, useState, useRef, createContext } from 'react';
const DatabaseContext = createContext({});

export interface IDatabaseValue {
  createConsoleDialogVisible: boolean
}

const initDatabaseValue = {
  createConsoleDialogVisible: false
}

export default function DatabaseContextProvider({ children }: { children: React.ReactNode }) {
  const [databaseValue, setDatabaseValue] = useState<IDatabaseValue>(initDatabaseValue);
  return <DatabaseContext.Provider value={databaseValue}>
    {children}
  </DatabaseContext.Provider>
}