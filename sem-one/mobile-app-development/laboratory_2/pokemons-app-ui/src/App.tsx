import { Redirect, Route } from 'react-router-dom';
import {IonApp, IonRouterOutlet} from '@ionic/react';
import { IonReactRouter } from '@ionic/react-router';
import Home from './pages/Home';

/* Core CSS required for Ionic components to work properly */
import '@ionic/react/css/core.css';

/* Basic CSS for apps built with Ionic */
import '@ionic/react/css/normalize.css';
import '@ionic/react/css/structure.css';
import '@ionic/react/css/typography.css';

/* Optional CSS utils that can be commented out */
import '@ionic/react/css/padding.css';
import '@ionic/react/css/float-elements.css';
import '@ionic/react/css/text-alignment.css';
import '@ionic/react/css/text-transformation.css';
import '@ionic/react/css/flex-utils.css';
import '@ionic/react/css/display.css';

/* Theme variables */
import './theme/variables.css';
import {AppContext, AppProps} from "./context/AppContext";
import React from "react";
import {useNetowrk} from "./hooks/AppHooks";
import {Login} from "./pages/Login";
import {ConnectionStatus} from "@capacitor/network";
import {Logger} from "./helpers/logger/Logger";

const App: React.FC = () => {

  const autoLog = () => {
    Logger.info(JSON.stringify(appProps));
  }
  const netStatus = useNetowrk();
  const setStateToken = async (token : string) => {
    appProps.token = token;
  };
  const setStateNetworkStatus = async (status : ConnectionStatus) => {
    appProps.networkStatus = status;
  };

  let appProps : AppProps = {
    token: "someTokenTest",
    networkStatus: netStatus,
    setContextToken : setStateToken,
    setContextNetworkStatus : setStateNetworkStatus,
    log : autoLog
  };

  return (
      <IonApp>
        <IonReactRouter>
          <IonRouterOutlet>
            <AppContext.Provider value={appProps}>
              <Route exact path="/home">
                <Home />
              </Route>
              <Route exact path="/">
                <Redirect to="/home" />
              </Route>
              <Route exact path="/login">
                <Login/>
              </Route>
            </AppContext.Provider>
          </IonRouterOutlet>
        </IonReactRouter>
      </IonApp>
  );
}

export default App;
