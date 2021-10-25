import React from "react";
import {IonBadge, IonButtons, IonFooter, IonItem, IonToolbar} from "@ionic/react";
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
                <IonButtons slot="start">
                    <IonItem>
                        <IonBadge  color={networkBadge.color}>{networkBadge.text}</IonBadge>
                    </IonItem>
                </IonButtons>
            </IonToolbar>
        </IonFooter>);
}
