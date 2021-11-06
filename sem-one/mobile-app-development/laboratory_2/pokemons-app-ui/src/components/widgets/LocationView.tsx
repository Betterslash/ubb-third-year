import React from "react";
import {LocationModel} from "../../model/LocationModel";
import {GoogleMap, Marker, useJsApiLoader} from "@react-google-maps/api";

export interface LocationViewProps{
    location : LocationModel;
}
const containerStyle = {
    width: '87.7vw',
    height: '25vh'
};
export const LocationView : React.FC<LocationViewProps> = ({location}) => {
    const { isLoaded } = useJsApiLoader({
        id: 'google-map-script',
        googleMapsApiKey: "AIzaSyDVFiw5J3yGtMmDH0qkid_d0cvd6pcl1AI"
    });
    return isLoaded ? (
        <GoogleMap
            mapContainerStyle={containerStyle}
            center={{lat: location.latitude, lng:location.longitude}}
            zoom={7}>
            <Marker position={{lat: location.latitude, lng: location.longitude}}/>
        </GoogleMap>
    ) : <></>;
}
