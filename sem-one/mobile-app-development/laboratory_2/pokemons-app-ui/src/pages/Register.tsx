import React, {useEffect, useReducer, useState} from "react";
import {
    IonButton,
    IonCard,
    IonCardContent,
    IonCardHeader,
    IonContent,
    IonDatetime,
    IonInput, IonLabel,
    IonPage,
    IonTitle
} from "@ionic/react";
import {Header} from "../components/layout/Header";
import {Footer} from "../components/layout/Footer";
import {AuthenticationService, UserModel} from "../services/AuthService";
import {LocalDateService} from "../services/LocalDateService";
import {useHistory} from "react-router";
import {UserService} from "../services/UserService";
import {Logger} from "../helpers/logger/Logger";

const initialState : UserModel = {
    username : '',
    password : '',
    email : '',
    birthday : ''
};

export const Register : React.FC = () => {
    const history = useHistory();
    const copyUserModel = (currentUserModel : UserModel) : UserModel=> {
        return {
            username : currentUserModel.username,
            password : currentUserModel.password,
            email : currentUserModel.email,
            birthday : currentUserModel.birthday
        } as UserModel;
    }
    const [passwordConfirmationState,  changePasswordConfirmationState] = useState(initialState.password);
    const [userLabel, setUserLabel] = useState('');
    const reducer = (state:any,action:any) : UserModel => {
        const newCurrentUserModel = copyUserModel(state);
        switch (action.type){
            case 'USERNAME' : { newCurrentUserModel.username = action.item.detail.value; return newCurrentUserModel}
            case 'PASSWORD' : { newCurrentUserModel.password = action.item.detail.value; return newCurrentUserModel}
            case 'EMAIL' : { newCurrentUserModel.email = action.item.detail.value; return newCurrentUserModel}
            case 'BIRTHDAY' : { newCurrentUserModel.birthday = LocalDateService.parseToJavaLocalDate(action.item.detail.value); return newCurrentUserModel}
            default:{return newCurrentUserModel;}
        }
    }
    const [registerState, dispatcher] = useReducer(reducer, initialState);
    const register = () => {
        Logger.info(`Attempting ${register.name} -> ${JSON.stringify(registerState)}` )
        AuthenticationService.register(registerState)
            .then(result => {
                Logger.info(`Finished ${register.name} -> ${JSON.stringify(result)}`);
                history.push('/login');
                },
                (err) => {Logger.danger(`${err} : occured during ${register.name}...`)})
    }
    const onSubmit = (e : any) => {
        e.preventDefault();
        setUserLabel('');
        if(registerState.birthday != null && registerState.birthday !== ''){
            if(registerState.password !== passwordConfirmationState){
                setUserLabel('Please check that the passwords are matching!!');
            }else{
                register();
            }
        }else{
            setUserLabel('Please select a date of birth!!');
        }
    }

    return (<IonPage>
            <Header/>
            <IonContent>
                <IonCard>
                    <IonCardHeader>
                        <IonTitle>Register</IonTitle>
                    </IonCardHeader>
                    <IonCardContent>
                        <form onSubmit={onSubmit}>
                            <IonInput type={"text"} placeholder={"Username"} onIonChange={(e:any) => dispatcher({item : e, type: 'USERNAME'})} required/>
                            <IonInput type={"password"} placeholder={"Password"} onIonChange={(e:any) => dispatcher({item : e, type: 'PASSWORD'})} required/>
                            <IonInput type={"password"} placeholder={"Confirm Password"} onIonChange={(e:any) => changePasswordConfirmationState(e.detail.value)} required/>
                            <IonInput type={"email"} placeholder={"Email"} onIonChange={(e:any) => dispatcher({item : e, type: 'EMAIL'})}/>
                            <IonLabel>
                                Birthday :
                                <IonDatetime
                                             displayFormat="YYYY-MM-DD"
                                             min="2003-03-14"
                                             max={LocalDateService.parseToJavaLocalDate(new Date().toISOString())} onIonChange={(e : any) => dispatcher({item : e, type: 'BIRTHDAY'})}/>
                            </IonLabel>
                            <IonButton expand={"block"} type={"submit"}>
                                Register
                            </IonButton>
                        </form>
                        <br/>
                        <IonLabel color={'danger'}>
                            {userLabel}
                        </IonLabel>
                    </IonCardContent>
                </IonCard>
            </IonContent>
            <Footer/>
        </IonPage>);
}
