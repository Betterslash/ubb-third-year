import React from "react";
import {
    IonButton,
    IonButtons,
    IonHeader, IonIcon,
    IonMenuButton,
    IonTitle,
    IonToolbar
} from "@ionic/react";
import {Menu} from "./menu/Menu";
import {home, notifications} from "ionicons/icons";
import {useHistory} from "react-router";

export const Header : React.FC = () =>{
    const history = useHistory();
    const navigateHome = () => {
        history.push("/");
    }
    return (
        <>
            <IonHeader>
                <IonToolbar>
                    <IonButtons slot="start">
                        <IonButton onClick={navigateHome}>
                            <IonIcon icon={home}/>
                        </IonButton>

                    </IonButtons>
                    <IonTitle>Header</IonTitle>
                    <IonButtons slot="end">
                        <IonButton onClick={navigateHome}>
                            <IonIcon icon={notifications}/>
                        </IonButton>
                        <IonMenuButton id="first"/>
                    </IonButtons>
                </IonToolbar>
            </IonHeader>
            <Menu/>
        </>);
}
