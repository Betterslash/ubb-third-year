import {
    IonContent, IonPage
} from '@ionic/react';
import './Home.css';
import {Header} from "../components/layout/Header";
import React from "react";
import {Footer} from "../components/layout/Footer";

const Home: React.FC = () => {

  return (
    <IonPage>
        <Header/>
            <IonContent/>
        <Footer/>
    </IonPage>
  );
};

export default Home;
