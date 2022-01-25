import React, {useEffect, useState} from "react";
import {IonItem, IonList, useIonModal} from "@ionic/react";
import {Message} from "../core/model/message.model";
import {MessageService} from "../core/service/message.service";
import {MessagesMapper} from "../core/mapper/messages.mapper";
import {MessagesModal} from "./modals/MessagesModal";

const initialMessagesList : Map<string, Message[]> = new Map<string, Message[]>();

export const UsersList: React.FC = () => {

    const [modalData, setModalData] = useState([] as Message[]);

    const handleDismiss = () => {
        dismiss();
    };
    const [messages, setMessagesList] = useState(initialMessagesList);
    const [sortedUsers, setSortedUsers] = useState([] as string[]);
    const [present, dismiss] = useIonModal(MessagesModal, {
        onDismiss: handleDismiss,
        messages: modalData
    });
    const getUnreadCount = (sender: string): number => {
        return messages.get(sender)?.filter(e => !e.read)?.length || 0;
    }

    useEffect(() => {
        MessageService.getMessages()
            .then((data) => {
                const messages = MessagesMapper.mapMessagesList(data.data);
                setMessagesList(messages);
                setSortedUsers(MessageService.filterMessages(messages));
            });
    }, []);

    const handlePresent = (e: string) => {
        setModalData(messages.get(e) || [] as Message[]);
        present();
    }

    return (
            <IonList>
                {sortedUsers.map(e => <IonItem onClick={() => handlePresent(e)} key = {e}>{e} with unread count {getUnreadCount(e)}</IonItem>)}
            </IonList>
    );
}
