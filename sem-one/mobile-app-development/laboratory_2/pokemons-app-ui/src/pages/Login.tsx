import React, {useState} from "react";
import {
    IonButton,
    IonCard,
    IonCardContent,
    IonContent,
    IonInput,
    IonLabel,
    IonPage
} from "@ionic/react";
import {AuthenticationService, UserModel} from "../services/AuthService";
import {StorageService} from "../services/StorageService";
import {useLogin} from "../hooks/LoginHook";
import {AxiosResponse} from "axios";
import {Logger} from "../helpers/logger/Logger";
import {useHistory} from "react-router";
import {Header} from "../components/layout/Header";
import {Footer} from "../components/layout/Footer";
import {useUserState} from "../hooks/AppHooks";
import jwtDecode from "jwt-decode";

export const Login : React.FC = () => {
    const getAuthorities = (token : string) : string[]  => {
        if(token !== ""){
            console.log(token);
            // @ts-ignore
            return jwtDecode(token).authorities;
        }
        return [] as string[];
    }
    const loginState = useLogin();
    const [loginFailureLabel, setLabelState] = useState("");
    const history = useHistory();
    const context = useUserState();
    const onLoginSuccess = async (response : AxiosResponse<string>) => {
        await StorageService.setToken(response.data);
        await context.setToken(response.data);
        await context.setAuthorities(getAuthorities(response.data));
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
            <Header/>
                <IonContent>
                    <IonCard>
                        <IonCardContent>
                            <form onSubmit={doSubmit}>
                                <IonInput placeholder="Username" type="text" onIonChange={(e : Event) => loginState.dispatcher({item : e, type : 'USERNAME'})}/>
                                <IonInput placeholder="Password" type="password" onIonChange={(e : Event) => loginState.dispatcher({item : e, type : 'PASSWORD'})}/>
                                <IonButton type="submit">Login</IonButton>
                            </form>
                            <IonLabel>{loginFailureLabel}</IonLabel>
                        </IonCardContent>
                    </IonCard>
                </IonContent>
            <Footer/>
        </IonPage>
    );
}
