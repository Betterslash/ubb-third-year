import {GetResult, Storage} from "@capacitor/storage";

export class StorageService {
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
}
