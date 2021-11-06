import React, {useState} from "react";
import {
    createAnimation,
    IonBadge, IonButton,
    IonButtons,
    IonCol,
    IonFooter,
    IonGrid,
    IonIcon,
    IonLabel, IonModal,
    IonRow,
    IonToolbar
} from "@ionic/react";
import {useFooterHook} from "../../hooks/FooterHooks";
import {arrowUp, logoFacebook, logoGithub, logoInstagram} from "ionicons/icons";
import {GuestHome} from "../widgets/GuestHome";

export interface NetworkBadgeProps{
    color : string;
    text : string;
}

export const Footer : React.FC = () => {
    const networkBadge = useFooterHook();

    const [showModal, setShowModal] = useState(false);
    const enterAnimation = (baseEl: any) => {
        const backdropAnimation = createAnimation()
            .addElement(baseEl.querySelector('ion-backdrop')!)
            .fromTo('opacity', '0.01', 'var(--backdrop-opacity)');

        const wrapperAnimation = createAnimation()
            .addElement(baseEl.querySelector('.modal-wrapper')!)
            .keyframes([
                { offset: 0, opacity: '0', transform: 'scale(0)' },
                { offset: 1, opacity: '0.99', transform: 'scale(1)' }
            ]);

        return createAnimation()
            .addElement(baseEl)
            .easing('ease-out')
            .duration(500)
            .addAnimation([backdropAnimation, wrapperAnimation]);
    }

    const leaveAnimation = (baseEl: any) => {
        return enterAnimation(baseEl).direction('reverse');
    }
    return (
        <IonFooter>
            <IonToolbar>
                <IonButtons slot="start">
                    <IonGrid color={'rgba(0,0,255,0)'}>
                        <IonRow>
                            <IonCol>
                                <IonBadge  color={networkBadge.color}>{networkBadge.text}
                                </IonBadge>
                            </IonCol>
                            <IonCol>
                                <IonBadge color={"dark"}>
                                    <IonIcon icon={logoGithub}/>
                                    <IonLabel>
                                        Betterslash
                                    </IonLabel>
                                </IonBadge>
                            </IonCol>
                            <IonCol>
                                <IonBadge color={"primary"}>
                                    <IonIcon icon={logoFacebook}/>
                                    <IonLabel>
                                        Andrei Dori
                                    </IonLabel>
                                </IonBadge>
                            </IonCol>
                            <IonCol>
                                <IonBadge color={"danger"}>
                                    <IonIcon icon={logoInstagram}/>
                                    <IonLabel>
                                        @doreataandrei
                                    </IonLabel>
                                </IonBadge>
                            </IonCol>
                        </IonRow>
                        <IonCol>
                            <IonButtons >
                                <IonButton onClick={() => setShowModal(true)}>
                                    <IonLabel>See patches</IonLabel><IonIcon icon={arrowUp}/>
                                </IonButton>
                            </IonButtons>
                        </IonCol>
                    </IonGrid>
                </IonButtons>
            </IonToolbar>
            <IonModal isOpen={showModal} enterAnimation={enterAnimation} leaveAnimation={leaveAnimation}>
                <GuestHome/>
                <IonButton onClick={() => setShowModal(false)}>Close Modal</IonButton>
            </IonModal>
        </IonFooter>);
}
