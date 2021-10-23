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

    return (
        <IonList>
            <IonItem>
               <IonIcon icon={list}/> My Pokemons
            </IonItem>
            <IonItem onClick={logout}>
                <IonIcon icon={logOut}/>Logout
            </IonItem>
            <IonItem>
                <IonIcon icon={mail}/>Contact
            </IonItem>
        </IonList>
    );
}
