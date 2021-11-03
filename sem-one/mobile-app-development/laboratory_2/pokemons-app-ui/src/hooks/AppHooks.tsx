import {useEffect, useState} from "react";
import {ConnectionStatus, Network} from "@capacitor/network";
import {UserTokenService} from "../services/UserTokenService";
import {Logger} from "../helpers/logger/Logger";
import jwtDecode from "jwt-decode";
import {LocalRepositoryService} from "../services/repository/LocalRepositoryService";
import {useIonToast} from "@ionic/react";
import {Camera, CameraResultType, CameraSource, Photo} from "@capacitor/camera";
import {UserPhoto} from "../model/UtilsModel";
import {Directory} from "@capacitor/filesystem";
import {Filesystem} from "@capacitor/filesystem";
import {Storage} from "@capacitor/storage";

const initialNetworkState : ConnectionStatus = {
    connected : true,
    connectionType : "unknown"
};

export interface AppProps{
    token : string;
    setToken : (token : string) => void;
    getToken : () => string;
    authorities : string[];
    setAuthorities : (auts : string[]) => void;
    log : () => void;
}

export const useNetowrk = () => {

    const [networkStatus, setNetworkStatus] = useState(initialNetworkState);
    const [present] = useIonToast();
    useEffect(() => {
        Network.addListener("networkStatusChange", handleNetworkStatusChange);
        Network.getStatus().then(handleNetworkStatusChange);
        let canceled = false;
        function handleNetworkStatusChange(status: ConnectionStatus) {
            if(!canceled){
                if(status.connected){
                    LocalRepositoryService.synchronize().then((results) => {
                            if(results && results.data.length > 0){
                                present('Synchronized data ...', 2000);
                                Logger.info('Synchronized data ...');
                            }
                        },
                        () => {Logger.warning("Couldn't synchronize data...");});
                }else{
                }
                setNetworkStatus(status);
            }
        }
        return () => {
            canceled = true;
            Network.removeAllListeners().then();
        };
    }, []);

    return networkStatus;
}

export const useUserState = () => {
    const getAuthorities = (token : string) : string[]  => {
        if(token !== ""){
            // @ts-ignore
            return jwtDecode(token).authorities;
        }
        return [] as string[];
    }
    const copyContext = (token : string) : AppProps => {
        return {
            token: token,
            setToken: setToken,
            getToken: getToken,
            authorities : getAuthorities(token),
            setAuthorities : setAuthorities,
            log : selfLog
        } as AppProps;
    };
    const setToken = (token : string) => {
        setAppContext(copyContext(token));
    }
    const getToken = () : string => {
        return initialContext.token;
    }
    const selfLog = () => {
        Logger.info(JSON.stringify(appContext));
    }
    const setAuthorities = (auts : string[]) => {
        const user = copyContext(appContext.token);
        user.authorities = auts;
        setAppContext(user);
    }
    const initialContext = {
        token: "",
        setToken: setToken,
        getToken: getToken,
        authorities : [] as string[],
        setAuthorities : setAuthorities,
        log : selfLog
    } as unknown as AppProps;

    const [appContext, setAppContext] = useState(initialContext);

    useEffect(() => {
        UserTokenService.getToken().then(result => {
            if(result.value !== null){
                setAppContext(copyContext(result.value));
            }
        });
    },[]);

    return appContext;
}

export const usePhotoHook = () => {
    const [photos, setPhotos] = useState<UserPhoto[]>([]);
    const fileName = new Date().getTime() + ".jpeg";
    const takePhoto = async () => {
        try{
            const cameraPhoto = await Camera.getPhoto({
                resultType: CameraResultType.Uri,
                source: CameraSource.Camera,
                quality: 100
            });
            const newPhotos = [
                {
                    filepath: fileName,
                    webviewPath: cameraPhoto.webPath,
                },
                ...photos,
            ];
            setPhotos(newPhotos);

            const savedFileImage = await savePicture(cameraPhoto, fileName);
            await Storage.set({key: 'PHOTO_STORAGE', value: JSON.stringify(newPhotos)});
            Logger.info(`Photo ${savedFileImage.webviewPath} was taken ...`);
        }catch (err){
            Logger.warning(JSON.stringify(err));
        }

    };
    return {photos, takePhoto};
}

export async function base64FromPath(path: string): Promise<string> {
    const response = await fetch(path);
    const blob = await response.blob();
    return new Promise((resolve, reject) => {
        const reader = new FileReader();
        reader.onerror = reject;
        reader.onload = () => {
            if (typeof reader.result === 'string') {
                resolve(reader.result);
            } else {
                reject('method did not return a string')
            }
        };
        reader.readAsDataURL(blob);
    });
}

const savePicture = async (photo: Photo, fileName: string): Promise<UserPhoto> => {
    const base64Data = await base64FromPath(photo.webPath!);
    const savedFile = await Filesystem.writeFile({
        path: fileName,
        data: base64Data,
        directory: Directory.Data
    });

    // Use webPath to display the new image instead of base64 since it's
    // already loaded into memory
    return {
        filepath: fileName,
        webviewPath: photo.webPath
    };
};
