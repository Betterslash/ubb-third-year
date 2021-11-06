import {ReadFileResult} from "@capacitor/filesystem";

export class ImageMapper{
    public static mappToMultipartFile = (event: ReadFileResult): FormData =>{
            let formData = new FormData();
            formData.set('jsonBodyData',
                event.data);
            console.log(formData);
            return formData;
    }
}
