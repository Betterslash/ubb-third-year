import {
    IonPage
} from '@ionic/react';
import './Home.css';
import {Header} from "../components/layout/Header";
import React from "react";
import {Footer} from "../components/layout/Footer";
import {Pokedex} from "../components/widgets/Pokedex";
import {useUserState} from "../hooks/AppHooks";
import {GuestHome} from "../components/widgets/GuestHome";

const Home: React.FC = () => {
    const userContext = useUserState();
    const loggedIn = userContext.token !== "";
    const getHomeItems = () => {
        if(!loggedIn){
            return <GuestHome/>
        }else{
            return <Pokedex/>
        }
    }
    return (
    <IonPage>
        <Header/>
        {getHomeItems()}
        <Footer/>
    </IonPage>
  );
};

export default Home;
