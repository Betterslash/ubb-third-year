import React from "react";

export interface AppProps{
    connectionStatus: boolean
}

export const AppContext = React.createContext({connectionStatus: true} as AppProps);
