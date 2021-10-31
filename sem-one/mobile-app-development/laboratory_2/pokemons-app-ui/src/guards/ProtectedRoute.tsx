import React, {FunctionComponent, useEffect, useState} from "react";
import {Redirect, Route} from "react-router-dom";
import {UserTokenService} from "../services/UserTokenService";
import jwtDecode from "jwt-decode";

export interface ProtectedRouteProps{
    path : string;
    ProtectedComponent : FunctionComponent<any>
}

export const ProtectedRoute : React.FC<ProtectedRouteProps> = ({path, ProtectedComponent}) => {

    const logout =  () => {(async()=>{
        await UserTokenService.deleteToken();
    })();}
    const checkToken = () => {
        (async()=>{
            const token = (await UserTokenService.getToken()).value;
            // @ts-ignore
            if (jwtDecode(token).exp < (new Date().getTime() + 1) / 1000) {
                setRendering(<Redirect to="/"/>);
                logout();
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
