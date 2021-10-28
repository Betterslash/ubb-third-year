import React from "react";
import {IonCardContent, IonIcon, IonImg, IonItem, IonList} from "@ionic/react";
import {list, logOut, mail, person} from "ionicons/icons";
import {UserTokenService} from "../../../../services/UserTokenService";
import {useHistory} from "react-router";
import {useUserState} from "../../../../hooks/AppHooks";

export const UserOptions : React.FC = () => {
    const history = useHistory();
    const context = useUserState();
    const logout = async () => {
        await UserTokenService.deleteToken();
        await context.setToken("");
        history.push("/");
    }
    const navigateToMyPokemons = () =>{
        history.push("/my-pokemons");
    }
    const navigateToMyProfile = () =>{
        history.push("/my-profile");
    }
    return (
        <>
        <IonList>
            <br/>
            <IonItem onClick={navigateToMyProfile}>
                <IonIcon slot={"start"} icon={person}/> My Profile
            </IonItem>

            <IonItem onClick={navigateToMyPokemons}>
               <IonIcon slot="start" icon={list}/> My Pokemons
            </IonItem>

            <IonItem onClick={logout}>
                <IonIcon slot="start" icon={logOut}/>Logout
            </IonItem>

            <IonItem>
                <IonIcon slot="start" icon={mail}/>Contact
            </IonItem>
        </IonList>
            <br/><br/>
            <IonCardContent>
                <IonImg src={"../assets/charizard.png"}/>
            </IonCardContent>
        </>
    );
}
