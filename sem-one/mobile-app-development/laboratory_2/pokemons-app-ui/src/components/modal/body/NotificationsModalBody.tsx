import {
    IonButton,
    IonButtons, IonContent,
    IonHeader, IonIcon,
    IonItem,
    IonList,
    IonLoading,
    IonTitle,
    IonToolbar
} from "@ionic/react";
import React, {useEffect, useState} from "react";
import {NotificationModel} from "../../../model/NotificationModel";
import {NotificationService} from "../../../services/NotificationService";
import {closeCircle, remove} from "ionicons/icons";

export const NotificationsModalBody: React.FC<{
    onDismiss: () => void;
    onNotificationsDeletion: (notificationId : string) => void
}> = ({ onDismiss, onNotificationsDeletion }) => {
    const [notifications, setNotifications] = useState([] as NotificationModel[]);
    const [fetching, setFetching] = useState(false);
    useEffect(() => {
        setFetching(true);
        setTimeout(() => {
            NotificationService.getNotifications()
                .then(result => {
                    if(result.value != null){
                        setNotifications(JSON.parse(result.value));
                        setFetching(false);
                    }
                })
        }, 100);
    }, []);
    return(<>
        <IonLoading isOpen={fetching}/>
    <IonHeader>
        <IonToolbar>
            <IonTitle slot="start">Notifications</IonTitle>
            <IonButtons slot="end">
                <IonButton onClick={() => onDismiss()} >
                    <IonIcon  icon={closeCircle}/>
                </IonButton>
            </IonButtons>
        </IonToolbar>
    </IonHeader>
        <IonContent>
                <IonList>
                    {notifications.map((e, index) => <IonItem key={e.id}>{`${index + 1} ${e.action} executed on ${e.body.name}`}
                        <IonButtons slot="end">
                            <IonButton onClick={() => {onNotificationsDeletion(e.id); setNotifications(notifications.filter(z => z.id !== e.id));}}>
                                <IonIcon icon={remove}/>
                            </IonButton>
                        </IonButtons>
                    </IonItem>)}
                </IonList>
        </IonContent>
        </>
    );
}
