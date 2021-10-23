import {UserModel} from "../services/AuthService";
import React, {useReducer} from "react";

export const useLogin = () : {user : UserModel, dispatcher : React.Dispatch<any>} => {
    const initialFormState = {
        username : "",
        password : ""
    } as UserModel;
    const reducer = (state : any, action : any) : UserModel=> {
        switch (action.type){
            case 'USERNAME' : {
                return {username : action.item.detail.value, password : state.password} as UserModel;
            }
            case 'PASSWORD' : {
                return {username : state.username, password : action.item.detail.value} as UserModel;
            }
            default : {
                return {
                    username : "",
                    password : ""
                } as UserModel;
            }
        }
    }
    const [formState, dispatch] = useReducer(reducer, initialFormState);
    return {user : formState, dispatcher: dispatch};
}
