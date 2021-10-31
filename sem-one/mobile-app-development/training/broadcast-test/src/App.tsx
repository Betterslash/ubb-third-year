import React, {useEffect} from 'react';
import logo from './logo.svg';
import './App.css';
import SockJS from "sockjs-client";
import { Stomp} from "@stomp/stompjs";

const getMessage = () =>
{
  let socket = new SockJS("http://localhost:8080/ws");
  let stompClient = Stomp.over(socket);
  stompClient.connect({}, () => {
    stompClient.subscribe("/notify", (e : any) => {
      const message : any = JSON.parse(e.body);
      alert(message.content);
    })})
}
function App() {
  useEffect(getMessage, []);
  return (

    <div className="App">
      <header className="App-header">
        <img src={logo} className="App-logo" alt="logo" />
        <p>
          Edit <code>src/App.tsx</code> and save to reload.
        </p>
        <a
          className="App-link"
          href="https://reactjs.org"
          target="_blank"
          rel="noopener noreferrer"
        >
          Learn React
        </a>
      </header>
    </div>
  );
}

export default App;
