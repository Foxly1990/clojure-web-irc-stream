"use strict";

const stream = document.querySelector("#stream");

let ws = (() => {
  let loc = window.location;
  let protocol = loc.protocol;
  let protocolSocket = "";

  if (protocol === "http:") {
    protocolSocket = "ws://";
  } else {
    protocolSocket = "wss://";
  }

  let randNumber = Math.floor((Math.random() * 10000) + 1);
  let uri = `${protocolSocket}127.0.0.1:2405/stream/${randNumber}`;
  let socket = new WebSocket(uri);

  return socket;
})();

if (stream !== null) {
  ws.onmessage = function (e) {
    stream.innerHTML += e.data;
    stream.scrollTop = stream.scrollHeight;
  };
}
