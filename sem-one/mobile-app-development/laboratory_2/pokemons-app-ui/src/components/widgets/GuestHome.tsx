import React from "react";
import {
    IonCard,
    IonCardContent,
    IonCardHeader,
    IonContent,
    IonItem,
    IonList,
    IonListHeader,
    IonText,
    IonTitle
} from "@ionic/react";

export const GuestHome : React.FC = () => {
    return (
        <IonContent>
            <IonCard>
                <IonCardHeader>
                    <IonTitle>Welcome to Pokedex</IonTitle>
                </IonCardHeader>
                <IonCardContent>
                    <IonList>
                        <IonListHeader>
                            <IonTitle>Features : </IonTitle>
                        </IonListHeader>
                        <IonItem>
                            <IonText>AUTH Functionalities</IonText>
                        </IonItem>
                        <IonItem>
                            <IonText>ADMIN Functionalities</IonText>
                        </IonItem>
                        <IonItem>
                            <IonText>PARTICIPANT Functionalities</IonText>
                        </IonItem>
                    </IonList>
                </IonCardContent>
            </IonCard>
            <IonCard>
                <IonCardHeader>
                    <IonTitle>Welcome to Pokedex</IonTitle>
                </IonCardHeader>
                <IonCardContent>
                    <p>Hello there future trainer, we devised this app to help you in your wonderful journey!!</p>
                </IonCardContent>
            </IonCard>
            <IonCard>
                <IonCardHeader>
                    <IonTitle>What's new</IonTitle>
                </IonCardHeader>
                <IonCardContent>
                    <p>We are in the earliest stages of development for this application, please be patient as more features will be implemented soon!!</p>
                </IonCardContent>
            </IonCard>
            <IonCard>
                <IonCardHeader>
                    <IonTitle>Current version : 0.1.3</IonTitle>
                </IonCardHeader>
                <IonCardContent>
                    <p>Version : 0.1.3 02.10.2021</p>
                    <p>Version : 0.1.2 01.10.2021</p>
                    <p>Version : 0.1.1 30.09.2021</p>
                    <p>Version : 0.1.0 29.09.2021</p>
                </IonCardContent>
            </IonCard>
        </IonContent>
    );
}
