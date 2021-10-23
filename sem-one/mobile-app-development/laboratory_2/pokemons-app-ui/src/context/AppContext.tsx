import React from "react";
import {ConnectionStatus} from "@capacitor/network";

export interface AppProps {
    token : string;
    networkStatus : ConnectionStatus;
    setContextToken: (token : string) => void;
    setContextNetworkStatus : (status : ConnectionStatus) => void;
    log : () => void
}

export const AppContext = React.createContext<AppProps>({} as AppProps);
