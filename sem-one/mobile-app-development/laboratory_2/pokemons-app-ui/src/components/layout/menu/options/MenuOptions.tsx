import React from "react";
import {IonContent} from "@ionic/react";
import {GuestOptions} from "./GuestOptions";
import {UserOptions} from "./UserOptions";
import {useMenuOptions} from "../../../../hooks/MenuOptionsHook";

export interface MenuOptionsProps{
    isLoggedIn : boolean;
}

export const MenuOptions : React.FC = () => {

    const menuOptionsState = useMenuOptions();
    const getOptions = () => {
        if(!menuOptionsState.isLoggedIn){
            return <GuestOptions/>
        }else {
            return <UserOptions/>
        }
    }

    return(
        <IonContent>
            {getOptions()}
        </IonContent>
    );
}
