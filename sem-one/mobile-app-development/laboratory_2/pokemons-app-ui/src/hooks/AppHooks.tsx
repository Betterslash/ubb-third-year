import {useEffect, useState} from "react";
import {ConnectionStatus, Network} from "@capacitor/network";
const initialNetworkState : ConnectionStatus = {
    connected : false,
    connectionType : "unknown"
};

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
