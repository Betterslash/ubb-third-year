import {useEffect, useState} from "react";
import {useIonToast} from "@ionic/react";
import {Capacitor} from "@capacitor/core";

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
                    LocalRepositoryService.synchronize().then((results) => {
                            if(results && results.data.length > 0){
                                present('Synchronized data ...', 2000);
                                Logger.info('Synchronized data ...');
                            }
                        },
                        () => {Logger.warning("Couldn't synchronize data...");});
                }else{
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
