import React from "react";
import {IonIcon, IonItem, IonList} from "@ionic/react";
import {list, logOut, mail} from "ionicons/icons";
import {StorageService} from "../../../../services/StorageService";
import {useHistory} from "react-router";
import {useUserState} from "../../../../hooks/AppHooks";

export const UserOptions : React.FC = () => {
    const history = useHistory();
    const context = useUserState();
    const logout = async () => {
        await StorageService.deleteToken();
        await context.setToken("");
        history.push("/");
    }
    const navigateToMyPokemons = () =>{
        history.push("/my-pokemons");
    }

    return (
        <IonList>
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
    );
}
