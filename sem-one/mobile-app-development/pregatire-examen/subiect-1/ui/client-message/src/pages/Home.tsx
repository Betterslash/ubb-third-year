import React, {useEffect, useState} from "react";
import {Message} from "../buissness-logic/model/Message";
import {MessageService} from "../buissness-logic/service/MessageService";
import {AxiosResponse} from "axios";
import {IonContent, IonItem, IonList, IonPage, useIonModal} from "@ionic/react";
import {MessagesModal} from "../components/modals/MessagesModal";

const initialGroupedMessagesValue = new Map<string, Message[]>();
const initialSortedMessagesValue = new Set<string>();
const initialCurrentSelectedSenderValue = {} as {sender: string, messages: Message[]};

export const Home : React.FC = () => {

    const [groupedMessages, setGroupedMessages] = useState(initialGroupedMessagesValue);

    const [sortedMessages, setSortedMessages] = useState(initialSortedMessagesValue);

    const [currentSelectedSenderData, setCurrentSelectedSenderData] = useState(initialCurrentSelectedSenderValue);

    const handleDismiss = () => {
        dismiss();
    };

    const [present, dismiss] = useIonModal(MessagesModal, {
        onDismiss: handleDismiss,
        data: currentSelectedSenderData
    });

    useEffect(() => {
        MessageService.getAllMessages()
            .then((data: AxiosResponse<Message[]>) => {
                const sortedData = MessageService.sortMessages(data.data);
                const setSortedData = new Set<string>();
                sortedData.forEach(e => setSortedData.add(e.sender));
                const groupedMessages = new Map<string,  Message[]>();
                setSortedMessages(setSortedData);
                sortedData.forEach(e => {
                    let hasValue = groupedMessages.get(e.sender);
                    if(hasValue == null){
                        hasValue = [] as Message[];
                    }
                    hasValue.push(e);
                    groupedMessages.set(e.sender, hasValue);
                });
                setGroupedMessages(groupedMessages);
                console.log(groupedMessages);
            });
    },[currentSelectedSenderData]);

    const getUnreadCount = (sender: string): number => {
        const sentMessages = groupedMessages.get(sender);
        if(sentMessages == null){
            return 0;
        }
        return  sentMessages
                .filter(e => !e.read)
                .length;

    }

    const openModal = (sender: string) => {
        let messages = groupedMessages.get(sender);
        if(messages == null){
            messages = [] as Message[];
        }
        setCurrentSelectedSenderData({sender: sender, messages: messages});
        present();
    }

    return (
        <IonPage>
            <IonContent>
                <IonList>
                    {
                        Array.from(sortedMessages.keys())
                            .map(e => <IonItem key={e} onClick={() => openModal(e)}>User {e} | Unread Count : {getUnreadCount(e)}</IonItem>)
                    }
                </IonList>
            </IonContent>
        </IonPage>
    );
}
