import {useEffect, useState} from "react";
import {ConnectionStatus, Network} from "@capacitor/network";
import {StorageService} from "../services/StorageService";
import jwtDecode from "jwt-decode";
import {Logger} from "../helpers/logger/Logger";

const initialNetworkState : ConnectionStatus = {
    connected : false,
    connectionType : "unknown"
};

export interface AppProps{
    token : string;
    setToken : (token : string) => void;
    getToken : () => string;
    getAuthorities : (token : string) => void
}


export const useNetowrk = () => {

    const [networkStatus, setNetworkStatus] = useState(initialNetworkState);

    useEffect(() => {
        Network.addListener("networkStatusChange", handleNetworkStatusChange);
        Network.getStatus().then(handleNetworkStatusChange);
        let canceled = false;
        function handleNetworkStatusChange(status: ConnectionStatus) {
            if(!canceled){
                setNetworkStatus(status);
            }
        }
        return () => {
            canceled = true;
            Network.removeAllListeners().then();
        };
    }, []);

    return networkStatus;
}

export const useUserState = () => {
    const copyContext = (token : string) : AppProps => {
        return {
            token: token,
            setToken: setToken,
            getToken: getToken
        } as AppProps;
    };

    const setToken = (token : string) => {
        setAppContext(copyContext(token));
    }

    const getToken = () : string => {
        return initialContext.token;
    }

    const getAuthorities = ()  => {
        const authorities = jwtDecode(appContext.token);
        Logger.info(JSON.stringify(authorities));
    }

    const initialContext = {
        token: "",
        setToken: setToken,
        getToken: getToken,
        getAuthorities : getAuthorities
    } as unknown as AppProps;

    const [appContext, setAppContext] = useState(initialContext);

    useEffect(() => {
        StorageService.getToken().then(result => {
            if(result.value !== null){
                setAppContext(copyContext(result.value));
            }
        });
    },[]);



    return appContext;
}

