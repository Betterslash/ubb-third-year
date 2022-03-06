import React, {useContext, useEffect, useState} from "react";
import {
    IonButton, IonButtons, IonCheckbox,
    IonContent,
    IonGrid, IonItem, IonList, IonLoading,
    IonPage,
    IonRow,
    IonSelect, IonSelectOption, IonText,
    IonTitle,
    IonToolbar,
    useIonModal
} from "@ionic/react";
import {AppContext} from "../core/context/AppContext";
import {ModalBody} from "../components/modals/ModalBody";
import {WebSocketService} from "../core/service/web-socket.service";
import {Logger} from "../core/util/logger.util";
import {MessageModel} from "../core/model/message.model";
import {MessagesService} from "../core/service/messages.service";

export const Home: React.FC = () => {

    const {connectionStatus} = useContext(AppContext);
    const [users, setUsersState] = useState([] as string[]);
    const handleDismiss = () => {
        dismiss();
    }
    const [isFetching, setIsFetching] = useState(false);
    const [currentSelection, setCurrentSelection] = useState('');
    const [selectedMessages, setSelectedMessagesState] = useState([] as MessageModel[]);
    const [messages, setMessagesState] = useState([] as MessageModel[]);

    const [presentModal, dismiss] = useIonModal(ModalBody, {
        onDismiss: handleDismiss
    });

    useEffect(() => {
        WebSocketService.getInstance()
            .onopen = function (e) {
            Logger.info(`Received ${JSON.stringify(e)}`);
        }
        WebSocketService.getInstance().onmessage = ev => {
            const data = JSON.parse(ev.data);
            setUsersState(data.users);
            setCurrentSelection(data.users[0] || '');
        };
    }, [messages])

    const changeSelections = (e : MessageModel, checkValue: boolean) => {
        if(checkValue){
            const newSelectedMessagesState = selectedMessages.concat(e);
            setSelectedMessagesState(newSelectedMessagesState);
            console.log(newSelectedMessagesState);
        }else{
            const newSelectedMessagesState = selectedMessages.filter(f => f.id != e.id);
            setSelectedMessagesState(newSelectedMessagesState);
            console.log(newSelectedMessagesState);
        }

    }

    const getIsDeleteDisabled = () => {
        return selectedMessages.length > 0;
    }

    const deleteAllSelected = () => {
        messages.forEach(e => {
            setIsFetching(true);
            MessagesService.deleteOne(e.id)
                .then(() => {
                    const newMessagesList = messages.filter(q => q.id != e.id);
                    setMessagesState(newMessagesList);
                    setSelectedMessagesState(selectedMessages.filter(q => q.id != e.id));
                    setIsFetching(false);
                })
                .catch((err) => {
                    Logger.danger(`Error during deletion ${err}`);
                    setIsFetching(false);
                });
        });


        Logger.info(`Delete succeded `);
    }

    const getSendersMessages = (event: any): void => {
        setCurrentSelection(event.detail.value);
        setIsFetching(true);
        MessagesService.getMessagesForSender(event.detail.value)
            .then(data => {
                setMessagesState(data.data);
                setIsFetching(false);
            })
            .catch(err => {
                Logger.danger(`Couldn't fetch the data because : ${err}`);
                setIsFetching(false);
            });
    }

    return (
        <IonPage>
            <IonContent fullscreen>
                <IonLoading isOpen={isFetching}/>
              <IonToolbar>
                  <IonTitle class="ion-text-center">
                      Home Page
                  </IonTitle>
              </IonToolbar>
                <IonGrid>
                    <IonRow>
                        <IonSelect value={currentSelection} onIonChange={(e) => getSendersMessages(e)}>
                            {
                                users.map((e, index) =>
                                    <IonSelectOption key={index}>{e}</IonSelectOption>)
                            }
                        </IonSelect>
                    </IonRow>
                        <IonToolbar>
                            <IonTitle class="ion-text-center">
                                Messages for user {currentSelection}
                            </IonTitle>
                        </IonToolbar>
                        <IonList>
                            {messages.map(e =>
                                <IonItem key={e.id}>
                                    <IonRow>
                                        Text : {e.text}
                                    </IonRow>
                                        <IonCheckbox value={e} slot="end" onIonChange={(ev) => changeSelections(ev.detail.value, ev.detail.checked)}/>
                                </IonItem>)}
                        </IonList>
                </IonGrid>
                {getIsDeleteDisabled() &&
                    <IonButton expand="block" onClick={deleteAllSelected}>
                        Delete
                    </IonButton>
                }
                {!getIsDeleteDisabled() &&
                        <IonText>
                            Delete
                        </IonText>
                }
            </IonContent>
        </IonPage>
    );
}
