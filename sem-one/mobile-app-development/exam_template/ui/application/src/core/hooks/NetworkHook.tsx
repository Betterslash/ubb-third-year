import {useEffect, useState} from "react";
import {useIonToast} from "@ionic/react";
import {ConnectionStatus, Network} from "@capacitor/network";
import {Logger} from "../util/logger.util";

const initialNetworkState : ConnectionStatus = {
    connected : true,
    connectionType : "unknown"
};

export const useNetowrk = () => {
    const [networkStatus, setNetworkStatus] = useState(initialNetworkState);
    const [present] = useIonToast();
    useEffect(() => {
        Network.addListener("networkStatusChange", handleNetworkStatusChange);
        Network.getStatus().then(handleNetworkStatusChange);
        let canceled = false;
        function handleNetworkStatusChange(status: ConnectionStatus) {
            if(!canceled){
                if(status.connected){
                    present('Network is on .', 700).then(() => {
                        Logger.info('Network is on .');
                    });
                }else{
                    present('Network is off...', 700).then(() => {
                        Logger.warning('Network is off...');
                    });
                }
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
