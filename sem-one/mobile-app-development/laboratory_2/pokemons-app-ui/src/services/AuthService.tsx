import axios, {AxiosResponse} from 'axios';
import {Environment} from "../environment/Environment";
export interface UserModel {
    username: string;
    password: string;
    email : string;
    birthday: string;
}
export class AuthenticationService{

    public static login(user: UserModel) : Promise<AxiosResponse<string>>{
        return axios.post(`${Environment.apiUrl}auth/signin`,user);
    }

    public static register(user: UserModel) : Promise<AxiosResponse<UserModel>>{
        return axios.post(`${Environment.apiUrl}auth/register`,user);
    }
}
