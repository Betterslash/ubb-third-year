import React from "react";
import {IonContent, IonFooter, IonHeader, IonMenu, IonTitle, IonToolbar} from "@ionic/react";
import {MenuOptions} from "./options/MenuOptions";



export const Menu : React.FC = () => {
    return (
            <IonMenu side="end" type="overlay" contentId="first">
                <IonHeader>
                    <IonToolbar color="primary">
                        <IonTitle>Pokedex Menu</IonTitle>
                    </IonToolbar>
                </IonHeader>
                <IonContent>
                    <MenuOptions/>
                </IonContent>
                <IonFooter>
                    <IonToolbar/>
                </IonFooter>
            </IonMenu>
    );
}
