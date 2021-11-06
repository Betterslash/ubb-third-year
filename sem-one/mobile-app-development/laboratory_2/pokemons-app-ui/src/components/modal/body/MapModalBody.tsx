//AIzaSyDVFiw5J3yGtMmDH0qkid_d0cvd6pcl1AI
import React, {useEffect, useState} from 'react';
import {
    IonButton,
    IonButtons,
    IonCard,
    IonCardContent,
    IonContent,
    IonIcon,
    IonTitle,
    IonToolbar
} from "@ionic/react";
import {closeCircle, location} from "ionicons/icons";
import {GoogleMap, Marker, useJsApiLoader} from "@react-google-maps/api";
import {useMyLocation} from "../../../hooks/AppHooks";


const containerStyle = {
    width: '87.7vw',
    height: '75vh'
};

export interface MyMapProps {
    lat: number;
    lng: number;
    onMapClick: (e: any) => void,
    onMarkerClick: (e: any) => void,
    onDismiss: () => void
}


export const MapModalBody : React.FC<{
    onDismiss: () => void;
    updateMapProps: (prps: MyMapProps) => void; }>
    = ({ onDismiss, updateMapProps }) => {
    const [mapProps, setMapProps] = useState({} as MyMapProps);
    const mapHook = useMyLocation();
    const { isLoaded } = useJsApiLoader({
        id: 'google-map-script',
        googleMapsApiKey: "AIzaSyDVFiw5J3yGtMmDH0qkid_d0cvd6pcl1AI"
    });
    useEffect(() => {
        let newProps = mapProps;
        if(mapHook.position){
            newProps.lat = mapHook.position?.coords.latitude;
            newProps.lng = mapHook.position?.coords.longitude;
        }else{
            newProps.lat = 1;
            newProps.lng = 1;
        }
        setMapProps(newProps);
    }, [mapHook,]);

    return  isLoaded ? (<IonContent>
            <IonToolbar>
                    <IonTitle>
                        Select Location
                    </IonTitle>
                <IonButtons slot={"start"}>
                    <IonButton onClick={onDismiss}>
                        <IonIcon icon={closeCircle}/>
                    </IonButton>
                </IonButtons>
            </IonToolbar>
            <IonCard>
                <IonCardContent>
                        {mapHook.position &&
                        <GoogleMap
                            mapContainerStyle={containerStyle}
                            center={{lat: mapHook.position?.coords.latitude, lng:mapHook.position?.coords.longitude}}
                            zoom={9}>
                            <Marker
                                position={{ lat: mapProps.lat, lng: mapProps.lng}}
                                draggable={true}
                                onDrag={e => {
                                    let newMapProps = mapProps as MyMapProps;
                                    if(e.latLng){
                                        newMapProps.lat = e.latLng.lat();
                                        newMapProps.lng = e.latLng.lng();
                                    }
                                    setMapProps(newMapProps);
                                    updateMapProps(newMapProps);
                                }}
                            />
                        </GoogleMap>}
                    <br/>
                    <IonButton expand={"block"} onClick={onDismiss}>
                        Select this position <IonIcon icon={location}/>
                    </IonButton>
                </IonCardContent>
            </IonCard>

        </IonContent>) : <></>;
}
