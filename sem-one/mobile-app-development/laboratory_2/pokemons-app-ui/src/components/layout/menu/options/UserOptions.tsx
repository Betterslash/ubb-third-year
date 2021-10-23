import React from "react";
import {IonItem, IonList} from "@ionic/react";

export const UserOptions : React.FC = () => {
    return (
        <IonList>
            <IonItem>
                My Pokemons
            </IonItem>
            <IonItem>
                Logout
            </IonItem>
            <IonItem>
                Contact
            </IonItem>
        </IonList>
    );
}
