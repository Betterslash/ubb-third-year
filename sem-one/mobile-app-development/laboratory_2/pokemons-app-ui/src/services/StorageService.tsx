import {Storage} from "@capacitor/storage";

export class StorageService {
    public static async setToken(token: string): Promise<void> {
        await Storage.set({
            key: 'token',
            value: token,
        });
    }
}
