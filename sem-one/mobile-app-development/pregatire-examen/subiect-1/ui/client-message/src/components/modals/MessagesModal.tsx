import React, {useEffect, useState} from "react";
import {
    CreateAnimation,
    IonButton,
    IonButtons,
    IonContent,
    IonIcon,
    IonItem,
    IonList,
    IonListHeader, IonText,
    IonTitle,
    IonToolbar
} from "@ionic/react";
import {closeCircle} from "ionicons/icons";
import {Message} from "../../buissness-logic/model/Message";

export const MessagesModal: React.FC<{
    onDismiss: () => void;
    data: {sender: string, messages: Message[]};
}> = ({onDismiss, data}) => {

    useEffect(() => {
        console.log(data);
    }, []);

    return (
        <IonContent>
            <IonToolbar>
                <IonButtons slot={"end"}>
                    <IonButton onClick={() => onDismiss()} >
                        <IonIcon icon={closeCircle}/>
                    </IonButton>
                </IonButtons>
            </IonToolbar>
            <IonList>
                <IonListHeader>
                    <IonToolbar>
                        <IonTitle>Received Messages From {data.sender}</IonTitle>
                    </IonToolbar>
                </IonListHeader>
                {data.messages.sort((a, b) => a.created-b.created)
                    .map(e => <IonItem key={e.id}>
                        <CreateAnimation
                            duration={1000}
                            iterations={1}
                            keyframes={[
                                    { offset: 0, transform: 'scale(1.5)' },
                                    { offset: 1, transform: 'scale(1)' }
                                ]}
                        play={true}>
                            <div className="message-text">{e.text}</div>
                        </CreateAnimation></IonItem>)}
            </IonList>
        </IonContent>
    );
}
