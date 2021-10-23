import React, {useEffect, useState} from "react";
import {IonBadge, IonFooter, IonToolbar} from "@ionic/react";
import {useNetowrk} from "../../hooks/AppHooks";
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
