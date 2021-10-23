import React from "react";
import {IonContent, IonItem, IonList} from "@ionic/react";

export const GuestOptions : React.FC = () => {
    return (
        <IonContent>
            <IonList>
                <IonItem>
                    LogIn
                </IonItem>
                <IonItem>
                    Register
                </IonItem>
                <IonItem>
                    Contact
                </IonItem>
            </IonList>
        </IonContent>
    );
}
