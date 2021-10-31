import React from "react";
import {IonBadge, IonButtons, IonCol, IonFooter, IonGrid, IonIcon, IonLabel, IonRow, IonToolbar} from "@ionic/react";
import {useFooterHook} from "../../hooks/FooterHooks";
import {logoFacebook, logoGithub, logoInstagram} from "ionicons/icons";

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
                    <IonGrid color={'rgba(0,0,255,0)'}>
                        <IonRow>
                            <IonCol>
                                <IonBadge  color={networkBadge.color}>{networkBadge.text}
                                </IonBadge>
                            </IonCol>
                            <IonCol>
                                <IonBadge color={"dark"}>
                                    <IonIcon icon={logoGithub}/>
                                    <IonLabel>
                                        Betterslash
                                    </IonLabel>
                                </IonBadge>
                            </IonCol>
                            <IonCol>
                                <IonBadge color={"primary"}>
                                    <IonIcon icon={logoFacebook}/>
                                    <IonLabel>
                                        Andrei Dori
                                    </IonLabel>
                                </IonBadge>
                            </IonCol>
                            <IonCol>
                                <IonBadge color={"danger"}>
                                    <IonIcon icon={logoInstagram}/>
                                    <IonLabel>
                                        Andrei Dori
                                    </IonLabel>
                                </IonBadge>
                            </IonCol>
                        </IonRow>
                    </IonGrid>
                </IonButtons>
            </IonToolbar>
        </IonFooter>);
}
