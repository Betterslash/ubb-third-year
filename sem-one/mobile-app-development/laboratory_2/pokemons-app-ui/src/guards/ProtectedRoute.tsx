import React, {FunctionComponent, useEffect, useState} from "react";
import {Redirect, Route} from "react-router-dom";
import {StorageService} from "../services/StorageService";
import jwtDecode from "jwt-decode";

export interface ProtectedRouteProps{
    path : string;
    ProtectedComponent : FunctionComponent<any>
}

export const ProtectedRoute : React.FC<ProtectedRouteProps> = ({path, ProtectedComponent}) => {

    const logout =  () => {(async()=>{
        await StorageService.deleteToken();
    })();}
    const checkToken = () => {
        (async()=>{
            const token = (await StorageService.getToken()).value;
            // @ts-ignore
            if (!token || token==="" ||jwtDecode(token).exp < (new Date().getTime() + 1) / 1000) {
                setRendering(<Redirect to="/"/>);
                logout();
            }else{
                setRendering(<ProtectedComponent/>);
            }
        })();
    }
    const [toBeRedered, setRendering] = useState(<ProtectedComponent/>);
    useEffect(() => {
        checkToken();
    },[]);

    return (<Route exact path={path}>
        {toBeRedered}
    </Route>);
}
