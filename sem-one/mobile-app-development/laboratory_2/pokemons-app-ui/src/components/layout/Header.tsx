import React, {useEffect, useState} from "react";
import {
    IonButton,
    IonButtons,
    IonHeader, IonIcon, IonMenuButton,
    IonTitle,
    IonToolbar, useIonModal
} from "@ionic/react";
import {Menu} from "./menu/Menu";
import {home, notifications} from "ionicons/icons";
import {useHistory} from "react-router";
import {NotificationsModalBody} from "../modal/body/NotificationsModalBody";
import {NotificationService} from "../../services/NotificationService";
import {NotificationModel} from "../../model/NotificationModel";

export const Header : React.FC = () =>{
    const history = useHistory();
    const onNotificationDeletion = (notificationId : string) => {
        NotificationService.deleteNotification(notificationId)
            .then(() => {
                stateChange(notificationsState.filter(e => e.id !== notificationId));
            })
    }
    const navigateHome = () => {
        history.push("/");
    }

    const handleDismiss = () => {
        dismiss();
    };

    const [present, dismiss] = useIonModal(NotificationsModalBody, {
        onDismiss: handleDismiss,
        onNotificationsDeletion : onNotificationDeletion
    });
    const [notificationsState, stateChange] = useState([] as NotificationModel[]);
    useEffect(() => {
        NotificationService.getNotifications()
            .then(results => {
                if(results.value) {
                    stateChange(JSON.parse(results.value));
                }
            })
    },[]);
    return (
        <>
            <IonHeader>
                <IonToolbar>
                    <IonButtons slot="start">
                        <IonButton onClick={navigateHome}>
                            <IonIcon icon={home}/>
                        </IonButton>

                    </IonButtons>
                    <IonTitle>Header</IonTitle>
                    <IonButtons slot="end">
                        {/*<IonBadge>{notificationsState.length}</IonBadge>*/}
                        <IonButton onClick={() => {present()}}>
                            <IonIcon icon={notifications}/>
                        </IonButton>
                        <IonMenuButton id="first"/>
                    </IonButtons>
                </IonToolbar>
            </IonHeader>
            <Menu/>
        </>);
}
