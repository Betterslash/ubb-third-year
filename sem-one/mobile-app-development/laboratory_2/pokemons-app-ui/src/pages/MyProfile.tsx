import React, {useEffect, useState} from "react";
import {
    IonButton,
    IonButtons,
    IonCol,
    IonContent,
    IonGrid,
    IonIcon, IonImg,
    IonPage,
    IonRow,
    IonTitle,
    IonToolbar
} from "@ionic/react";
import {Header} from "../components/layout/Header";
import {Footer} from "../components/layout/Footer";
import {UserTokenService} from "../services/UserTokenService";
import jwtDecode from "jwt-decode";
import {camera} from "ionicons/icons";
import {usePhotoHook} from "../hooks/AppHooks";

export interface MyProfileProps {
    sub : string;
    authorities: string[];
}

const userModel : MyProfileProps = {
    sub : '',
    authorities : [] as string[]
} as MyProfileProps;

export const MyProfile : React.FC = () => {
    const cameraPhoto = usePhotoHook();
    const [user, setUser] = useState(userModel);
    useEffect(() => {
        UserTokenService.getToken()
            .then(result => {
                if(result.value){
                    const decodedToken = jwtDecode<MyProfileProps>(result.value);
                    setUser(decodedToken);
                }
            });
    }, []);

    return(
        <IonPage>
            <Header/>
                <IonContent>
                    <IonToolbar>
                        <IonTitle>{user.sub}</IonTitle>
                    </IonToolbar>
                    <IonGrid>
                        <IonRow>
                            {cameraPhoto.photos.map((photo, index) => (
                                <IonCol size="6" key={index}>
                                    <IonImg src={photo.webviewPath} />
                                </IonCol>
                            ))}
                        </IonRow>
                    </IonGrid>
                    <IonButtons>
                        <IonButton onClick={cameraPhoto.uploadPhoto}>
                            <IonIcon icon={camera}/>
                        </IonButton>
                    </IonButtons>
                </IonContent>
            <Footer/>
        </IonPage>
    );
}
