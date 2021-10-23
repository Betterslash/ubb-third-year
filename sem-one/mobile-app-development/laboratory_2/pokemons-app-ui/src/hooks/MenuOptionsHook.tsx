import {useEffect, useState} from "react";
import {MenuOptionsProps} from "../components/layout/menu/options/MenuOptions";
import {useUserState} from "./AppHooks";

export const useMenuOptions = () => {
    const context = useUserState();
    const initialState = {isLoggedIn: false} as MenuOptionsProps;
    const [menuOptionsState, setMenuOptionsState] = useState(initialState);
    const initializeMenu = () => {
            if (context.token !== "") {
                setMenuOptionsState({isLoggedIn: true});
            } else {
                setMenuOptionsState({isLoggedIn: false});
            }
    }
    useEffect(initializeMenu, [context]);
    return menuOptionsState;
}
