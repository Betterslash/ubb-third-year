import {UserTokenService} from "./UserTokenService";
import axios from "axios";
import {Environment} from "../environment/Environment";
import {Logger} from "../helpers/logger/Logger";

export class ImageService{
    public static uploadImage = async(formData : FormData) => {
        const token = await UserTokenService.getToken();
        const headerValue = 'Bearer : ' + token.value;
        return axios.post<string>(`${Environment.imageUploadUrl}`, formData, {headers: {'Authorization': headerValue}});
    }

    public static displayImageFromServer = async (id : string) => {
        const token = await UserTokenService.getToken();
        const headerValue = 'Bearer : ' + token.value;
        return axios.get<Blob>(`${Environment.imageUploadUrl}/${id}`, {headers: {'Authorization': headerValue}});
    }

    public static createImageFromBlob = (image: Blob) => {
        let reader = new FileReader();
        if(image){
            reader.readAsArrayBuffer(image);
            return reader.result as string;
        }else{
            Logger.warning(ImageService.name + ' -> ' + this.createImageFromBlob.name + 'not found!');
            return '';
        }

    }
}
