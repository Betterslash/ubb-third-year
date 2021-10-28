import {GetResult, Storage} from "@capacitor/storage";
import jwtDecode from "jwt-decode";

export class UserTokenService {
    public static async setToken(token: string): Promise<void> {
        await Storage.set({
            key: 'token',
            value: token,
        });
    }
    public static async deleteToken() : Promise<void>{
        await Storage.remove({key : 'token'});
    }
    public static async getToken() : Promise<GetResult>{
        return await Storage.get({key : 'token'});
    }
    public static getAuthoritiesFromToken(token : string){
        //@ts-ignore
        const authorities : string[] = jwtDecode(token).authorities;
        return authorities;
    }
}
