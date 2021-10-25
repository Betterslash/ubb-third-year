import React, {FunctionComponent, useEffect, useState} from "react";
import {Redirect, Route} from "react-router-dom";
import {StorageService} from "../services/StorageService";

export interface ProtectedRouteProps{
    path : string;
    ProtectedComponent : FunctionComponent<any>
}

export const ProtectedRoute : React.FC<ProtectedRouteProps> = ({path, ProtectedComponent}) => {

    const [toBeRedered, setRendering] = useState(<Redirect to={"/"}/>);
    useEffect(() => {
        StorageService.getToken()
            .then(result => {
                if(result.value !== ""){
                    setRendering(<ProtectedComponent/>);
                }
            });
    },[]);

    return (<Route exact path={path}>
        {toBeRedered}
    </Route>);
}
