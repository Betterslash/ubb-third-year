import {useEffect, useState} from "react";
import {ConnectionStatus, Network} from "@capacitor/network";
import {StorageService} from "../services/StorageService";
import {Logger} from "../helpers/logger/Logger";
import jwtDecode from "jwt-decode";

const initialNetworkState : ConnectionStatus = {
    connected : true,
    connectionType : "unknown"
};

export interface AppProps{
    token : string;
    setToken : (token : string) => void;
    getToken : () => string;
    authorities : string[];
    setAuthorities : (auts : string[]) => void;
    log : () => void;
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
    const getAuthorities = (token : string) : string[]  => {
        if(token !== ""){
            // @ts-ignore
            return jwtDecode(token).authorities;
        }
        return [] as string[];
    }
    const copyContext = (token : string) : AppProps => {
        return {
            token: token,
            setToken: setToken,
            getToken: getToken,
            authorities : getAuthorities(token),
            setAuthorities : setAuthorities,
            log : selfLog
        } as AppProps;
    };
    const setToken = (token : string) => {
        setAppContext(copyContext(token));
    }
    const getToken = () : string => {
        return initialContext.token;
    }
    const selfLog = () => {
        Logger.info(JSON.stringify(appContext));
    }
    const setAuthorities = (auts : string[]) => {
        const user = copyContext(appContext.token);
        user.authorities = auts;
        setAppContext(user);
    }
    const initialContext = {
        token: "",
        setToken: setToken,
        getToken: getToken,
        authorities : [] as string[],
        setAuthorities : setAuthorities,
        log : selfLog
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


