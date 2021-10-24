import React, {FunctionComponent, useEffect, useState} from "react";
import {Redirect, Route} from "react-router-dom";
import {StorageService} from "../services/StorageService";

export interface ProtectedRouteProps{
    path : string;
    ProtectedComponent : FunctionComponent<any>
}

export const ProtectedRoute : React.FC<ProtectedRouteProps> = ({path, ProtectedComponent}) => {
    const [isAuthenticated, setAuthenticated] = useState(false);
    useEffect(() => {
        StorageService.getToken()
            .then(result => {
                const value = result.value !== "";
                setAuthenticated(value);
            })
    }, []);
    return (<Route exact path={path} render={() => isAuthenticated ? <ProtectedComponent/> : <Redirect to="/"/>}/>);
}
