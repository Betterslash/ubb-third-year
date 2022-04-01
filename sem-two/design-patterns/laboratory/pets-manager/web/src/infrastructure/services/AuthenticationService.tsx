import axios, {AxiosResponse} from "axios";
import {Environment} from "../../core/environment/Environment";
import {UserModel} from "../../core/model/UserModel";
import {BaseResponse} from "../../core/dto/BaseResponse";
import {ContextService} from "./ContextService";

export class AuthenticationService{

    private static ENDPOINT = 'account';

    public static authenticate = (userModel: UserModel):Promise<AxiosResponse<BaseResponse<boolean>>> => {
        return axios.post<BaseResponse<boolean>>(`${Environment.apiUrl}${AuthenticationService.ENDPOINT}/login`, userModel);
    }

    public static logout = (): void => {
        return ContextService.logout();
    }
}