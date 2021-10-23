import React from "react";
import {IonBadge, IonFooter, IonToolbar} from "@ionic/react";
import {useFooterHook} from "../../hooks/FooterHooks";

export interface NetworkBadgeProps{
    color : string;
    text : string;
}

export const Footer : React.FC = () => {
    const networkBadge = useFooterHook();

    return (
        <IonFooter>
            <IonToolbar>
                <IonBadge slot="end" color={networkBadge.color}>{networkBadge.text}</IonBadge>
            </IonToolbar>
        </IonFooter>);
}
