import React from "react";
import {NotificationModel} from "../model/NotificationModel";

interface AppContextProps {
    notifications : NotificationModel[],
    stateChange :  React.Dispatch<NotificationModel[]>;
}

export const AppContext = React.createContext<AppContextProps>({} as AppContextProps);

