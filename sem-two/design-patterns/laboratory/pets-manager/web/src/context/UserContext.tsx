import {createContext, Dispatch, SetStateAction} from "react";
import {UserModel} from "../core/model/UserModel";
import {ContextService} from "../infrastructure/services/ContextService";

export interface UserContextProps {
    currentUser: UserModel,
    changeUserState: Dispatch<SetStateAction<UserModel>>,
    loggedIn: boolean,
    setLoggedInState: Dispatch<SetStateAction<boolean>>,
}

export const UserContext =
    createContext<UserContextProps>
    ({
        currentUser: {} as UserModel,
        changeUserState: () => {},
        loggedIn: ContextService.isAuthenticated(),
        setLoggedInState: () => {}
    });