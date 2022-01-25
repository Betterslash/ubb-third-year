import React, {useEffect, useRef, useState} from "react";
import {
    CreateAnimation,
    IonButton,
    IonButtons,
    IonContent,
    IonIcon, IonInfiniteScroll, IonInfiniteScrollContent,
    IonItem,
    IonList, IonTitle,
    IonToolbar
} from "@ionic/react";
import {closeCircle} from "ionicons/icons";
import {Message} from "../../core/model/message.model";
import {MessageService} from "../../core/service/message.service";

export const MessagesModal: React.FC<{
    onDismiss: () => void;
    messages : Message[]
}> = ({onDismiss, messages}) => {

    const divRef = useRef(null);

    const [isInfiniteDisabled, setInfiniteScrollDisabled] = useState(false);

    const [data, setData] = useState(messages.slice(0, 5));

    const [currentIndex, setCurrentIndex] = useState(6);

    const loadData = (event: any) => {
        if(currentIndex + 5 < messages.length){
            const newMessages = data.concat(messages.slice(currentIndex + 1, currentIndex + 5));
            newMessages.forEach(e => MessageService.readMessage(e));
            setData(newMessages);
            setCurrentIndex(currentIndex + 5);
            console.log(currentIndex);
        }else{
            const newMessages = messages.slice(currentIndex + 1, messages.length);
            newMessages.forEach(e => MessageService.readMessage(e));
            setData(data.concat(newMessages));
            setCurrentIndex(messages.length);
            setInfiniteScrollDisabled(true);
            console.log(currentIndex);
        }
        event.target.complete();
    }

    return (<div>
        <IonToolbar>
            <IonButtons slot="start">
                <IonButton onClick={() => onDismiss()}>
                    <IonIcon icon={closeCircle}/>
                </IonButton>
            </IonButtons>
            <IonTitle>{messages[0].sender}</IonTitle>
        </IonToolbar>
        <IonContent fullscreen>
            <IonList>
                {data.map(e => <IonItem key={e.id}>
                    <CreateAnimation
                        ref={divRef}
                        duration={2000}
                        fromTo={[
                            { property: 'color', fromValue: 'black', toValue: 'white'}
                        ]}
                        play={true}
                    >
                        <div ref={divRef}>
                            <strong>{e.text}</strong>
                        </div>
                    </CreateAnimation>
                </IonItem>)}
            </IonList>
            <IonInfiniteScroll
                onIonInfinite={loadData}
                threshold="100px"
                disabled={isInfiniteDisabled}
            >
                <IonInfiniteScrollContent
    loadingSpinner="bubbles"
    loadingText="Loading more data..."
    />
            </IonInfiniteScroll>
        </IonContent>
    </div>);
}
