import React from "react";
import {IonButton, IonButtons, IonIcon, IonTitle, IonToolbar} from "@ionic/react";
import {closeOutline} from "ionicons/icons";

export const ModalBody : React.FC<{onDismiss : () => void}> = ({onDismiss}) => {
    return (
        <div>
            <IonToolbar>
                <IonTitle>
                    
                </IonTitle>
                <IonButtons slot="end">
                    <IonButton onClick={onDismiss}>
                        <IonIcon icon={closeOutline}/>
                    </IonButton>
                </IonButtons>
            </IonToolbar>
        </div>
    );
}
