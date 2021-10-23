import {useEffect, useState} from "react";
import {NetworkBadgeProps} from "../components/layout/Footer";
import {useNetowrk} from "./AppHooks";

export const useFooterHook = () => {
    const networkState = useNetowrk();
    const [networkBadge, setNetworkBadge] = useState({} as NetworkBadgeProps);
    const setNetworkStateBadge = () => {
        if(networkState.connected){
            setNetworkBadge({color : "success", text : "Online"});
        }else{
            setNetworkBadge({color : "danger", text : "Offline"});
        }
    }
    useEffect(setNetworkStateBadge, [networkState]);
    return networkBadge;
}
