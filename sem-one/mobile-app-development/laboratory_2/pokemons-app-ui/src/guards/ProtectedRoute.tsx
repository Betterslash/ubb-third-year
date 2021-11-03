import React, {FunctionComponent, useEffect, useState} from "react";
import {Route} from "react-router-dom";
import {UserTokenService} from "../services/UserTokenService";
import jwtDecode from "jwt-decode";
import {useHistory} from "react-router";
import {Logger} from "../helpers/logger/Logger";

export interface ProtectedRouteProps{
    path : string;
    ProtectedComponent : FunctionComponent<any>
}

export const ProtectedRoute : React.FC<ProtectedRouteProps> = ({path, ProtectedComponent}) => {

    const logout =  () => {(async()=>{
        await UserTokenService.deleteToken();
    })();}
    const history = useHistory();
    const checkToken = () => {
        (async()=>{
            const token = (await UserTokenService.getToken()).value;
            // @ts-ignore
            if (token === "" || token === null ||jwtDecode(token).exp < (new Date().getTime() + 1) / 1000) {
                    history.push("/");
                    logout();
            }
        })();
    }
    const [toBeRedered] = useState(<ProtectedComponent/>);
    useEffect(() => {
        try{
            checkToken();
        }catch (e){
            Logger.danger(ProtectedRoute.name + ' -> ' + logout.name);
        }
    },[]);

    return (<Route exact path={path}>
        {toBeRedered}
    </Route>);
}
