import React from "react";
import {IonContent, IonPage, IonTitle, IonToolbar} from "@ionic/react";
import {UsersList} from "../components/UsersList";

export const Home : React.FC = () => {
    return (
        <IonPage>
            <IonContent>
                <IonToolbar>
                    <UsersList/>
                </IonToolbar>
            </IonContent>
        </IonPage>);
}
