import React from "react";
import {ConnectionStatus} from "@capacitor/network";

export interface AppProps{
    connectionStatus: ConnectionStatus
}

export const AppContext = React.createContext({connectionStatus: {} as ConnectionStatus} as AppProps);
