import React, {useState} from "react";
import {IonContent} from "@ionic/react";
import {GuestOptions} from "./GuestOptions";
import {UserOptions} from "./UserOptions";

export interface MenuOptionsProps{
    isLoggedIn : boolean;
}

export const MenuOptions : React.FC = () => {
    const initialState = {isLoggedIn : false} as MenuOptionsProps;
    const getOptions = () => {
        if(!menuOptionsState.isLoggedIn){
            return <GuestOptions/>
        }else {
            return <UserOptions/>
        }
    }
    const [menuOptionsState, setMenuOptionsState] = useState(initialState);


    return(
        <IonContent>
            {getOptions()}
        </IonContent>
    );
}
