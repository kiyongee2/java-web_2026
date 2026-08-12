//time.js

setInterval(() => {
    let date = new Date();
    let now = date.toLocaleTimeString();
    document.getElementById("display").innerHTML = now;
}, 1000)