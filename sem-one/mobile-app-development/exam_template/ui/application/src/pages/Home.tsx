import React, {useContext, useEffect, useState} from "react";
import {IonButton, IonContent, IonGrid, IonPage, IonRow, IonTitle, IonToolbar, useIonModal} from "@ionic/react";
import {AppContext} from "../core/context/AppContext";
import {ModalBody} from "../components/modals/ModalBody";
import {WebSocketService} from "../core/service/web-socket.service";
import {Logger} from "../core/util/logger.util";

export const Home: React.FC = () => {

    const {connectionStatus} = useContext(AppContext);

    const handleDismiss = () => {
        dismiss();
    }

    const [presentModal, dismiss] = useIonModal(ModalBody, {
        onDismiss: handleDismiss
    });

    useEffect(() => {
        Logger.info(JSON.stringify(connectionStatus));
    }, [connectionStatus])

    return (
        <IonPage>
            <IonContent fullscreen>
              <IonToolbar>
                  <IonTitle class="ion-text-center">
                      Home Page
                  </IonTitle>
              </IonToolbar>
                <IonGrid>

                </IonGrid>
            </IonContent>
        </IonPage>
    );
}
