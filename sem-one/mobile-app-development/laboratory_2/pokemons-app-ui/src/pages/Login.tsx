import React, {useContext, useState} from "react";
import {
    IonButton,
    IonCard,
    IonCardContent,
    IonCardHeader,
    IonContent,
    IonInput,
    IonLabel,
    IonPage,
    IonTitle
} from "@ionic/react";
import {AuthenticationService, UserModel} from "../services/AuthService";
import {StorageService} from "../services/StorageService";
import {useLogin} from "../hooks/LoginHook";
import {AppContext} from "../context/AppContext";
import {AxiosResponse} from "axios";
import {Logger} from "../helpers/logger/Logger";
import {useHistory} from "react-router";

export const Login : React.FC = () => {
    const loginState = useLogin();
    const appProps = useContext(AppContext);
    const [loginFailureLabel, setLabelState] = useState("");
    const history = useHistory();

    const onLoginSuccess = async (response : AxiosResponse<string>) => {
        await StorageService.setToken(response.data);
        await appProps.setContextToken(response.data);
        appProps.log();
        history.push("/");
    }
    const onLoginFailure = (error : string) => {
        setLabelState("Username or Password are incorrect !!");
        Logger.warning(error);
    };
    const doSubmit = async (e: any) => {
        e.preventDefault();
        await login(loginState.user);
    }
    const login = async (user : UserModel) => {
        AuthenticationService.login(user)
            .then((response) => onLoginSuccess(response),
                  (error) => onLoginFailure(error));
    }

    return(
        <IonPage>
            <IonContent>
                <IonCard>
                    <IonCardHeader>
                        <IonTitle>
                            Hello trainer !!
                        </IonTitle>
                    </IonCardHeader>
                    <IonCardContent>
                        <form onSubmit={doSubmit}>
                            <IonInput type="text" onIonChange={(e : Event) => loginState.dispatcher({item : e, type : 'USERNAME'})}/>
                            <IonInput type="password" onIonChange={(e : Event) => loginState.dispatcher({item : e, type : 'PASSWORD'})}/>
                            <IonButton type="submit">Login</IonButton>
                        </form>
                        <IonLabel>{loginFailureLabel}</IonLabel>
                    </IonCardContent>
                </IonCard>
            </IonContent>
        </IonPage>
    );
}
