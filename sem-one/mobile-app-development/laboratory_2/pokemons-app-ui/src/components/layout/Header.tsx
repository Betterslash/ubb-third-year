import React from "react";
import {
    IonButtons,
    IonHeader,
    IonMenuButton,
    IonTitle,
    IonToolbar
} from "@ionic/react";
import {Menu} from "./menu/Menu";

export const Header : React.FC = () =>{
    return (
        <>
            <Menu/>
            <IonHeader>
                <IonToolbar>
                    <IonButtons slot="end">
                        <IonMenuButton/>
                    </IonButtons>
                    <IonTitle>Header</IonTitle>
                </IonToolbar>
            </IonHeader>
        </>);
}
