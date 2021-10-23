import React from "react";
import {IonContent, IonHeader, IonMenu, IonTitle, IonToolbar} from "@ionic/react";
import {MenuOptions} from "./options/MenuOptions";



export const Menu : React.FC = () => {
    return (
        <IonContent id="first">
            <IonMenu side="end" type="overlay" contentId="first">
                <IonHeader>
                    <IonToolbar color="primary">
                        <IonTitle>Start Menu</IonTitle>
                    </IonToolbar>
                </IonHeader>
                <IonContent>
                    <MenuOptions/>
                </IonContent>
            </IonMenu>
        </IonContent>
    );
}
