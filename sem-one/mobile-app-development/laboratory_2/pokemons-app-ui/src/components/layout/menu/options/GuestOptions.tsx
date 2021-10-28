import React from "react";
import {IonContent, IonIcon, IonItem, IonList} from "@ionic/react";
import {enter, logIn, mail} from "ionicons/icons";
import {useHistory} from "react-router";

export const GuestOptions : React.FC = () => {
    const history = useHistory();
    const navigateToLogin = () => {
        history.push("/login");
    }
    const navigateToRegister =()=>{
        history.push("/register");
    }
    return (
        <IonContent>
            <IonList>
                <IonItem onClick={navigateToLogin}>
                    <IonIcon slot="start" icon={logIn}/>Login
                </IonItem>
                <IonItem onClick={navigateToRegister}>
                    <IonIcon slot="start" icon={enter} />Register
                </IonItem>
                <IonItem>
                    <IonIcon slot="start" icon={mail}/>Contact
                </IonItem>
            </IonList>
        </IonContent>
    );
}
